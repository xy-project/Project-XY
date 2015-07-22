package com.projextxy.core.item

import java.util

import com.projextxy.core.reference.MCColors
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.item.{Item, ItemStack}

trait TColoredItem extends Item {
  def colors: List[MCColors]

  override def getColorFromItemStack(stack: ItemStack, pass: Int): Int = colors(stack.getItemDamage).rgb

  override def getSubItems(item: Item, tabs: CreativeTabs, _list: util.List[_]) = {
    val list = _list.asInstanceOf[util.List[ItemStack]]
    for (stainedIndex <- colors.indices) {
      list.add(new ItemStack(this, 1, stainedIndex))
    }
  }
}
