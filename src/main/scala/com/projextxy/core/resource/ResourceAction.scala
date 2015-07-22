package com.projextxy.core.resource

import net.minecraft.client.Minecraft
import net.minecraft.util.ResourceLocation

class ResourceAction(loc: ResourceLocation) {
  def bind() {
    mc.renderEngine.bindTexture(loc)
  }

  def mc = Minecraft.getMinecraft
}
