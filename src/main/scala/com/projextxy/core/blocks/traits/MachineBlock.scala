package com.projextxy.core.blocks.traits

import java.util

import com.google.common.collect.Lists
import com.projextxy.core.tile.TileAbstractMachine
import com.projextxy.core.tile.traits.TMachineTile
import net.minecraft.block.{Block, ITileEntityProvider}
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.world.World

trait MachineBlock extends Block with ITileEntityProvider {
  override def onBlockPlacedBy(world: World, x: Int, y: Int, z: Int, player: EntityLivingBase, stack: ItemStack) = {
    super.onBlockPlacedBy(world, x, y, z, player, stack)
    val tile = world.getTileEntity(x, y, z)
    tile match {
      case machine: TMachineTile =>
        machine.readFromItemStack(stack)
      case _ =>
    }
  }

  override def getDrops(world: World, x: Int, y: Int, z: Int, metadata: Int, fortune: Int): util.ArrayList[ItemStack] = {
    if (!customDrops)
      return super.getDrops(world, x, y, z, metadata, fortune)

    val tile = world.getTileEntity(x, y, z)

    tile match {
      case machine: TMachineTile =>
        val stack = new ItemStack(this, 1, metadata)
        machine.writeToItemStack(stack)
        Lists.newArrayList(stack)
      case _ => super.getDrops(world, x, y, z, metadata, fortune)
    }
  }

  def customDrops: Boolean = true

  override def removedByPlayer(world: World, player: EntityPlayer, x: Int, y: Int, z: Int, willHarvest: Boolean): Boolean = if (willHarvest) true else super.removedByPlayer(world, player, x, y, z, willHarvest)

  override def harvestBlock(world: World, player: EntityPlayer, x: Int, y: Int, z: Int, meta: Int) = {
    super.harvestBlock(world, player, x, y, z, meta)
    world.setBlockToAir(x, y, z)
  }
}
