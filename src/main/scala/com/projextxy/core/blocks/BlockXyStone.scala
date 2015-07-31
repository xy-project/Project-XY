package com.projextxy.core.blocks

import java.util

import com.projextxy.core.ProjectXYCore
import net.minecraft.block.material.Material
import net.minecraft.client.renderer.texture.IIconRegister
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.item.{Item, ItemStack}
import net.minecraft.util.IIcon

class BlockXyStone extends BlockXy(Material.rock) {
  val icons = Array.fill[IIcon](3)(null)
  setBlockName("blockXyStone")

  override def registerBlockIcons(reg: IIconRegister): Unit = {
    icons(0) = reg.registerIcon(s"${ProjectXYCore.MOD_ID}:blockXyStone")
    icons(1) = reg.registerIcon(s"${ProjectXYCore.MOD_ID}:blockXyStoneBrick")
    icons(2) = reg.registerIcon(s"${ProjectXYCore.MOD_ID}:blockXyStoneBrickFancy")
  }


  override def getSubBlocks(item: Item, tab: CreativeTabs, _list: util.List[_]): Unit = {
    val list = _list.asInstanceOf[util.List[ItemStack]]

    for (x <- icons.indices)
      list.add(new ItemStack(this, 1, x))
  }

  override def getIcon(side: Int, meta: Int): IIcon = icons(meta)

  override def damageDropped(meta: Int): Int = meta
}
