package com.projextxy.mech.multiblock

import codechicken.lib.vec.{BlockCoord, CuboidCoord}
import net.minecraft.world.{ChunkCoordIntPair, World}
import net.minecraftforge.common.util.ForgeDirection

object MultiTankInitiator extends MultiBlockInitiator[MultiTank] {
  val matcher = new MultiBlockMatcher {
    override def matches(world: World, pos: BlockCoord): Boolean = {
      val block = world.getBlock(pos.x, pos.y, pos.z)
      block.isInstanceOf[BlockMulti] || MultiShadowCategoryMatcher.ANY_BUT_AIR.matches(world, pos)
    }
  }
  var bounds: CuboidCoord = new CuboidCoord()
  var pattern: MultiBlockPattern = null

  override def create(world: World, tileCoords: BlockCoord): Option[MultiTank] = {
    val tile = world.getTileEntity(tileCoords.x, tileCoords.y, tileCoords.z)
    if (!tile.isInstanceOf[TileMultiBlock])
      None
    for (side <- ForgeDirection.VALID_DIRECTIONS) {
      if (checkTank(world, side, tileCoords)) {
        val multiBlock = new MultiTank(world, new ChunkCoordIntPair(tileCoords.x >> 4, tileCoords.z >> 4), bounds.copy())
        pattern.addAndCheckPattern(multiBlock, bounds.min.copy().sub(1, 1, 1))
        multiBlock.getWorldExt.addMultiBlock(multiBlock)
      }
    }
    None
  }

  /**
   * You have to validate each side, because a valve can be put on any face of the block.
   * @return
   */
  def checkTank(world: World, side: ForgeDirection, tileCoords: BlockCoord): Boolean = {
    computeBoundsForSide(world, tileCoords, side)
    checkPattern(world: World)
  }

  def checkPattern(world: World): Boolean = {
    pattern = new MultiBlockPattern(bounds.max.x - bounds.min.x + 3, bounds.max.y - bounds.min.y + 3, bounds.max.z - bounds.min.z + 3)
    pattern.addMatcher(matcher)
    pattern.addMatcher(MultiShadowCategoryMatcher.ANY_OPAQUE_EXCEPT_VALVE)
    //Fill inner part with -1 being air or ignored
    pattern.fill(1, 1, 1, pattern.width - 2, pattern.height - 2, pattern.depth - 2, -1)
    pattern.fillFaces(0, 0, 0, pattern.width - 1, pattern.height - 1, pattern.depth - 1, 0)
    pattern.fillEdgesAndCorners(0, 0, 0, pattern.width - 1, pattern.height - 1, pattern.depth - 1, 1)
    pattern.worldDemonstratesPattern(world, bounds.min.copy().sub(1, 1, 1))
  }

  def computeBoundsForSide(world: World, tileCoords: BlockCoord, side: ForgeDirection): Unit = {
    val sideId = side.ordinal()
    bounds.min.set(tileCoords).offset(sideId)
    bounds.max.set(bounds.min)

    for (s <- 0 until 6) {
      while (bounds.size(s) <= 9 && validateInnerBounds(world, ForgeDirection.getOrientation(s)))
        bounds.expand(s, 1)
    }
  }

  def validateInnerBounds(world: World, side: ForgeDirection): Boolean = {
    val shrunk = MultiBlockPattern.shrink(Array(bounds.min.y, bounds.max.y, bounds.min.z, bounds.max.z, bounds.min.x, bounds.max.x), side)
    for (x <- shrunk(4) to shrunk(5))
      for (y <- shrunk(0) to shrunk(1))
        for (z <- shrunk(2) to shrunk(3)) {
          val block = world.getBlock(x, y, z)
          if (!world.isAirBlock(x, y, z))
            return false
        }
    true
  }
}
