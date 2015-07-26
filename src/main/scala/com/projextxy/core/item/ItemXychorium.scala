package com.projextxy.core.item

import com.projextxy.core.ProjectXYCore
import com.projextxy.core.reference.{MCColors, ModColors}
import net.minecraft.client.renderer.texture.IIconRegister

class ItemXychorium extends ItemXy with TColoredItem {
  setUnlocalizedName("itemxychorium")
  setCreativeTab(ProjectXYCore.tabItem)
  setHasSubtypes(true)

  override def colors: List[MCColors] = ModColors.xyColors

  override def registerIcons(iconRegister: IIconRegister): Unit = {
    /*NOPE*/
  }
}
