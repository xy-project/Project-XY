package com.projextxy.core.client.render.block

import com.projextxy.core.client.render.block.RenderConnectedTexture._
import com.projextxy.core.client.render.connected.ConnectedRenderBlocks
import cpw.mods.fml.client.registry.{ISimpleBlockRenderingHandler, RenderingRegistry}
import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.minecraft.block.Block
import net.minecraft.client.renderer.{EntityRenderer, RenderBlocks, Tessellator}
import net.minecraft.world.IBlockAccess
import org.lwjgl.opengl.GL11

class RenderConnectedTexture extends ISimpleBlockRenderingHandler {
  override def getRenderId: Int = renderType

  override def shouldRender3DInInventory(modelId: Int): Boolean = true

  override def renderInventoryBlock(block: Block, metadata: Int, modelId: Int, renderer: RenderBlocks): Unit = {
    val tessellator = Tessellator.instance
    block.setBlockBoundsForItemRender()
    renderer.setRenderBoundsFromBlock(block)
    GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F)
    GL11.glTranslatef(-0.5F, -0.0F, -0.5F)
    var red = 1.0F
    var green = 1.0F
    var blue = 1.0F
    if (EntityRenderer.anaglyphEnable) {
      val f3 = (red * 30.0F + green * 59.0F + blue * 11.0F) / 100.0F
      val f4 = (red * 30.0F + green * 70.0F) / 100.0F
      val f5 = (red * 30.0F + blue * 70.0F) / 100.0F
      red = f3
      green = f4
      blue = f5
    }
    GL11.glColor4f(red, green, blue, 1.0F)
    renderer.colorRedTopLeft *= red
    renderer.colorRedTopRight *= red
    renderer.colorRedBottomLeft *= red
    renderer.colorRedBottomRight *= red
    renderer.colorGreenTopLeft *= green
    renderer.colorGreenTopRight *= green
    renderer.colorGreenBottomLeft *= green
    renderer.colorGreenBottomRight *= green
    renderer.colorBlueTopLeft *= blue
    renderer.colorBlueTopRight *= blue
    renderer.colorBlueBottomLeft *= blue
    renderer.colorBlueBottomRight *= blue
    if (block.getIcon(0, metadata) == null) {
      return
    }
    tessellator.startDrawingQuads()
    tessellator.setNormal(0.0F, -1.0F, 0.0F)
    renderer.renderFaceYNeg(block, 0.0D, -0.5D, 0.0D, block.getIcon(0, metadata))
    tessellator.draw()
    tessellator.startDrawingQuads()
    tessellator.setNormal(0.0F, 1.0F, 0.0F)
    renderer.renderFaceYPos(block, 0.0D, -0.5D, 0.0D, block.getIcon(1, metadata))
    tessellator.draw()
    tessellator.startDrawingQuads()
    tessellator.setNormal(0.0F, 0.0F, -1.0F)
    renderer.renderFaceXPos(block, 0.0D, -0.5D, 0.0D, block.getIcon(2, metadata))
    tessellator.draw()
    tessellator.startDrawingQuads()
    tessellator.setNormal(0.0F, 0.0F, 1.0F)
    renderer.renderFaceXNeg(block, 0.0D, -0.5D, 0.0D, block.getIcon(3, metadata))
    tessellator.draw()
    tessellator.startDrawingQuads()
    tessellator.setNormal(-1.0F, 0.0F, 0.0F)
    renderer.renderFaceZNeg(block, 0.0D, -0.5D, 0.0D, block.getIcon(4, metadata))
    tessellator.draw()
    tessellator.startDrawingQuads()
    tessellator.setNormal(1.0F, 0.0F, 0.0F)
    renderer.renderFaceZPos(block, 0.0D, -0.5D, 0.0D, block.getIcon(5, metadata))
    tessellator.draw()

    GL11.glTranslatef(0.5F, 0.0F, 0.5F)
    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F)
  }

  override def renderWorldBlock(world: IBlockAccess, x: Int, y: Int, z: Int, block: Block, modelId: Int, renderer: RenderBlocks): Boolean = {
    connectedRenderer.setWorld(world)
    connectedRenderer.curMeta = world.getBlockMetadata(x, y, z)
    connectedRenderer.curBlock = block
    connectedRenderer.setRenderBoundsFromBlock(block)
    connectedRenderer.renderStandardBlock(block, x, y, z)
  }
}

object RenderConnectedTexture {
  val renderType: Int = RenderingRegistry.getNextAvailableRenderId

  lazy val connectedRenderer = new ConnectedRenderBlocks
}
