package club.chachy.oofmod.commands;

import cc.hyperium.commands.BaseCommand;
import cc.hyperium.handlers.HyperiumHandlers;
import club.chachy.oofmod.gui.OofGui;

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
        new HyperiumHandlers().getGuiDisplayHandler().setDisplayNextTick(new OofGui());
    }
}
