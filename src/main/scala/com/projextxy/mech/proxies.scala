package com.projextxy.mech

import cpw.mods.fml.common.event.{FMLInitializationEvent, FMLPostInitializationEvent, FMLPreInitializationEvent}
import cpw.mods.fml.relauncher.{Side, SideOnly}

class CommonProxy {
  def preInit(event: FMLPreInitializationEvent) {
    MechBlocks.init()
    MechItems.init()
  }

  def init(event: FMLInitializationEvent) {
  }

  def postInit(event: FMLPostInitializationEvent) {
  }
}

class ClientProxy extends CommonProxy {
  @SideOnly(Side.CLIENT)
  override def preInit(event: FMLPreInitializationEvent) {
    super.preInit(event)
  }

  @SideOnly(Side.CLIENT)
  override def init(event: FMLInitializationEvent) {
    super.init(event)
  }

  @SideOnly(Side.CLIENT)
  override def postInit(event: FMLPostInitializationEvent) {
    super.postInit(event)
  }
}

object ProjectXYMechProxy extends ClientProxy