package com.projextxy.mech.nei;

import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;
import com.projextxy.mech.gui.GuiFabricator;

public class NEIProjectXYConfig implements IConfigureNEI {
    @Override
    public void loadConfig() {
        API.registerGuiOverlayHandler(GuiFabricator.class, new FabricatorOverlay(), "crafting");
    }

    @Override
    public String getName() {
        return "Project XY Machines";
    }

    @Override
    public String getVersion() {
        return "1";
    }
}
