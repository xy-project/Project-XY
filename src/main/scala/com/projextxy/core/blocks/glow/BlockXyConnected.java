package com.projextxy.core.blocks.glow;

import com.projextxy.core.client.render.block.RenderSimpleGlow;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

public class BlockXyConnected extends BlockXyGlow {
    private static final IIcon[] icons = new IIcon[16];
    private final String folder;

    public BlockXyConnected(Material material, String textureFolder) {
        super(material, RenderSimpleGlow.modelId);
        this.folder = textureFolder;
    }


    public static IIcon getConnectedBlockTexture(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5, Block connectBlock) {
        boolean isOpenUp = false, isOpenDown = false, isOpenLeft = false, isOpenRight = false;
        switch (par5) {
            case 0:
                if (shouldConnectToBlock(par1IBlockAccess, par2, par3, par4, par1IBlockAccess.getBlock(par2 - 1, par3, par4), par1IBlockAccess.getBlockMetadata(par2 - 1, par3, par4), connectBlock)) {
                    isOpenDown = true;
                }

                if (shouldConnectToBlock(par1IBlockAccess, par2, par3, par4, par1IBlockAccess.getBlock(par2 + 1, par3, par4), par1IBlockAccess.getBlockMetadata(par2 + 1, par3, par4), connectBlock)) {
                    isOpenUp = true;
                }

                if (shouldConnectToBlock(par1IBlockAccess, par2, par3, par4, par1IBlockAccess.getBlock(par2, par3, par4 - 1), par1IBlockAccess.getBlockMetadata(par2, par3, par4 - 1), connectBlock)) {
                    isOpenLeft = true;
                }

                if (shouldConnectToBlock(par1IBlockAccess, par2, par3, par4, par1IBlockAccess.getBlock(par2, par3, par4 + 1), par1IBlockAccess.getBlockMetadata(par2, par3, par4 + 1), connectBlock)) {
                    isOpenRight = true;
                }

                if (isOpenUp && isOpenDown && isOpenLeft && isOpenRight) {
                    return icons[15];
                } else if (isOpenUp && isOpenDown && isOpenLeft) {
                    return icons[11];
                } else if (isOpenUp && isOpenDown && isOpenRight) {
                    return icons[12];
                } else if (isOpenUp && isOpenLeft && isOpenRight) {
                    return icons[13];
                } else if (isOpenDown && isOpenLeft && isOpenRight) {
                    return icons[14];
                } else if (isOpenDown && isOpenUp) {
                    return icons[5];
                } else if (isOpenLeft && isOpenRight) {
                    return icons[6];
                } else if (isOpenDown && isOpenLeft) {
                    return icons[8];
                } else if (isOpenDown && isOpenRight) {
                    return icons[10];
                } else if (isOpenUp && isOpenLeft) {
                    return icons[7];
                } else if (isOpenUp && isOpenRight) {
                    return icons[9];
                } else if (isOpenDown) {
                    return icons[3];
                } else if (isOpenUp) {
                    return icons[4];
                } else if (isOpenLeft) {
                    return icons[2];
                } else if (isOpenRight) {
                    return icons[1];
                }
                break;
            case 1:
                if (shouldConnectToBlock(par1IBlockAccess, par2, par3, par4, par1IBlockAccess.getBlock(par2 - 1, par3, par4), par1IBlockAccess.getBlockMetadata(par2 - 1, par3, par4), connectBlock)) {
                    isOpenDown = true;
                }

                if (shouldConnectToBlock(par1IBlockAccess, par2, par3, par4, par1IBlockAccess.getBlock(par2 + 1, par3, par4), par1IBlockAccess.getBlockMetadata(par2 + 1, par3, par4), connectBlock)) {
                    isOpenUp = true;
                }

                if (shouldConnectToBlock(par1IBlockAccess, par2, par3, par4, par1IBlockAccess.getBlock(par2, par3, par4 - 1), par1IBlockAccess.getBlockMetadata(par2, par3, par4 - 1), connectBlock)) {
                    isOpenLeft = true;
                }

                if (shouldConnectToBlock(par1IBlockAccess, par2, par3, par4, par1IBlockAccess.getBlock(par2, par3, par4 + 1), par1IBlockAccess.getBlockMetadata(par2, par3, par4 + 1), connectBlock)) {
                    isOpenRight = true;
                }

                if (isOpenUp && isOpenDown && isOpenLeft && isOpenRight) {
                    return icons[15];
                } else if (isOpenUp && isOpenDown && isOpenLeft) {
                    return icons[11];
                } else if (isOpenUp && isOpenDown && isOpenRight) {
                    return icons[12];
                } else if (isOpenUp && isOpenLeft && isOpenRight) {
                    return icons[13];
                } else if (isOpenDown && isOpenLeft && isOpenRight) {
                    return icons[14];
                } else if (isOpenDown && isOpenUp) {
                    return icons[5];
                } else if (isOpenLeft && isOpenRight) {
                    return icons[6];
                } else if (isOpenDown && isOpenLeft) {
                    return icons[8];
                } else if (isOpenDown && isOpenRight) {
                    return icons[10];
                } else if (isOpenUp && isOpenLeft) {
                    return icons[7];
                } else if (isOpenUp && isOpenRight) {
                    return icons[9];
                } else if (isOpenDown) {
                    return icons[3];
                } else if (isOpenUp) {
                    return icons[4];
                } else if (isOpenLeft) {
                    return icons[2];
                } else if (isOpenRight) {
                    return icons[1];
                }
                break;
            case 2:
                if (shouldConnectToBlock(par1IBlockAccess, par2, par3, par4, par1IBlockAccess.getBlock(par2, par3 - 1, par4), par1IBlockAccess.getBlockMetadata(par2, par3 - 1, par4), connectBlock)) {
                    isOpenDown = true;
                }

                if (shouldConnectToBlock(par1IBlockAccess, par2, par3, par4, par1IBlockAccess.getBlock(par2, par3 + 1, par4), par1IBlockAccess.getBlockMetadata(par2, par3 + 1, par4), connectBlock)) {
                    isOpenUp = true;
                }

                if (shouldConnectToBlock(par1IBlockAccess, par2, par3, par4, par1IBlockAccess.getBlock(par2 - 1, par3, par4), par1IBlockAccess.getBlockMetadata(par2 - 1, par3, par4), connectBlock)) {
                    isOpenLeft = true;
                }

                if (shouldConnectToBlock(par1IBlockAccess, par2, par3, par4, par1IBlockAccess.getBlock(par2 + 1, par3, par4), par1IBlockAccess.getBlockMetadata(par2 + 1, par3, par4), connectBlock)) {
                    isOpenRight = true;
                }

                if (isOpenUp && isOpenDown && isOpenLeft && isOpenRight) {
                    return icons[15];
                } else if (isOpenUp && isOpenDown && isOpenLeft) {
                    return icons[13];
                } else if (isOpenUp && isOpenDown && isOpenRight) {
                    return icons[14];
                } else if (isOpenUp && isOpenLeft && isOpenRight) {
                    return icons[11];
                } else if (isOpenDown && isOpenLeft && isOpenRight) {
                    return icons[12];
                } else if (isOpenDown && isOpenUp) {
                    return icons[6];
                } else if (isOpenLeft && isOpenRight) {
                    return icons[5];
                } else if (isOpenDown && isOpenLeft) {
                    return icons[9];
                } else if (isOpenDown && isOpenRight) {
                    return icons[10];
                } else if (isOpenUp && isOpenLeft) {
                    return icons[7];
                } else if (isOpenUp && isOpenRight) {
                    return icons[8];
                } else if (isOpenDown) {
                    return icons[1];
                } else if (isOpenUp) {
                    return icons[2];
                } else if (isOpenLeft) {
                    return icons[4];
                } else if (isOpenRight) {
                    return icons[3];
                }
                break;
            case 3:
                if (shouldConnectToBlock(par1IBlockAccess, par2, par3, par4, par1IBlockAccess.getBlock(par2, par3 - 1, par4), par1IBlockAccess.getBlockMetadata(par2, par3 - 1, par4), connectBlock)) {
                    isOpenDown = true;
                }

                if (shouldConnectToBlock(par1IBlockAccess, par2, par3, par4, par1IBlockAccess.getBlock(par2, par3 + 1, par4), par1IBlockAccess.getBlockMetadata(par2, par3 + 1, par4), connectBlock)) {
                    isOpenUp = true;
                }

                if (shouldConnectToBlock(par1IBlockAccess, par2, par3, par4, par1IBlockAccess.getBlock(par2 - 1, par3, par4), par1IBlockAccess.getBlockMetadata(par2 - 1, par3, par4), connectBlock)) {
                    isOpenLeft = true;
                }

                if (shouldConnectToBlock(par1IBlockAccess, par2, par3, par4, par1IBlockAccess.getBlock(par2 + 1, par3, par4), par1IBlockAccess.getBlockMetadata(par2 + 1, par3, par4), connectBlock)) {
                    isOpenRight = true;
                }

                if (isOpenUp && isOpenDown && isOpenLeft && isOpenRight) {
                    return icons[15];
                } else if (isOpenUp && isOpenDown && isOpenLeft) {
                    return icons[14];
                } else if (isOpenUp && isOpenDown && isOpenRight) {
                    return icons[13];
                } else if (isOpenUp && isOpenLeft && isOpenRight) {
                    return icons[11];
                } else if (isOpenDown && isOpenLeft && isOpenRight) {
                    return icons[12];
                } else if (isOpenDown && isOpenUp) {
                    return icons[6];
                } else if (isOpenLeft && isOpenRight) {
                    return icons[5];
                } else if (isOpenDown && isOpenLeft) {
                    return icons[10];
                } else if (isOpenDown && isOpenRight) {
                    return icons[9];
                } else if (isOpenUp && isOpenLeft) {
                    return icons[8];
                } else if (isOpenUp && isOpenRight) {
                    return icons[7];
                } else if (isOpenDown) {
                    return icons[1];
                } else if (isOpenUp) {
                    return icons[2];
                } else if (isOpenLeft) {
                    return icons[3];
                } else if (isOpenRight) {
                    return icons[4];
                }
                break;
            case 4:
                if (shouldConnectToBlock(par1IBlockAccess, par2, par3, par4, par1IBlockAccess.getBlock(par2, par3 - 1, par4), par1IBlockAccess.getBlockMetadata(par2, par3 - 1, par4), connectBlock)) {
                    isOpenDown = true;
                }

                if (shouldConnectToBlock(par1IBlockAccess, par2, par3, par4, par1IBlockAccess.getBlock(par2, par3 + 1, par4), par1IBlockAccess.getBlockMetadata(par2, par3 + 1, par4), connectBlock)) {
                    isOpenUp = true;
                }

                if (shouldConnectToBlock(par1IBlockAccess, par2, par3, par4, par1IBlockAccess.getBlock(par2, par3, par4 - 1), par1IBlockAccess.getBlockMetadata(par2, par3, par4 - 1), connectBlock)) {
                    isOpenLeft = true;
                }

                if (shouldConnectToBlock(par1IBlockAccess, par2, par3, par4, par1IBlockAccess.getBlock(par2, par3, par4 + 1), par1IBlockAccess.getBlockMetadata(par2, par3, par4 + 1), connectBlock)) {
                    isOpenRight = true;
                }

                if (isOpenUp && isOpenDown && isOpenLeft && isOpenRight) {
                    return icons[15];
                } else if (isOpenUp && isOpenDown && isOpenLeft) {
                    return icons[14];
                } else if (isOpenUp && isOpenDown && isOpenRight) {
                    return icons[13];
                } else if (isOpenUp && isOpenLeft && isOpenRight) {
                    return icons[11];
                } else if (isOpenDown && isOpenLeft && isOpenRight) {
                    return icons[12];
                } else if (isOpenDown && isOpenUp) {
                    return icons[6];
                } else if (isOpenLeft && isOpenRight) {
                    return icons[5];
                } else if (isOpenDown && isOpenLeft) {
                    return icons[10];
                } else if (isOpenDown && isOpenRight) {
                    return icons[9];
                } else if (isOpenUp && isOpenLeft) {
                    return icons[8];
                } else if (isOpenUp && isOpenRight) {
                    return icons[7];
                } else if (isOpenDown) {
                    return icons[1];
                } else if (isOpenUp) {
                    return icons[2];
                } else if (isOpenLeft) {
                    return icons[3];
                } else if (isOpenRight) {
                    return icons[4];
                }
                break;
            case 5:
                if (shouldConnectToBlock(par1IBlockAccess, par2, par3, par4, par1IBlockAccess.getBlock(par2, par3 - 1, par4), par1IBlockAccess.getBlockMetadata(par2, par3 - 1, par4), connectBlock)) {
                    isOpenDown = true;
                }

                if (shouldConnectToBlock(par1IBlockAccess, par2, par3, par4, par1IBlockAccess.getBlock(par2, par3 + 1, par4), par1IBlockAccess.getBlockMetadata(par2, par3 + 1, par4), connectBlock)) {
                    isOpenUp = true;
                }

                if (shouldConnectToBlock(par1IBlockAccess, par2, par3, par4, par1IBlockAccess.getBlock(par2, par3, par4 - 1), par1IBlockAccess.getBlockMetadata(par2, par3, par4 - 1), connectBlock)) {
                    isOpenLeft = true;
                }

                if (shouldConnectToBlock(par1IBlockAccess, par2, par3, par4, par1IBlockAccess.getBlock(par2, par3, par4 + 1), par1IBlockAccess.getBlockMetadata(par2, par3, par4 + 1), connectBlock)) {
                    isOpenRight = true;
                }

                if (isOpenUp && isOpenDown && isOpenLeft && isOpenRight) {
                    return icons[15];
                } else if (isOpenUp && isOpenDown && isOpenLeft) {
                    return icons[13];
                } else if (isOpenUp && isOpenDown && isOpenRight) {
                    return icons[14];
                } else if (isOpenUp && isOpenLeft && isOpenRight) {
                    return icons[11];
                } else if (isOpenDown && isOpenLeft && isOpenRight) {
                    return icons[12];
                } else if (isOpenDown && isOpenUp) {
                    return icons[6];
                } else if (isOpenLeft && isOpenRight) {
                    return icons[5];
                } else if (isOpenDown && isOpenLeft) {
                    return icons[9];
                } else if (isOpenDown && isOpenRight) {
                    return icons[10];
                } else if (isOpenUp && isOpenLeft) {
                    return icons[7];
                } else if (isOpenUp && isOpenRight) {
                    return icons[8];
                } else if (isOpenDown) {
                    return icons[1];
                } else if (isOpenUp) {
                    return icons[2];
                } else if (isOpenLeft) {
                    return icons[4];
                } else if (isOpenRight) {
                    return icons[3];
                }
                break;
        }
        return icons[0];
    }

    public static boolean shouldConnectToBlock(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, Block par5, int par6, Block connectBlock) {
        return par5 == connectBlock && par6 == par1IBlockAccess.getBlockMetadata(par2, par3, par4);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5) {
        return getConnectedBlockTexture(par1IBlockAccess, par2, par3, par4, par5, this);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int par1, int par2) {
        return icons[0];
    }

    @Override
    public void registerBlockIcons(IIconRegister reg) {
        /*icons[0] = reg.registerIcon(Reference.MOD_ID.toLowerCase() + ":overlay/" + folder + "/dyeGlow");
        icons[1] = reg.registerIcon(Reference.MOD_ID.toLowerCase() + ":overlay/" + folder + "/dyeGlow_1_d");
        icons[2] = reg.registerIcon(Reference.MOD_ID.toLowerCase() + ":overlay/" + folder + "/dyeGlow_1_u");
        icons[3] = reg.registerIcon(Reference.MOD_ID.toLowerCase() + ":overlay/" + folder + "/dyeGlow_1_l");
        icons[4] = reg.registerIcon(Reference.MOD_ID.toLowerCase() + ":overlay/" + folder + "/dyeGlow_1_r");
        icons[5] = reg.registerIcon(Reference.MOD_ID.toLowerCase() + ":overlay/" + folder + "/dyeGlow_2_h");
        icons[6] = reg.registerIcon(Reference.MOD_ID.toLowerCase() + ":overlay/" + folder + "/dyeGlow_2_v");
        icons[7] = reg.registerIcon(Reference.MOD_ID.toLowerCase() + ":overlay/" + folder + "/dyeGlow_2_dl");
        icons[8] = reg.registerIcon(Reference.MOD_ID.toLowerCase() + ":overlay/" + folder + "/dyeGlow_2_dr");
        icons[9] = reg.registerIcon(Reference.MOD_ID.toLowerCase() + ":overlay/" + folder + "/dyeGlow_2_ul");
        icons[10] = reg.registerIcon(Reference.MOD_ID.toLowerCase() + ":overlay/" + folder + "/dyeGlow_2_ur");
        icons[11] = reg.registerIcon(Reference.MOD_ID.toLowerCase() + ":overlay/" + folder + "/dyeGlow_3_d");
        icons[12] = reg.registerIcon(Reference.MOD_ID.toLowerCase() + ":overlay/" + folder + "/dyeGlow_3_u");
        icons[13] = reg.registerIcon(Reference.MOD_ID.toLowerCase() + ":overlay/" + folder + "/dyeGlow_3_l");
        icons[14] = reg.registerIcon(Reference.MOD_ID.toLowerCase() + ":overlay/" + folder + "/dyeGlow_3_r");
        icons[15] = reg.registerIcon(Reference.MOD_ID.toLowerCase() + ":overlay/" + folder + "/dyeGlow_4");*/
        super.registerBlockIcons(reg);
    }

    @Override
    public boolean shouldSideBeRendered(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5) {
        final Block b = par1IBlockAccess.getBlock(par2, par3, par4);
        return b != this && super.shouldSideBeRendered(par1IBlockAccess, par2, par3, par4, par5);
    }
}
