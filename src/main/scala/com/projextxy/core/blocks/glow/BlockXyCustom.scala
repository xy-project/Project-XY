package com.projextxy.core.blocks.glow

import java.util

import com.projextxy.core.blocks.BlockXy
import com.projextxy.core.blocks.glow.BlockXyCustom.buildColoredStack
import com.projextxy.core.blocks.traits.{MachineBlock, TColorBlock}
import com.projextxy.core.client.render.block.RenderCustomGlow
import com.projextxy.core.tile.{TileColorizer, TileXyCustomColor}
import com.projextxy.core.{ProjectXYCore, ProjectXYCoreProxy}
import net.minecraft.block.material.Material
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.entity.EnumCreatureType
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.{Item, ItemStack}
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.{IIcon, MovingObjectPosition}
import net.minecraft.world.{IBlockAccess, World}


class BlockXyCustom(blocks: List[BlockXyGlow]) extends BlockXy(Material.rock) with MachineBlock with TBlockXyGlow {
  setBlockName("blockXyCustom")
  setHardness(0.5F)
  setCreativeTab(ProjectXYCore.tabCustomColored)

  val sub_blocks: List[BlockXyGlow] = blocks

  override def createNewTileEntity(world: World, meta: Int): TileEntity = new TileXyCustomColor

  override def getIcon(side: Int, meta: Int): IIcon = blocks(meta).getIcon(side, meta)

  override def getIcon(world: IBlockAccess, x: Int, y: Int, z: Int, side: Int): IIcon = blocks(world.getBlockMetadata(x, y, z)).getIcon(world, x, y, z, side)

  override def getSubBlocks(item: Item, tabs: CreativeTabs, list: util.List[_]) = {
    for (i <- blocks.indices) {
      list.asInstanceOf[util.List[ItemStack]].add(buildColoredStack(this, i, 255, 255, 255))
      for (color <- ProjectXYCoreProxy.rainbowColors) {
        list.asInstanceOf[util.List[ItemStack]].add(buildColoredStack(this, i, color._1, color._2, color._3))
      }
    }
  }


  override def colorMultiplier(world: IBlockAccess, x: Int, y: Int, z: Int): Int = {
    blocks(world.getBlockMetadata(x, y, z)) match {
      case block: TColorBlock =>
        world.getTileEntity(x, y, z) match {
          case custom: TileXyCustomColor => if (block.hasColorMultiplier) custom.color else super.colorMultiplier(world, x, y, z)
          case _ => super.colorMultiplier(world, x, y, z)
        }
      case _ => super.colorMultiplier(world, x, y, z)
    }
  }

  override def canCreatureSpawn(creatureType: EnumCreatureType, world: IBlockAccess, x: Int, y: Int, z: Int): Boolean = false

  override def getRenderType: Int = RenderCustomGlow.renderId

  override def getPickBlock(target: MovingObjectPosition, world: World, x: Int, y: Int, z: Int, player: EntityPlayer): ItemStack = {
    val meta = world.getBlockMetadata(x, y, z)
    val tile = world.getTileEntity(x, y, z)

    tile match {
      case tileColor: TileXyCustomColor => buildColoredStack(this, meta, tileColor.r, tileColor.g, tileColor.b)
      case _ => super.getPickBlock(target, world, x, y, z, player)
    }
  }

  override def damageDropped(meta: Int): Int = meta

  override def customDrops: Boolean = false

  override val hasColorMultiplier: Boolean = false

  override def getColor(world: IBlockAccess, x: Int, y: Int, z: Int): Int = {
    world.getTileEntity(x, y, z) match {
      case tile: TileColorizer => tile.getColor
      case _ =>
    }
    0
  }

  override def getColor(meta: Int): Int = 0xFFF
}

object BlockXyCustom {
  def buildColoredStack(custom: BlockXyCustom, meta: Int, r: Int, g: Int, b: Int): ItemStack = {
    val stack = new ItemStack(custom, 1, meta)
    stack.stackTagCompound = new NBTTagCompound

    stack.stackTagCompound.setBoolean("xy.machine", true)
    stack.stackTagCompound.setInteger("r", r)
    stack.stackTagCompound.setInteger("g", g)
    stack.stackTagCompound.setInteger("b", b)

    stack
  }
}


