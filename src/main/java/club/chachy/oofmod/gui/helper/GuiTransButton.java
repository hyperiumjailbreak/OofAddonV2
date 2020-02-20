package club.chachy.oofmod.gui.helper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.OpenGlHelper;
import org.lwjgl.opengl.GL11;

public class GuiTransButton extends GuiButton {
    private int packedFGColour;

    public GuiTransButton(int id, int x, int y, int width, int height, String displayString) {
        super(id, x, y, width, height, displayString);
    }

    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        if (visible) {
            FontRenderer fontrenderer = mc.fontRendererObj;
            mc.getTextureManager().bindTexture(GuiTransButton.buttonTextures);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            hovered = (mouseX >= xPosition && mouseY >= yPosition && mouseX < xPosition + width && mouseY < yPosition + height);
            int k = getHoverState(hovered);
            GL11.glEnable(3042);
            OpenGlHelper.glBlendFunc(770, 771, 1, 0);
            GL11.glBlendFunc(770, 771);
            drawRect(xPosition + 1, yPosition + 1, xPosition + width - 1, yPosition + height - 1, getButtonColor());
            mouseDragged(mc, mouseX, mouseY);
            int l = 14737632;
            if (this.packedFGColour != 0) {
                l = packedFGColour;
            } else if (!enabled) {
                l = 10526880;
            } else if (hovered) {
                l = 16777120;
            }
            drawCenteredString(fontrenderer, displayString, xPosition + width / 2, yPosition + (height - 8) / 2, l);
        }
    }

    private int getButtonColor() {
        switch (super.getHoverState(super.hovered)) {
            case 0: {
                return getColorWithAlpha(8168374, 50);
            }
            case 1: {
                return getColorWithAlpha(8168374, 100);
            }
            case 2: {
                return getColorWithAlpha(8168374, 150);
            }
            default: {
                return 0;
            }
        }
    }

    private int getColorWithAlpha(int rgb, int a) {
        int r = rgb >> 16 & 0xFF;
        int g = rgb >> 8 & 0xFF;
        int b = rgb & 0xFF;
        return a << 24 | r << 16 | g << 8 | b;
    }
}

