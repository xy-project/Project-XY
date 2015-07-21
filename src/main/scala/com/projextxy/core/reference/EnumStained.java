package com.projextxy.core.reference;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public enum EnumStained {
    WHITE("White", null, MCColors.WHITE.getOreDict()),
    ORANGE("Orange", null, MCColors.ORANGE.getOreDict()),
    MAGENTA("Magenta", null, MCColors.MAGENTA.getOreDict()),
    LIGHT_BLUE("Light Blue", null, MCColors.LIGHT_BLUE.getOreDict()),
    YELLOW("Yellow", null, MCColors.YELLOW.getOreDict()),
    LIME("Lime", null, MCColors.LIME.getOreDict()),
    PINK("Pink", null, MCColors.PINK.getOreDict()),
    GREY("Grey", null, MCColors.GREY.getOreDict()),
    LIGHT_GREY("Light Grey", null, MCColors.LIGHT_GREY.getOreDict()),
    CYAN("Cyan", null, MCColors.CYAN.getOreDict()),
    PURPLE("Purple", null, MCColors.PURPLE.getOreDict()),
    BLUE("Blue", null, MCColors.BLUE.getOreDict()),
    BROWN("Brown", null, MCColors.BROWN.getOreDict()),
    GREEN("Green", null, MCColors.GREEN.getOreDict()),
    RED("Red", null, MCColors.RED.getOreDict()),
    BLACK("Black", null, MCColors.BLACK.getOreDict());
    public static final EnumStained[] main_colors = new EnumStained[]{WHITE, RED, GREEN, BLUE, BLACK};
    public static final EnumStained[] VALID_TYPES = values();
    public final int meta = ordinal();
    public final String name;
    public final String oreDic;
    public final ItemStack specailDrop;

    EnumStained(String name, ItemStack specailDrop, String oreDic) {
        this.name = name;
        this.specailDrop = specailDrop;
        this.oreDic = oreDic;
    }

    public ItemStack getBlockItemStack(Block block) {
        return getBlockItemStack(block, 1);
    }

    public ItemStack getBlockItemStack(Block block, int i) {
        return new ItemStack(block, i, meta);
    }

    public ItemStack getItemItemStack(Item item, int i) {
        return new ItemStack(item, i, meta);
    }


    public ItemStack getItemItemStack(Item item) {
        return getItemItemStack(item, 1);
    }

    public MCColors getColor() {
        return MCColors.get(ordinal());
    }

}
