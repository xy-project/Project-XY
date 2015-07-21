package com.projextxy.core.tile.traits

import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.tileentity.TileEntity

trait TMachineTile extends TileEntity {
  def writeToItemStack(stack: ItemStack) {
    if (stack == null) return
    if (stack.stackTagCompound == null) stack.stackTagCompound = new NBTTagCompound
    val root: NBTTagCompound = stack.stackTagCompound
    root.setBoolean("xy.abstractMachine", true)
    writeCommon(root)
  }

  def readFromItemStack(stack: ItemStack) {
    if (stack == null || stack.stackTagCompound == null) return
    val root: NBTTagCompound = stack.stackTagCompound
    if (!root.hasKey("xy.abstractMachine")) return
    readCommon(root)
  }

  def writeCommon(compound: NBTTagCompound)

  def readCommon(compound: NBTTagCompound)

  override def readFromNBT(nbt: NBTTagCompound) {
    readCommon(nbt)
    super.readFromNBT(nbt)
  }

  override def writeToNBT(nbt: NBTTagCompound) {
    writeCommon(nbt)
    super.writeToNBT(nbt)
  }

}
