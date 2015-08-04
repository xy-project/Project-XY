package com.projextxy.core.handler;

import com.projextxy.core.client.gui.GuiRGB;
import com.projextxy.core.inventory.ContainerRGB;
import com.projextxy.core.tile.TileColorizer;
import com.projextxy.mech.gui.ContainerFabricator;
import com.projextxy.mech.gui.GuiFabricator;
import com.projextxy.mech.tile.TileFabricator;
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
            case FABRICATOR:
                TileFabricator tileFabricator = (TileFabricator) world.getTileEntity(x, y, z);
                return new ContainerFabricator(player.inventory, tileFabricator);
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        switch (GuiIds.VALID_TYPES[id]) {
            case RGB:
                TileColorizer tileCustomColor = (TileColorizer) world.getTileEntity(x, y, z);
                return new GuiRGB(player.inventory, tileCustomColor);
            case FABRICATOR:
                TileFabricator tileFabricator = (TileFabricator) world.getTileEntity(x, y, z);
                return new GuiFabricator(player.inventory, tileFabricator);
        }
        return null;
    }

    public enum GuiIds {
        RGB, FABRICATOR;
        public static final GuiIds[] VALID_TYPES = values();
        public final int id = ordinal();
    }
}
