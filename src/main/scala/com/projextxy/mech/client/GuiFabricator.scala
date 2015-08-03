package com.projextxy.mech.client

import com.projextxy.core.client.gui.GuiCommon
import com.projextxy.mech.inventory.ContainerFabricator
import com.projextxy.mech.tile.TileFabricator
import net.minecraft.entity.player.InventoryPlayer

class GuiFabricator(inventory: InventoryPlayer, tile: TileFabricator) extends GuiCommon(new ContainerFabricator(inventory, tile)) {
  override def guiName: String = "Fabricator"

  override def drawBackground(): Unit = {}

  override def drawForeground(): Unit = {}
}
