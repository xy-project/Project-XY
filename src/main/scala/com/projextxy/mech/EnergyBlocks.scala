package com.projextxy.mech

import com.projextxy.util.LogHelper
import cpw.mods.fml.common.registry.GameRegistry
import net.minecraft.block.Block
import net.minecraft.item.ItemBlock

object EnergyBlocks {


  def init() {
  }


  def registerBlock(block: Block, itemBlock: Class[_ <: ItemBlock]) = {
    if ("tile.null" == block.getUnlocalizedName) throw new RuntimeException(String.format("Block is miising a proper name: %s", block.getClass.getName))
    LogHelper.info("Registering Block: \"%s\" as \"%s\" with item \"%s\"", block.getClass.getSimpleName, block.getUnlocalizedName.substring("tile.".length), itemBlock.getSimpleName)
    GameRegistry.registerBlock(block, itemBlock, block.getUnlocalizedName.substring("tile.".length))
  }

  def registerBlock(block: Block): Block = {
    return registerBlock(block, classOf[ItemBlock])
  }
}
