package com.projextxy.mech.fmp

import com.projextxy.core.reference.MCColors
import com.projextxy.core.{ProjectXYCore, ProjectXYCoreProxy}
import com.projextxy.mech.XynergyNetworkHandler
import com.projextxy.mech.client.TileRecieverRenderer
import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.minecraft.util.ResourceLocation
import org.lwjgl.opengl.GL11

import scala.collection.mutable.ArrayBuffer

class ReceiverPart extends TCuboidPart with TXyRedstonePart {
  val parents: ArrayBuffer[TXyRedstonePart] = new ArrayBuffer[TXyRedstonePart]()
  val children: ArrayBuffer[TXyRedstonePart] = new ArrayBuffer[TXyRedstonePart]()

  override def getBounds: Cuboid6 = new Cuboid6(0, 0, 0, 1, .5, 1)

  override def getType: String = "projectxy_power_receiver"

  override def update() {
    if (!XynergyNetworkHandler.isReceiverIn(this))
      XynergyNetworkHandler.addReceiver(this)
  }

  override def onRemoved(): Unit = {
    XynergyNetworkHandler.removeReceiver(this)
    parents.foreach(r => r.removeChild(this))
    super.onRemoved()
  }

  @SideOnly(Side.CLIENT)
  override def renderDynamic(pos: Vector3, frame: Float, pass: Int): Unit = {
    GL11.glPushMatrix()
    GL11.glTranslated(pos.x, pos.y, pos.z)
    GL11.glEnable(GL11.GL_BLEND)
    CCRenderState.reset()
    CCRenderState.useNormals = true
    CCRenderState.setBrightness(tile.getWorldObj, tile.xCoord, tile.yCoord, tile.zCoord)
    CCRenderState.changeTexture(new ResourceLocation(ProjectXYCore.MOD_ID.toLowerCase, "textures/model/modelReceiver.png"))
    CCRenderState.startDrawing(4)
    TileRecieverRenderer.base.computeLightCoords().render()
    CCRenderState.draw()
    CCRenderState.startDrawing(4)
    CCRenderState.setBrightness(220)
    TileRecieverRenderer.pedestal.render()
    CCRenderState.draw()
    CCRenderState.startDrawing(4)
    TileRecieverRenderer.icosahedron.setColour(MCColors.PURPLE.c.rgba()).render(new IconTransformation(ProjectXYCoreProxy.animationFx.texture))
    CCRenderState.draw()
    GL11.glPopMatrix()
  }


}
