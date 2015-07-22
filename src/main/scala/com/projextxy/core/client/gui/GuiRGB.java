package com.projextxy.core.client.gui;

import codechicken.lib.colour.ColourRGBA;
import com.projextxy.core.ProjectXYCore$;
import com.projextxy.core.inventory.ContainerRGB;
import com.projextxy.core.reference.MCColors;
import com.projextxy.core.tile.TileColorizer;
import com.projextxy.lib.cofh.gui.GuiColor;
import com.projextxy.lib.cofh.gui.element.listbox.SliderHorizontal;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;


public class GuiRGB extends GuiCommon {
    private final TileColorizer tileCustomColor;

    public GuiRGB(InventoryPlayer inventory, TileColorizer tileColorizer) {
        super(new ContainerRGB(inventory, tileColorizer), new ResourceLocation(ProjectXYCore$.MODULE$.MOD_ID().toLowerCase(), "/textures/gui/guiColorizer.png"));
        this.tileCustomColor = tileColorizer;
        drawInventory = false;
    }

    @Override
    public void initGui() {
        super.initGui();
        SliderHorizontal sliderRed = new SliderHorizontal(this, 10, 20, xSize - 20, 15, 255) {
            @Override
            public void onValueChanged(int value) {
                tileCustomColor.setR(value);
                tileCustomColor.buildPacket().sendToServer();
            }
        };
        SliderHorizontal sliderGreen = new SliderHorizontal(this, 10, 45, xSize - 20, 15, 255) {
            @Override
            public void onValueChanged(int value) {
                tileCustomColor.setG(value);
                tileCustomColor.buildPacket().sendToServer();
            }
        };

        SliderHorizontal sliderBlue = new SliderHorizontal(this, 10, 70, xSize - 20, 15, 255) {
            @Override
            public void onValueChanged(int value) {
                tileCustomColor.setB(value);
                tileCustomColor.buildPacket().sendToServer();
            }
        };

        sliderRed.setValue(tileCustomColor.getR());
        sliderGreen.setValue(tileCustomColor.getG());
        sliderBlue.setValue(tileCustomColor.getB());
        sliderRed.setColor(new GuiColor(120, 120, 120, 255).getColor(), new GuiColor(60, 60, 60, 255).getColor());
        sliderGreen.setColor(new GuiColor(120, 120, 120, 255).getColor(), new GuiColor(60, 60, 60, 255).getColor());
        sliderBlue.setColor(new GuiColor(120, 120, 120, 255).getColor(), new GuiColor(60, 60, 60, 255).getColor());
        elements.add(sliderRed);
        elements.add(sliderGreen);
        elements.add(sliderBlue);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int x, int y) {
        super.drawGuiContainerForegroundLayer(x, y);
        fontRendererObj.drawStringWithShadow("Red: " + String.valueOf(tileCustomColor.getR()), 10, 10, MCColors.RED.rgb);
        fontRendererObj.drawStringWithShadow("Green: " + String.valueOf(tileCustomColor.getG()), 10, 36, MCColors.GREEN.rgb);
        fontRendererObj.drawStringWithShadow("Blue: " + String.valueOf(tileCustomColor.getB()), 10, 61, MCColors.BLUE.rgb);
        drawProgressBar(xSize / 2 - 14, 93, 1D, new ColourRGBA(tileCustomColor.getR(), tileCustomColor.getG(), tileCustomColor.getB(), 255));
    }

    @Override
    public void drawBackground(int p_146278_1_) {
        super.drawBackground(p_146278_1_);
    }

    @Override
    public void onGuiClosed() {
        tileCustomColor.buildPacket().sendToServer();
        super.onGuiClosed();
    }
}
