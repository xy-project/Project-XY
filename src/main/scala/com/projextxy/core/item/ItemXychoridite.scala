package com.projextxy.core.item

import com.projextxy.core.ProjectXYCore
import com.projextxy.core.reference.{MCColors, ModColors, EnumStained}

class ItemXychoridite extends ItemXy with TColoredItem {
  setUnlocalizedName("itemXychoridite")
  setTextureName("itemXychoridite")
  setCreativeTab(ProjectXYCore.tabItem)
  setHasSubtypes(true)

  override def colors: List[MCColors] = ModColors.xyColors
}
