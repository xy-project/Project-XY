package com.projextxy.core

import codechicken.lib.packet.PacketCustom
import codechicken.lib.vec.BlockCoord
import com.projextxy.core.tile.TileColorizer
import com.projextxy.core.tile.traits.TCustomPacketHandler
import net.minecraft.client.Minecraft
import net.minecraft.client.multiplayer.WorldClient
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.network.play.{INetHandlerPlayClient, INetHandlerPlayServer}
import net.minecraft.tileentity.TileEntity

object ProjectXyCPH extends PacketCustom.IClientPacketHandler {
  val channel = ProjectXYCore.MOD_ID
  val CLIENT_UPDATE_PACKET = 1

  override def handlePacket(packet: PacketCustom, minecraft: Minecraft, handler: INetHandlerPlayClient): Unit = {
    packet.getType match {
      case CLIENT_UPDATE_PACKET => handleTilePacket(minecraft.theWorld, packet, packet.readCoord())
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

object ProjectXySPH extends PacketCustom.IServerPacketHandler {

  override def handlePacket(packet: PacketCustom, player: EntityPlayerMP, handler: INetHandlerPlayServer): Unit = {
    packet.getType match {
      case 1 => {
        val blockCoord: BlockCoord = packet.readCoord
        val tile: TileEntity = player.getEntityWorld.getTileEntity(blockCoord.x, blockCoord.y, blockCoord.z)
        tile match {
          case colorizer: TileColorizer => colorizer.sync(packet)
          case _ =>
        }
      }
      case _ =>
    }
  }
}

