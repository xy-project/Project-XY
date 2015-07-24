package com.projextxy.mech

import com.projextxy.mech.fmp.{ReceiverPart, TXyRedstonePart}
import com.projextxy.util.MathHelper
import net.minecraft.world.World

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

object XynergyNetworkHandler {
  val receivers = mutable.WeakHashMap[World, ArrayBuffer[PartSignature]]()

  def clear() {
    receivers.clear()
  }

  def addReceiver(part: TXyRedstonePart) {
    val world = part.world

    if (!receivers.contains(world))
      receivers.put(world, new ArrayBuffer[PartSignature]())

    val tiles = receivers.get(world)

    if (!tiles.contains(part))
      tiles.get += new PartSignature(part, world.isRemote)
  }

  def isReceiverIn(part: TXyRedstonePart): Boolean = {
    if (part == null)
      return false

    val tiles = receivers.get(part.world)

    if (tiles.isEmpty)
      return false

    for (sig <- tiles.get) {
      if (sig.part == part)
        return true
    }

    false
  }

  def getClosestReceiver(pos: BlockCoord, world: World): ArrayBuffer[ReceiverPart] = {
    val tilesInRange = new ArrayBuffer[ReceiverPart]

    //Return an empty array buffer
    if (!receivers.contains(world))
      return tilesInRange

    val tiles = receivers.get(world)

    var closestDist = Float.MaxValue
    var closest: TXyRedstonePart = null
    if (tiles.isDefined)
      tiles.get.foreach { sig =>
        if (sig.remote == world.isRemote) {
          val tile = sig.part
          if (!tile.tile.isInvalid) {
            val distance = MathHelper.distanceBetweenPointsSpace(sig.part.z, sig.part.y, sig.part.x, pos.x, pos.y, pos.z)
            if (distance < closestDist) {
              closest = sig.part
              closestDist = distance
            }
          }
        }
      }
    tilesInRange
  }

  def getClosestReceiversInRange(part: TXyRedstonePart, maxDistance: Float, limit: Int): ArrayBuffer[TXyRedstonePart] = {
    val tilesInRange = new ArrayBuffer[TXyRedstonePart]()
    val calc = mutable.HashMap[TXyRedstonePart, Float]()

    val tileInWorld = getReceiversInWorld(part.world)
    if (tileInWorld.isEmpty)
      return tilesInRange

    for (sig <- tileInWorld.get) {
      if (sig.remote == part.world.isRemote) {
        val receiverPart = sig.part
        if (receiverPart != part && !part.isPartInChain(receiverPart)) {
          val distance = MathHelper.distanceBetweenPointsSpace(receiverPart.x, receiverPart.y, receiverPart.z, part.x, part.y, part.z)
          if (distance <= maxDistance) {
            calc.put(receiverPart, distance)
          }
        }
      }
    }

    val size = if (calc.size < limit) calc.size else limit

    val sorted = calc.toSeq.sortBy(_._2)
    for (i <- 0 to size - 1) {
      tilesInRange += sorted(i)._1
    }
    tilesInRange
  }

  def getReceiversInWorld(world: World): Option[ArrayBuffer[PartSignature]] = receivers.get(world)

  def removeReceiver(part: TXyRedstonePart): Unit = {
    val world = part.world

    if (!receivers.contains(world))
      return

    val tiles = receivers.getOrElse(world, new ArrayBuffer[PartSignature]())

    tiles.foreach { sig =>
      if (sig.part.equals(part)) {
        tiles -= sig
        println(s"Removed tile at X:${sig.part.x} Y:${sig.part.y} Z:${sig.part.z}")
        return
      }
    }
  }


}

case class PartSignature(part: TXyRedstonePart, remote: Boolean)