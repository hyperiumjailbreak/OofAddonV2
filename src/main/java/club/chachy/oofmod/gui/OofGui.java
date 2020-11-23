package club.chachy.oofmod.gui;

import cc.hyperium.Hyperium;
import club.chachy.oofmod.OofMod;
import club.chachy.oofmod.gui.helper.GuiSlideControl;
import club.chachy.oofmod.gui.helper.GuiTransButton;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class OofGui extends GuiScreen {
    private OofMod mod = OofMod.INSTANCE;
    private int currentIndex = mod.getSettings().getSounds().indexOf(mod.getSettings().getSelectedSound());
    private GuiSlideControl volume;


    public void initGui() {
        buttonList.add(new GuiTransButton(0, getCenter() - 46, getRowPos(2) + 10, 16, 16, "<"));
        buttonList.add(new GuiTransButton(1, getCenter() + 30, getRowPos(2) + 10, 16, 16, ">"));
        buttonList.add(new GuiTransButton(2, getCenter() - 23, getRowPos(1) + 8, 46, 55, ""));
        buttonList.add(new GuiTransButton(3, getCenter() - 45, getRowPos(4) + 10, 90, 18, mod.getSettings().isEnabled() ? (EnumChatFormatting.GREEN + "Enabled") : (EnumChatFormatting.RED + "Disabled")));
        buttonList.add(volume = new GuiSlideControl(4, getCenter() - 45, getRowPos(5) + 10, 90, 18, "Volume: ", 0.0f, 30.0f, mod.getSettings().getVolume(), true));
        buttonList.add(new GuiTransButton(5, getCenter() - 45, height - 20, 90, 20, "Save"));
    }

    public int getRowPos(int rowNumber) {
        return height / 4 + (24 * rowNumber - 24) - 16;
    }

    public int getCenter() {
        return width / 2;
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        int yPosTitle = 10;
        super.drawDefaultBackground();
        GL11.glPushMatrix();
        GL11.glTranslatef(1.0f * -getCenter(), 1.0f * -yPosTitle, 0.0f);
        GL11.glScaled(2.0, 2.0, 2.0);
        drawCenteredString(mc.fontRendererObj, "OofMod V2", getCenter(), yPosTitle, 815000);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glTranslatef(-0.3f * -getCenter(), -0.3f * -yPosTitle + 12.0f, 0.0f);
        GL11.glScaled(0.7, 0.7, 0.7);
        drawCenteredString(mc.fontRendererObj, "By Refraction & powns, ported by chachy", getCenter(), yPosTitle + 12, 815000);
        GL11.glPopMatrix();
        super.drawScreen(mouseX, mouseY, partialTicks);
        renderUI();
        renderWavIcon();
    }

    protected void actionPerformed(GuiButton button) {
        switch (button.id) {
            case 0: {
                ArrayList<File> allSounds = mod.getSettings().getSounds();
                if (allSounds.size() <= 1) {
                    return;
                }
                if (currentIndex - 1 < 0) {
                    currentIndex = allSounds.size() - 1;
                    break;
                }
                --currentIndex;
                break;
            }
            case 1: {
                ArrayList<File> allSounds = mod.getSettings().getSounds();
                if (allSounds.size() <= 1) {
                    return;
                }
                if (currentIndex + 1 > allSounds.size() - 1) {
                    currentIndex = 0;
                    break;
                }
                ++currentIndex;
                break;
            }
            case 2: {
                try {
                    previewSound(mod.getSettings().getSounds().get(currentIndex).getName(), volume.getValueAsFloat() - 30.0f);
                } catch (Exception ignored) {
                }
                break;
            }
            case 3: {
                mod.getSettings().setEnabled(!mod.getSettings().isEnabled());
                button.displayString = (mod.getSettings().isEnabled() ? (EnumChatFormatting.GREEN + "Enabled") : (EnumChatFormatting.RED + "Disabled"));
                break;
            }
            case 5: {
                mod.getSettings().setVolume(volume.getValueAsFloat());
                mod.getSettings().setSelectedSoundName(mod.getSettings().getSounds().get(currentIndex).getName());
                Hyperium.CONFIG.save();
                mc.displayGuiScreen(null);
                break;
            }
        }
    }

    protected void renderUI() {
        String version = "2.2";
        float f2 = 0.4862745f;
        float f3 = 0.6392157f;
        float f4 = 0.7137255f;
        if (width >= 640 && height >= 350) {
            GL11.glPushMatrix();
            GL11.glScaled(1.0, 1.0, 0.0);
            drawString(mc.fontRendererObj, "Version: " + Minecraft.getMinecraft().getVersion() + " - " + version, 2, 2, 8168374);
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glEnable(3042);
            GL11.glDisable(3553);
            GL11.glBlendFunc(770, 771);
            GL11.glColor4f(f2, f3, f4, 0.3f);
            Tessellator tes = Tessellator.getInstance();
            WorldRenderer worldrenderer = tes.getWorldRenderer();
            worldrenderer.begin(4, DefaultVertexFormats.POSITION);
            worldrenderer.pos(0.0, 0.0, 0.0).endVertex();
            worldrenderer.pos(0.0, 140.0, 0.0).endVertex();
            worldrenderer.pos(140.0, 0.0, 0.0).endVertex();
            tes.draw();
            GL11.glEnable(3553);
            GL11.glDisable(3042);
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glEnable(3042);
            GL11.glDisable(3553);
            GL11.glBlendFunc(770, 771);
            GL11.glColor4f(f2, f3, f4, 0.3f);
            Tessellator tes2 = Tessellator.getInstance();
            WorldRenderer worldrenderer2 = tes2.getWorldRenderer();
            worldrenderer2.begin(4, DefaultVertexFormats.POSITION);
            worldrenderer2.pos(width, height, 0.0).endVertex();
            worldrenderer2.pos(width, height - 140, 0.0).endVertex();
            worldrenderer2.pos(width - 140, height, 0.0).endVertex();
            tes.draw();
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            GL11.glEnable(3553);
            GL11.glDisable(3042);
            GL11.glPopMatrix();
        }
    }

    private void renderWavIcon() {
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.color(1.0f, 1.0f, 1.0f);
        ResourceLocation location = new ResourceLocation("wav-icon.png");
        mc.getRenderManager().renderEngine.bindTexture(location);
        Gui.drawModalRectWithCustomSizedTexture(getCenter() - 20, getRowPos(1) + 10, 0.0f, 0.0f, 40, 51, 40.0f, 51.0f);
        GlStateManager.popMatrix();
        GL11.glPushMatrix();
        GL11.glTranslatef(-0.19999999f * -getCenter(), -0.19999999f * -(getRowPos(4) - 6), 0.0f);
        GL11.glScaled(0.8, 0.8, 0.8);
        drawCenteredString(mc.fontRendererObj, mod.getSettings().getSounds().get(currentIndex).getName(), getCenter(), getRowPos(4) - 6, -1);
        GL11.glPopMatrix();
    }

    private void previewSound(String fileName, float volume) throws Exception {
        File soundFile = new File("config/oofmod/sounds/" + fileName);
        AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile.toURI().toURL());
        Clip clip = AudioSystem.getClip();
        clip.open(audioIn);
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(volume);
        clip.start();
    }

    private int getColorWithAlpha(int rgb, int a) {
        int r = rgb >> 16 & 0xFF;
        int g = rgb >> 8 & 0xFF;
        int b = rgb & 0xFF;
        return a << 24 | r << 16 | g << 8 | b;
    }

    public void onGuiClosed() {
        Hyperium.CONFIG.save();
    }
}