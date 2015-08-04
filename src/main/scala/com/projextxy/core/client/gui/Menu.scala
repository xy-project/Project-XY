package com.projextxy.core.client.gui

import codechicken.lib.gui.GuiDraw
import codechicken.lib.vec.Rectangle4i
import com.projextxy.core.resource.{ResourceAction, ResourceLib}
import net.minecraft.block.Block
import net.minecraft.client.audio.PositionedSoundRecord
import net.minecraft.item.Item
import net.minecraft.util.{IIcon, ResourceLocation}

import scala.collection.mutable.ArrayBuffer

case class Menu(gui: GuiCommon, x: Int, y: Int) {
  val menuItems = new ArrayBuffer[MenuItem]()
  val callbacks = new ArrayBuffer[Int => Unit]()
  var selected = 0

  def addCallback(callback: Int => Unit) = callbacks += callback

  def addMenuItem(tooltip: String, item: Item, meta: Int): Menu = addMenuItem(tooltip, item.getIconFromDamage(meta), ResourceLib.itemSheet)

  def addMenuItem(tooltip: String, icon: IIcon, resourceAction: ResourceAction): Menu = {
    menuItems += new MenuItem(gui, tooltip, icon, resourceAction)
    this
  }

  def addMenuItem(tooltip: String, block: Block, meta: Int): Menu = addMenuItem(tooltip, block.getIcon(0, meta), ResourceLib.blockSheet)

  def draw(): Unit = {
    ResourceLib.baseGui.bind()
    //Top
    gui.drawTexturedModalRect(x - 34, y, 194, 32, 34, 3)
    //Bottom
    gui.drawTexturedModalRect(x - 34, y + 22 * menuItems.length + 3, 194, 53, 34, 5)
    //Back
    for (i <- menuItems.indices) {
      gui.drawTexturedModalRect(x - 34, y + 22 * i + 3, 194, 34, 34, 22)
    }
    //Button
    for (i <- menuItems.indices) {
      gui.drawButton(x - 28, y + 22 * i + 4, menuItems(i).icon, menuItems(i).resourceAction, selected = selected == i)
    }
  }

  def mouseClicked(mouse: (Int, Int), button: Int): Unit = {
    for (itemIndex <- menuItems.indices) {
      if (isButtonIn(mouse, itemIndex)) {
        selected = itemIndex
        callbacks.foreach(callback => callback(itemIndex))
        gui.mc.getSoundHandler.playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F))
      }
    }
  }

  def setSelected(selected: Int) = this.selected = selected

  def manageTooltips(mouse: (Int, Int)): Unit = {

    for (itemIndex <- menuItems.indices) {
      if (isButtonIn(mouse, itemIndex)) {
        GuiDraw.drawTip(mouse._1 + 9, mouse._2, menuItems(itemIndex).tooltip)
      }
    }
  }

  def isButtonIn(mouse: (Int, Int), index: Int): Boolean = new Rectangle4i(x - 28 + gui.getGuiLeft, y + 22 * index + 4 + gui.getGuiTop, 22, 22).contains(mouse._1, mouse._2)
}

case class MenuItem(gui: GuiCommon, tooltip: String, icon: IIcon, resourceAction: ResourceAction)
