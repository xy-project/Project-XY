package com.projextxy.core.tile;

import codechicken.lib.colour.ColourRGBA;
import codechicken.lib.packet.PacketCustom;
import com.projextxy.core.CoreBlocks$;
import com.projextxy.core.ProjectXYCore$;
import com.projextxy.util.LogHelper;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.Packet;

public class TileColorizer extends TileAbstractMachine implements ISidedInventory {
    public static final int INVENTORY_SIZE = 2;
    public static final int INPUT_INVENTORY_INDEX = 0;
    public static final int OUTPUT_INVENTORY_INDEX = 1;
    private static final int progressTicksEnd = 40;
    ItemStack[] inv = new ItemStack[2];
    private int r = 255;
    private int g = 255;
    private int b = 255;

    @Override
    void writeCommon(NBTTagCompound compound) {
        NBTTagList tagList = new NBTTagList();
        for (int i = 0; i < inv.length; ++i) {
            if (inv[i] != null) {
                NBTTagCompound tagCompound = new NBTTagCompound();
                tagCompound.setByte("Slot", (byte) i);
                inv[i].writeToNBT(tagCompound);
                tagList.appendTag(tagCompound);
            }
        }
        compound.setTag("inventory.items", tagList);
        compound.setInteger("r", r);
        compound.setInteger("g", g);
        compound.setInteger("b", b);
    }

    @Override
    void readCommon(NBTTagCompound compound) {
        NBTTagList tagList = compound.getTagList("inventory.items", 10);
        inv = new ItemStack[getSizeInventory()];

        for (int i = 0; i < tagList.tagCount(); ++i) {
            NBTTagCompound tagCompound = tagList.getCompoundTagAt(i);
            byte slot = tagCompound.getByte("Slot");
            if (slot >= 0 && slot < inv.length) {
                inv[slot] = ItemStack.loadItemStackFromNBT(tagCompound);
            }
        }

        r = compound.getInteger("r");
        g = compound.getInteger("g");
        b = compound.getInteger("b");
    }

    @Override
    public int getSizeInventory() {
        return inv.length;
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return inv[slot];
    }

    @Override
    public ItemStack decrStackSize(int slot, int ammount) {
        LogHelper.info("decrStackSize");
        System.out.println(ammount);
        ItemStack stack = getStackInSlot(slot);
        if (stack != null) {
            if (stack.stackSize <= ammount) {
                inv[slot] = null;
                return stack;
            } else {
                stack = stack.splitStack(ammount);
                if (stack.stackSize == 0) {
                    inv[slot] = null;
                }
            }
        }
        return stack;
    }

    @Override
    public void updateEntity() {
        if (!worldObj.isRemote) {
            if (inv[INPUT_INVENTORY_INDEX] != null && inv[OUTPUT_INVENTORY_INDEX] == null) {
                inv[OUTPUT_INVENTORY_INDEX] = createOutputFromInput(inv[INPUT_INVENTORY_INDEX]);
                inv[INPUT_INVENTORY_INDEX] = null;
            }
        }
    }


    public ItemStack createOutputFromInput(ItemStack input) {
        ItemStack output = new ItemStack(CoreBlocks$.MODULE$.blockXyCustom(), input.stackSize, input.getItemDamage());
        output.stackTagCompound = new NBTTagCompound();
        output.stackTagCompound.setBoolean("xy.abstractMachine", true);
        output.stackTagCompound.setInteger("r", r);
        output.stackTagCompound.setInteger("g", g);
        output.stackTagCompound.setInteger("b", b);
        return output;
    }


    @Override
    public ItemStack getStackInSlotOnClosing(int slot) {
        ItemStack stack = getStackInSlot(slot);
        if (stack != null) {
            inv[slot] = null;
            return stack;
        } else {
            return null;
        }
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack stack) {
        inv[slot] = stack;
        if (stack != null && stack.stackSize > this.getInventoryStackLimit()) {
            stack.stackSize = this.getInventoryStackLimit();
        }
    }

    @Override
    public Packet getDescriptionPacket() {
        return buildPacket().toPacket();
    }

    @Override
    public String getInventoryName() {
        return null;
    }

    @Override
    public boolean hasCustomInventoryName() {
        return false;
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        return true;
    }

    @Override
    public void openInventory() {

    }

    @Override
    public void closeInventory() {

    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack item) {
        if (item == null)
            return false;
        return slot != OUTPUT_INVENTORY_INDEX && (slot == INPUT_INVENTORY_INDEX && Block.getBlockFromItem(item.getItem()).equals(CoreBlocks$.MODULE$.blockXyCustom()));
    }

    public int getR() {
        return r;
    }

    public void setR(int r) {
        this.r = r;
    }

    public int getG() {
        return g;
    }

    public void setG(int g) {
        this.g = g;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int p_94128_1_) {
        return new int[0];
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack stack, int side) {
        return isItemValidForSlot(slot, stack);
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack stack, int side) {
        return slot == 1;
    }

    public void sync(PacketCustom packet) {
        this.inv = new ItemStack[INVENTORY_SIZE];

        for (int i = 0; i < INVENTORY_SIZE; i++) {
            this.inv[i] = packet.readItemStack();
        }

        this.r = packet.readInt();
        this.g = packet.readInt();
        this.b = packet.readInt();
    }

    public PacketCustom buildPacket() {
        PacketCustom packetCustom = new PacketCustom(ProjectXYCore$.MODULE$.MOD_ID(), 1);
        packetCustom.writeCoord(xCoord, yCoord, zCoord);

        for (ItemStack stack : inv) {
            packetCustom.writeItemStack(stack);
        }

        packetCustom.writeInt(r);
        packetCustom.writeInt(g);
        packetCustom.writeInt(b);
        return packetCustom;
    }

    public int getColor() {
        return new ColourRGBA(r, g, b, 255).rgb();
    }
}
