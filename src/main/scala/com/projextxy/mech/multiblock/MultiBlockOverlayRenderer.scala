package com.projextxy.mech.multiblock

import codechicken.lib.render.RenderUtils
import codechicken.lib.vec.BlockCoord
import com.projextxy.core.client.BlockIconRegistry
import com.projextxy.core.resource.ResourceLib
import cpw.mods.fml.common.eventhandler.SubscribeEvent
import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.Tessellator
import net.minecraft.util.MovingObjectPosition.MovingObjectType
import net.minecraftforge.client.event.DrawBlockHighlightEvent
import org.lwjgl.opengl.GL11
import scala.collection.JavaConversions._

import scala.collection.mutable

object MultiBlockOverlayRenderer {
  @SideOnly(Side.CLIENT)
  @SubscribeEvent
  def onDrawBlockHighlight(event: DrawBlockHighlightEvent): Unit = {
    val target = event.target
    val world = Minecraft.getMinecraft.theWorld
    if (target.typeOfHit == MovingObjectType.BLOCK) {
      world.getTileEntity(target.blockX, target.blockY, target.blockZ) match {
        case tileMulti: TileMultiBlock =>
          val player = event.player
          GL11.glPushMatrix()
          //Translate player coords to world coords used in entities
          val interpPosX: Double = player.lastTickPosX + (player.posX - player.lastTickPosX) * event.partialTicks
          val interpPosY: Double = player.lastTickPosY + (player.posY - player.lastTickPosY) * event.partialTicks
          val interpPosZ: Double = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * event.partialTicks
          GL11.glTranslated(-interpPosX, -interpPosY, -interpPosZ)
          renderOverlay(tileMulti)
          GL11.glPopMatrix()
        case _ =>
      }
    }
  }

  def renderOverlay(tile: TileMultiBlock): Unit = {
    val blocksToRender = new mutable.HashSet[BlockCoord]
    for (multiBlock <- tile.formedMultiBlocks) {
      blocksToRender ++= multiBlock.inBlocks
    }
    val tess = Tessellator.instance
    val texture = BlockIconRegistry.get("multiBlockOverlay")
    val copy = new BlockCoord()
    GL11.glEnable(GL11.GL_BLEND)
    GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA)
    GL11.glDepthMask(false)
    ResourceLib.blockSheet.bind()
    tess.startDrawingQuads()
    tess.setColorRGBA_F(1, 1, 1, .25f)
    if (texture.isDefined)
      for (coord <- blocksToRender) {
        for (side <- 0 until 6) {
          if (!blocksToRender.contains(copy.set(coord).offset(side))) {
            RenderUtils.renderBlockOverlaySide(coord.x, coord.y, coord.z, side, texture.get.getMinU, texture.get.getMaxU, texture.get.getMinV, texture.get.getMaxV)
          }
        }
      }
    tess.draw()
  }
}
