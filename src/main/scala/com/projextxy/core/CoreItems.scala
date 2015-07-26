package com.projextxy.core

import com.projextxy.core.item.{ItemXy, ItemXychoridite, ItemXychorium}
import com.projextxy.util.LogHelper
import cpw.mods.fml.common.registry.GameRegistry
import net.minecraft.item.Item

object CoreItems {

  var itemXychoridite: ItemXychoridite = null
  var itemXychorium: ItemXychorium = null
  var itemXyColorMatrixUpgrade: Item = null

  def init() = {
    itemXychorium = new ItemXychorium()
    itemXychoridite = new ItemXychoridite()

    itemXyColorMatrixUpgrade = new ItemXy()
    itemXyColorMatrixUpgrade.setTextureName("itemXyColorMatrixUpgrade")
    itemXyColorMatrixUpgrade.setUnlocalizedName("itemXyColorMatrixUpgrade")
    itemXyColorMatrixUpgrade.setCreativeTab(ProjectXYCore.tabItem)

    registerItem(itemXychorium)
    registerItem(itemXychoridite)
    registerItem(itemXyColorMatrixUpgrade)
  }

  def registerItem(item: Item): Item = {
    if ("item.null" == item.getUnlocalizedName) throw new RuntimeException(String.format("Item is missing a proper name: %s", item.getClass.getName))
    LogHelper.info("Registering Item: \"%s\" as \"%s\"", item.getClass.getSimpleName, item.getUnlocalizedName.substring("item.".length))
    GameRegistry.registerItem(item, item.getUnlocalizedName.substring("item.".length))
    item
  }
}
