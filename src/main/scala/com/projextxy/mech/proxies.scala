package com.projextxy.mech

import com.projextxy.core.ProjectXYCore
import com.projextxy.core.tile.TileXyCustomColor
import com.projextxy.mech.tile.TileFabricator
import cpw.mods.fml.common.event.{FMLInitializationEvent, FMLPostInitializationEvent, FMLPreInitializationEvent}
import cpw.mods.fml.common.registry.GameRegistry
import cpw.mods.fml.relauncher.{Side, SideOnly}

class CommonProxy {
  def preInit(event: FMLPreInitializationEvent) {
    MechBlocks.init()
    MechItems.init()
    GameRegistry.registerTileEntity(classOf[TileFabricator], ProjectXYMech.MOD_ID.toLowerCase + ".tileFabricator")
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