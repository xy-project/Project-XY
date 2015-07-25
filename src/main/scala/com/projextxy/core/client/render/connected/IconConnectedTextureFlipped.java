package com.projextxy.core.client.render.connected;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class IconConnectedTextureFlipped
  extends IconConnectedTexture
{
  public IconConnectedTextureFlipped(IconConnectedTexture icon)
  {
    super(icon.icons[3], icon.icons[1], icon.icons[2], icon.icons[0], icon.icons[4]);
  }
  
  public float getMinV()
  {
    return super.getMaxV();
  }
  
  public float getMaxV()
  {
    return super.getMinV();
  }
}
