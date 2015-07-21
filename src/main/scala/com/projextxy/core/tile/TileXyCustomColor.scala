package com.projextxy.core.tile

import codechicken.lib.colour.ColourRGBA
import codechicken.lib.packet.PacketCustom
import com.projextxy.core.tile.traits.{TMachineTile, TCustomPacketHandler}
import com.projextxy.core.{ProjectXYCore, ProjectXyCPH}
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.network.Packet
import net.minecraft.tileentity.TileEntity

class TileXyCustomColor extends TileEntity with TMachineTile with TCustomPacketHandler {
  var r: Int = 255
  var g: Int = 255
  var b: Int = 255

  def color: Int = new ColourRGBA(r, g, b, 255).rgb

  override def writeCommon(compound: NBTTagCompound) {
    compound.setInteger("r", r)
    compound.setInteger("g", g)
    compound.setInteger("b", b)
  }

  override def readCommon(compound: NBTTagCompound) {
    r = compound.getInteger("r")
    g = compound.getInteger("g")
    b = compound.getInteger("b")
  }

  override def handleClientSidePacket(packet: PacketCustom) {
    r = packet.readInt()
    g = packet.readInt()
    b = packet.readInt()
    worldObj.func_147479_m(xCoord, yCoord, zCoord)
  }

  override def getDescriptionPacket: Packet = {
    val packet = new PacketCustom(ProjectXYCore.MOD_ID, ProjectXyCPH.CLIENT_UPDATE_PACKET)
    packet.writeCoord(xCoord, yCoord, zCoord)
    packet.writeInt(r)
    packet.writeInt(g)
    packet.writeInt(b)
    packet.toPacket
  }
}
