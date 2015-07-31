package com.projextxy.lib.cofh;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import org.lwjgl.opengl.GL11;

/**
 * Contains various helper functions to assist with rendering.
 *
 * @author King Lemming
 */
public final class RenderHelper {
    private RenderHelper() {
    }
    public static void renderTextureAsBlock(RenderBlocks renderer, IIcon texture, double translateX, double translateY, double translateZ, int color) {
        Tessellator tessellator = Tessellator.instance;
        Block block = Blocks.stone;

        if (texture == null) {
            return;
        }
        renderer.setRenderBoundsFromBlock(block);
        GL11.glTranslated(translateX, translateY, translateZ);
        tessellator.startDrawingQuads();
        tessellator.setBrightness(220);
        tessellator.setColorRGBA_I(color, 255);
        tessellator.setNormal(0.0F, -1.0F, 0.0F);
        renderer.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, texture);

        tessellator.setNormal(0.0F, 1.0F, 0.0F);
        renderer.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, texture);

        tessellator.setNormal(0.0F, 0.0F, -1.0F);
        renderer.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, texture);

        tessellator.setNormal(0.0F, 0.0F, 1.0F);
        renderer.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, texture);

        tessellator.setNormal(-1.0F, 0.0F, 0.0F);
        renderer.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, texture);

        tessellator.setNormal(1.0F, 0.0F, 0.0F);
        renderer.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, texture);

        GL11.glTranslated(-translateX, -translateY, -translateZ);
        tessellator.draw();
    }

    public static void renderTextureAsBlock(RenderBlocks renderer, Block block, int meta, double translateX, double translateY, double translateZ, int color) {
        Tessellator tessellator = Tessellator.instance;
        renderer.setRenderBoundsFromBlock(block);
        GL11.glTranslated(translateX, translateY, translateZ);
        tessellator.startDrawingQuads();
        tessellator.setColorRGBA_I(color, 255);
        tessellator.setNormal(0.0F, -1.0F, 0.0F);
        renderer.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(0, meta));
        tessellator.setNormal(0.0F, 1.0F, 0.0F);
        renderer.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(1, meta));
        tessellator.setNormal(0.0F, 0.0F, -1.0F);
        renderer.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(2, meta));
        tessellator.setNormal(0.0F, 0.0F, 1.0F);
        renderer.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(3, meta));
        tessellator.setNormal(-1.0F, 0.0F, 0.0F);
        renderer.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(4, meta));
        tessellator.setNormal(1.0F, 0.0F, 0.0F);
        renderer.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(5, meta));
        GL11.glTranslated(-translateX, -translateY, -translateZ);
        tessellator.draw();
    }
}
