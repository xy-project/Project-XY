package com.projextxy.core.client.render.fx;

import com.projextxy.core.resource.ResourceLib;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

public class FXBeam extends EntityFX {

    public int particle = 16;
    private double offset = 0.0D;
    private double tX = 0.0D;
    private double tY = 0.0D;
    private double tZ = 0.0D;
    private double ptX = 0.0D;
    private double ptY = 0.0D;
    private double ptZ = 0.0D;
    private boolean shown = false;

    public FXBeam(World par1World, double px, double py, double pz, double tx, double ty, double tz, float red, float green, float blue, int age) {
        super(par1World, px, py, pz, 0.0D, 0.0D, 0.0D);

        this.particleRed = 0.5F;
        this.particleGreen = 0.5F;
        this.particleBlue = 0.5F;
        setSize(0.02F, 0.02F);
        this.noClip = true;
        this.motionX = 0.0D;
        this.motionY = 0.0D;
        this.motionZ = 0.0D;
        this.tX = tx;
        this.tY = ty;
        this.tZ = tz;
        this.prevYaw = this.rotYaw;
        this.prevPitch = this.rotPitch;
        this.particleMaxAge = age;
    }

    public void updateBeam(double xx, double yy, double zz, double x, double y, double z, boolean shown) {
        this.shown = shown;
        setPosition(xx, yy, zz);
        this.tX = x;
        this.tY = y;
        this.tZ = z;
        while (this.particleMaxAge - this.particleAge < 4) {
            this.particleMaxAge += 1;
        }
    }

    @Override
    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = (this.posY + this.offset);
        this.prevPosZ = this.posZ;

        this.ptX = this.tX;
        this.ptY = this.tY;
        this.ptZ = this.tZ;

        this.prevYaw = this.rotYaw;
        this.prevPitch = this.rotPitch;

        float xd = (float) (this.posX - this.tX);
        float yd = (float) (this.posY - this.tY);
        float zd = (float) (this.posZ - this.tZ);
        this.length = MathHelper.sqrt_float(xd * xd + yd * yd + zd * zd);
        double var7 = MathHelper.sqrt_double(xd * xd + zd * zd);
        this.rotYaw = ((float) (Math.atan2(xd, zd) * 180.0D / 3.141592653589793D));
        this.rotPitch = ((float) (Math.atan2(yd, var7) * 180.0D / 3.141592653589793D));
        this.prevYaw = this.rotYaw;
        this.prevPitch = this.rotPitch;
        if (this.opacity > 0.3F) {
            this.opacity -= 0.025F;
        }
        if (this.opacity < 0.3F) {
            this.opacity = 0.3F;
        }
        if (this.particleAge++ >= this.particleMaxAge) {
            setDead();
        }
    }

    public void setRGB(float r, float g, float b) {
        this.particleRed = r;
        this.particleGreen = g;
        this.particleBlue = b;
    }

    private float length = 0.0F;
    private float rotYaw = 0.0F;
    private float rotPitch = 0.0F;
    private float prevYaw = 0.0F;
    private float prevPitch = 0.0F;
    private float opacity = 0.3F;

    public void setPulse(boolean pulse, float r, float g, float b) {
        setRGB(r, g, b);
        if (pulse) {
            this.opacity = 0.8F;
        }
    }

    private float prevSize = 0.0F;

    @Override
    public void renderParticle(Tessellator tessellator, float f, float f1, float f2, float f3, float f4, float f5) {
        tessellator.draw();

        GL11.glPushMatrix();
        float var9 = 1.0F;
        float slide = Minecraft.getMinecraft().thePlayer.ticksExisted;
        float size = 0.7F;


        ResourceLib.beamTexture().bind();

        GL11.glTexParameterf(3553, 10242, 10497.0F);
        GL11.glTexParameterf(3553, 10243, 10497.0F);

        GL11.glDisable(2884);

        float var11 = slide + f;
        float var12 = -var11 * 0.2F - MathHelper.floor_float(-var11 * 0.1F);

        GL11.glBlendFunc(770, 1);
        GL11.glDepthMask(false);

        float xx = (float) (this.prevPosX + (this.posX - this.prevPosX) * f - interpPosX);
        float yy = (float) (this.prevPosY + (this.posY - this.prevPosY) * f - interpPosY);
        float zz = (float) (this.prevPosZ + (this.posZ - this.prevPosZ) * f - interpPosZ);
        GL11.glTranslated(xx, yy, zz);

        float ry = this.prevYaw + (this.rotYaw - this.prevYaw) * f;
        float rp = this.prevPitch + (this.rotPitch - this.prevPitch) * f;
        GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
        GL11.glRotatef(180.0F + ry, 0.0F, 0.0F, -1.0F);
        GL11.glRotatef(rp, 1.0F, 0.0F, 0.0F);

        double var44 = -0.15D * size;
        double var17 = 0.15D * size;

        for (int t = 0; t < 2; t++) {
            double var29 = this.length * var9;
            double var31 = 0.0D;
            double var33 = 1.0D;
            double var35 = -1.0F + var12 + t / 3.0F;
            double var37 = this.length * var9 + var35;

            GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
            tessellator.startDrawingQuads();
            tessellator.setBrightness(200);
            tessellator.setColorRGBA_F(this.particleRed, this.particleGreen, this.particleBlue, 1.0f);

            tessellator.addVertexWithUV(var44, var29, 0.0D, var33, var37);
            tessellator.addVertexWithUV(var44, 0.0D, 0.0D, var33, var35);
            tessellator.addVertexWithUV(var17, 0.0D, 0.0D, var31, var35);
            tessellator.addVertexWithUV(var17, var29, 0.0D, var31, var37);
            tessellator.draw();
        }
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glDepthMask(true);
        GL11.glBlendFunc(770, 771);

        GL11.glEnable(2884);
        GL11.glPopMatrix();


        ResourceLib.particleSheet().bind();
        tessellator.startDrawingQuads();
        this.prevSize = size;
    }
}
