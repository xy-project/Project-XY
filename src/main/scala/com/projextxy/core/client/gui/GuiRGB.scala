package com.projextxy.core.client.gui

import codechicken.lib.colour.ColourRGBA
import com.projextxy.core.inventory.ContainerRGB
import com.projextxy.core.reference.MCColors
import com.projextxy.core.tile.TileColorizer
import net.minecraft.client.gui.GuiTextField
import net.minecraft.entity.player.InventoryPlayer
import org.lwjgl.opengl.GL11

class GuiRGB(inventory: InventoryPlayer, tile: TileColorizer) extends GuiCommon(new ContainerRGB(inventory, tile)) {

  var rText: GuiTextField = null
  var gText: GuiTextField = null
  var bText: GuiTextField = null

  override def initGui(): Unit = {
    super.initGui()
    rText = new GuiTextField(fontRendererObj, guiLeft + 8, guiTop + 45, 30, 10)
    gText = new GuiTextField(fontRendererObj, guiLeft + xSize / 2 - 15, guiTop + 45, 30, 10)
    bText = new GuiTextField(fontRendererObj, guiLeft + xSize - 38, guiTop + 45, 30, 10)
    rText.setText(tile.getR.toString)
    gText.setText(tile.getG.toString)
    bText.setText(tile.getB.toString)
  }


  override def mouseClicked(mouseX: Int, mouseY: Int, button: Int): Unit = {
    super.mouseClicked(mouseX, mouseY, button)
    rText.mouseClicked(mouseX, mouseY, button)
    gText.mouseClicked(mouseX, mouseY, button)
    bText.mouseClicked(mouseX, mouseY, button)
  }


  override def keyTyped(key: Char, i: Int): Unit = {
    super.keyTyped(key, i)

    def doTextChange(guiTextField: GuiTextField): Boolean = {
      if (guiTextField.isFocused) if (guiTextField.getText.length < 3)
        if ((guiTextField.getText + key).toInt <= 255) {
          guiTextField.textboxKeyTyped(key, i)
          return true
        }
      false
    }

    if (key.isDigit) {
      if (doTextChange(rText)) {
        tile.setR(getValueFromText(rText))
        tile.buildPacket().sendToServer()
      }
      if (doTextChange(gText)) {
        tile.setG(getValueFromText(gText))
        tile.buildPacket().sendToServer()
      }
      if (doTextChange(bText)) {
        tile.setB(getValueFromText(bText))
        tile.buildPacket().sendToServer()
      }
    } else if (key.isControl) {
      rText.textboxKeyTyped(key, i)
      gText.textboxKeyTyped(key, i)
      bText.textboxKeyTyped(key, i)
    }
  }

  def getValueFromText(textField: GuiTextField) = {
    try {
      textField.getText.toInt
    } catch {
      case numberFormat: NumberFormatException => 0
    }
  }

  override def drawScreen(mouseX: Int, mouseY: Int, par3: Float): Unit = {
    super.drawScreen(mouseX, mouseY, par3)
    GL11.glDisable(GL11.GL_LIGHTING)
    rText.drawTextBox()
    gText.drawTextBox()
    bText.drawTextBox()
    GL11.glEnable(GL11.GL_LIGHTING)
  }

  override def updateScreen(): Unit = {
    super.updateScreen()
  }

  override def onGuiClosed(): Unit = {
    tile.buildPacket().sendToServer()
    tile.getWorldObj.markBlockForUpdate(tile.xCoord, tile.yCoord, tile.zCoord)
    super.onGuiClosed()
  }

  override def guiName: String = "Color Injector"

  override def drawBackground(): Unit = {
    drawProgressBar(xSize / 2 - 15, 70, 1.0D, new ColourRGBA(getValueFromText(rText), getValueFromText(gText), getValueFromText(bText), 255))
  }

  override def drawForeground(): Unit = {
    fontRendererObj.drawStringWithShadow("Red", 8, 35, MCColors.RED.rgb)
    fontRendererObj.drawStringWithShadow("Green", xSize / 2 - 15, 35, MCColors.GREEN.rgb)
    fontRendererObj.drawStringWithShadow("Blue", xSize - 38, 35, MCColors.BLUE.rgb)

    tile.setR(getValueFromText(rText))
    tile.setG(getValueFromText(gText))
    tile.setB(getValueFromText(bText))
  }
}
