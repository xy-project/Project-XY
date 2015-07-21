package com.projextxy.core.reference;


import codechicken.lib.colour.Colour;
import codechicken.lib.colour.ColourRGBA;

public enum MCColors {
    WHITE(0xFFFFFF, 0),
    ORANGE(0xFF8C00, 1),
    MAGENTA(0xFF00FF, 2),
    LIGHT_BLUE(0x1E90FF, 3),
    YELLOW(0xFFFF00, 4),
    LIME(0x00FF00, 5),
    PINK(0xFF8DA1, 6),
    GREY(0x535353, 7),
    LIGHT_GREY(0x939393, 8),
    CYAN(0x008787, 9),
    PURPLE(0x5E00C0, 10),
    BLUE(0x1e40ff, 11),
    BROWN(0x8B4513, 12),
    GREEN(0x009F00, 13),
    RED(0xA20F06, 14),
    BLACK(0x272727, 15);

    public static final MCColors[] VALID_COLORS = values();
    private static final String[] dyeDictionary = {"dyeBlack", "dyeRed", "dyeGreen", "dyeBrown", "dyeBlue", "dyePurple", "dyeCyan", "dyeLightGray", "dyeGray", "dyePink", "dyeLime", "dyeYellow",
            "dyeLightBlue", "dyeMagenta", "dyeOrange", "dyeWhite"};
    public final int colorId;
    public final int meta = ordinal();
    public final String name;
    public final int rgb;
    public final Colour c;

    MCColors(int rgb, int colorId) {
        name = name().substring(0, 1) + name().substring(1).toLowerCase();
        this.rgb = rgb;
        this.colorId = colorId;
        c = new ColourRGBA(rgb << 8 | 0xFF);
    }

    public static MCColors get(int i) {
        if (i > 15)
            return BLACK;
        if (i < 0)
            return WHITE;
        return VALID_COLORS[i];
    }

    public int dyeId() {
        return 15 - ordinal();
    }

	/*
     * public ItemStack getDye() { return new ItemStack(Item, 1, dyeId()); }
	 */

    public String getOreDict() {
        return dyeDictionary[dyeId()];
    }

    public int woolId() {
        return ordinal();
    }
}
