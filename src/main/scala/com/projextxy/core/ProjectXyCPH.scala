package com.projextxy.core

import codechicken.lib.packet.PacketCustom
import codechicken.lib.vec.BlockCoord
import com.projextxy.core.tile.traits.TCustomPacketHandler
import net.minecraft.client.Minecraft
import net.minecraft.client.multiplayer.WorldClient
import net.minecraft.network.play.INetHandlerPlayClient

object ProjectXyCPH extends PacketCustom.IClientPacketHandler {
  val channel = ProjectXYCore.MOD_ID
  val CLIENT_UPDATE_PACKET = 1

  override def handlePacket(packetCustom: PacketCustom, minecraft: Minecraft, iNetHandlerPlayClient: INetHandlerPlayClient) {
    packetCustom.getType match {
      case CLIENT_UPDATE_PACKET => handleTilePacket(minecraft.theWorld, packetCustom, packetCustom.readCoord())
    }
  }

  def handleTilePacket(world: WorldClient, packet: PacketCustom, pos: BlockCoord) {
    val tile = world.getTileEntity(pos.x, pos.y, pos.z)
    tile match {
      case packetHandler: TCustomPacketHandler => packetHandler.handleClientSidePacket(packet)
      case _ =>
    }
  }
}

