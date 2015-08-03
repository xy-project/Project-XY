package com.projextxy.core.client.render.block

import com.projextxy.core.CoreBlocks
import com.projextxy.core.blocks.glow.{BlockXyCustom, BlockXyGlow}
import com.projextxy.core.blocks.traits.TConnectedTextureBlock
import com.projextxy.core.client.CTRegistry
import com.projextxy.core.client.render.block.RenderCustomGlow.fakeRenderer
import com.projextxy.core.client.render.connected.{ConnectedRenderBlocks, IconConnectedTexture}
import com.projextxy.core.tile.{TileColorizer, TileXyCustomColor}
import cpw.mods.fml.client.registry.{ISimpleBlockRenderingHandler, RenderingRegistry}
import net.minecraft.block.Block
import net.minecraft.client.renderer.{RenderBlocks, Tessellator}
import net.minecraft.world.IBlockAccess
import org.lwjgl.opengl.GL11

class RenderCustomGlow extends RenderBlock with ISimpleBlockRenderingHandler {
  override def getRenderId: Int = RenderCustomGlow.renderId

  override def shouldRender3DInInventory(modelId: Int): Boolean = true

  override def renderInventoryBlock(block: Block, metadata: Int, modelId: Int, renderer: RenderBlocks): Unit = {}

  override def renderWorldBlock(world: IBlockAccess, x: Int, y: Int, z: Int, _block: Block, modelId: Int, renderer: RenderBlocks): Boolean = {
    val tile = world.getTileEntity(x, y, z)
    val tess = Tessellator.instance
    tile match {
      case tileCustom: TileXyCustomColor =>
        val block = _block.asInstanceOf[BlockXyCustom]
        val meta = world.getBlockMetadata(x, y, z)
        tess.setBrightness(220)
        tess.setColorRGBA_I(tileCustom.color, 255)
        RenderBlock.renderAllSides(world, x, y, z, _block, renderer, block.sub_blocks(meta).getAnimationIcon(), false)

        block.sub_blocks(meta) match {
          case connectedBlock: TConnectedTextureBlock =>
            if (connectedBlock.renderBlockTexture)
              renderer.renderStandardBlock(_block, x, y, z)
            val folder: String = connectedBlock.connectedFolder
            val texture: IconConnectedTexture = CTRegistry.getTexture(folder)
            fakeRenderer.setOverrideBlockTexture(texture)
            fakeRenderer.setWorld(world)
            fakeRenderer.curBlock = _block
            fakeRenderer.changeBounds = true
            fakeRenderer.curMeta = world.getBlockMetadata(x, y, z)
            fakeRenderer.setRenderBoundsFromBlock(_block)
            GL11.glEnable(GL11.GL_BLEND)
            return fakeRenderer.renderStandardBlock(_block, x, y, z)
          case _ =>
            return renderer.renderStandardBlock(_block, x, y, z);
        }

        true
      case colorizer: TileColorizer =>
        tess.setBrightness(220)
        tess.setColorRGBA_I(colorizer.getColor, 255)
        RenderBlock.renderAllSides(world, x, y, z, _block, renderer, BlockXyGlow.animationIcon, false)
        renderer.renderStandardBlock(_block, x, y, z);
    }
  }
}

object RenderCustomGlow {
  val renderId = RenderingRegistry.getNextAvailableRenderId
  val fakeRenderer = new ConnectedRenderBlocks()
}
