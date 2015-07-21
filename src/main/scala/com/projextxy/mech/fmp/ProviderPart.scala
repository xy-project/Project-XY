package com.projextxy.mech.fmp

import codechicken.lib.render.CCRenderState
import codechicken.lib.render.uv.IconTransformation
import codechicken.lib.vec.{Cuboid6, Vector3}
import codechicken.multipart.{IRedstonePart, TCuboidPart}
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

class ProviderPart extends TCuboidPart with TXyRedstonePart with IRedstonePart {
  var tickCount = 0
  val parents = new ArrayBuffer[TXyRedstonePart]
  val children = new ArrayBuffer[TXyRedstonePart]()

  @SideOnly(Side.CLIENT)
  override def renderStatic(pos: Vector3, pass: Int): Boolean = super.renderStatic(pos, pass)

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
    TileRecieverRenderer.icosahedron.setColour(MCColors.GREEN.c.rgba()).render(new IconTransformation(ProjectXYCoreProxy.animationFx.texture))
    CCRenderState.draw()
    GL11.glPopMatrix()
  }

  override def getBounds: Cuboid6 = Cuboid6.full

  override def update() {
    tickCount += 1
    if (tickCount >= 8) {
      val possibleChildren = XynergyNetworkHandler.getClosestReceiversInRange(this, 8, 2)

      //Remove children if it isn't exist in the buffer
      children.foreach(r => if (!possibleChildren.contains(r)) children -= r)

      //Add if there is news
      possibleChildren.foreach { r =>
        if (!children.contains(r)) {
          if (!r.isParentIn(this)) r.parents += this
          children += r
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

  override def scheduledTick(){
    println("scheduledTick")
  }

  override def getType: String = "projectxy_power_provider"

  override def strongPowerLevel(side: Int): Int = 2

  override def canConnectRedstone(side: Int): Boolean = true

  override def weakPowerLevel(side: Int): Int = 0
}
