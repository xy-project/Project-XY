package com.projextxy.mech.tile

import cofh.api.energy.{EnergyStorage, IEnergyProvider, IEnergyReceiver}
import com.projextxy.core.tile.traits.TMachineTile
import net.minecraft.nbt.NBTTagCompound
import net.minecraftforge.common.util.ForgeDirection

/**
 * Used to provide power to the xynergy framework
 * this can be a capacitor bank, generator, etc.
 */
trait TPowerHandler extends TMachineTile with IEnergyProvider with IEnergyReceiver {
  def energyStorage: EnergyStorage

  override def extractEnergy(from: ForgeDirection, maxExtract: Int, simulate: Boolean): Int = energyStorage.extractEnergy(maxExtract, simulate)

  override def getEnergyStored(from: ForgeDirection): Int = energyStorage.getEnergyStored

  override def getMaxEnergyStored(from: ForgeDirection): Int = energyStorage.getMaxEnergyStored

  override def canConnectEnergy(from: ForgeDirection): Boolean

  override def receiveEnergy(from: ForgeDirection, maxReceive: Int, simulate: Boolean): Int = energyStorage.receiveEnergy(maxReceive, simulate)

  override def readFromNBT(nbt: NBTTagCompound) = {
    super.readFromNBT(nbt)
    energyStorage.writeToNBT(nbt)
  }

  override def writeToNBT(nbt: NBTTagCompound) = {
    super.writeToNBT(nbt)
    energyStorage.writeToNBT(nbt)
  }
}
