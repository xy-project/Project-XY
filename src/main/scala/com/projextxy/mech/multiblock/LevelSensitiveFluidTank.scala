package com.projextxy.mech.multiblock

import codechicken.core.fluid.{ExtendedFluidTank, FluidUtils}
import codechicken.lib.data.{MCDataInput, MCDataOutput}
import codechicken.lib.math.MathHelper
import net.minecraftforge.fluids.{FluidStack, FluidTankInfo, IFluidTank}

case class LevelSensitiveFluidTank(fluidMultiBlock: TFluidMultiBlock) extends ExtendedFluidTank(null, 0) {
  val area = fluidMultiBlock.area.size(2) * fluidMultiBlock.area.size(4)
  val height = fluidMultiBlock.area.size(0)
  val worldHeight = fluidMultiBlock.area.min.y
  var c_ammount = 0.0

  override def getCapacity: Int = (area * height * FluidUtils.B * 16.0D).toInt

  class FluidMultiBlockAccess(level: Double) extends IFluidTank {
    val capacityForLevel: Int = ((level - worldHeight) * area * FluidUtils.B * 16.0D).toInt

    override def drain(maxDrain: Int, doDrain: Boolean): FluidStack = LevelSensitiveFluidTank.this.drain(math.min(maxDrain, LevelSensitiveFluidTank.this.getFluid.amount - capacityForLevel), doDrain)

    override def getFluidAmount: Int = math.max(LevelSensitiveFluidTank.this.getFluid.amount - capacityForLevel, 0)

    override def getInfo: FluidTankInfo = LevelSensitiveFluidTank.this.getInfo

    override def getCapacity: Int = getCapacity

    override def getFluid: FluidStack = {
      val copy = LevelSensitiveFluidTank.this.getFluid.copy
      copy.amount = getFluidAmount
      copy
    }

    override def fill(resource: FluidStack, doFill: Boolean): Int = LevelSensitiveFluidTank.this.fill(resource, doFill)
  }

  def access(level: Double): FluidMultiBlockAccess = new FluidMultiBlockAccess(level)

  def write(out: MCDataOutput): Unit = {
    out.writeNBTTagCompound(toTag)
  }

  def read(in: MCDataInput): Unit = {
    fromTag(in.readNBTTagCompound())
  }

  override def onLiquidChanged(): Unit = fluidMultiBlock.requestUpdate

  def update(): Unit = {
    c_ammount = MathHelper.approachExp(c_ammount, getFluid.amount, 0.125)
  }


}
