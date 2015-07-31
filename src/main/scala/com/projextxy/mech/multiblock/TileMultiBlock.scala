package com.projextxy.mech.multiblock

import net.minecraft.entity.player.EntityPlayer
import net.minecraft.tileentity.TileEntity

abstract class TileMultiBlock extends TileEntity {
  def onBlockActivated(player: EntityPlayer): Boolean

  def onBlockClicked(player: EntityPlayer)

  def getLightValue(): Int
}
