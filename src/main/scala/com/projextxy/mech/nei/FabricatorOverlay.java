package com.projextxy.mech.nei;

import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.DefaultOverlayHandler;
import codechicken.nei.recipe.IRecipeHandler;
import com.projextxy.mech.gui.ContainerFabricator;
import com.projextxy.mech.gui.GuiFabricator;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import java.util.List;

public class FabricatorOverlay extends DefaultOverlayHandler {
    public FabricatorOverlay() {
        super(-17, 24);
    }

    @Override
    public void overlayRecipe(GuiContainer gui, IRecipeHandler recipe, int recipeIndex, boolean shift) {
        List<PositionedStack> ingredients = recipe.getIngredientStacks(recipeIndex);
        Slot[][] slots = mapIngredSlots(gui, ingredients);
        ItemStack[] itemstacks = new ItemStack[9];
        for (int i = 0; i < ingredients.size(); i++) {
            itemstacks[slots[i][0].slotNumber] = ingredients.get(i).items[0];
        }
        ((ContainerFabricator) gui.inventorySlots).changeCraftingRecipe(itemstacks);
    }
}
