package com.projextxy.mech.fmp

import com.projextxy.util.LogHelper

object FMP extends IPartFactory {
  def init() {
    LogHelper.info("Starting FMP Integration")
    MultiPartRegistry.registerParts(this, Array("projectxy_power_receiver", "projectxy_power_provider", "projectxy_power_node"))
  }

  override def createPart(name: String, client: Boolean): TMultiPart = {
    name match {
      case "projectxy_power_receiver" => new ReceiverPart
      case "projectxy_power_provider" => new ProviderPart
      case "projectxy_power_node" => new NodePart
      case _ => null
    }
  }
}
