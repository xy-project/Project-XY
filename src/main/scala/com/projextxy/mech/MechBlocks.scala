package com.projextxy.mech


import com.projextxy.core.blocks.item.ItemBlockMetaHandler
import com.projextxy.mech.block.{BlockFabricator, BlockXyBasicMachine}
import com.projextxy.mech.multiblock.{BlockMultiTank, BlockMultiShadow}
import com.projextxy.util.Registry._
import net.minecraft.block.material.Material

object MechBlocks {

  var blockXyBasicMachine: BlockXyBasicMachine = null
  var blockFabricator: BlockFabricator = null

  var blockShadowRock: BlockMultiShadow = null
  var blockShadowAir: BlockMultiShadow = null
  var blockShadowGlass: BlockMultiShadow = null
  var blockShadowWood: BlockMultiShadow = null
  var blockShadowGrass: BlockMultiShadow = null

  var blockMultiBlock: BlockMultiTank = null

  def init(): Unit = {
    blockXyBasicMachine = new BlockXyBasicMachine
    blockFabricator = new BlockFabricator

    blockShadowRock = new BlockMultiShadow(Material.rock, "Rock")
    blockShadowAir = new BlockMultiShadow(Material.air, "Air")
    blockShadowGlass = new BlockMultiShadow(Material.glass, "Glass")
    blockShadowWood = new BlockMultiShadow(Material.wood, "Wood")
    blockShadowGrass = new BlockMultiShadow(Material.ground, "Grass")

    blockMultiBlock = new BlockMultiTank()

    registerBlock(blockXyBasicMachine, classOf[ItemBlockMetaHandler])
    registerBlock(blockFabricator)
    registerBlock(blockMultiBlock)

    registerBlock(blockShadowRock)
    registerBlock(blockShadowAir)
    registerBlock(blockShadowGlass)
    registerBlock(blockShadowWood)
    registerBlock(blockShadowGrass)
  }
}
