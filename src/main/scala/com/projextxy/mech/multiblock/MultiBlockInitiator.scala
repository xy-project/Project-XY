package com.projextxy.mech.multiblock

import codechicken.lib.vec.BlockCoord
import net.minecraft.world.World

abstract class MultiBlockInitiator[T] {
  def create(world: World, tileCoords: BlockCoord): Option[T]
}
