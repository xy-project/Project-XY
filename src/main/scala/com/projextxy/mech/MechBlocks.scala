package com.projextxy.mech

import com.projextxy.core.blocks.glow.BlockXyGlow
import com.projextxy.core.blocks.item.ItemBlockColor
import com.projextxy.mech.block.BlockXyBasicMachine
import com.projextxy.util.LogHelper
import cpw.mods.fml.common.registry.GameRegistry
import net.minecraft.block.Block
import net.minecraft.item.ItemBlock

object MechBlocks {

  var blockXyBasicMachine: BlockXyGlow = null

  def init(): Unit = {
    blockXyBasicMachine = new BlockXyBasicMachine

    registerBlock(blockXyBasicMachine, classOf[ItemBlockColor])
  }

  def registerBlock(block: Block): Block = {
    return registerBlock(block, classOf[ItemBlock])

  }

  def registerBlock(block: Block, itemBlock: Class[_ <: ItemBlock]) = {
    if ("tile.null" == block.getUnlocalizedName) throw new RuntimeException(String.format("Block is miising a proper name: %s", block.getClass.getName))
    LogHelper.info("Registering Block: \"%s\" as \"%s\" with item \"%s\"", block.getClass.getSimpleName, block.getUnlocalizedName.substring("tile.".length), itemBlock.getSimpleName)
    GameRegistry.registerBlock(block, itemBlock, block.getUnlocalizedName.substring("tile.".length))
  }
}
