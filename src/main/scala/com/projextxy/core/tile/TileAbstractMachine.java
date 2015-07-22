package com.projextxy.core.tile;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public abstract class TileAbstractMachine extends TileEntity {

    public void writeToItemStack(ItemStack stack) {
        if (stack == null)
            return;

        if (stack.stackTagCompound == null)
            stack.stackTagCompound = new NBTTagCompound();

        NBTTagCompound root = stack.stackTagCompound;
        root.setBoolean("xy.abstractMachine", true);

        writeCommon(root);
    }

    public void readFromItemStack(ItemStack stack) {
        if (stack == null || stack.stackTagCompound == null)
            return;

        NBTTagCompound root = stack.stackTagCompound;

        if (!root.hasKey("xy.abstractMachine"))
            return;

        readCommon(root);
    }

    abstract void writeCommon(NBTTagCompound compound);

    abstract void readCommon(NBTTagCompound compound);

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        readCommon(nbt);
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        writeCommon(nbt);
    }
}
