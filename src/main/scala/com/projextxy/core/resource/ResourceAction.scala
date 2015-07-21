package com.projextxy.core.resource

import net.minecraft.client.Minecraft
import net.minecraft.util.ResourceLocation

class ResourceAction(loc: ResourceLocation) {
  def mc = Minecraft.getMinecraft

  def bind() {
    mc.renderEngine.bindTexture(loc)
  }
}
