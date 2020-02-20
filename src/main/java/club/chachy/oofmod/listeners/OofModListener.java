package club.chachy.oofmod.listeners;

import cc.hyperium.event.InvokeEvent;
import cc.hyperium.event.network.chat.ServerChatEvent;
import club.chachy.oofmod.OofMod;
import com.mojang.authlib.GameProfile;
import me.semx11.autotip.event.impl.EventClientTick;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.network.NetworkPlayerInfo;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OofModListener {
    private Minecraft mc = Minecraft.getMinecraft();

    private String nameToCheck = "";

    private OofMod mod = OofMod.INSTANCE;

    @InvokeEvent
    public void onDeathMessage(ServerChatEvent event) throws Exception {
        if (nameToCheck.isEmpty()) {
            nameToCheck = mc.thePlayer.getName();
        }
        String line = event.getChat().getUnformattedText();
        if (!mod.getSettings().isEnabled() || line.split(" ").length == 0) {
            return;
        }
        String killMessageRegex = "(\\w{1,16}).+ (by|of|to|for|with) (" + nameToCheck + ")";
        String usernamePatternRegex = "^[a-zA-Z0-9_-]{3,16}$";
        Pattern killMessagePattern = Pattern.compile(killMessageRegex);
        Pattern usernamePattern = Pattern.compile(usernamePatternRegex);
        Matcher killMessageMatcher = killMessagePattern.matcher(line);
        Matcher usernameMatcher = usernamePattern.matcher(line.split(" ")[0]);
        if (usernameMatcher.matches() && killMessageMatcher.find()) {
            String killed = killMessageMatcher.group(1);
            if (!killed.equals(nameToCheck)) {
                playOofSound();
            }
        }
    }

    @InvokeEvent
    public void profileCheck(EventClientTick event) {
        EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
        if (player == null) {
            return;
        }
        NetHandlerPlayClient sendQueue = player.sendQueue;
        if (sendQueue == null) {
            return;
        }
        for (NetworkPlayerInfo networkPlayerInfo : sendQueue.getPlayerInfoMap()) {
            GameProfile gameProfile = networkPlayerInfo.getGameProfile();
            if (gameProfile.getId() != null && gameProfile.getId().equals(Minecraft.getMinecraft().getSession().getProfile().getId())) {
                nameToCheck = gameProfile.getName();
                break;
            }
        }
    }

    private void playOofSound() throws Exception {
        if (!mod.getSettings().getSelectedSound().exists()) {
            return;
        }
        File soundFile = mod.getSettings().getSelectedSound();
        AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile.toURI().toURL());
        Clip clip = AudioSystem.getClip();
        clip.open(audioIn);
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(mod.getSettings().getVolume() - 30.0f);
        clip.start();
    }
}

