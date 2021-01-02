package io.github.aluria.common.utils;

import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;

public class CommonPlugin extends Plugin {

    protected void registerListeners(Listener... listeners) {
        final PluginManager manager = getProxy().getPluginManager();

        for (Listener listener : listeners) {
            manager.registerListener(this, listener);
        }
    }

    protected void registerCommands(Command... commands) {
        final PluginManager manager = getProxy().getPluginManager();

        for (Command command : commands) {
            manager.registerCommand(this, command);
        }
    }
}
