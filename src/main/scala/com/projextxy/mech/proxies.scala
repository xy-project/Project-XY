package com.projextxy.mech

import codechicken.core.featurehack.FeatureHack
import codechicken.lib.packet.PacketCustom
import codechicken.lib.world.WorldExtensionManager
import com.projextxy.core.client.BlockIconRegistry
import com.projextxy.mech.client.{RenderBlockMultiShadow, TileFabricatorRenderer}
import com.projextxy.mech.multiblock._
import com.projextxy.mech.tile.TileFabricator
import cpw.mods.fml.client.registry.{ClientRegistry, RenderingRegistry}
import cpw.mods.fml.common.event.{FMLInitializationEvent, FMLPostInitializationEvent, FMLPreInitializationEvent}
import cpw.mods.fml.common.registry.GameRegistry
import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.minecraftforge.common.MinecraftForge

class CommonProxy {
  def preInit(event: FMLPreInitializationEvent) {
    MechBlocks.init()
    MechItems.init()
    MinecraftForge.EVENT_BUS.register(MultiBlockOverlayRenderer)
    GameRegistry.registerTileEntity(classOf[TileFabricator], ProjectXYMech.MOD_ID.toLowerCase + ".tileFabricator")
    GameRegistry.registerTileEntity(classOf[TileMultiShadow], ProjectXYMech.MOD_ID.toLowerCase + ".tileMultiShadow")
    GameRegistry.registerTileEntity(classOf[TileValve], ProjectXYMech.MOD_ID.toLowerCase + ".tileValve")
  }

  def init(event: FMLInitializationEvent): Unit = {
    WorldExtensionManager.registerWorldExtension(XYWEI)
    FeatureHack.enableUpdateHook()
  }

  def postInit(event: FMLPostInitializationEvent): Unit = {
    MultiBlocks.init()
  }
}

class ClientProxy extends CommonProxy {
  @SideOnly(Side.CLIENT)
  override def preInit(event: FMLPreInitializationEvent) {
    BlockIconRegistry.add("multiBlockOverlay", "overlay/multiblockOverlay")
    super.preInit(event)
  }

  @SideOnly(Side.CLIENT)
  override def init(event: FMLInitializationEvent) {
    ClientRegistry.bindTileEntitySpecialRenderer(classOf[TileFabricator], new TileFabricatorRenderer)
    RenderingRegistry.registerBlockHandler(new RenderBlockMultiShadow)
    FeatureHack.enableRenderHook()
    PacketCustom.assignHandler(MultiBlocksCPH.CHANNEL, MultiBlocksCPH)
    super.init(event)
  }

  @SideOnly(Side.CLIENT)
  override def postInit(event: FMLPostInitializationEvent) {
    super.postInit(event)
  }
}

object ProjectXYMechProxy extends ClientProxy