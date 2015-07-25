package com.projextxy.core.client.render.block

import com.projextxy.core.CoreBlocks
import com.projextxy.core.blocks.glow.{BlockXyGlow$, BlockXyGlow}
import com.projextxy.core.blocks.traits.TConnectedTextureBlock
import com.projextxy.core.client.render.connected.{IconConnectedTexture, ConnectedRenderBlocks}
import com.projextxy.core.client.{CTRegistry$, CTRegistry}
import com.projextxy.core.tile.TileXyCustomColor
import cpw.mods.fml.client.registry.{ISimpleBlockRenderingHandler, RenderingRegistry}
import net.minecraft.block.Block
import net.minecraft.client.renderer.{RenderBlocks, Tessellator}
import net.minecraft.world.IBlockAccess
import RenderCustomGlow.fakeRenderer
import org.lwjgl.opengl.GL11

class RenderCustomGlow extends RenderBlock with ISimpleBlockRenderingHandler {
  override def getRenderId: Int = RenderCustomGlow.renderId

  override def shouldRender3DInInventory(modelId: Int): Boolean = true

  override def renderInventoryBlock(block: Block, metadata: Int, modelId: Int, renderer: RenderBlocks): Unit = {}

  override def renderWorldBlock(world: IBlockAccess, x: Int, y: Int, z: Int, block: Block, modelId: Int, renderer: RenderBlocks): Boolean = {
    val tess = Tessellator.instance
    val tile = world.getTileEntity(x, y, z)
    tile match {
      case tileCustom: TileXyCustomColor =>
        tess.setBrightness(220)
        tess.setColorRGBA_I(tileCustom.color, 255)

        CoreBlocks.blockXyCustom.sub_blocks(world.getBlockMetadata(x, y, z)) match {
          case connectedBlock: TConnectedTextureBlock =>
            RenderBlock.renderAllSides(world, x, y, z, block, renderer, BlockXyGlow.baseIcon, false)
            val folder: String = connectedBlock.connectedFolder
            val texture: IconConnectedTexture = CTRegistry.getTexture(folder)
            fakeRenderer.setOverrideBlockTexture(texture)
            fakeRenderer.setWorld(world)
            fakeRenderer.curBlock = block
            fakeRenderer.curMeta = world.getBlockMetadata(x, y, z)
            fakeRenderer.setRenderBoundsFromBlock(block)
            GL11.glEnable(GL11.GL_BLEND)
            return fakeRenderer.renderStandardBlock(block, x, y, z)
          case _ =>
            RenderBlock.renderAllSides(world, x, y, z, block, renderer, BlockXyGlow.baseIcon, false)
            return renderer.renderStandardBlock(block, x, y, z);
        }
        true
      case _ => false
    }
  }
}

object RenderCustomGlow {
  val renderId = RenderingRegistry.getNextAvailableRenderId
  val fakeRenderer = new ConnectedRenderBlocks()
}
