package com.projextxy.core.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

public class ContainerEmpty extends Container {
    @Override
    public boolean canInteractWith(EntityPlayer entityPlayer) {
        return true;
    }
}
