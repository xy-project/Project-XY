package com.projextxy.core

import com.projextxy.core.handler.GuiHandler
import cpw.mods.fml.common.Mod
import cpw.mods.fml.common.Mod.EventHandler
import cpw.mods.fml.common.event.{FMLInitializationEvent, FMLPostInitializationEvent, FMLPreInitializationEvent}
import cpw.mods.fml.common.network.NetworkRegistry
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.item.{Item, ItemStack}
import net.minecraft.nbt.NBTTagCompound

@Mod(modid = "ProjectXY", name = "Project XY", modLanguage = "scala", dependencies = "required-before:ProjectXYMech;required-after:CodeChickenCore;")
object ProjectXYCore {
  val MOD_ID = "ProjectXY"

  val tabBlock: CreativeTabs = new CreativeTabs(CreativeTabs.getNextID, MOD_ID.toLowerCase + ".tabCoreBlocks") {
    override def getIconItemStack: ItemStack = new ItemStack(CoreBlocks.blockXyOre, 1, 2)

    def getTabIconItem: Item = getIconItemStack.getItem
  }

  val tabItem: CreativeTabs = new CreativeTabs(CreativeTabs.getNextID, MOD_ID.toLowerCase + ".tabCoreItems") {
    override def getIconItemStack: ItemStack = new ItemStack(CoreItems.itemXychorium, 1, 2)

    def getTabIconItem: Item = getIconItemStack.getItem
  }

  val tabCustomColored = new CreativeTabs(CreativeTabs.getNextID, MOD_ID.toLowerCase + ".tabCustomBlocks") {
    override def getIconItemStack: ItemStack = {
      val stack = new ItemStack(CoreBlocks.blockXyCustom, 1, 0)

      val compound = new NBTTagCompound
      compound.setBoolean("lsd", true)
      stack.stackTagCompound = compound
      stack
    }

    override def getTabIconItem: Item = getIconItemStack.getItem
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
