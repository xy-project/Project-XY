package com.projextxy.core.client.render.connected;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

@SideOnly(Side.CLIENT)
public class IconConnectedTexture
        implements IIcon {
    public final IIcon[] icons = new IIcon[5];
    private int n;

    public IconConnectedTexture(IIconRegister r, String texture) {
        this(r.registerIcon(texture), r.registerIcon(texture + "_vertical"), r.registerIcon(texture + "_horizontal"), r.registerIcon(texture + "_corners"), r.registerIcon(texture + "_anticorners"));
    }

    public IconConnectedTexture(IIcon baseIcon, IIcon verticalIcon, IIcon horizontalIcon, IIcon cornerIcon, IIcon anticornerIcon) {
        this.icons[0] = cornerIcon;
        this.icons[1] = verticalIcon;
        this.icons[2] = horizontalIcon;
        this.icons[3] = baseIcon;
        this.icons[4] = anticornerIcon;
    }

    public void setType(int i) {
        this.n = i;
    }

    public void resetType() {
        setType(0);
    }

    public float getMinU() {
        return this.icons[this.n].getMinU();
    }

    public float getMaxU() {
        return this.icons[this.n].getMaxU();
    }

    public float getInterpolatedU(double par1) {
        float f = getMaxU() - getMinU();
        return getMinU() + f * ((float) par1 / 16.0F);
    }

    public float getMinV() {
        return this.icons[this.n].getMinV();
    }

    public float getMaxV() {
        return this.icons[this.n].getMaxV();
    }

    public float getInterpolatedV(double par1) {
        float f = getMaxV() - getMinV();
        return getMinV() + f * ((float) par1 / 16.0F);
    }

    public String getIconName() {
        return this.icons[this.n].getIconName();
    }

    @SideOnly(Side.CLIENT)
    public int getIconWidth() {
        return this.icons[this.n].getIconWidth();
    }

    @SideOnly(Side.CLIENT)
    public int getIconHeight() {
        return this.icons[this.n].getIconHeight();
    }

    private class TextureVirtual implements IIcon {
        private int width, height;
        private float umin, umax, vmin, vmax;
        private String name;
        private IIcon parentIcon;

        private TextureVirtual(IIcon parent, int w, int h, int x, int y) {
            parentIcon = parent;

            umin = parentIcon.getInterpolatedU(16.0 * (x) / w);
            umax = parentIcon.getInterpolatedU(16.0 * (x + 1) / w);
            vmin = parentIcon.getInterpolatedV(16.0 * (y) / h);
            vmax = parentIcon.getInterpolatedV(16.0 * (y + 1) / h);

            name = parentIcon.getIconName() + "|" + x + "." + y;

            width = parentIcon.getIconWidth();
            height = parentIcon.getIconHeight();
        }

        @Override
        @SideOnly(Side.CLIENT)
        public float getMinU() {
            return umin;
        }

        @Override
        @SideOnly(Side.CLIENT)
        public float getMaxU() {
            return umax;
        }

        @Override
        @SideOnly(Side.CLIENT)
        public float getInterpolatedU(double d0) {
            return (float) (umin + (umax - umin) * d0 / 16.0);
        }

        @Override
        @SideOnly(Side.CLIENT)
        public float getMinV() {
            return vmin;
        }

        @Override
        @SideOnly(Side.CLIENT)
        public float getMaxV() {
            return vmax;
        }

        @Override
        @SideOnly(Side.CLIENT)
        public float getInterpolatedV(double d0) {
            return (float) (vmin + (vmax - vmin) * d0 / 16.0);
        }

        @Override
        @SideOnly(Side.CLIENT)
        public String getIconName() {
            return name;
        }

        @Override
        @SideOnly(Side.CLIENT)
        public int getIconWidth() {
            return width;
        }

        @Override
        @SideOnly(Side.CLIENT)
        public int getIconHeight() {
            return height;
        }
    }
}
