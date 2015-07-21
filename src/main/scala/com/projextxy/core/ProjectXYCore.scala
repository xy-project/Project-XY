package com.projextxy.core

import com.projextxy.core.handler.GuiHandler
import com.projextxy.core.item.CoreItems
import com.projextxy.core.reference.EnumStained
import cpw.mods.fml.common.Mod
import cpw.mods.fml.common.Mod.EventHandler
import cpw.mods.fml.common.event.{FMLInitializationEvent, FMLPostInitializationEvent, FMLPreInitializationEvent}
import cpw.mods.fml.common.network.NetworkRegistry
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.item.{Item, ItemStack}

@Mod(modid = "ProjectXyCore", name = "Project XY", modLanguage = "scala")
object ProjectXYCore {
  val MOD_ID = "ProjectXyCore"

  val tabBlock: CreativeTabs = new CreativeTabs(CreativeTabs.getNextID, MOD_ID.toLowerCase + ".tabCoreBlocks") {
    override def getIconItemStack: ItemStack = new ItemStack(CoreBlocks.blockXyOre, 1, 2)

    def getTabIconItem: Item = getIconItemStack.getItem
  }

  val tabItem: CreativeTabs = new CreativeTabs(CreativeTabs.getNextID, MOD_ID.toLowerCase + ".tabCoreItems") {
    override def getIconItemStack: ItemStack = new ItemStack(CoreItems.itemXychorium, 1, 2)

    def getTabIconItem: Item = getIconItemStack.getItem
  }

  var instance = this

  @EventHandler def init(event: FMLInitializationEvent) {
    NetworkRegistry.INSTANCE.registerGuiHandler(MOD_ID, new GuiHandler)
    ProjectXYCoreProxy.init(event)
  }

  @EventHandler def preInit(event: FMLPreInitializationEvent) {
    ProjectXYCoreProxy.preInit(event)
  }


  @EventHandler def postInit(event: FMLPostInitializationEvent) {
    ProjectXYCoreProxy.postInit(event)
  }


}
