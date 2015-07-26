package com.projextxy.core.client

import cpw.mods.fml.common.Mod.EventHandler
import cpw.mods.fml.common.eventhandler.SubscribeEvent
import cpw.mods.fml.common.gameevent.TickEvent
import cpw.mods.fml.common.gameevent.TickEvent.Phase

object RenderTickHandler {
  var renderTime = 0
  var renderFrame = 0.0f

  var r = 255
  var g = 0
  var b = 0

  @SubscribeEvent
  def clientTickEvent(event: TickEvent.ClientTickEvent) {
    if (event.phase == Phase.END) renderTime += 1
  }

  @SubscribeEvent
  def renderTickEvent(event: TickEvent.RenderTickEvent) {
    if (event.phase == Phase.END) renderFrame = event.renderTickTime

    if (r > 0 && b == 0) {
      r -= 1
      g += 1
    } else if (g > 0) {
      g -= 1
      b += 1
    } else if (b > 0) {
      b -= 1
      r += 1
    }
  }

  def getRenderFrame = renderFrame

  def getRenderTime = renderTime + renderFrame
}
