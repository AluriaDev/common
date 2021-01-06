package io.github.aluria.common;

import co.aikar.commands.BukkitCommandManager;
import co.aikar.commands.BukkitLocales;
import co.aikar.commands.MessageType;
import io.github.aluria.common.database.provider.sql.HikariConnectionProvider;
import io.github.aluria.common.listeners.ConnectionListener;
import io.github.aluria.common.menu.MenuListener;
import io.github.aluria.common.registries.UserRegistry;
import io.github.aluria.common.utils.CommonPlugin;
import lombok.Getter;
import org.bukkit.ChatColor;

import java.util.Locale;

public class BukkitCommonPlugin extends CommonPlugin {

    @Getter
    private UserRegistry userRegistry;
    @Getter
    private HikariConnectionProvider hikariConnectionProvider;

    public static BukkitCommonPlugin getInstance() {
        return getPlugin(BukkitCommonPlugin.class);
    }

    @Override
    public void onLoad() {
        this.saveDefaultConfig();

        this.userRegistry = new UserRegistry();

        hikariConnectionProvider = new HikariConnectionProvider();
        hikariConnectionProvider.connect(
          read(getConfig().getConfigurationSection("database"))
        );
    }

    @Override
    public void onEnable() {
        this.registerListeners(
          new ConnectionListener(this),
          new MenuListener()
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
