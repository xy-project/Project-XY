package com.projextxy.core.client;

import codechicken.lib.render.TextureFX;
import net.minecraft.util.MathHelper;

//tweaked parts of the lava texture fx from minecraft 1.4.7, rescaled to 32 x 32 by super method
//Thanks to chicken-bones for keeping/porting texture fx to newer versions of minecraft.
public class AnimationFX extends TextureFX {

    private float[] red;
    private float[] green;
    private float[] blue;
    private float[] alpha;

    public AnimationFX() {
        super(32, "fxAnimationCloud");
        setup();
    }

    @Override
    public void setup() {
        super.setup();
        this.red = new float[this.tileSizeSquare];
        this.green = new float[this.tileSizeSquare];
        this.blue = new float[this.tileSizeSquare];
        this.alpha = new float[this.tileSizeSquare];
    }

    @Override
    public void onTick() {
        for (int i = 0; i < this.tileSizeBase; i++) {
            for (int j = 0; j < this.tileSizeBase; j++) {
                float var3 = -0.05F;
                int rotation1 = (int) (MathHelper.sin(j * (float) Math.PI * 3.0F / 2.0F) * 1.0F);
                int rotation2 = (int) (MathHelper.sin(i * (float) Math.PI * 3.0F / 2.0F) * 1.0F);
                for (int k = i - 1; k <= i + 1; k++) {
                    for (int l = j - 1; l <= j + 1; l++) {
                        int mod1 = k + rotation1 & this.tileSizeMask;
                        int mod2 = l + rotation2 & this.tileSizeMask;
                        var3 += this.red[(mod1 + mod2 * this.tileSizeBase)];
                    }
                }
                this.green[(i + j * this.tileSizeBase)] = (var3 / 10.0F + (this.blue[((i + 0 & this.tileSizeMask) + (j + 0 & this.tileSizeMask) * this.tileSizeBase)] + this.blue[((i + 1 & this.tileSizeMask) + (j + 0 & this.tileSizeMask) * this.tileSizeBase)] + this.blue[((i + 1 & this.tileSizeMask) + (j + 1 & this.tileSizeMask) * this.tileSizeBase)] + this.blue[((i + 0 & this.tileSizeMask) + (j + 1 & this.tileSizeMask) * this.tileSizeBase)]) / 4.0F * 0.8F);
                this.blue[(i + j * this.tileSizeBase)] += this.alpha[(i + j * this.tileSizeBase)] * 0.01F;
                if (this.blue[(i + j * this.tileSizeBase)] < 0.0F) {
                    this.blue[(i + j * this.tileSizeBase)] = 0.0F;
                }
                this.alpha[(i + j * this.tileSizeBase)] -= 0.052F;
                if (Math.random() < 0.005D) {
                    this.alpha[(i + j * this.tileSizeBase)] = 1.33F;
                }
            }
        }

        float[] var11 = this.green;
        this.green = this.red;
        this.red = var11;
        for (int i = 0; i < tileSizeSquare; i++) {
            float var3 = this.red[i] * 2.0f;

            if (var3 > 1.0F) {
                var3 = 1.0F;
            }

            if (var3 < 0.0F) {
                var3 = 0.0F;
            }

            int r = (int) (var3 * 255.0F);
            int g = (int) (var3 * 255.0F);
            int b = (int) (var3 * 255.0F);

            if (this.anaglyphEnabled) {
                int var8 = (r * 30 + g * 59 + b * 11) / 100;
                int var9 = (r * 30 + g * 70) / 100;
                int var10 = (r * 30 + b * 70) / 100;
                r = var8;
                g = var9;
                b = var10;
            }

            this.imageData[i] = (255) << 24 | (r & 255) << 16 | (g & 255) << 8 | b & 255;
        }
    }
}
