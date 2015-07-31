package com.projextxy.core.client

import codechicken.lib.render.TextureUtils
import codechicken.lib.render.TextureUtils.IIconSelfRegister
import net.minecraft.client.renderer.texture.IIconRegister
import net.minecraft.util.IIcon

import scala.collection.mutable

class IconRegistry(atlasIn: Int) extends IIconSelfRegister {
  TextureUtils.addIconRegistrar(this)

  val toRegister = new mutable.HashMap[String, String]()
  val icons = new mutable.WeakHashMap[String, IIcon]

  def add(name: String, icon: String): Unit = toRegister.put(name, icon)

  /**
   * 0 for blocks, 1 for items
   */
  override def atlasIndex(): Int = atlasIn

  override def registerIcons(reg: IIconRegister): Unit = for (x <- toRegister) icons.put(x._1, reg.registerIcon(x._2))

  def get(textureLoc: String): Option[IIcon] = {
    if (!icons.contains(textureLoc))
      return None
    icons.get(textureLoc)
  }
}

object BlockIconRegistry extends IconRegistry(0)

object ItemIconRegistry extends IconRegistry(1)


