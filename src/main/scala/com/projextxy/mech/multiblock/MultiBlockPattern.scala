package com.projextxy.mech.multiblock

import codechicken.lib.vec.BlockCoord
import com.projextxy.mech.multiblock.MultiBlockPattern._
import net.minecraft.world.World
import net.minecraftforge.common.util.ForgeDirection

import scala.collection.mutable.ArrayBuffer

case class MultiBlockPattern(width: Int, height: Int, depth: Int) {
  val pattern = Array.ofDim[Int](width, height, depth)
  val matchers = new ArrayBuffer[MultiBlockMatcher]()

  def fill(minx: Int, miny: Int, minz: Int, maxx: Int, maxy: Int, maxz: Int, matcherIndex: Int): Unit = {
    for (xIndex <- minx to maxx)
      for (yIndex <- miny to maxy)
        for (zIndex <- minz to maxz)
          pattern(xIndex)(yIndex)(zIndex) = matcherIndex
  }

  def fillFaces(minx: Int, miny: Int, minz: Int, maxx: Int, maxy: Int, maxz: Int, matcherIndex: Int): Unit = {
    for (side <- ForgeDirection.VALID_DIRECTIONS) {
      val shrunk = shrink(shift(minx, miny, minz, maxx, maxy, minz, 1), side)
      for (x <- shrunk(0) to shrunk(1))
        for (y <- shrunk(2) to shrunk(3))
          for (z <- shrunk(4) to shrunk(5))
            pattern(x)(y)(z) = matcherIndex
    }
  }

  /**
   * Fills the edges excluding corners.
   * @param matcherIndex - can be -1 to be ignored, ex air.
   */
  def fillEdges(minx: Int, miny: Int, minz: Int, maxx: Int, maxy: Int, maxz: Int, matcherIndex: Int): Unit = {
    val shiftedBound = shift(minx, miny, minz, maxx, maxy, maxz, 1)
    for (x <- shiftedBound(0) to shiftedBound(1)) {
      pattern(x)(miny)(minz) = matcherIndex
      pattern(x)(miny)(maxy) = matcherIndex
      pattern(x)(maxy)(minz) = matcherIndex
      pattern(x)(maxy)(maxz) = matcherIndex
    }
    for (y <- shiftedBound(2) to shiftedBound(3)) {
      pattern(minx)(y)(minz) = matcherIndex
      pattern(minx)(y)(maxy) = matcherIndex
      pattern(maxx)(y)(minz) = matcherIndex
      pattern(maxx)(y)(maxz) = matcherIndex
    }
    for (z <- shiftedBound(4) to shiftedBound(5)) {
      pattern(minx)(miny)(z) = matcherIndex
      pattern(minx)(miny)(z) = matcherIndex
      pattern(maxx)(maxy)(z) = matcherIndex
      pattern(maxx)(maxy)(z) = matcherIndex
    }
  }

  /**
   * Fills the edges with corners.
   * @param matcherIndex - can be -1 to be ignored, ex air.
   */
  //Same as filledges but filles corners
  def fillEdgesAndCorners(minx: Int, miny: Int, minz: Int, maxx: Int, maxy: Int, maxz: Int, matcherIndex: Int): Unit = {
    val shifted = shift(minx, miny, minz, maxx, maxy, maxz, 1)
    for (x <- shifted(0) to shifted(1)) {
      pattern(x)(miny)(minz) = matcherIndex
      pattern(x)(miny)(maxz) = matcherIndex
      pattern(x)(maxy)(minz) = matcherIndex
      pattern(x)(maxy)(maxz) = matcherIndex
    }
    //Undo the offset of y to fill corners
    for (y <- shifted(2) - 1 to shifted(3) + 1) {
      pattern(minx)(y)(minz) = matcherIndex
      pattern(minx)(y)(maxz) = matcherIndex
      pattern(maxx)(y)(minz) = matcherIndex
      pattern(maxx)(y)(maxz) = matcherIndex
    }
    for (z <- shifted(4) to shifted(5)) {
      pattern(minx)(miny)(z) = matcherIndex
      pattern(minx)(maxy)(z) = matcherIndex
      pattern(maxx)(miny)(z) = matcherIndex
      pattern(maxx)(maxy)(z) = matcherIndex
    }
  }

  /**
   * Adds a matcher to the index.
   */
  def addMatcher(matcher: MultiBlockMatcher) = matchers += matcher

  def addAndCheckPattern(multiBlock: MultiBlock, pos: BlockCoord): Unit = {
    for (x <- 0 until width; y <- 0 until height; z <- 0 until depth) {
      val blockPos = pos.copy().add(x, y, z)
      val tile = multiBlock.world.getTileEntity(blockPos.x, blockPos.y, blockPos.z)
      if (!tile.isInstanceOf[TileMultiBlock])
        MultiBlockManager.convertBlockToShadow(multiBlock.world, blockPos.x, blockPos.y, blockPos.z)
      multiBlock.addTile(blockPos)
    }
  }

  def worldDemonstratesPattern(world: World, pos: BlockCoord): Boolean = {
    for (x <- 0 until width; y <- 0 until height; z <- 0 until depth) {
      val matcherId = pattern(x)(y)(z)
      if (matcherId >= 0 && !matchers(matcherId).matches(world, pos.copy().add(x, y, z))) {
        return false
      } else if(matcherId == -1){
        val offsetPos = pos.copy().add(x, y, z)
        if(!world.isAirBlock(offsetPos.x, offsetPos.y, offsetPos.z))
          return false
      }
    }
    true
  }
}

object MultiBlockPattern {
  def shrink(minxyzmaxxyz: Array[Int], side: ForgeDirection): Array[Int] = {
    if (side.ordinal() % 2 == 0)
      minxyzmaxxyz(side.ordinal()) -= 1
    else
      minxyzmaxxyz(side.ordinal()) += 1
    minxyzmaxxyz
  }

  def shift(minx: Int, miny: Int, minz: Int, maxx: Int, maxy: Int, maxz: Int, amt: Int): Array[Int] = Array(minx + amt, minx - amt, miny + amt, maxy - amt, minz + amt, maxz - amt)
}



