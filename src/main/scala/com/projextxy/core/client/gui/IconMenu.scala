package com.projextxy.core.client.gui

import net.minecraft.item.ItemStack

import scala.collection.mutable.ArrayBuffer

case class IconMenu(gui: GuiCommon, x: Int, y: Int) {
  val menuItems = new ArrayBuffer[MenuItem]()

  def addMenuItem(string: String, itemStack: ItemStack): IconMenu = {
    menuItems += new MenuItem(gui, string, itemStack)
    this
  }
}

case class MenuItem(gui: GuiCommon, string: String, itemStack: ItemStack)
