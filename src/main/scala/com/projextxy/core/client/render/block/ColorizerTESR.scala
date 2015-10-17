package com.projextxy.core.client.render.block

import codechicken.lib.render.{CCModel, CCRenderState}
import codechicken.lib.vec.SwapYZ
import com.projextxy.core.ProjectXYCore
import com.projextxy.core.client.render.block.ColorizerTESR._
import com.projextxy.core.reference.MCColors
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.ResourceLocation
import org.lwjgl.opengl.GL11

class ColorizerTESR extends TileEntitySpecialRenderer {
  override def renderTileEntityAt(tile: TileEntity, x: Double, y: Double, z: Double, frameTime: Float): Unit = {
    CCRenderState.reset()
    CCRenderState.pullLightmap()
    CCRenderState.setDynamic()
    GL11.glDisable(GL11.GL_TEXTURE_2D)
    GL11.glEnable(GL11.GL_COLOR_MATERIAL)
    GL11.glPushMatrix()
    GL11.glTranslated(x, y, z)

    GL11.glDisable(GL11.GL_LIGHTING)
    CCRenderState.setBrightness(220)
    CCRenderState.startDrawing(7)
    MCColors.RED.c.glColour()
    red.render()
    CCRenderState.draw()

    CCRenderState.startDrawing(7)
    MCColors.GREEN.c.glColour()
    green.render()
    CCRenderState.draw()

    CCRenderState.startDrawing(7)
    MCColors.BLUE.c.glColour()
    blue.render()
    CCRenderState.draw()
    GL11.glEnable(GL11.GL_LIGHTING)


    GL11.glColor4f(.15F, .15F, .15F, 1.0F)
    CCRenderState.startDrawing(7)
    base.render()
    CCRenderState.draw()

    GL11.glEnable(GL11.GL_TEXTURE_2D)
    GL11.glDisable(GL11.GL_COLOR_MATERIAL)
    GL11.glPopMatrix()
  }
}

object ColorizerTESR {
  private val models = CCModel.parseObjModels(new ResourceLocation(ProjectXYCore.MOD_ID.toLowerCase, "models/colorizerTop.obj"), GL11.GL_QUADS, new SwapYZ)
  val red = models.get("Box010").computeLightCoords()
  val green = models.get("Box009").computeLightCoords()
  val blue = models.get("Box011").computeLightCoords()
  val base = models.get("Box002").computeLightCoords()
}
