package com.projextxy.core.blocks.glow

import net.minecraft.world.IBlockAccess

trait ColorMultiplier extends BlockXyGlow {
  override def colorMultiplier(world: IBlockAccess, x: Int, y: Int, z: Int): Int = getColor(world, x, y, z)
}
