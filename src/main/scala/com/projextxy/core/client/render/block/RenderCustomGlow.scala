package com.projextxy.core.client.render.block

import com.projextxy.core.CoreBlocks
import com.projextxy.core.blocks.glow.BlockXyGlow
import com.projextxy.core.blocks.traits.TConnectedTextureBlock
import com.projextxy.core.client.{CTRegistry}
import com.projextxy.core.tile.TileXyCustomColor
import cpw.mods.fml.client.registry.{ISimpleBlockRenderingHandler, RenderingRegistry}
import net.minecraft.block.Block
import net.minecraft.client.renderer.{RenderBlocks, Tessellator}
import net.minecraft.world.IBlockAccess

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

        RenderBlock.renderAllSides(world, x, y, z, block, renderer, BlockXyGlow.baseIcon, false)
        renderer.renderStandardBlock(block, x, y, z)
        CoreBlocks.blockXyCustom.sub_blocks(world.getBlockMetadata(x, y, z)) match {
          case connectedBlock: TConnectedTextureBlock =>
            val folder = connectedBlock.connectedFolder
            renderer.renderFaceYNeg(block, x, y, z, CTRegistry.getConnectedBlockTexture(folder, world, x, y, z, 0, block))
            renderer.renderFaceYPos(block, x, y, z, CTRegistry.getConnectedBlockTexture(folder, world, x, y, z, 1, block))
            renderer.renderFaceZNeg(block, x, y, z, CTRegistry.getConnectedBlockTexture(folder, world, x, y, z, 2, block))
            renderer.renderFaceZPos(block, x, y, z, CTRegistry.getConnectedBlockTexture(folder, world, x, y, z, 3, block))
            renderer.renderFaceXNeg(block, x, y, z, CTRegistry.getConnectedBlockTexture(folder, world, x, y, z, 4, block))
            renderer.renderFaceXPos(block, x, y, z, CTRegistry.getConnectedBlockTexture(folder, world, x, y, z, 5, block))
          case _ =>
        }
        true
      case _ => false
    }
  }
}

object RenderCustomGlow {
  val renderId = RenderingRegistry.getNextAvailableRenderId
}
