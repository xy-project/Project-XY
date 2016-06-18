package com.projextxy.mech.gui

import com.projextxy.core.client.gui.GuiCommon
import com.projextxy.core.reference.MCColors
import com.projextxy.mech.multiblock.MultiTank
import net.minecraft.entity.player.InventoryPlayer

class GuiMultiTank(inventory: InventoryPlayer, multiTank: MultiTank) extends GuiCommon(new ContainerMultitank(inventory, multiTank)) {
  override def guiName: String = "Tank"

  override def drawBackground(): Unit = {

  }

  override def drawForeground(): Unit = {
    fontRendererObj.drawString(multiTank.getTankInfo.fluid.amount.toString + "/" + multiTank.getTankInfo.capacity.toString, 10, 56, MCColors.LIGHT_GREY.rgb)
    fontRendererObj.drawString("WIP", xSize / 2 - 10, 20, MCColors.LIGHT_GREY.rgb)
  }
}
