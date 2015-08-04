package com.projextxy.core.item

import com.projextxy.core.ProjectXYCore
import com.projextxy.core.reference.{MCColors, ModColors}
import net.minecraft.client.renderer.texture.IIconRegister
import net.minecraft.item.ItemStack

class ItemXychorium extends ItemXy with TColoredItem {
  setUnlocalizedName("itemXychorium")
  setCreativeTab(ProjectXYCore.tabItem)
  setHasSubtypes(true)

  override def colors: List[MCColors] = ModColors.xyColors

  override def registerIcons(iconRegister: IIconRegister): Unit = {
    /*NOPE*/
  }

  override def getUnlocalizedName(stack: ItemStack): String = s"${super.getUnlocalizedName(stack)}|${stack.getItemDamage}"
}
