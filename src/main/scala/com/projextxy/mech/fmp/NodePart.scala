package com.projextxy.mech.fmp

import com.projextxy.core.client.render.fx.FXBeam
import com.projextxy.core.reference.MCColors
import com.projextxy.core.{ProjectXYCore, ProjectXYCoreProxy}
import com.projextxy.mech.XynergyNetworkHandler
import com.projextxy.mech.client.TileRecieverRenderer
import cpw.mods.fml.client.FMLClientHandler
import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.minecraft.util.ResourceLocation
import org.lwjgl.opengl.GL11

import scala.collection.mutable.ArrayBuffer

class NodePart extends TCuboidPart with TXyRedstonePart {
  val children: ArrayBuffer[TXyRedstonePart] = new ArrayBuffer[TXyRedstonePart]()
  val parents: ArrayBuffer[TXyRedstonePart] = new ArrayBuffer[TXyRedstonePart]()
  var tickCount = 0

  override def getBounds: Cuboid6 = Cuboid6.full

  override def getType: String = "projectxy_power_node"

  override def update() {
    if (!XynergyNetworkHandler.isReceiverIn(this))
      XynergyNetworkHandler.addReceiver(this)
    tickCount += 1
    if (parents.nonEmpty) {
      if (tickCount >= 8) {
        tickCount = 0
        val possibleChildren = XynergyNetworkHandler.getClosestReceiversInRange(this, 8, 2)

        //Remove children if it isn't exist in the buffer
        children.foreach(r => if (!possibleChildren.contains(r)) children -= r)

        //Add if there is children
        possibleChildren.foreach { r =>
          if (r != this) {
            if (!children.contains(r) && !r.isChildIn(this)) {
              if (!r.isParentIn(this)) r.parents += this
              children += r
            }
          }
        }

        //Render particle
        if (world.isRemote) {
          for (receiver <- children) {
            val particle = new FXBeam(world, x + 0.5, y + 0.5, z + 0.5, receiver.x + 0.5, receiver.y + 0.5, receiver.z + 0.5, 1.0F, 1.0F, 1.0F, 8)
            FMLClientHandler.instance().getClient.effectRenderer.addEffect(particle)
          }
        }

        //Finish update
        world.markBlockForUpdate(x, y, z)
        tickCount = 0
      }
    }

  }

  override def onRemoved() {
    XynergyNetworkHandler.removeReceiver(this)
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
    TileRecieverRenderer.icosahedron.setColour(MCColors.RED.c.rgba()).render(new IconTransformation(ProjectXYCoreProxy.animationFx.texture))
    CCRenderState.draw()
    GL11.glPopMatrix()
  }

}
