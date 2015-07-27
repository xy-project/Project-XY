package com.projextxy.core.blocks.traits

import com.projextxy.core.blocks.BlockXy
import net.minecraft.world.IBlockAccess

trait ColorMultiplier extends BlockXy {
  def getColor(iBlockAccess: IBlockAccess, x: Int, y: Int, z: Int): Int

  def getColor(meta: Int): Int

  override def colorMultiplier(world: IBlockAccess, x: Int, y: Int, z: Int): Int = getColor(world, x, y, z)

  override def getRenderColor(meta: Int): Int = getColor(meta)
}
