package com.projextxy.core

import com.projextxy.core.blocks.glow._
import com.projextxy.core.blocks.item.{ItemBLockColorContainer, ItemBlockColor}
import com.projextxy.core.blocks.traits.TConnectedTextureBlock
import com.projextxy.core.client.CTRegistry
import com.projextxy.core.client.render.block.RenderSimpleGlow
import com.projextxy.core.reference.ModColors
import com.projextxy.util.LogHelper
import cpw.mods.fml.common.registry.GameRegistry
import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.item.ItemBlock

object CoreBlocks {
  var blockXyBrick: BlockXyGlow = null
  var blockXyBrickColored: BlockXyGlow = null
  var blockXyBrickInverted: BlockXyGlow = null
  var blockXyBrickInvertedWhite: BlockXyGlow = null
  var blockXyBrickFancy: BlockXyGlow = null
  var blockXyBrickFancyInverted: BlockXyGlow = null
  var blockXyBrickFancyInvertedWhite: BlockXyGlow = null

  var blockXyStorage: BlockXyGlow = null
  var blockXyOre: BlockXyOre = null
  var blockXyCustom: BlockXyCustom = null
  var blockXyColorizer: BlockXyColorizer = null
  var blockXyMachineBlock: BlockXyGlow = null


  var blockXyMachineBlockWhite: BlockXyGlow = null

  def init() {
    blockXyOre = new BlockXyOre()
    blockXyStorage = new BlockXyGlow(Material.rock, RenderSimpleGlow.modelId)
    blockXyStorage.setBlockName("blockXyStorage")
    blockXyStorage.colors = ModColors.xyColors

    registerBlock(blockXyOre, classOf[ItemBlockColor])
    registerBlock(blockXyStorage, classOf[ItemBlockColor])

    initBricks()
    initMachineBlocks()

    blockXyCustom = new BlockXyCustom(List(
      blockXyStorage,
      blockXyBrick,
      blockXyBrickInverted,
      blockXyBrickInvertedWhite,
      blockXyBrickColored,
      blockXyBrickFancy,
      blockXyBrickFancyInverted,
      blockXyBrickFancyInvertedWhite,
      blockXyMachineBlock,
      blockXyMachineBlockWhite))
    blockXyColorizer = new BlockXyColorizer()

    registerBlock(blockXyCustom, classOf[ItemBLockColorContainer])
    registerBlock(blockXyColorizer, classOf[ItemBLockColorContainer])
  }

  private def initBricks(): Unit = {
    blockXyBrick = new BlockXyGlow(Material.rock, RenderSimpleGlow.modelId) with ColorMultiplier
    blockXyBrick.setBlockName("blockXyBrick")
    blockXyBrick.colors = ModColors.xyColors

    blockXyBrickColored = new BlockXyGlow(Material.rock, RenderSimpleGlow.modelId) with ColorMultiplier
    blockXyBrickColored.setBlockName("blockXyBrickColored")
    blockXyBrickColored.colors = ModColors.xyColors

    blockXyBrickInverted = new BlockXyGlow(Material.rock, RenderSimpleGlow.modelId)
    blockXyBrickInverted.setBlockName("blockXyBrickInverted")
    blockXyBrickInverted.colors = ModColors.xyColors

    blockXyBrickInvertedWhite = new BlockXyGlow(Material.rock, RenderSimpleGlow.modelId)
    blockXyBrickInvertedWhite.setBlockName("blockXyBrickInvertedWhite")
    blockXyBrickInvertedWhite.colors = ModColors.xyColors

    blockXyBrickFancy = new BlockXyGlow(Material.rock, RenderSimpleGlow.modelId) with ColorMultiplier
    blockXyBrickFancy.setBlockName("blockXyBrickFancy")
    blockXyBrickFancy.colors = ModColors.xyColors

    blockXyBrickFancyInverted = new BlockXyGlow(Material.rock, RenderSimpleGlow.modelId)
    blockXyBrickFancyInverted.setBlockName("blockXyBrickFancyInverted")
    blockXyBrickFancyInverted.colors = ModColors.xyColors

    blockXyBrickFancyInvertedWhite = new BlockXyGlow(Material.rock, RenderSimpleGlow.modelId)
    blockXyBrickFancyInvertedWhite.setBlockName("blockXyBrickFancyInvertedWhite")
    blockXyBrickFancyInvertedWhite.colors = ModColors.xyColors

    registerBlock(blockXyBrick, classOf[ItemBlockColor])
    registerBlock(blockXyBrickColored, classOf[ItemBlockColor])
    registerBlock(blockXyBrickInverted, classOf[ItemBlockColor])
    registerBlock(blockXyBrickInvertedWhite, classOf[ItemBlockColor])
    registerBlock(blockXyBrickFancy, classOf[ItemBlockColor])
    registerBlock(blockXyBrickFancyInverted, classOf[ItemBlockColor])
    registerBlock(blockXyBrickFancyInvertedWhite, classOf[ItemBlockColor])
  }

  private def initMachineBlocks(): Unit = {
    blockXyMachineBlock = new BlockXyGlow(Material.rock, RenderSimpleGlow.modelId) with TConnectedTextureBlock {
      val connectedFolder = CTRegistry.XY_MACHINE_FOLDER
    }
    blockXyMachineBlock.setBlockName("blockXyMachineBlock")
    blockXyMachineBlock.colors = ModColors.xyColors

    blockXyMachineBlockWhite = new BlockXyGlow(Material.rock, RenderSimpleGlow.modelId) with TConnectedTextureBlock {
      val connectedFolder = CTRegistry.XY_MACHINE_WHITE_FOLDER
    }
    blockXyMachineBlockWhite.setBlockName("blockXyMachineBlockWhite")
    blockXyMachineBlockWhite.colors = ModColors.xyColors

    registerBlock(blockXyMachineBlock, classOf[ItemBlockColor])
    registerBlock(blockXyMachineBlockWhite, classOf[ItemBlockColor])
  }

  def registerBlock(block: Block, itemBlock: Class[_ <: ItemBlock]) = {
    if ("tile.null" == block.getUnlocalizedName) throw new RuntimeException(String.format("Block is miising a proper name: %s", block.getClass.getName))
    LogHelper.info("Registering Block: \"%s\" as \"%s\" with item \"%s\"", block.getClass.getSimpleName, block.getUnlocalizedName.substring("tile.".length), itemBlock.getSimpleName)
    GameRegistry.registerBlock(block, itemBlock, block.getUnlocalizedName.substring("tile.".length))
  }

  def registerBlock(block: Block): Block = registerBlock(block, classOf[ItemBlock])
}
