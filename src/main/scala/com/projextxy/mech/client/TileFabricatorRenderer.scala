package com.projextxy.mech.client

import com.projextxy.mech.tile.TileFabricator
import net.minecraft.client.renderer.OpenGlHelper
import net.minecraft.client.renderer.entity.RenderManager
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer
import net.minecraft.entity.item.EntityItem
import net.minecraft.tileentity.TileEntity
import org.lwjgl.opengl.GL11

class TileFabricatorRenderer extends TileEntitySpecialRenderer {
  override def renderTileEntityAt(_tile: TileEntity, x: Double, y: Double, z: Double, frameTime: Float): Unit = {
    val tile = _tile.asInstanceOf[TileFabricator]
    if (tile.crafting_render_time > 0) {
      val renderer = RenderManager.instance.getEntityClassRenderObject(classOf[EntityItem])
      val entityItem = new EntityItem(tile.getWorldObj)
      entityItem.age = 0
      //Hover should be set to 0 so it doesn't spin or start at a random angle
      entityItem.hoverStart = 0.0f


      GL11.glPushMatrix()
      //Shift to top middle and up 1
      GL11.glTranslated(x + .5, y + 1, z + .5)
      for (i <- 0 until 9) {
        val stack = tile.getCrafted().getStackInSlot(i)
        if (stack != null) {
          //Row should be divisable by 3
          val row = i / 3
          //Column is index divided by three remainder
          val col = i % 3

          entityItem.setEntityItemStack(stack)
          val spacing = .1875D

          val translateX = -spacing + col * spacing
          val translateZ = -spacing + row * spacing
          GL11.glTranslated(translateX, 0, translateZ)
          GL11.glScaled(.50, .5, .5)
          val var5 = 0xF000F0
          val var6 = var5 % 65536
          val var7 = var5 / 65536
          OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, var6 / 1.0F, var7 / 1.0F)
          renderer.doRender(entityItem, 0, 0, 0, 0, 0)
          GL11.glScaled(1 / .5, 1 / .5, 1 / .5)
          GL11.glTranslated(-translateX, 0, -translateZ)
        }
      }
      GL11.glPopMatrix()
    }
  }
}
