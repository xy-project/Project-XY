package com.projextxy.core.client.render.item;

import codechicken.lib.colour.ColourRGBA;
import codechicken.lib.render.CCModel;
import codechicken.lib.render.CCModelLibrary;
import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.uv.IconTransformation;
import codechicken.lib.vec.*;
import com.projextxy.core.CoreItems;
import com.projextxy.core.ProjectXYCoreProxy;
import com.projextxy.core.resource.ResourceLib;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

public class XychoriumlItemRenderer implements IItemRenderer {

    public static CCModel model = CCModelLibrary.icosahedron7.copy().apply(new TransformationList(new Scale(new Vector3(.35, 1, .35))));

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        return true;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
        return true;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
        Transformation transformation = null;
        CCRenderState.reset();
        CCRenderState.useNormals = true;
        CCRenderState.startDrawing();
        CCRenderState.setBrightness(220);

        switch (type) {
            case INVENTORY:
                transformation = new TransformationList(new Scale(.45));
                break;
            case EQUIPPED_FIRST_PERSON:
                transformation = new TransformationList(new Scale(.3), new Translation(new Vector3(0F, 1F, .75F)), new Rotation(270, new Vector3(1, 0, 0)));
                break;
            case ENTITY:
                transformation = new TransformationList(new Scale(.3), new Translation(new Vector3(0F, .5F, 0F)));
                break;
            case EQUIPPED:
                transformation = new TransformationList(new Scale(.5), new Translation(new Vector3(.7, 1, .7)));
                break;
        }

        ResourceLib.blockSheet().bind();
        model.setColour(new ColourRGBA(CoreItems.itemXychorium().colors().apply(item.getItemDamage()).rgb << 8 | 0xFF).rgba()).computeNormals().render(transformation, new IconTransformation(ProjectXYCoreProxy.animationFx().texture));
        CCRenderState.draw();
        ResourceLib.itemSheet().bind();
    }
}
