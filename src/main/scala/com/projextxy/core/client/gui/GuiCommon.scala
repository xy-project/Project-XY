package com.projextxy.core.client.gui

import java.util

import codechicken.lib.colour.ColourRGBA
import codechicken.lib.render.{CCRenderState, RenderUtils}
import codechicken.lib.vec.Vector3
import com.projextxy.core.blocks.glow.BlockXyGlow
import com.projextxy.core.reference.MCColors
import com.projextxy.core.resource.ResourceLib
import com.projextxy.lib.cofh.render.RenderHelper
import net.minecraft.client.gui.inventory.GuiContainer
import net.minecraft.inventory.{Container, Slot}
import org.lwjgl.opengl.GL11

import scala.collection.JavaConverters._

abstract class GuiCommon(container: Container) extends GuiContainer(container) {
  override def initGui(): Unit = {
    super.initGui()
    ySize = 187
    guiLeft = (width - xSize) / 2
    guiTop = (height - ySize) / 2
  }

  override def drawGuiContainerBackgroundLayer(par1: Float, par2: Int, par3: Int): Unit = {
    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F)
    ResourceLib.baseGui.bind()
    drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize)
    GL11.glTranslatef(guiLeft, guiTop, 0.0F)
    drawBackgroundSlots()
    drawBackground()
    GL11.glTranslatef(-guiLeft, -guiTop, 0.0F)
  }

  def drawProgressBar(x: Int, y: Int, progress: Double, colourRGBA: ColourRGBA) {
    CCRenderState.reset()
    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F)
    drawBar(x, y)
    val width: Double = 26 * progress
    RenderHelper.setBlockTextureSheet()
    CCRenderState.startDrawing()
    colourRGBA.glColour()
    RenderUtils.renderFluidQuad(
      new Vector3(x + 2, 2 + y, this.zLevel),
      new Vector3(x + 2, 8 + y, this.zLevel),
      new Vector3(x + 2 + width, 8 + y, this.zLevel),
      new Vector3(x + 2 + width, 2 + y, this.zLevel),
      BlockXyGlow.baseIcon, 16.0D)
    CCRenderState.draw()
    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F)
  }

  private def drawBar(x: Int, y: Int) {
    ResourceLib.baseGui.bind()
    drawTexturedModalRect(x, y, 176, 0, 30, 10)
  }

  private def drawSlot(slot: Slot): Unit = {
    val x = slot.xDisplayPosition - 1
    val y = slot.yDisplayPosition - 1
    drawTexturedModalRect(x, y, 176, 10, 18, 18)
  }


  override def drawGuiContainerForegroundLayer(par1: Int, par2: Int): Unit = {
    fontRendererObj.drawString(guiName, xSize / 2 - fontRendererObj.getStringWidth(guiName) / 2, 8, MCColors.LIGHT_GREY.rgb)
    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F)
    GL11.glDisable(GL11.GL_LIGHTING)
    drawForeground()
    GL11.glEnable(GL11.GL_LIGHTING)
  }

  private def drawBackgroundSlots(): Unit = {
    val slots = container.inventorySlots.asInstanceOf[util.List[Slot]]
    for (slot <- slots.asScala)
      drawSlot(slot)
  }

  def guiName: String

  def drawForeground()

  def drawBackground()
}
