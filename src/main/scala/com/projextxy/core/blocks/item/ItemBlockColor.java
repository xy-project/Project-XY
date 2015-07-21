package com.projextxy.core.blocks.item;

import com.projextxy.core.blocks.glow.BlockXyGlow;
import com.projextxy.core.reference.EnumStained;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

import java.util.List;

public class ItemBlockColor extends ItemBlock {
    public ItemBlockColor(Block block) {
        super(block);
        setHasSubtypes(true);
        setMaxDamage(0);
    }

    @Override
    public int getMetadata(int i) {
        return i;
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return super.getUnlocalizedName(stack);
    }

    @Override
    public void addInformation(ItemStack itemStack, EntityPlayer entityPlayer, List list, boolean par4) {
        BlockXyGlow blockXyGlow = (BlockXyGlow) Block.getBlockFromItem(itemStack.getItem());
        list.add(EnumStained.VALID_TYPES[blockXyGlow.colors().apply(itemStack.getItemDamage()).colorId].name);
        super.addInformation(itemStack, entityPlayer, list, par4);
    }
}
