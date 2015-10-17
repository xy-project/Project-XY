package com.projextxy.core.blocks.traits

import com.projextxy.core.blocks.BlockXy
import net.minecraft.world.IBlockAccess

trait TColorBlock extends BlockXy {
  val hasColorMultiplier: Boolean

  def getColor(iBlockAccess: IBlockAccess, x: Int, y: Int, z: Int): Int

  def getColor(meta: Int): Int

  override def colorMultiplier(world: IBlockAccess, x: Int, y: Int, z: Int): Int = if (hasColorMultiplier)
    getColor(world, x, y, z)
  else
    super.colorMultiplier(world, x, y, z)

}
