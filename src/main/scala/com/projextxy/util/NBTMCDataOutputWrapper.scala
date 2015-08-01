package com.projextxy.util

import codechicken.lib.data.MCDataOutput
import codechicken.lib.vec.BlockCoord
import net.minecraft.item.ItemStack
import net.minecraft.nbt._
import net.minecraftforge.fluids.FluidStack

/**
 * Used to write to NBT via enclosed write and read's
 */
class NBTMCDataOutputWrapper(input: NBTTagList) extends MCDataOutput {
  def this() = this(new NBTTagList)

  override def writeVarInt(i: Int): MCDataOutput = input.

  override def writeCoord(i: Int, i1: Int, i2: Int): MCDataOutput = ???

  override def writeCoord(blockCoord: BlockCoord): MCDataOutput = ???

  override def writeString(s: String): MCDataOutput = ???

  override def writeFloat(v: Float): MCDataOutput = ???

  override def writeDouble(v: Double): MCDataOutput = ???

  override def writeShort(i: Int): MCDataOutput = ???

  override def writeVarShort(i: Int): MCDataOutput = ???

  override def writeInt(i: Int): MCDataOutput = ???

  override def writeFluidStack(fluidStack: FluidStack): MCDataOutput = ???

  override def writeByteArray(bytes: Array[Byte]): MCDataOutput = appendTag(new NBTTagByteArray(bytes))

  override def writeBoolean(b: Boolean): MCDataOutput = appendTag(new NBTTagByte(if (b) 1 else 0))

  private def appendTag(byte: NBTBase): MCDataOutput = {
    input.appendTag(byte)
    this
  }

  override def writeItemStack(itemStack: ItemStack): MCDataOutput = appendTag(itemStack.writeToNBT(new NBTTagCompound))

  override def writeNBTTagCompound(nbtTagCompound: NBTTagCompound): MCDataOutput = appendTag(nbtTagCompound)

  override def writeChar(c: Char): MCDataOutput = appendTag(new NBTTagShort(c))

  override def writeLong(l: Long): MCDataOutput = appendTag(new NBTTagLong(l))

  override def writeByte(i: Int): MCDataOutput = appendTag(new NBTTagByte(i.toByte))

}
