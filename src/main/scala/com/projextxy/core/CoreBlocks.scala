package com.projextxy.core

import com.projextxy.core.blocks.glow._
import com.projextxy.core.blocks.item.{ItemBLockColorContainer, ItemBlockColor, ItemBlockMetaHandler}
import com.projextxy.core.blocks.{BlockXyColored, BlockXyDecor, BlockXyStone}
import com.projextxy.core.client.CTRegistry
import com.projextxy.util.Registry._
import net.minecraft.block.Block
import net.minecraft.block.material.Material

object CoreBlocks {
  var blockXyOre: BlockXyOre = null
  var blockXyStorage: BlockXyGlow = null
  var blockXyGlass: BlockXyDecor = null
  var blockXyDecor: BlockXyDecor = null

  var blockXychoriumBrick: BlockXyColored = null
  var blockXychoriumBrickFancy: BlockXyColored = null
  var blockXyBrick: BlockXyGlow = null
  var blockXyBrickInverted: BlockXyGlow = null
  var blockXyBrickInvertedWhite: BlockXyGlow = null
  var blockXyBrickFancy: BlockXyGlow = null
  var blockXyBrickFancyInverted: BlockXyGlow = null
  var blockXyBrickFancyInvertedWhite: BlockXyGlow = null

  var blockXyMachineBlock: BlockXyGlow = null
  var blockXyMachineBlockWhite: BlockXyGlow = null

  var blockXyCustom: BlockXyCustom = null
  var blockXyColorizer: BlockXyColorizer = null

  var blockXyStone: BlockXyStone = null


  def init(): Unit = {
    blockXyStone = new BlockXyStone
    blockXyOre = new BlockXyOre()
    blockXyStorage = BlockXyGlowConnected("blockXyStorage", CTRegistry.XY_STORAGE_FOLDER, Material.rock, None, colorMultiplier = false, renderBlockIcon = false)
    blockXychoriumBrick = new BlockXyColored()
    blockXychoriumBrick.setBlockName("blockXychoriumBrick")
    blockXychoriumBrickFancy = new BlockXyColored()
    blockXychoriumBrickFancy.setBlockName("blockXychoriumBrickFancy")


    blockXyDecor = new BlockXyDecor(List(CTRegistry.XY_STONE_CARVED_FOLDER), Material.rock, false)
    blockXyDecor.setBlockName("blockXyDecor")
    blockXyGlass = new BlockXyDecor(List(CTRegistry.GLASS_VIEWER_FOLDER, CTRegistry.CLEAN_CONNECTED_FOLDER), Material.glass, true)
    blockXyGlass.setStepSound(Block.soundTypeGlass)
    blockXyGlass.setBlockName("blockXyGlass")

    initBricks()
    initMachineBlocks()

    blockXyCustom = new BlockXyCustom(List(
      blockXyStorage,
      blockXyBrick,
      blockXyBrickInverted,
      blockXyBrickInvertedWhite,
      blockXyBrickFancy,
      blockXyBrickFancyInverted,
      blockXyBrickFancyInvertedWhite,
      blockXyMachineBlock,
      blockXyMachineBlockWhite))
    blockXyColorizer = new BlockXyColorizer()

    registerAllBlocks()
  }

  private def initBricks(): Unit = {
    blockXyBrick = BlockXyGlow("blockXyBrick", colorMultiplier = true)
    blockXyBrickInverted = BlockXyGlow("blockXyBrickInverted")
    blockXyBrickInvertedWhite = BlockXyGlow("blockXyBrickInvertedWhite")
    blockXyBrickFancy = BlockXyGlow("blockXyBrickFancy", colorMultiplier = true)
    blockXyBrickFancyInverted = BlockXyGlow("blockXyBrickFancyInverted")
    blockXyBrickFancyInvertedWhite = BlockXyGlow("blockXyBrickFancyInvertedWhite")
  }


  private def initMachineBlocks(): Unit = {
    blockXyMachineBlock = BlockXyGlowConnected("blockXyMachineBlock", CTRegistry.CLEAN_CONNECTED_FOLDER, Material.rock, None, colorMultiplier = false, renderBlockIcon = true)
    blockXyMachineBlockWhite = BlockXyGlowConnected("blockXyMachineBlockWhite", CTRegistry.CLEAN_CONNECTED_FOLDER, Material.rock, None, colorMultiplier = false, renderBlockIcon = true)
  }

  private def registerAllBlocks(): Unit = {
    //Ores and resources
    registerBlock(blockXyStone, classOf[ItemBlockMetaHandler])
    registerBlock(blockXyDecor, classOf[ItemBlockMetaHandler])
    registerBlock(blockXyGlass, classOf[ItemBlockMetaHandler])
    registerBlock(blockXyOre, classOf[ItemBlockMetaHandler])

    //Main Xychorium blocks
    registerBlock(blockXyStorage, classOf[ItemBlockMetaHandler])
    registerBlock(blockXychoriumBrick, classOf[ItemBlockMetaHandler])
    registerBlock(blockXychoriumBrickFancy, classOf[ItemBlockMetaHandler])

    //Xychorium Bricks - glowing variants
    registerBlock(blockXyBrick, classOf[ItemBlockMetaHandler])
    registerBlock(blockXyBrickInverted, classOf[ItemBlockMetaHandler])
    registerBlock(blockXyBrickInvertedWhite, classOf[ItemBlockMetaHandler])
    registerBlock(blockXyBrickFancy, classOf[ItemBlockMetaHandler])
    registerBlock(blockXyBrickFancyInverted, classOf[ItemBlockMetaHandler])
    registerBlock(blockXyBrickFancyInvertedWhite, classOf[ItemBlockMetaHandler])

    //Machine Blocks
    registerBlock(blockXyMachineBlock, classOf[ItemBlockMetaHandler])
    registerBlock(blockXyMachineBlockWhite, classOf[ItemBlockMetaHandler])

    //Custom variants
    registerBlock(blockXyCustom, classOf[ItemBLockColorContainer])
    registerBlock(blockXyColorizer, classOf[ItemBLockColorContainer])
  }


}
