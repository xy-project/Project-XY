package com.projextxy.core.blocks

import com.projextxy.core.ProjectXYCore
import net.minecraft.block.Block
import net.minecraft.block.material.Material

class BlockXy(mat: Material) extends Block(mat) {
  setCreativeTab(ProjectXYCore.tabBlock)

  override def getUnlocalizedName: String = s"tile.${unwrappedLocalizedName(super.getUnlocalizedName)}"

  def unwrappedLocalizedName(unlocalizedName: String): String = unlocalizedName.substring(unlocalizedName.indexOf(".") + 1)

  override def setBlockName(name: String): Block = {
    textureName = name
    super.setBlockName(name)
  }
}
