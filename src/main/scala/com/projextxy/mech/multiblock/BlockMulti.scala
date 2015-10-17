package com.projextxy.mech.multiblock

import com.projextxy.core.blocks.BlockXy
import net.minecraft.block.ITileEntityProvider
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.world.World

trait BlockMulti extends BlockXy with ITileEntityProvider {

  override def onBlockActivated(world: World, x: Int, y: Int, z: Int, player: EntityPlayer, par6: Int, par7: Float, par8: Float, par9: Float): Boolean = {
    if (world.isRemote)
      return true
    world.getTileEntity(x, y, z) match {
      case tileMultiBlock: TileMultiBlock => tileMultiBlock.onBlockActivated(player)
      case _ => true
    }
  }
}