package com.projextxy.core.client.render.block;

import codechicken.lib.colour.ColourRGBA;
import codechicken.lib.render.CCModel;
import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.uv.IconTransformation;
import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.Translation;
import codechicken.lib.vec.Vector3;
import com.projextxy.core.blocks.glow.BlockXyGlow;
import com.projextxy.core.blocks.glow.BlockXyGlow$;
import com.projextxy.core.blocks.glow.ColorMultiplier;
import com.projextxy.core.blocks.traits.TConnectedTextureBlock;
import com.projextxy.core.client.CTRegistry$;
import com.projextxy.core.client.render.connected.ConnectedRenderBlocks;
import com.projextxy.core.client.render.connected.IconConnectedTexture;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.MinecraftForgeClient;
import org.lwjgl.opengl.GL11;

public class RenderSimpleGlow extends RenderBlock implements ISimpleBlockRenderingHandler {
    public static final int modelId = RenderingRegistry.getNextAvailableRenderId();
    private static final CCModel baseModel = CCModel.quadModel(24).generateBlock(0, new Cuboid6(new Vector3(0.001, 0.001, 0.001), new Vector3(.999, .999, .999))).apply(new Translation(new Vector3(-.5, -.5, -.5))).computeNormals();
    public static ConnectedRenderBlocks fakeRender = new ConnectedRenderBlocks();

    public ConnectedRenderBlocks getFakeRender() {
        return fakeRender;
    }

    @Override
    public int getRenderId() {
        return modelId;
    }

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
        BlockXyGlow blockXyGlow = (BlockXyGlow) block;
        if (block instanceof ColorMultiplier) {
            new ColourRGBA(blockXyGlow.getColor(metadata) << 8 | 0xFF).glColour();
        }

        if (block instanceof TConnectedTextureBlock) {
            final String folder = ((TConnectedTextureBlock) block).connectedFolder();
            renderStandardInvBlockWithTexture(renderer, block, CTRegistry$.MODULE$.getTexture(folder).icons[0]);
        } else {
            renderStandardInvBlock(renderer, block, metadata);
        }

        CCRenderState.reset();
        CCRenderState.useNormals = true;
        CCRenderState.startDrawing();
        CCRenderState.setBrightness(blockXyGlow.getBrightness(metadata));
        baseModel.setColour(new ColourRGBA(blockXyGlow.getColor(metadata) << 8 | 0xFF).rgba()).render(new IconTransformation(BlockXyGlow$.MODULE$.baseIcon()));
        CCRenderState.draw();
    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
        final Tessellator tess = Tessellator.instance;
        BlockXyGlow blockXyGlow = (BlockXyGlow) block;

        tess.setBrightness(blockXyGlow.getBrightness(world, x, y, z));
        tess.setColorRGBA_I(blockXyGlow.getColor(world.getBlockMetadata(x, y, z)), 255);
        if (block instanceof TConnectedTextureBlock) {
            //renderer.setRenderBounds(0.001, 0.001, 0.001, .999, .999, .999);
            renderAllSides(world, x, y, z, block, renderer, BlockXyGlow$.MODULE$.baseIcon(), false);
            final String folder = ((TConnectedTextureBlock) block).connectedFolder();
            final IconConnectedTexture texture = CTRegistry$.MODULE$.getTexture(folder);
            getFakeRender().setOverrideBlockTexture(texture);
            getFakeRender().setWorld(world);
            getFakeRender().curBlock = block;
            getFakeRender().curMeta = world.getBlockMetadata(x, y, z);
            getFakeRender().changeBounds = true;
            getFakeRender().setRenderBoundsFromBlock(block);
            GL11.glEnable(GL11.GL_BLEND);
            return getFakeRender().renderStandardBlock(block, x, y, z);
        } else {
            renderAllSides(world, x, y, z, block, renderer, BlockXyGlow$.MODULE$.baseIcon(), false);
            renderer.setRenderBoundsFromBlock(block);
            renderer.renderStandardBlock(block, x, y, z);
        }
        return true;
    }


    @Override
    public boolean shouldRender3DInInventory(int modelId) {
        return true;
    }
}
