package com.projextxy.core

import codechicken.lib.packet.PacketCustom
import com.projextxy.core.client.render.block.{RenderConnectedTexture, RenderCustomGlow, RenderSimpleGlow}
import com.projextxy.core.client.render.item.{RenderXyCustomItemBlock, XychoriumlItemRenderer}
import com.projextxy.core.client.{AnimationFX, CTRegistry, RenderTickHandler}
import com.projextxy.core.generator.WorldGeneratorManager
import com.projextxy.core.tile.{TileColorizer, TileXyCustomColor}
import com.projextxy.util.Registry
import cpw.mods.fml.client.registry.RenderingRegistry
import cpw.mods.fml.common.FMLCommonHandler
import cpw.mods.fml.common.event.{FMLInitializationEvent, FMLPostInitializationEvent, FMLPreInitializationEvent}
import cpw.mods.fml.common.registry.GameRegistry
import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.minecraft.item.Item
import net.minecraftforge.client.MinecraftForgeClient
import net.minecraftforge.common.MinecraftForge

class CommonProxy {
  var rainbowColors = Array.fill[(Int, Int, Int)](8)((0, 0, 0))

  def preInit(event: FMLPreInitializationEvent) {
    CoreBlocks.init()
    CoreItems.init()
    GameRegistry.registerTileEntity(classOf[TileXyCustomColor], ProjectXYCore.MOD_ID.toLowerCase + ".tileColorCustomRGB")
    GameRegistry.registerTileEntity(classOf[TileColorizer], ProjectXYCore.MOD_ID.toLowerCase + ".tileColorizer")
  }

  def init(event: FMLInitializationEvent) {
    PacketCustom.assignHandler(ProjectXYCore.MOD_ID, ProjectXySPH)
    GameRegistry.registerWorldGenerator(new WorldGeneratorManager, 0)
    RecipeHandler.init()
  }

  def postInit(event: FMLPostInitializationEvent): Unit = {
    Registry.file.close()

    val freq: Double = math.Pi * 2 / rainbowColors.length
    for (j <- rainbowColors.indices) {
      val red = math.sin(freq * j + 0) * 127 + 128
      val green = math.sin(freq * j + 2) * 127 + 128
      val blue = math.sin(freq * j + 4) * 127 + 128
      rainbowColors(j) = (math.floor(red).toInt, math.floor(green).toInt, math.floor(blue).toInt)
    }

  }
}

class ClientProxy extends CommonProxy {
  var animationFx: AnimationFX = null

  @SideOnly(Side.CLIENT)
  override def preInit(event: FMLPreInitializationEvent): Unit = {
    super.preInit(event)

    MinecraftForge.EVENT_BUS.register(RenderTickHandler)
  }

  @SideOnly(Side.CLIENT)
  override def init(event: FMLInitializationEvent): Unit = {
    super.init(event)
    CTRegistry.init()
    animationFx = new AnimationFX
    RenderingRegistry.registerBlockHandler(new RenderSimpleGlow)
    RenderingRegistry.registerBlockHandler(new RenderCustomGlow)
    RenderingRegistry.registerBlockHandler(new RenderConnectedTexture)

    MinecraftForgeClient.registerItemRenderer(CoreItems.itemXychorium, new XychoriumlItemRenderer)
    MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(CoreBlocks.blockXyCustom), new RenderXyCustomItemBlock)
    MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(CoreBlocks.blockXyColorizer), new RenderXyCustomItemBlock)
    PacketCustom.assignHandler(ProjectXYCore.MOD_ID, ProjectXyCPH)

  }

  @SideOnly(Side.CLIENT)
  override def postInit(event: FMLPostInitializationEvent) = {
    FMLCommonHandler.instance.bus.register(RenderTickHandler)
    MinecraftForge.EVENT_BUS.register(RenderTickHandler)
    super.postInit(event)
  }
}

object ProjectXYCoreProxy extends ClientProxy
