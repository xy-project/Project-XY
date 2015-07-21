package com.projextxy.core.tile

import com.projextxy.core.tile.traits.TMachineTile
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.inventory.ISidedInventory
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.tileentity.TileEntity

/**
 * Created on 7/13/2015.
 */
//TODO
class TileXyColorizer extends TileEntity with TMachineTile with ISidedInventory {
  override def writeCommon(compound: NBTTagCompound) = {}

  override def readCommon(compound: NBTTagCompound) = {}

  override def canExtractItem(slot: Int, stack: ItemStack, side: Int): Boolean = slot == 1

  override def canInsertItem(p_102007_1_ : Int, p_102007_2_ : ItemStack, p_102007_3_ : Int): Boolean = ???

  override def getAccessibleSlotsFromSide(p_94128_1_ : Int): Array[Int] = ???

  override def decrStackSize(p_70298_1_ : Int, p_70298_2_ : Int): ItemStack = ???

  override def closeInventory() = {}

  override def getSizeInventory: Int = ???

  override def getInventoryStackLimit: Int = ???

  override def isItemValidForSlot(p_94041_1_ : Int, p_94041_2_ : ItemStack): Boolean = ???

  override def getStackInSlotOnClosing(p_70304_1_ : Int): ItemStack = ???

  override def openInventory() = {}

  override def setInventorySlotContents(p_70299_1_ : Int, p_70299_2_ : ItemStack) = {}

  override def isUseableByPlayer(p_70300_1_ : EntityPlayer): Boolean = ???

  override def getStackInSlot(p_70301_1_ : Int): ItemStack = ???

  override def hasCustomInventoryName: Boolean = ???

  override def getInventoryName: String = ???
}
