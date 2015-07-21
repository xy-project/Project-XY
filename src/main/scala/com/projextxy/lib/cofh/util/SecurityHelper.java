package com.projextxy.lib.cofh.util;

import com.projextxy.lib.cofh.api.core.ISecurable;
import net.minecraft.item.ItemStack;

public class SecurityHelper {

    private SecurityHelper() {

    }

    public static boolean isSecure(ItemStack stack) {

        return stack.stackTagCompound == null ? false : stack.stackTagCompound.hasKey("Secure");
    }

    public static boolean setAccess(ItemStack stack, ISecurable.AccessMode access) {

        if (!isSecure(stack)) {
            return false;
        }
        stack.stackTagCompound.setByte("Access", (byte) access.ordinal());
        return true;
    }

    public static ISecurable.AccessMode getAccess(ItemStack stack) {

        return stack.stackTagCompound == null ? ISecurable.AccessMode.PUBLIC : ISecurable.AccessMode.values()[stack.stackTagCompound.getByte("Access")];
    }

    public static boolean setOwnerName(ItemStack stack, String name) {

        if (!isSecure(stack)) {
            return false;
        }
        stack.stackTagCompound.setString("Owner", name);
        return true;
    }

    public static String getOwnerName(ItemStack stack) {

        return stack.stackTagCompound == null ? null : stack.stackTagCompound.getString("Owner");
    }

}
