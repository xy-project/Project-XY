package com.projextxy.core.client

import codechicken.lib.colour.ColourRGBA
import cpw.mods.fml.common.eventhandler.SubscribeEvent
import cpw.mods.fml.common.gameevent.TickEvent
import cpw.mods.fml.common.gameevent.TickEvent.Phase
import sun.plugin2.util.ColorUtil.ColorRGB

object RenderTickHandler {
  var renderTime = 0
  var renderFrame = 0.0f

  @SubscribeEvent
  def clientTick(event: TickEvent.ClientTickEvent) {
    if (event.phase == Phase.END) renderTime += 1
  }

  @SubscribeEvent
  def renderTick(event: TickEvent.RenderTickEvent) {
    if (event.phase == Phase.END) renderFrame = event.renderTickTime
  }

  def getRenderFrame = renderFrame

  def getRenderTime = renderTime + renderFrame
}
