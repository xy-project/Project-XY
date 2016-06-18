package com.projextxy.core.blocks

import java.util

import com.projextxy.core.ProjectXYCore
import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.item.ItemStack

class BlockXy(mat: Material) extends Block(mat) {
  setCreativeTab(ProjectXYCore.tabBlock)
  setHardness(1.0F)

  override def getUnlocalizedName: String = s"tile.${unwrappedLocalizedName(super.getUnlocalizedName)}"

  def unwrappedLocalizedName(unlocalizedName: String): String = unlocalizedName.substring(unlocalizedName.indexOf(".") + 1)

  override def setBlockName(name: String): Block = {
    textureName = name
    super.setBlockName(name)
  }
}

object BlockXy {
  implicit def asItemStackList(list: util.List[_]): util.List[ItemStack] = list.asInstanceOf[util.List[ItemStack]]
}
