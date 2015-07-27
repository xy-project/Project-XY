package com.projextxy.core

import com.projextxy.core.blocks.glow._
import com.projextxy.core.blocks.item.{ItemBLockColorContainer, ItemBlockColor, ItemBlockMetaHandler}
import com.projextxy.core.blocks.traits.{ColorMultiplier, TConnectedTextureBlock}
import com.projextxy.core.blocks.{BlockXyColored, BlockXyDecor, BlockXyStone}
import com.projextxy.core.client.CTRegistry
import com.projextxy.core.client.render.block.RenderSimpleGlow
import com.projextxy.core.reference.{MCColors, ModColors}
import com.projextxy.util.Registry._
import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.item.ItemBlock

object CoreBlocks {
  var blockXyOre: BlockXyOre = null
  var blockXyStorage: BlockXyGlow = null
  var blockXyGlass: BlockXyDecor = null
  var blockXyDecor: BlockXyDecor = null

  var blockXychoriumBrick:BlockXyColored = null
  var blockXychoriumBrickFancy:BlockXyColored = null
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
    blockXyStorage = createXyGlowConnected("blockXyStorage", CTRegistry.XY_STORAGE_FOLDER, Material.rock, None, colorMultiplier = false)
    blockXychoriumBrick = new BlockXyColored()
    blockXychoriumBrick.setBlockName("blockXychoriumBrick")

    blockXychoriumBrickFancy = new BlockXyColored()
    blockXychoriumBrickFancy.setBlockName("blockXychoriumBrickFancy")


    blockXyDecor = new BlockXyDecor(List(CTRegistry.XY_STONE_CARVED_FOLDER), Material.rock, false)
    blockXyDecor.setBlockName("blockXyDecor")
    blockXyGlass = new BlockXyDecor(List(CTRegistry.GLASS_VIEWER_FOLDER, CTRegistry.CLEAN_CONNECTED_FOLDER), Material.glass, true)
    blockXyGlass.setStepSound(Block.soundTypeGlass)
    blockXyGlass.setBlockName("blockXyGlass")

    registerBlock(blockXyStone, classOf[ItemBlock])
    registerBlock(blockXyDecor, classOf[ItemBlockMetaHandler])
    registerBlock(blockXyGlass, classOf[ItemBlockMetaHandler])
    registerBlock(blockXyOre, classOf[ItemBlockColor])
    registerBlock(blockXyStorage, classOf[ItemBlockColor])
    registerBlock(blockXychoriumBrick, classOf[ItemBlockMetaHandler])
    registerBlock(blockXychoriumBrickFancy, classOf[ItemBlockMetaHandler])

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

    registerBlock(blockXyCustom, classOf[ItemBLockColorContainer])
    registerBlock(blockXyColorizer, classOf[ItemBLockColorContainer])
  }

  private def initBricks(): Unit = {
    blockXyBrick = createXyGlow("blockXyBrick", colorMultiplier = true)
    blockXyBrickInverted = createXyGlow("blockXyBrickInverted")
    blockXyBrickInvertedWhite = createXyGlow("blockXyBrickInvertedWhite")
    blockXyBrickFancy = createXyGlow("blockXyBrickFancy", colorMultiplier = true)
    blockXyBrickFancyInverted = createXyGlow("blockXyBrickFancyInverted")
    blockXyBrickFancyInvertedWhite = createXyGlow("blockXyBrickFancyInvertedWhite")

    registerBlock(blockXyBrick, classOf[ItemBlockColor])
    registerBlock(blockXyBrickInverted, classOf[ItemBlockColor])
    registerBlock(blockXyBrickInvertedWhite, classOf[ItemBlockColor])
    registerBlock(blockXyBrickFancy, classOf[ItemBlockColor])
    registerBlock(blockXyBrickFancyInverted, classOf[ItemBlockColor])
    registerBlock(blockXyBrickFancyInvertedWhite, classOf[ItemBlockColor])
  }

  def createXyGlow(name: String): BlockXyGlow = createXyGlow(name, Material.rock, None, colorMultiplier = false)

  def createXyGlow(name: String, colorMultiplier: Boolean): BlockXyGlow = createXyGlow(name, Material.rock, None, colorMultiplier)

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

  def createXyGlowConnected(name: String, texture: String, mat: Material, option: Option[List[MCColors]], colorMultiplier: Boolean): BlockXyGlow = {
    val block = if (colorMultiplier)
      new BlockXyGlow(mat, RenderSimpleGlow.modelId) with TConnectedTextureBlock with ColorMultiplier {
        val connectedFolder = texture
      }
    else new BlockXyGlow(mat, RenderSimpleGlow.modelId) with TConnectedTextureBlock {
      val connectedFolder = texture
    }
    block.setBlockName(name)
    if (option.isDefined)
      block.colors = option.get
    block
  }

  def createXyGlow(name: String, mat: Material, colorMultiplier: Boolean): BlockXyGlow = createXyGlow(name, mat, None, colorMultiplier)

  def createXyGlow(name: String, material: Material, colors: Option[List[MCColors]], colorMultiplier: Boolean): BlockXyGlow = {
    val block: BlockXyGlow = if (colorMultiplier) new BlockXyGlow(material, RenderSimpleGlow.modelId) with ColorMultiplier else new BlockXyGlow(material, RenderSimpleGlow.modelId)
    block.setBlockName(name)
    if (colors.isDefined)
      blockXyBrick.colors = colors.get
    block
  }
}
