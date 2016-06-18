package com.projextxy.mech.multiblock

import codechicken.lib.packet.PacketCustom
import net.minecraft.client.Minecraft
import net.minecraft.network.play.INetHandlerPlayClient

object MultiBlocksCPH extends PacketCustom.IClientPacketHandler {
  val CHANNEL = "ProjectXyMultiBlocks"

  override def handlePacket(packet: PacketCustom, minecraft: Minecraft, inhpc: INetHandlerPlayClient): Unit = {
    packet.getType match {
      case 1 => XYWEI.getExtensionXy(minecraft.theWorld).handleDescriptionPacket(packet)
      case 2 => XYWEI.getExtensionXy(minecraft.theWorld).handleRemoveMultiBlockPacket(packet)
      case 3 => XYWEI.getExtensionXy(minecraft.theWorld).handleMultiBlockUpdate(packet)
      case _ =>
    }
  }
}
