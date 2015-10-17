package com.projextxy.mech.client

import com.projextxy.core.blocks.BlockXyDecor
import com.projextxy.core.blocks.traits.TConnectedTextureBlock
import com.projextxy.core.client.render.block.RenderConnectedTexture
import com.projextxy.mech.multiblock.TileMultiShadow
import cpw.mods.fml.client.registry.{RenderingRegistry, ISimpleBlockRenderingHandler}
import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.client.renderer.RenderBlocks
import net.minecraft.world.IBlockAccess
import RenderBlockMultiShadow._

class RenderBlockMultiShadow extends ISimpleBlockRenderingHandler {
  override def getRenderId: Int = renderId

  override def shouldRender3DInInventory(modelId: Int): Boolean = true

  override def renderInventoryBlock(block: Block, metadata: Int, modelId: Int, renderer: RenderBlocks): Unit = {}

  override def renderWorldBlock(world: IBlockAccess, x: Int, y: Int, z: Int, block: Block, modelId: Int, renderer: RenderBlocks): Boolean = {
    if (block.getMaterial == Material.air)
      return false
    world.getTileEntity(x, y, z) match {
      case shadow: TileMultiShadow =>
        val block2 = shadow.getCurrBLock
        if (block2.isDefined)
          if (block2.get.isInstanceOf[BlockXyDecor]) {
            RenderConnectedTexture.connectedRenderer.setWorld(world)
            RenderConnectedTexture.connectedRenderer.curMeta = world.getBlockMetadata(x, y, z)
            RenderConnectedTexture.connectedRenderer.curBlock = block
            return RenderConnectedTexture.connectedRenderer.renderStandardBlock(block2.get, x, y, z)
          } else
            return renderer.renderBlockByRenderType(block2.get, x, y, z)
        false
      case _ => false
    }
  }
}

object RenderBlockMultiShadow {
  val renderId = RenderingRegistry.getNextAvailableRenderId
}
