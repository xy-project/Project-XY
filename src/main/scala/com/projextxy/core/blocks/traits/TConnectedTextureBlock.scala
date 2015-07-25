package com.projextxy.core.blocks.traits

import com.projextxy.core.blocks.BlockXy
import net.minecraft.block.Block
import net.minecraft.world.IBlockAccess

trait TConnectedTextureBlock extends Block {
  def connectedFolder: String
}
