package com.projextxy.core.tile.traits

import codechicken.lib.data.{MCDataInput, MCDataOutput}
import codechicken.lib.packet.PacketCustom
import codechicken.lib.vec.BlockCoord
import com.projextxy.core.ProjectXyCPH
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.network.Packet
import net.minecraft.tileentity.TileEntity

trait TMachineTile extends TileEntity with TCustomPacketHandler {
  def writeToItemStack(stack: ItemStack) {
    if (stack == null) return
    if (stack.stackTagCompound == null) stack.stackTagCompound = new NBTTagCompound
    val root: NBTTagCompound = stack.stackTagCompound
    root.setBoolean("xy.abstractMachine", true)
    saveNBT(root)
  }

  def readFromItemStack(stack: ItemStack) {
    if (stack == null || stack.stackTagCompound == null) return
    val root: NBTTagCompound = stack.stackTagCompound
    if (!root.hasKey("xy.abstractMachine")) return
    readNBT(root)
  }

  def saveNBT(compound: NBTTagCompound)

  def readNBT(compound: NBTTagCompound)

  def writeToPacket(out: MCDataOutput)

  def receivePacket(in: MCDataInput)

  override def readFromNBT(nbt: NBTTagCompound) {
    readNBT(nbt)
    super.readFromNBT(nbt)
  }

  override def writeToNBT(nbt: NBTTagCompound) {
    readNBT(nbt)

    super.writeToNBT(nbt)
  }

  override def getDescriptionPacket: Packet = {
    val packet = new PacketCustom(ProjectXyCPH.channel, ProjectXyCPH.CLIENT_UPDATE_PACKET)
    packet.writeCoord(new BlockCoord(this))
    writeToPacket(packet)
    packet.toPacket
  }

  override def handleClientSidePacket(packet: PacketCustom): Unit = receivePacket(packet)
}
