package com.projextxy.core.tile.traits

import codechicken.lib.packet.PacketCustom

trait TCustomPacketHandler {
  def handleClientSidePacket(packet: PacketCustom)
}
