package com.projextxy.lib.cofh.gui.element;

import com.projextxy.lib.cofh.api.energy.IEnergyStorage;
import com.projextxy.lib.cofh.gui.GuiBase;
import com.projextxy.lib.cofh.gui.GuiProps;
import com.projextxy.lib.cofh.render.RenderHelper;
import com.projextxy.lib.cofh.util.MathHelper;
import net.minecraft.util.ResourceLocation;

import java.util.List;

public class ElementEnergyStored extends ElementBase {

    public static final ResourceLocation DEFAULT_TEXTURE = new ResourceLocation(GuiProps.PATH_ELEMENTS + "Energy.png");
    public static final int DEFAULT_SCALE = 42;

    protected IEnergyStorage storage;

    public ElementEnergyStored(GuiBase gui, int posX, int posY, IEnergyStorage storage) {

        super(gui, posX, posY);
        this.storage = storage;

        this.texture = DEFAULT_TEXTURE;
        this.sizeX = 16;
        this.sizeY = DEFAULT_SCALE;

        this.texW = 32;
        this.texH = 64;
    }

    @Override
    public void drawBackground(int mouseX, int mouseY, float gameTicks) {

        RenderHelper.bindTexture(texture);
        drawTexturedModalRect(posX, posY, 0, 0, sizeX, sizeY);
        int qty = getScaled();
        drawTexturedModalRect(posX, posY + DEFAULT_SCALE - qty, 16, DEFAULT_SCALE - qty, sizeX, qty);
    }

    @Override
    public void drawForeground(int mouseX, int mouseY) {

    }

    @Override
    public void addTooltip(List<String> list) {

        if (storage.getMaxEnergyStored() < 0) {
            list.add("Infinite RF");
        } else {
            list.add(storage.getEnergyStored() + " / " + storage.getMaxEnergyStored() + " RF");
        }
    }

    int getScaled() {

        if (storage.getMaxEnergyStored() < 0) {
            return sizeY;
        }
        return MathHelper.round(storage.getEnergyStored() * sizeY / storage.getMaxEnergyStored());
    }

}
