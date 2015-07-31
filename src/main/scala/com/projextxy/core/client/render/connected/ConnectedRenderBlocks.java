package com.projextxy.core.client.render.connected;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

@SideOnly(Side.CLIENT)
public class ConnectedRenderBlocks
        extends RenderBlocks {
    public static final double[] u = {-1.0D, 1.0D, 1.0D, -1.0D};
    public static final double[] v = {1.0D, 1.0D, -1.0D, -1.0D};
    public Block curBlock = null;
    public int curMeta = 0;
    public boolean isOpaque = false;
    public boolean changeBounds = false;

    public void setWorld(IBlockAccess blockAccess) {
        this.blockAccess = blockAccess;
    }

    public void setLightAndColor(double u2, double v2, int side) {
        if (this.enableAO) {
            Tessellator t = Tessellator.instance;
            double u = 0.0D;
            double v = 0.0D;
            if ((side == 0) || (side == 1)) {
                u = 1.0D - u2;
                v = 1.0D - v2;
            } else if (side == 2) {
                u = v2;
                v = 1.0D - u2;
            } else if (side == 3) {
                u = u2;
                v = v2;
            } else if (side == 4) {
                u = v2;
                v = 1.0D - u2;
            } else if (side == 5) {
                u = 1.0D - v2;
                v = u2;
            }
            t.setBrightness(mixAoBrightness(this.brightnessTopLeft, this.brightnessTopRight, this.brightnessBottomLeft, this.brightnessBottomRight, u * v, v * (1.0D - u), (1.0D - v) * u, (1.0D - u) * (1.0D - v)));
            t.setColorOpaque_F(mix(this.colorRedTopLeft, this.colorRedTopRight, this.colorRedBottomLeft, this.colorRedBottomRight, u, v), mix(this.colorGreenTopLeft, this.colorGreenTopRight, this.colorGreenBottomLeft, this.colorGreenBottomRight, u, v), mix(this.colorBlueTopLeft, this.colorBlueTopRight, this.colorBlueBottomLeft, this.colorBlueBottomRight, u, v));
        }
    }

    public float mix(double tl, double tr, double bl, double br, double u, double v) {
        return (float) (tl * u * v + tr * (1.0D - u) * v + bl * u * (1.0D - v) + br * (1.0D - u) * (1.0D - v));
    }

    public void renderSide(Block block, double x, double y, double z, double ox, double oy, double oz, int ax, int ay, int az, int bx, int by, int bz, IconConnectedTexture icon, int side, double rx, double ry, double rz) {
        Tessellator t = Tessellator.instance;
        byte[] i = new byte[4];
        this.isOpaque = block.isOpaqueCube();

        boolean areSame = true;
        for (int j = 0; j < 4; j++) {
            i[j] = getType(block, side, (int) x, (int) y, (int) z, ax * (int) u[j], ay * (int) u[j], az * (int) u[j], bx * (int) v[j], by * (int) v[j], bz * (int) v[j], (int) (ox * 2.0D - 1.0D), (int) (oy * 2.0D - 1.0D), (int) (oz * 2.0D - 1.0D));
            if ((areSame) && (j > 0) && (i[j] != i[0])) {
                areSame = false;
            }
        }
        if (areSame) {
            icon.setType(i[0]);
            for (int j = 0; j < 4; j++) {
                double cx = x + rx + ox + u[j] * ax * 0.5D + v[j] * bx * 0.5D;
                double cy = y + ry + oy + u[j] * ay * 0.5D + v[j] * by * 0.5D;
                double cz = z + rz + oz + u[j] * az * 0.5D + v[j] * bz * 0.5D;

                double offsetX = 0;
                double offsetY = 0;
                double offsetZ = 0;
                if (changeBounds)
                    switch (side) {
                        case 0:
                            offsetY -= .0004;
                            break;
                        case 1:
                            offsetY += .0004;
                            break;
                        case 2:
                            offsetZ -= .0004;
                            break;
                        case 3:
                            offsetZ += .0004;
                            break;
                        case 4:
                            offsetX -= .0004;
                            break;
                        case 5:
                            offsetX += .0004;
                            break;
                    }

                setLightAndColor(0.5D + u[j] * 0.5D, 0.5D + v[j] * 0.5D, side);
                t.addVertexWithUV(cx + offsetX, cy + offsetY, cz + offsetZ, icon.getInterpolatedU(16.0D - (8.0D + u[j] * 8.0D)), icon.getInterpolatedV(16.0D - (8.0D + v[j] * 8.0D)));
            }
            icon.resetType();
            return;
        }
        for (int j = 0; j < 4; j++) {
            icon.setType(i[j]);
            double cx = x + rx + ox + ax * u[j] / 4.0D + bx * v[j] / 4.0D;
            double cy = y + ry + oy + ay * u[j] / 4.0D + by * v[j] / 4.0D;
            double cz = z + rz + oz + az * u[j] / 4.0D + bz * v[j] / 4.0D;

            double offsetX = 0;
            double offsetY = 0;
            double offsetZ = 0;
            if (changeBounds)
                switch (side) {
                    case 0:
                        offsetY -= .0004;
                        break;
                    case 1:
                        offsetY += .0004;
                        break;
                    case 2:
                        offsetZ -= .0004;
                        break;
                    case 3:
                        offsetZ += .0004;
                        break;
                    case 4:
                        offsetX -= .0004;
                        break;
                    case 5:
                        offsetX += .0004;
                        break;
                }

            for (int k = 0; k < 4; k++) {
                setLightAndColor(0.5D + u[j] * 0.25D + u[k] * 0.25D, 0.5D + v[j] * 0.25D + v[k] * 0.25D, side);
                t.addVertexWithUV(cx + u[k] * ax * 0.25D + v[k] * bx * 0.25D + offsetX, cy + u[k] * ay * 0.25D + v[k] * by * 0.25D + offsetY, cz + u[k] * az * 0.25D + v[k] * bz * 0.25D + offsetZ, icon.getInterpolatedU(16.0D - (8.0D + u[j] * 4.0D + u[k] * 4.0D)), icon.getInterpolatedV(16.0D - (8.0D + v[j] * 4.0D + v[k] * 4.0D)));
            }
            icon.resetType();
        }
    }

    public int getSideFromDir(int dx, int dy, int dz) {
        if (dy < 0) {
            return 0;
        }
        if (dy > 0) {
            return 1;
        }
        if (dz < 0) {
            return 2;
        }
        if (dz > 0) {
            return 3;
        }
        if (dx < 0) {
            return 4;
        }
        if (dx > 0) {
            return 5;
        }
        return 0;
    }

    public boolean matchBlock(int side2, int x2, int y2, int z2) {
        Block block = this.blockAccess.getBlock(x2, y2, z2);
        if (block == this.curBlock) {
            return this.curMeta == this.blockAccess.getBlockMetadata(x2, y2, z2);
        }
        return false;
    }

    public byte getType(Block block, int side, int x, int y, int z, int ax, int ay, int az, int bx, int by, int bz, int cx, int cy, int cz) {
        int sidea = getSideFromDir(ax, ay, az);
        int sideb = getSideFromDir(bx, by, bz);
        boolean a = (matchBlock(side, x + ax, y + ay, z + az)) && (!matchBlock(sidea, x + cx, y + cy, z + cz)) && (!matchBlock(net.minecraft.util.Facing.oppositeSide[sidea], x + ax + cx, y + ay + cy, z + az + cz));
        boolean b = (matchBlock(side, x + bx, y + by, z + bz)) && (!matchBlock(sideb, x + cx, y + cy, z + cz)) && (!matchBlock(net.minecraft.util.Facing.oppositeSide[sideb], x + bx + cx, y + by + cy, z + bz + cz));
        if (a) {
            if (b) {
                if (matchBlock(side, x + ax + bx, y + ay + by, z + az + bz)) {
                    if ((matchBlock(net.minecraft.util.Facing.oppositeSide[sidea], x + ax + bx + cx, y + ay + by + cy, z + az + bz + cz)) || (matchBlock(net.minecraft.util.Facing.oppositeSide[sideb], x + ax + bx + cx, y + ay + by + cy, z + az + bz + cz)) || (matchBlock(sidea, x + bx + cx, y + by + cy, z + bz + cz)) || (matchBlock(sideb, x + ax + cx, y + ay + cy, z + az + cz))) {
                        return 4;
                    }
                    return 3;
                }
                return 4;
            }
            return 2;
        }
        if (b) {
            return 1;
        }
        return 0;
    }

    @Override
    public void renderFaceYNeg(Block block, double x, double y, double z, IIcon IIcon) {
        if (hasOverrideBlockTexture()) {
            IIcon = this.overrideBlockTexture;
        }
        if ((IIcon instanceof IconConnectedTexture)) {
            renderSide(block, x, y, z, 0.5D, 0.0D, 0.5D, -1, 0, 0, 0, 0, 1, new IconConnectedTextureFlipped((IconConnectedTexture) IIcon), 0, 0.0D, 0.0D, 0.0D);
        } else {
            super.renderFaceYNeg(block, x, y, z, IIcon);
        }
    }

    @Override
    public void renderFaceYPos(Block block, double x, double y, double z, IIcon IIcon) {
        if (hasOverrideBlockTexture()) {
            IIcon = this.overrideBlockTexture;
        }
        if ((IIcon instanceof IconConnectedTexture)) {
            renderSide(block, x, y, z, 0.5D, 1.0D, 0.5D, -1, 0, 0, 0, 0, -1, (IconConnectedTexture) IIcon, 1, 0.0D, 0.0D, 0.0D);
        } else {
            super.renderFaceYPos(block, x, y, z, IIcon);
        }
    }

    @Override
    public void renderFaceZNeg(Block block, double x, double y, double z, IIcon IIcon) {
        if (hasOverrideBlockTexture()) {
            IIcon = this.overrideBlockTexture;
        }
        if ((IIcon instanceof IconConnectedTexture)) {
            renderSide(block, x, y, z, 0.5D, 0.5D, 0.0D, 1, 0, 0, 0, 1, 0, (IconConnectedTexture) IIcon, 2, 0.0D, 0.0D, 0.0D);
        } else {
            super.renderFaceZNeg(block, x, y, z, IIcon);
        }
    }

    @Override
    public void renderFaceZPos(Block block, double x, double y, double z, IIcon IIcon) {
        if (hasOverrideBlockTexture()) {
            IIcon = this.overrideBlockTexture;
        }
        if ((IIcon instanceof IconConnectedTexture)) {
            renderSide(block, x, y, z, 0.5D, 0.5D, 1.0D, -1, 0, 0, 0, 1, 0, (IconConnectedTexture) IIcon, 3, 0.0D, 0.0D, 0.0D);
        } else {
            super.renderFaceZPos(block, x, y, z, IIcon);
        }
    }

    @Override
    public void renderFaceXNeg(Block block, double x, double y, double z, IIcon IIcon) {
        if (hasOverrideBlockTexture()) {
            IIcon = this.overrideBlockTexture;
        }
        if ((IIcon instanceof IconConnectedTexture)) {
            renderSide(block, x, y, z, 0.0D, 0.5D, 0.5D, 0, 0, -1, 0, 1, 0, (IconConnectedTexture) IIcon, 4, 0.0D, 0.0D, 0.0D);
        } else {
            super.renderFaceXNeg(block, x, y, z, IIcon);
        }
    }

    @Override
    public void renderFaceXPos(Block block, double x, double y, double z, IIcon IIcon) {
        if (hasOverrideBlockTexture()) {
            IIcon = this.overrideBlockTexture;
        }
        if ((IIcon instanceof IconConnectedTexture)) {
            renderSide(block, x, y, z, 1.0D, 0.5D, 0.5D, 0, 0, 1, 0, 1, 0, (IconConnectedTexture) IIcon, 5, 0.0D, 0.0D, 0.0D);
        } else {
            super.renderFaceXPos(block, x, y, z, IIcon);
        }
    }
}
