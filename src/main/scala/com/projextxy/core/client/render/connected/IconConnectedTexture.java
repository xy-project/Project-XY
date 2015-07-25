package com.projextxy.core.client.render.connected;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

@SideOnly(Side.CLIENT)
public class IconConnectedTexture
  implements IIcon
{
  public final IIcon[] icons = new IIcon[5];
  private int n;
  
  public IconConnectedTexture(IIconRegister r, String texture)
  {
    this(r.registerIcon(texture), r.registerIcon(texture + "_vertical"), r.registerIcon(texture + "_horizontal"), r.registerIcon(texture + "_corners"), r.registerIcon(texture + "_anticorners"));
  }
  
  public IconConnectedTexture(IIcon baseIcon, IIcon verticalIcon, IIcon horizontalIcon, IIcon cornerIcon, IIcon anticornerIcon)
  {
    this.icons[0] = cornerIcon;
    this.icons[1] = verticalIcon;
    this.icons[2] = horizontalIcon;
    this.icons[3] = baseIcon;
    this.icons[4] = anticornerIcon;
  }
  
  public void setType(int i)
  {
    this.n = i;
  }
  
  public void resetType()
  {
    setType(0);
  }
  
  public float getMinU()
  {
    return this.icons[this.n].getMinU();
  }
  
  public float getMaxU()
  {
    return this.icons[this.n].getMaxU();
  }
  
  public float getInterpolatedU(double par1)
  {
    float f = getMaxU() - getMinU();
    return getMinU() + f * ((float)par1 / 16.0F);
  }
  
  public float getMinV()
  {
    return this.icons[this.n].getMinV();
  }
  
  public float getMaxV()
  {
    return this.icons[this.n].getMaxV();
  }
  
  public float getInterpolatedV(double par1)
  {
    float f = getMaxV() - getMinV();
    return getMinV() + f * ((float)par1 / 16.0F);
  }
  
  public String getIconName()
  {
    return this.icons[this.n].getIconName();
  }
  
  @SideOnly(Side.CLIENT)
  public int getIconWidth()
  {
    return this.icons[this.n].getIconWidth();
  }
  
  @SideOnly(Side.CLIENT)
  public int getIconHeight()
  {
    return this.icons[this.n].getIconHeight();
  }
}
