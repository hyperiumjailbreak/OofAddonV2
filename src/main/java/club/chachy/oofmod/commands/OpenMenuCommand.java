package club.chachy.oofmod.commands;

import cc.hyperium.Hyperium;
import cc.hyperium.commands.BaseCommand;
import club.chachy.oofmod.OofMod;
import club.chachy.oofmod.gui.OofGui;
import net.minecraft.client.Minecraft;
import net.minecraft.event.ClickEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;

public class OpenMenuCommand implements BaseCommand {

    /**
     * Gets the name of the command
     */
    @Override
    public String getName() {
        return "oofmod";
    }

    /**
     * Gets the usage string for the command.
     */
    @Override
    public String getUsage() {
        return "/" + getName();
    }

    /**
     * Callback when the command is invoked
     *
     * @param args
     */
    @Override
    public void onExecute(String[] args) {
        if (OofMod.INSTANCE.getSettings().getSounds().size() == 0) {
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_RED + "------[OOFMOD ERROR]------"));
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.GOLD + ".minecraft/config/oofmod/sounds is empty!"));
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.GOLD + "Add some files before using this command."));

            IChatComponent download = new ChatComponentText(EnumChatFormatting.GOLD + "" + EnumChatFormatting.UNDERLINE + "CLICK HERE to download oof.wav.");
            download.getChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://www.mediafire.com/file/l7tv8u9dsleq7ul/oof.wav/file"));
            Minecraft.getMinecraft().thePlayer.addChatMessage(download);
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_RED + "-------------------------"));
            return;
        }
        Hyperium.INSTANCE.getHandlers().getGuiDisplayHandler().setDisplayNextTick(new OofGui());
    }
}
