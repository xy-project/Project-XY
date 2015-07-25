package com.projextxy.lib.cofh.gui.element;

import com.projextxy.lib.cofh.gui.GuiBase;
import com.projextxy.lib.cofh.gui.GuiProps;
import com.projextxy.lib.cofh.gui.TabTracker;
import com.projextxy.lib.cofh.render.RenderHelper;
import com.projextxy.lib.cofh.vec.Rectangle4i;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

/**
 * Base class for a tab element. Has self-contained rendering methods and a link back to the {@link GuiBase} it is a part of.
 *
 * @author King Lemming
 */
public abstract class TabBase extends ElementBase {

    public static final ResourceLocation DEFAULT_TEXTURE_LEFT = new ResourceLocation(GuiProps.PATH_ELEMENTS + "Tab_Left.png");
    public static final ResourceLocation DEFAULT_TEXTURE_RIGHT = new ResourceLocation(GuiProps.PATH_ELEMENTS + "Tab_Right.png");
    public static int tabExpandSpeed = 8;
    public boolean open;
    public int side = 1;
    public int headerColor = 0xe1c92f;
    public int subheaderColor = 0xaaafb8;
    public int textColor = 0x000000;
    public int backgroundColor = 0xffffff;
    public int currentShiftX = 0;
    public int currentShiftY = 0;
    public int minWidth = 22;
    public int maxWidth = 124;
    public int currentWidth = minWidth;
    public int minHeight = 22;
    public int maxHeight = 22;
    public int currentHeight = minHeight;

    public TabBase(GuiBase gui) {

        super(gui, 0, 0);
        texture = DEFAULT_TEXTURE_RIGHT;
    }

    public TabBase(GuiBase gui, int side) {

        super(gui, 0, 0);
        this.side = side;

        if (side == 0) {
            texture = DEFAULT_TEXTURE_LEFT;
        } else {
            texture = DEFAULT_TEXTURE_RIGHT;
        }
    }

    public void draw(int x, int y) {

        posX = x;
        posY = y;
        draw();
    }

    public void draw() {

        return;
    }

    @Override
    public void drawBackground(int mouseX, int mouseY, float gameTicks) {

    }

    @Override
    public void drawForeground(int mouseX, int mouseY) {

    }

    @Override
    public void update() {

        if (open && currentWidth < maxWidth) {
            currentWidth += tabExpandSpeed;
        } else if (!open && currentWidth > minWidth) {
            currentWidth -= tabExpandSpeed;
        }
        if (currentWidth > maxWidth) {
            currentWidth = maxWidth;
        } else if (currentWidth < minWidth) {
            currentWidth = minWidth;
        }
        if (open && currentHeight < maxHeight) {
            currentHeight += tabExpandSpeed;
        } else if (!open && currentHeight > minHeight) {
            currentHeight -= tabExpandSpeed;
        }
        if (currentHeight > maxHeight) {
            currentHeight = maxHeight;
        } else if (currentHeight < minHeight) {
            currentHeight = minHeight;
        }
        if (open && currentWidth == maxWidth && currentHeight == maxHeight) {
            setFullyOpen();
        }
    }

    protected void drawBackground() {

        float colorR = (backgroundColor >> 16 & 255) / 255.0F;
        float colorG = (backgroundColor >> 8 & 255) / 255.0F;
        float colorB = (backgroundColor & 255) / 255.0F;

        GL11.glColor4f(colorR, colorG, colorB, 1.0F);

        RenderHelper.bindTexture(texture);

        int xPosition = posX();

        gui.drawTexturedModalRect(xPosition, posY + 4, 0, 256 - currentHeight + 4, 4, currentHeight - 4);
        gui.drawTexturedModalRect(xPosition + 4, posY, 256 - currentWidth + 4, 0, currentWidth - 4, 4);
        gui.drawTexturedModalRect(xPosition, posY, 0, 0, 4, 4);
        gui.drawTexturedModalRect(xPosition + 4, posY + 4, 256 - currentWidth + 4, 256 - currentHeight + 4, currentWidth - 4, currentHeight - 4);

        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0F);
    }

    protected void drawTabIcon(String iconName) {

        gui.drawIcon(iconName, posXOffset(), posY + 3, 1);
    }

    /**
     * Shortcut to correct for the proper X position.
     */
    protected int posX() {

        if (side == 0) {
            return posX - currentWidth;
        }
        return posX;
    }

    /**
     * Corrects for shadowing differences in tabs to ensure that they always look nice - used in font rendering, typically.
     */
    protected int posXOffset() {

        return posX() + offset();
    }

    protected int offset() {

        return (side == 0 ? 4 : 2);
    }

    public boolean intersectsWith(int mouseX, int mouseY, int shiftX, int shiftY) {

        if (side == 0) {
            if (mouseX <= shiftX && mouseX >= shiftX - currentWidth && mouseY >= shiftY && mouseY <= shiftY + currentHeight) {
                return true;
            }
        } else if (mouseX >= shiftX && mouseX <= shiftX + currentWidth && mouseY >= shiftY && mouseY <= shiftY + currentHeight) {
            return true;
        }
        return false;
    }

    public boolean isFullyOpened() {

        return currentWidth >= maxWidth;
    }

    public void setFullyOpen() {

        open = true;
        currentWidth = maxWidth;
        currentHeight = maxHeight;
    }

    public void toggleOpen() {

        if (open) {
            open = false;
            if (side == 0) {
                TabTracker.setOpenedLeftTab(null);
            } else {
                TabTracker.setOpenedRightTab(null);
            }
        } else {
            open = true;
            if (side == 0) {
                TabTracker.setOpenedLeftTab(this.getClass());
            } else {
                TabTracker.setOpenedRightTab(this.getClass());
            }
        }
    }

    public Rectangle4i getBounds() {

        return new Rectangle4i(posX() + gui.getGuiLeft(), posY + gui.getGuiTop(), currentWidth, currentHeight);
    }

}