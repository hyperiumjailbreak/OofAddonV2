package club.chachy.oofmod;

import cc.hyperium.Hyperium;
import cc.hyperium.event.EventBus;
import cc.hyperium.event.InvokeEvent;
import cc.hyperium.event.client.InitializationEvent;
import cc.hyperium.internal.addons.IAddon;
import club.chachy.oofmod.commands.OpenMenuCommand;
import club.chachy.oofmod.listeners.OofModListener;
import club.chachy.oofmod.settings.Settings;

public class OofMod implements IAddon {
    public static OofMod INSTANCE = new OofMod();
    private Settings settings = new Settings();

    /**
     * Invoked once the plugin has successfully loaded
     * {@link cc.hyperium.internal.addons.AddonMinecraftBootstrap#init}
     */
    @Override
    public void onLoad() {
        Hyperium.LOGGER.info("Successfully loaded OofAddonV2!");
        EventBus.INSTANCE.register(this);
    }

    @InvokeEvent
    public void onInitialization(InitializationEvent event) {
        EventBus.INSTANCE.register(this);
        Hyperium.CONFIG.register(Settings.INSTANCE);
        EventBus.INSTANCE.register(new OofModListener());
        Hyperium.INSTANCE.getHandlers().getCommandHandler().registerCommand(new OpenMenuCommand());
    }

    /**
     * Invoked once the game has been closed
     * this is executed at the start of {@link net.minecraft.client.Minecraft#shutdown}
     */
    @Override
    public void onClose() {
        // Log that the addon is being closed
        Hyperium.LOGGER.info("Closing OofAddonV2");
    }

    public Settings getSettings() {
        return settings;
    }
}
