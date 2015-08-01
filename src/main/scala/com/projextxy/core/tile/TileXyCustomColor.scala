package com.projextxy.core.tile

import codechicken.lib.colour.ColourRGBA
import codechicken.lib.data.{MCDataInput, MCDataOutput}
import com.projextxy.core.tile.traits.TMachineTile
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.tileentity.TileEntity

//TODO: Change to color int instead of individual values to decrease packet size
class TileXyCustomColor extends TileEntity with TMachineTile {
  var r: Int = 255
  var g: Int = 255
  var b: Int = 255

  def color: Int = new ColourRGBA(r, g, b, 255).rgb

  override def saveNBT(compound: NBTTagCompound): Unit = {
    compound.setInteger("r", r)
    compound.setInteger("g", g)
    compound.setInteger("b", b)
  }

  override def receivePacket(in: MCDataInput): Unit = {
    r = in.readInt()
    g = in.readInt()
    b = in.readInt()
    worldObj.func_147479_m(xCoord, yCoord, zCoord)
  }

  override def writeToPacket(out: MCDataOutput): Unit = {
    out.writeInt(r)
    out.writeInt(g)
    out.writeInt(b)
  }

  override def readNBT(compound: NBTTagCompound): Unit = {
    r = compound.getInteger("r")
    g = compound.getInteger("g")
    b = compound.getInteger("b")
  }
}
