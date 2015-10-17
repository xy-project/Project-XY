package com.projextxy.core.client.render.block

import codechicken.lib.render.uv.IconTransformation
import codechicken.lib.render.{CCModel, CCRenderState}
import codechicken.lib.vec.SwapYZ
import com.projextxy.core.ProjectXYCore
import com.projextxy.core.blocks.glow.TBlockXyGlow
import com.projextxy.core.client.BlockIconRegistry
import com.projextxy.core.client.render.block.BlockFrameRenderer._
import cpw.mods.fml.client.registry.{ISimpleBlockRenderingHandler, RenderingRegistry}
import net.minecraft.block.Block
import net.minecraft.client.renderer.{RenderBlocks, Tessellator}
import net.minecraft.util.{IIcon, ResourceLocation}
import net.minecraft.world.IBlockAccess
import org.lwjgl.opengl.GL11

class BlockFrameRenderer extends ISimpleBlockRenderingHandler {
  override def getRenderId: Int = renderType

  override def shouldRender3DInInventory(modelId: Int): Boolean = true

  override def renderInventoryBlock(block: Block, metadata: Int, modelId: Int, renderer: RenderBlocks): Unit = {
    CCRenderState.reset()
    CCRenderState.startDrawing()
    val tess = Tessellator.instance
    tess.addTranslation(0, -.125f, 0)
    renderFrame(icons(0))
    tess.addTranslation(0, .125f, 0)
    CCRenderState.draw()
  }

  override def renderWorldBlock(world: IBlockAccess, x: Int, y: Int, z: Int, block: Block, modelId: Int, renderer: RenderBlocks): Boolean = {
    val tess = Tessellator.instance
    tess.addTranslation(x, y, z)
    tess.setBrightness(world.getBlock(x, y, z).getMixedBrightnessForBlock(world, x, y, z))
    renderFrame(icons(0))
    tess.addTranslation(-x, -y, -z)
    tess.setBrightness(220)
    renderer.setRenderBounds(.125, .125, .125, .875, .875, .875)
    RenderBlock.renderAllSides(world, x, y, z, block, renderer, TBlockXyGlow.animationIcon)
    true
  }
}

object BlockFrameRenderer {
  val renderType = RenderingRegistry.getNextAvailableRenderId

  val frame = CCModel.parseObjModels(new ResourceLocation(ProjectXYCore.MOD_ID.toLowerCase, "models/frame.obj"), GL11.GL_QUADS, new SwapYZ).get("frame").computeNormals()
  lazy val icons = Array(BlockIconRegistry.get("frame").get, BlockIconRegistry.get("frame1").get, BlockIconRegistry.get("frame2").get, BlockIconRegistry.get("frame3").get)

  def renderFrame(icon: IIcon): Unit = {
    CCRenderState.reset()
    CCRenderState.useNormals = true
    CCRenderState.computeLighting = true
    frame.render(new IconTransformation(icon))
  }
}

