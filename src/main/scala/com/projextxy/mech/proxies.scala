package com.projextxy.mech

import com.projextxy.mech.client.TileFabricatorRenderer
import com.projextxy.mech.tile.TileFabricator
import cpw.mods.fml.client.registry.ClientRegistry
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
    ClientRegistry.bindTileEntitySpecialRenderer(classOf[TileFabricator], new TileFabricatorRenderer)
    super.init(event)
  }

  @SideOnly(Side.CLIENT)
  override def postInit(event: FMLPostInitializationEvent) {
    super.postInit(event)
  }
}

object ProjectXYMechProxy extends ClientProxy