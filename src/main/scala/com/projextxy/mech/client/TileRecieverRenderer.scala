package com.projextxy.mech.client

import com.projextxy.core.ProjectXYCore
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.ResourceLocation

class TileRecieverRenderer extends TileEntitySpecialRenderer {
  override def renderTileEntityAt(tile: TileEntity, x: Double, y: Double, z: Double, partialTicks: Float) {

  }
}

object TileRecieverRenderer {
  val icosahedron = CCModel.parseObjModels(new ResourceLocation(ProjectXYCore.MOD_ID.toLowerCase, "models/truncatedIcosahedron.obj"), new SwapYZ).get("hedron").computeNormals()
  val base = models.get("base").computeNormals()
  val pedestal = models.get("pedestal").computeNormals()
  private val models = CCModel.parseObjModels(new ResourceLocation(ProjectXYCore.MOD_ID.toLowerCase, "models/nodeReceiver.obj"), new SwapYZ)
}
