package io.github.aluria.common;

import co.aikar.commands.BukkitCommandManager;
import co.aikar.commands.BukkitLocales;
import co.aikar.commands.MessageType;
import io.github.aluria.common.listeners.ConnectionListener;
import io.github.aluria.common.registries.UserRegistry;
import io.github.aluria.common.sql.provider.mysql.HikariConnectionProvider;
import io.github.aluria.common.utils.CommonPlugin;
import io.github.aluria.common.utils.PropertiesBuilder;
import lombok.Getter;
import org.bukkit.ChatColor;

import java.util.Locale;
import java.util.Properties;

public class BukkitCommonPlugin extends CommonPlugin {

    public static BukkitCommonPlugin getInstance() {
        return getPlugin(BukkitCommonPlugin.class);
    }

    @Getter
    private UserRegistry userRegistry;

    @Getter
    private HikariConnectionProvider connectionProvider;

    @Override
    public void onLoad() {
        this.saveDefaultConfig();

        this.userRegistry = new UserRegistry();

        this.connectionProvider = new HikariConnectionProvider(
          read(getConfig().getConfigurationSection("database"))
        );
    }

    @Override
    public void onEnable() {
        this.registerListeners(
          new ConnectionListener(this)
        );

        this.startCommandManager();
    }

    private void startCommandManager() {
        final BukkitCommandManager manager = new BukkitCommandManager(this);
        manager.registerDependency(UserRegistry.class, userRegistry);

        manager.setFormat(MessageType.INFO, ChatColor.GREEN);
        manager.setFormat(MessageType.ERROR, ChatColor.RED);

        final Locale locale = new Locale("pt", "BR");

        final BukkitLocales locales = manager.getLocales();
        locales.setDefaultLocale(locale);
        locales.addMessageBundle(getClassLoader(), "messages", locale);
    }
}
