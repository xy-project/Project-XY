package com.projextxy.mech

import com.projextxy.util.LogHelper
import cpw.mods.fml.common.registry.GameRegistry
import net.minecraft.item.Item

object MechItems {
  var itemXynergyPart: ItemXynergy = _

  def init() {
    itemXynergyPart = new ItemXynergy

    //FIXME: registerItem(itemXynergyPart)
  }

  def registerItem(item: Item): Item = {
    if ("item.null" == item.getUnlocalizedName) throw new RuntimeException(String.format("Item is missing a proper name: %s", item.getClass.getName))
    LogHelper.info("Registering Item: \"%s\" as \"%s\"", item.getClass.getSimpleName, item.getUnlocalizedName.substring("item.".length))
    GameRegistry.registerItem(item, item.getUnlocalizedName.substring("item.".length))
    item
  }
}
