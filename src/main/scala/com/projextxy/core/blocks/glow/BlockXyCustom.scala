package com.projextxy.core.blocks.glow

import java.util

import com.projextxy.core.ProjectXYCoreProxy
import com.projextxy.core.blocks.BlockXy
import com.projextxy.core.blocks.traits.MachineBlock
import com.projextxy.core.client.render.block.RenderCustomGlow
import com.projextxy.core.tile.TileXyCustomColor
import net.minecraft.block.material.Material
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.entity.EnumCreatureType
import net.minecraft.item.{Item, ItemStack}
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.IIcon
import net.minecraft.world.{IBlockAccess, World}


class BlockXyCustom(blocks: List[BlockXyGlow]) extends BlockXy(Material.rock) with MachineBlock {
  setBlockName("blockXyCustom")
  setHardness(0.5F)

  val sub_blocks: List[BlockXyGlow] = blocks

  override def createNewTileEntity(world: World, meta: Int): TileEntity = new TileXyCustomColor

  override def getIcon(side: Int, meta: Int): IIcon = blocks(meta).getIcon(side, meta)

  override def getIcon(world: IBlockAccess, x: Int, y: Int, z: Int, side: Int): IIcon = blocks(world.getBlockMetadata(x, y, z)).getIcon(world, x, y, z, side)

  override def getSubBlocks(item: Item, tabs: CreativeTabs, list: util.List[_]) = {
    for (i <- blocks.indices) {
      list.asInstanceOf[util.List[ItemStack]].add(buildColoredStack(i, 255, 255, 255))
      for (color <- ProjectXYCoreProxy.rainbowColors) {
        list.asInstanceOf[util.List[ItemStack]].add(buildColoredStack(i, color.r, color.g, color.b))
      }
    }
  }


  def buildColoredStack(meta: Int, r: Int, g: Int, b: Int): ItemStack = {
    val stack = new ItemStack(this, 1, meta)
    stack.stackTagCompound = new NBTTagCompound

    stack.stackTagCompound.setBoolean("xy.abstractMachine", true)
    stack.stackTagCompound.setInteger("r", r)
    stack.stackTagCompound.setInteger("g", g)
    stack.stackTagCompound.setInteger("b", b)

    stack
  }

  override def colorMultiplier(world: IBlockAccess, x: Int, y: Int, z: Int): Int = {
    blocks(world.getBlockMetadata(x, y, z)) match {
      case block: ColorMultiplier =>
        world.getTileEntity(x, y, z) match {
          case custom: TileXyCustomColor => custom.color
          case _ => super.colorMultiplier(world, x, y, z)
        }
      case _ => super.colorMultiplier(world, x, y, z)
    }
  }

  override def canCreatureSpawn(creatureType: EnumCreatureType, world: IBlockAccess, x: Int, y: Int, z: Int): Boolean = false

  override def getRenderType: Int = RenderCustomGlow.renderId
}


