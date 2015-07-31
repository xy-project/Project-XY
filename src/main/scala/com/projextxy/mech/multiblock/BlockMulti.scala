package com.projextxy.mech.multiblock

import com.projextxy.core.blocks.BlockXy
import net.minecraft.block.ITileEntityProvider
import net.minecraft.block.material.Material
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.world.{IBlockAccess, World}

abstract class BlockMulti(mat: Material) extends BlockXy(mat) with ITileEntityProvider {
  override def onBlockActivated(world: World, x: Int, y: Int, z: Int, player: EntityPlayer, par6: Int, par7: Float, par8: Float, par9: Float): Boolean = {
    if (player.worldObj.isRemote)
      return true
    world.getTileEntity(x, y, z) match {
      case tileMultiBlock: TileMultiBlock => tileMultiBlock.onBlockActivated(player)
      case _ => true
    }
  }

  override def onBlockClicked(world: World, x: Int, y: Int, z: Int, player: EntityPlayer): Unit = {
    if (player.worldObj.isRemote)
      return
    world.getTileEntity(x, y, z) match {
      case tileMultiBlock: TileMultiBlock => tileMultiBlock.onBlockClicked(player)
      case _ =>
    }
  }

  override def getLightValue(world: IBlockAccess, x: Int, y: Int, z: Int): Int = {
    world.getTileEntity(x, y, z) match {
      case tileMultiBlock: TileMultiBlock => tileMultiBlock.getLightValue()
      case _ => 0
    }
  }
}
