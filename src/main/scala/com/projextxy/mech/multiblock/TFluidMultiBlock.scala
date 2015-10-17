package com.projextxy.mech.multiblock

import codechicken.core.fluid.FluidUtils
import codechicken.lib.data.{MCDataInput, MCDataOutput}
import codechicken.lib.vec.{BlockCoord, CuboidCoord}
import net.minecraftforge.fluids.{FluidStack, FluidTankInfo}

import scala.collection.mutable.ArrayBuffer

trait TFluidMultiBlock extends MultiBlock {
  var tank: LevelSensitiveFluidTank = null
  var area: CuboidCoord

  def initTank(): Unit = tank = new LevelSensitiveFluidTank(this)

  def fill(pos: BlockCoord, resource: FluidStack, doFill: Boolean): Int = tank.fill(resource, doFill)

  def drain(coord: BlockCoord, maxDrain: Int, doDrain: Boolean): FluidStack = tank.access(coord.y).drain(maxDrain, doDrain)

  def getTankInfo: FluidTankInfo = tank.getInfo

  override def writeToUpdatePacket(out: MCDataOutput): Unit = {
    tank.write(out)
  }

  override def readFromUpdatePacket(in: MCDataInput): Unit = {
    tank.read(in)
  }

  override def onUpdate(): Unit = {
    if (world.isRemote)
      tank.update()
    super.onUpdate()
  }


  override def onJoinTile(blockPos: BlockCoord): Unit = {
    world.getTileEntity(blockPos.x, blockPos.y, blockPos.z) match {
      case valve: TileValve =>
        if(tank != null)
          tank.fill(valve.inactiveFluid, true)
        valve.inactiveFluid = FluidUtils.emptyFluid()
      case _ =>
    }
    super.onJoinTile(blockPos)
  }

  override def unload(remove: Boolean): Unit = {
    if (!world.isRemote && remove) {
      val valves = new ArrayBuffer[TileValve]()
      for (coords <- inBlocks) {
        world.getTileEntity(coords.x, coords.y, coords.z) match {
          case valve: TileValve =>
            if (valve.getTank(0).contains(this))
              valves += valve
          case _ =>
        }
      }
      if(valves.nonEmpty){
        val splitAmnt = tank.getFluid.amount / valves.size
        for (valve <- valves) {
          valve.inactiveFluid = tank.getFluid
          valve.inactiveFluid.amount = splitAmnt
        }
      }
    }
    super.unload(remove)
  }
}
