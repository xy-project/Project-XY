package com.projextxy.core;

import codechicken.lib.packet.PacketCustom;
import codechicken.lib.vec.BlockCoord;
import com.projextxy.core.tile.TileColorizer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.INetHandlerPlayServer;
import net.minecraft.tileentity.TileEntity;

public class ServerPacketHandler implements PacketCustom.IServerPacketHandler {
    @Override
    public void handlePacket(PacketCustom packet, EntityPlayerMP entityPlayerMP, INetHandlerPlayServer iNetHandlerPlayServer) {
        switch (packet.getType()) {
            case 1: {

                BlockCoord blockCoord = packet.readCoord();
                TileEntity tile = entityPlayerMP.getEntityWorld().getTileEntity(blockCoord.x, blockCoord.y, blockCoord.z);

                if (tile instanceof TileColorizer) {
                    ((TileColorizer) tile).sync(packet);
                }

                break;
            }
        }
    }
}
