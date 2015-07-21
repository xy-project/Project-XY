package com.projextxy.core.client.render.block;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import org.lwjgl.opengl.GL11;

public class RenderBlock {

    public static IIcon getIcon(IIcon icon) {
        if (icon != null)
            return icon;
        return ((TextureMap) Minecraft.getMinecraft().getTextureManager().getTexture(TextureMap.locationBlocksTexture)).getAtlasSprite("missingno");
    }

    protected static void renderAllSides(IBlockAccess world, int x, int y, int z, Block block, RenderBlocks renderer, IIcon tex) {
        renderAllSides(world, x, y, z, block, renderer, tex, true);
    }

    public static void renderAllSides(IBlockAccess world, int x, int y, int z, Block block, RenderBlocks renderer, IIcon tex, boolean allsides) {
        if (allsides || block.shouldSideBeRendered(world, x + 1, y, z, 6))
            renderer.renderFaceXPos(block, x, y, z, tex);
        if (allsides || block.shouldSideBeRendered(world, x - 1, y, z, 6))
            renderer.renderFaceXNeg(block, x, y, z, tex);
        if (allsides || block.shouldSideBeRendered(world, x, y, z + 1, 6))
            renderer.renderFaceZPos(block, x, y, z, tex);
        if (allsides || block.shouldSideBeRendered(world, x, y, z - 1, 6))
            renderer.renderFaceZNeg(block, x, y, z, tex);
        if (allsides || block.shouldSideBeRendered(world, x, y + 1, z, 6))
            renderer.renderFaceYPos(block, x, y, z, tex);
        if (allsides || block.shouldSideBeRendered(world, x, y - 1, z, 6))
            renderer.renderFaceYNeg(block, x, y, z, tex);
    }

    public static void renderStandardInvBlock(RenderBlocks renderblocks, Block block, int meta) {
        final Tessellator tessellator = Tessellator.instance;
        GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, -1F, 0.0F);
        renderblocks.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, getIcon(block.getIcon(0, meta)));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 1.0F, 0.0F);
        renderblocks.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, getIcon(block.getIcon(1, meta)));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 0.0F, -1F);
        renderblocks.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, getIcon(block.getIcon(2, meta)));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 0.0F, 1.0F);
        renderblocks.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, getIcon(block.getIcon(3, meta)));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(-1F, 0.0F, 0.0F);
        renderblocks.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, getIcon(block.getIcon(4, meta)));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(1.0F, 0.0F, 0.0F);
        renderblocks.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, getIcon(block.getIcon(5, meta)));
        tessellator.draw();
        GL11.glTranslatef(0.5F, 0.5F, 0.5F);
    }

    public static void renderStandardInvBlockWithTexture(RenderBlocks renderblocks, Block block, IIcon icon) {
        final Tessellator tessellator = Tessellator.instance;
        GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, -1F, 0.0F);
        renderblocks.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, icon);
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 1.0F, 0.0F);
        renderblocks.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, icon);
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 0.0F, -1F);
        renderblocks.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, icon);
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 0.0F, 1.0F);
        renderblocks.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, icon);
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(-1F, 0.0F, 0.0F);
        renderblocks.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, icon);
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(1.0F, 0.0F, 0.0F);
        renderblocks.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, icon);
        tessellator.draw();
        GL11.glTranslatef(0.5F, 0.5F, 0.5F);
    }
}
