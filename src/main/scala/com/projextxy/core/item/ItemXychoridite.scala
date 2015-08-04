package com.projextxy.core.item

import com.projextxy.core.ProjectXYCore
import com.projextxy.core.reference.{MCColors, ModColors}
import net.minecraft.item.ItemStack

class ItemXychoridite extends ItemXy with TColoredItem {
  setUnlocalizedName("itemXychoridite")
  setTextureName("itemXychoridite")
  setCreativeTab(ProjectXYCore.tabItem)
  setHasSubtypes(true)

  override def colors: List[MCColors] = ModColors.xyColors

  override def getUnlocalizedName(stack: ItemStack): String = s"${super.getUnlocalizedName(stack)}|${stack.getItemDamage}"
}
