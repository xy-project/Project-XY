package com.projextxy.mech.block

import java.util.Random

import com.projextxy.core.ProjectXYCore
import com.projextxy.core.blocks.glow.BlockXyGlow
import com.projextxy.core.client.render.block.RenderSimpleGlow
import com.projextxy.core.reference.ModColors
import com.projextxy.mech.block.BlockXyBasicMachine._
import net.minecraft.block.Block
import net.minecraft.block.material.{Material, MaterialLiquid}
import net.minecraft.client.renderer.texture.IIconRegister
import net.minecraft.init.Blocks
import net.minecraft.util.IIcon
import net.minecraft.world.{IBlockAccess, World}
import net.minecraftforge.common.IPlantable
import net.minecraftforge.common.util.ForgeDirection

class BlockXyBasicMachine extends BlockXyGlow(Material.rock, RenderSimpleGlow.modelId) {
  setBlockName("blockXyBasicMachine")
  setTickRandomly(true)
  colors = ModColors.basicMachineColors
  val icons = Array.fill[IIcon](colors.length)(null)
  var upIcon: IIcon = null

  override def registerBlockIcons(iconRegister: IIconRegister) {
    def registerMachineIcon(name: String): IIcon = iconRegister.registerIcon(s"${ProjectXYCore.MOD_ID}:overlay/$name")
    icons(XY_WATER_META) = registerMachineIcon("blockXyWater")
    icons(XY_VOID_META) = registerMachineIcon("blockXyVoid")
    icons(XY_ICE_META) = registerMachineIcon("blockXyIce")
    icons(XY_SOIL_META) = registerMachineIcon("blockXySoil")
    upIcon = registerMachineIcon("basicMachineTop")
  }


  override def onNeighborBlockChange(world: World, x: Int, y: Int, z: Int, block: Block) {
    searchForAll(world, x, y, z)
  }

  override def onBlockAdded(world: World, x: Int, y: Int, z: Int): Unit = {
    searchForAll(world, x, y, z)
  }

  def searchForAll(world: World, x: Int, y: Int, z: Int): Unit = {
    world.getBlockMetadata(x, y, z) match {
      case XY_WATER_META =>
        //Water
        //Not the best... But it works
        searchForBlockAndReplaceWith(world, x, y, z, Blocks.flowing_lava, Blocks.obsidian, Some(0))
        searchForBlockAndReplaceWith(world, x, y, z, Blocks.lava, Blocks.obsidian, Some(0))
        searchForBlockAndReplaceWith(world, x, y, z, Blocks.flowing_lava, Blocks.cobblestone, None)
        searchForBlockAndReplaceWith(world, x, y, z, Blocks.lava, Blocks.cobblestone, None)
      case XY_VOID_META =>
        //Void
        searchForLiquid(world, x, y, z)
      case XY_ICE_META =>
        //Ice
        searchForBlockAndReplaceWith(world, x, y, z, Blocks.water, Blocks.ice, None)
        searchForBlockAndReplaceWith(world, x, y, z, Blocks.flowing_water, Blocks.ice, None)
      case _ =>
    }
  }

  def searchForLiquid(world: World, x: Int, y: Int, z: Int) {
    for (direction <- ForgeDirection.values()) {
      val offsetBlock = world.getBlock(x + direction.offsetX, y + direction.offsetY, z + direction.offsetZ)
      if (offsetBlock.getMaterial.isInstanceOf[MaterialLiquid])
        world.setBlockToAir(x + direction.offsetX, y + direction.offsetY, z + direction.offsetZ)
    }
  }

  def searchForBlockAndReplaceWith(world: World, x: Int, y: Int, z: Int, block: Block, replaceWith: Block, meta: Option[Int]) {
    for (direction <- ForgeDirection.values()) {
      val offsetBlock = world.getBlock(x + direction.offsetX, y + direction.offsetY, z + direction.offsetZ)
      if (offsetBlock == block)
        if (meta.isDefined) {
          if (world.getBlockMetadata(x + direction.offsetX, y + direction.offsetY, z + direction.offsetZ) == meta.get) {
            world.setBlock(x + direction.offsetX, y + direction.offsetY, z + direction.offsetZ, replaceWith)
          }
        } else {
          world.setBlock(x + direction.offsetX, y + direction.offsetY, z + direction.offsetZ, replaceWith)
        }
    }
  }

  override def updateTick(world: World, x: Int, y: Int, z: Int, rand: Random): Unit = {
    if (world.getBlockMetadata(x, y, z) == XY_SOIL_META)
      world.getBlock(x, y + 1, z) match {
        case blockBasicMachine: BlockXyBasicMachine =>
          if (world.getBlockMetadata(x, y + 1, z) == 3)
            blockBasicMachine.updateTick(world, x, y + 1, z, rand)
        case plantable: IPlantable =>
          plantable.updateTick(world, x, y + 1, z, rand)
        case _ =>
      }
  }

  override def canSustainPlant(world: IBlockAccess, x: Int, y: Int, z: Int, direction: ForgeDirection, plantable: IPlantable): Boolean = world.getBlockMetadata(x, y, z) == XY_SOIL_META

  override def isFertile(world: World, x: Int, y: Int, z: Int): Boolean = world.getBlockMetadata(x, y, z) == XY_SOIL_META

  override def getIcon(side: Int, meta: Int): IIcon = if (side == ForgeDirection.UP.ordinal()) upIcon else icons(meta)
}

object BlockXyBasicMachine {
  val XY_SOIL_META = 3
  val XY_ICE_META = 2
  val XY_VOID_META = 1
  val XY_WATER_META = 0
}
