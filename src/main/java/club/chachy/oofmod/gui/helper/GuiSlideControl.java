package club.chachy.oofmod.gui.helper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import org.lwjgl.opengl.GL11;

import java.text.DecimalFormat;

public class GuiSlideControl extends GuiButton {
    private static DecimalFormat numFormat = new DecimalFormat("#.00");
    public String label;
    public float curValue;
    public float minValue;
    public float maxValue;
    public boolean isSliding;
    public boolean useIntegers;
    public int packedFGColour;


    public GuiSlideControl(int id, int x, int y, int width, int height, String displayString, float minVal, float maxVal, float curVal, boolean useInts) {
        super(id, x, y, width, height, useInts ? (displayString + (int) curVal) : (displayString + GuiSlideControl.numFormat.format(curVal)));
        label = displayString;
        minValue = minVal;
        maxValue = maxVal;
        curValue = (curVal - minVal) / (maxVal - minVal);
        useIntegers = useInts;
    }

    public float getValueAsFloat() {
        return (maxValue - minValue) * curValue + minValue;
    }

    public int getValueAsInt() {
        return (int) ((maxValue - minValue) * curValue + minValue);
    }

    protected float roundValue(float value) {
        value = 0.01f * Math.round(value / 0.01f);
        return value;
    }

    public String getLabel() {
        if (useIntegers) {
            return label + getValueAsInt();
        }
        return label + GuiSlideControl.numFormat.format(getValueAsFloat());
    }

    protected void setLabel() {
        displayString = getLabel();
    }

    public int getHoverState(boolean isMouseOver) {
        return 0;
    }

    protected void mouseDragged(Minecraft mc, int mousePosX, int mousePosY) {
        if (visible) {
            if (isSliding) {
                curValue = (mousePosX - (xPosition + 4.0f)) / (width - 8.0f);
                if (curValue < 0.0f) {
                    curValue = 0.0f;
                }
                if (curValue > 1.0f) {
                    curValue = 1.0f;
                }
                setLabel();
            }
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            drawRect(xPosition + 1, yPosition + 1, xPosition + (int) (curValue * (width - 8)) + 7, yPosition + height - 1, getColorWithAlpha(8168374, 100));
            drawRect(xPosition + (int) (curValue * (width - 8)) + 1, yPosition + 1, xPosition + (int) (curValue * (width - 8)) + 7, yPosition + height - 1, getColorWithAlpha(8168374, 200));
        }
    }

    public boolean mousePressed(Minecraft mc, int mousePosX, int mousePosY) {
        if (!super.mousePressed(mc, mousePosX, mousePosY)) {
            return false;
        }
        curValue = (mousePosX - (xPosition + 4.0f)) / (width - 8.0f);
        if (curValue < 0.0f) {
            curValue = 0.0f;
        }
        if (curValue > 1.0f) {
            curValue = 1.0f;
        }
        setLabel();
        if (isSliding) {
            return isSliding = false;
        }
        return isSliding = true;
    }

    public void mouseReleased(int mousePosX, int mousePosY) {
        isSliding = false;
    }

    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        if (visible) {
            FontRenderer fontrenderer = mc.fontRendererObj;
            mc.getTextureManager().bindTexture(GuiSlideControl.buttonTextures);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            hovered = (mouseX >= xPosition && mouseY >= yPosition && mouseX < xPosition + width && mouseY < yPosition + height);
            int k = getHoverState(visible);
            GL11.glEnable(3042);
            OpenGlHelper.glBlendFunc(770, 771, 1, 0);
            GL11.glBlendFunc(770, 771);
            drawRect(xPosition + 1, yPosition + 1, xPosition + width - 1, yPosition + height - 1, getColorWithAlpha(8168374, 50));
            mouseDragged(mc, mouseX, mouseY);
            int l = 14737632;
            if (packedFGColour != 0) {
                l = packedFGColour;
            } else if (!enabled) {
                l = 10526880;
            } else if (hovered) {
                l = 16777120;
            }
            drawCenteredString(fontrenderer, displayString, xPosition + width / 2, yPosition + (height - 8) / 2, l);
            GL11.glColor3f(1.0f, 1.0f, 1.0f);
        }
    }

    private int getColorWithAlpha(int rgb, int a) {
        int r = rgb >> 16 & 0xFF;
        int g = rgb >> 8 & 0xFF;
        int b = rgb & 0xFF;
        return a << 24 | r << 16 | g << 8 | b;
    }
}

