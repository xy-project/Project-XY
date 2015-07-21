package com.projextxy.core.client.gui;

import codechicken.lib.colour.ColourRGBA;
import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.RenderUtils;
import codechicken.lib.vec.Vector3;
import com.projextxy.core.ProjectXYCore$;
import com.projextxy.core.blocks.glow.BlockXyGlow$;
import com.projextxy.lib.cofh.gui.GuiBase;
import com.projextxy.lib.cofh.render.RenderHelper;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GuiCommon extends GuiBase {
    public static final ResourceLocation baseGui = new ResourceLocation(ProjectXYCore$.MODULE$.MOD_ID().toLowerCase(), "/textures/gui/guiBase.png");

    public GuiCommon(Container container, ResourceLocation resourceLocation) {
        super(container, resourceLocation);
        xSize = 176;
        ySize = 214;
    }

    public void drawProgressBar(int x, int y, double progress) {
        drawProgressBar(x, y, progress, new ColourRGBA(255, 255, 255, 255));
    }

    public void drawProgressBar(int x, int y, double progress, ColourRGBA colourRGBA) {
        CCRenderState.reset();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        bindTexture(baseGui);
        drawBar(x, y);
        double width = 25 * progress;
        RenderHelper.setBlockTextureSheet();
        CCRenderState.startDrawing();
        colourRGBA.glColour();
        RenderUtils.renderFluidQuad(new Vector3(x + 2, 2 + y, this.zLevel), new Vector3(x + 2, 8 + y, this.zLevel), new Vector3(x + 2 + width, 8 + y, this.zLevel), new Vector3(x + 2 + width, 2 + y, this.zLevel), BlockXyGlow$.MODULE$.baseIcon(), 16.0D);
        CCRenderState.draw();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }

    public void drawBar(int x, int y) {
        drawTexturedModalRect(x, y, 176, 0, 29, 10);
    }

}
