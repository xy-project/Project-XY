package com.projextxy.core.item

import com.projextxy.core.ProjectXYCore
import net.minecraft.client.renderer.texture.IIconRegister
import net.minecraft.item.Item

class ItemXy extends Item {
  override def registerIcons(iconRegister: IIconRegister) = itemIcon = iconRegister.registerIcon(s"${ProjectXYCore.MOD_ID}:$getIconString")
}
