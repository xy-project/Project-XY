package com.projextxy.mech.multiblock

import codechicken.core.fluid.FluidUtils
import codechicken.lib.vec.BlockCoord
import net.minecraft.nbt.NBTTagCompound
import net.minecraftforge.common.util.ForgeDirection
import net.minecraftforge.fluids._

import scala.collection.JavaConversions._

class TileValve extends TileMultiBlock with IFluidHandler {
  var inactiveFluid = FluidUtils.emptyFluid()

  override def drain(from: ForgeDirection, resource: FluidStack, doDrain: Boolean): FluidStack = drain(from, resource.amount, doDrain)

  override def drain(from: ForgeDirection, maxDrain: Int, doDrain: Boolean): FluidStack = {
    val tank = getTank(0)
    if (tank.isDefined) {
      return tank.get.drain(new BlockCoord(this), maxDrain, doDrain)
    }
    null
  }

  override def canFill(from: ForgeDirection, fluid: Fluid): Boolean = true

  override def canDrain(from: ForgeDirection, fluid: Fluid): Boolean = true

  override def fill(from: ForgeDirection, resource: FluidStack, doFill: Boolean): Int = {
    val tank = getTank(0)
    if (tank.isDefined) {
      return tank.get.fill(new BlockCoord(this), resource, doFill)
    }
    0
  }


  override def writeToNBT(nbt: NBTTagCompound): Unit = {
    nbt.setTag("inactive_fluid", FluidUtils.write(inactiveFluid, new NBTTagCompound))
    super.writeToNBT(nbt)
  }


  override def readFromNBT(nbt: NBTTagCompound): Unit = {
    inactiveFluid = FluidUtils.read(nbt.getCompoundTag("inactive_fluid"))
    super.readFromNBT(nbt)
  }

  override def getTankInfo(from: ForgeDirection): Array[FluidTankInfo] = {
    val tank = getTank(0)
    if (tank.isDefined) {
      return Array(tank.get.getTankInfo)
    }
    null
  }


}
