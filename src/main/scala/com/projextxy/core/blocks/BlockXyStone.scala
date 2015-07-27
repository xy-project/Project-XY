package com.projextxy.core.blocks

import com.projextxy.core.ProjectXYCore
import net.minecraft.block.material.Material
import net.minecraft.client.renderer.texture.IIconRegister

class BlockXyStone extends BlockXy(Material.rock) {
  setBlockName("blockXyStone")

  override def registerBlockIcons(reg: IIconRegister): Unit = {
    this.blockIcon = reg.registerIcon(s"${ProjectXYCore.MOD_ID}:blockXyStone")
  }
}
