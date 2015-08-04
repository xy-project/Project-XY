package com.projextxy.mech.gui

import com.projextxy.core.client.gui.{GuiCommon, Menu}
import com.projextxy.mech.tile.TileFabricator
import net.minecraft.entity.player.InventoryPlayer
import net.minecraft.init.{Blocks, Items}

class GuiFabricator(inventory: InventoryPlayer, tile: TileFabricator) extends GuiCommon(new ContainerFabricator(inventory, tile)) {
  val redstone_control = addMenu(new Menu(this, 0, 9)
    .addMenuItem("Auto: Low", Blocks.unlit_redstone_torch, 0)
    .addMenuItem("Auto: High", Blocks.redstone_torch, 0)
    .addMenuItem("Pulse", Items.redstone, 0))
  redstone_control.addCallback(onRedstoneControlSelected)
  redstone_control.setSelected(tile.mode)

  override def guiName: String = "Fabricator"

  override def drawBackground(): Unit = {}

  override def drawForeground(): Unit = {
    redstone_control.draw()
  }

  def onRedstoneControlSelected(selected: Int): Unit = container.asInstanceOf[ContainerFabricator].handleGuiChange(selected)
}
