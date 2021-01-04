package io.github.aluria.common.utils;

import org.bukkit.command.Command;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.Properties;

public class CommonPlugin extends JavaPlugin {

    protected void registerCommands(Command... commands) {
        ((CraftServer) getServer()).getCommandMap().registerAll(getName().toLowerCase(), Arrays.asList(commands));
    }

    protected void registerListeners(Listener... listeners) {
        final PluginManager manager = getServer().getPluginManager();

        for (Listener listener : listeners) {
            manager.registerEvents(listener, this);
        }
    }

    protected Properties read(ConfigurationSection section) {
        final Properties properties = new Properties();

        for (String key : section.getKeys(false)) {
            final String value = section.getString(key);
            if(value == null) continue;

            properties.put(key, value);
        }

        return properties;
    }
}
