package com.projextxy.core.handler;

import com.projextxy.core.client.gui.GuiRGB;
import com.projextxy.core.inventory.ContainerRGB;
import com.projextxy.core.tile.TileColorizer;
import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class GuiHandler implements IGuiHandler {
    @Override
    public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        switch (GuiIds.VALID_TYPES[id]) {
            case RGB:
                TileColorizer tileCustomColor = (TileColorizer) world.getTileEntity(x, y, z);
                return new ContainerRGB(player.inventory, tileCustomColor);
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        switch (GuiIds.VALID_TYPES[id]) {
            case RGB:
                TileColorizer tileCustomColor = (TileColorizer) world.getTileEntity(x, y, z);
                return new GuiRGB(player.inventory, tileCustomColor);
        }
        return null;
    }

    public enum GuiIds {
        RGB,;
        public static final GuiIds[] VALID_TYPES = values();
        public final int id = ordinal();
    }
}
