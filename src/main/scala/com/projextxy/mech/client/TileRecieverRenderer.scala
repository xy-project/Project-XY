package com.projextxy.mech.client

import codechicken.lib.colour.ColourRGBA
import codechicken.lib.render.uv.IconTransformation
import codechicken.lib.render.{CCModel, CCModelLibrary, CCRenderState}
import codechicken.lib.vec
import codechicken.lib.vec._
import com.projextxy.core.reference.MCColors
import com.projextxy.core.{ProjectXYCore, ProjectXYCoreProxy}
import com.projextxy.lib.cofh.render.RenderHelper
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.ResourceLocation
import org.lwjgl.opengl.GL11

class TileRecieverRenderer extends TileEntitySpecialRenderer {
  override def renderTileEntityAt(tile: TileEntity, x: Double, y: Double, z: Double, partialTicks: Float) {

  }
}

object TileRecieverRenderer {
  private val models = CCModel.parseObjModels(new ResourceLocation(ProjectXYCore.MOD_ID.toLowerCase, "models/nodeReceiver.obj"), new SwapYZ)
  val icosahedron = CCModel.parseObjModels(new ResourceLocation(ProjectXYCore.MOD_ID.toLowerCase, "models/truncatedIcosahedron.obj"), new SwapYZ).get("hedron").computeNormals()
  val base = models.get("base").computeNormals()
  val pedestal = models.get("pedestal").computeNormals()
}
