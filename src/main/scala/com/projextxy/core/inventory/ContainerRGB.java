package com.projextxy.core.inventory;

import com.projextxy.core.CoreBlocks$;
import com.projextxy.core.tile.TileColorizer;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerRGB extends Container {
    private final int PLAYER_INVENTORY_ROWS = 3;
    private final int PLAYER_INVENTORY_COLUMNS = 9;
    private TileColorizer tileColorizer;

    public ContainerRGB(InventoryPlayer inventoryPlayer, TileColorizer tileCustomColor) {
        this.tileColorizer = tileCustomColor;
        this.addSlotToContainer(new Slot(tileCustomColor, TileColorizer.INPUT_INVENTORY_INDEX, 44, 67) {
            @Override
            public boolean isItemValid(ItemStack stack) {
                return Block.getBlockFromItem(stack.getItem()).equals(CoreBlocks$.MODULE$.blockXyCustom());
            }
        });
        this.addSlotToContainer(new Slot(tileCustomColor, TileColorizer.OUTPUT_INVENTORY_INDEX, 116, 67) {
            @Override
            public boolean isItemValid(ItemStack stack) {
                return false;
            }
        });


        // Add the player's inventory slots to the container
        for (int inventoryRowIndex = 0; inventoryRowIndex < PLAYER_INVENTORY_ROWS; ++inventoryRowIndex) {
            for (int inventoryColumnIndex = 0; inventoryColumnIndex < PLAYER_INVENTORY_COLUMNS; ++inventoryColumnIndex) {
                this.addSlotToContainer(new Slot(inventoryPlayer, inventoryColumnIndex + inventoryRowIndex * 9 + 9, inventoryColumnIndex * 18 + 8, 103 + inventoryRowIndex * 18));
            }
        }

        // Add the player's action bar slots to the container
        for (int actionBarSlotIndex = 0; actionBarSlotIndex < PLAYER_INVENTORY_COLUMNS; ++actionBarSlotIndex) {
            this.addSlotToContainer(new Slot(inventoryPlayer, actionBarSlotIndex, 8 + actionBarSlotIndex * 18, 162));
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer palyer) {
        return true;
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer entityPlayer, int slotIndex) {
        ItemStack itemStack = null;
        Slot slot = (Slot) inventorySlots.get(slotIndex);

        if (slot != null && slot.getHasStack()) {
            ItemStack slotItemStack = slot.getStack();
            itemStack = slotItemStack.copy();

            if (slotIndex < TileColorizer.INVENTORY_SIZE) {
                if (!this.mergeItemStack(slotItemStack, TileColorizer.INVENTORY_SIZE, inventorySlots.size(), false)) {
                    return null;
                }
            } else {
                if (Block.getBlockFromItem(slotItemStack.getItem()).equals(CoreBlocks$.MODULE$.blockXyCustom())) {
                    if (!this.mergeItemStack(slotItemStack, TileColorizer.INPUT_INVENTORY_INDEX, TileColorizer.OUTPUT_INVENTORY_INDEX, false)) {
                        return null;
                    }
                } else {
                    if (!this.mergeItemStack(slotItemStack, TileColorizer.OUTPUT_INVENTORY_INDEX, TileColorizer.OUTPUT_INVENTORY_INDEX, false)) {
                        return null;
                    }
                }
            }

            if (slotItemStack.stackSize == 0) {
                slot.putStack(null);
            } else {
                slot.onSlotChanged();
            }
        }

        return itemStack;
    }
}
