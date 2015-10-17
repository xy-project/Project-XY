package com.projextxy.mech.multiblock

import com.projextxy.mech.MechBlocks
import net.minecraft.block.ITileEntityProvider
import net.minecraft.world.World

object MultiBlockManager {
  def convertBlockToShadow(world: World, x: Int, y: Int, z: Int): Option[TileMultiBlock] = {
    val block = world.getBlock(x, y, z)
    val meta = world.getBlockMetadata(x, y, z)

    if (block.isInstanceOf[ITileEntityProvider] || block.hasTileEntity(meta))
      return None

    MultiCategoryMatcher.getCategoryForBlock(block) match {
      case MultiShadowCategory.ROCK => world.setBlock(x, y, z, MechBlocks.blockShadowRock, meta, 2)
      case MultiShadowCategory.WOOD => world.setBlock(x, y, z, MechBlocks.blockShadowWood, meta, 2)
      case MultiShadowCategory.GLASS => world.setBlock(x, y, z, MechBlocks.blockShadowGlass, meta, 2)
      case MultiShadowCategory.GRASS => world.setBlock(x, y, z, MechBlocks.blockShadowGrass, meta, 2)
      case MultiShadowCategory.AIR => world.setBlock(x, y, z, MechBlocks.blockShadowAir, meta, 2)
      case _ => world.setBlock(x, y, z, MechBlocks.blockShadowRock, meta, 2)
    }

    val tile = world.getTileEntity(x, y, z).asInstanceOf[TileMultiShadow]
    tile.setCurrentBlock(block, meta)
    Some(tile)
  }

  def createMultiBlock(multiBlockId: MultiBlockId, worldEx: XYWE, chunkEx: XYCE): MultiBlock = {
    multiBlockId match {
      case MultiBlockId.TANK => new MultiTank(worldEx, chunkEx)
    }
  }
}
