package io.github.aluria.common;

import co.aikar.commands.BungeeCommandManager;
import co.aikar.commands.BungeeLocales;
import co.aikar.commands.MessageType;
import io.github.aluria.common.commands.WebsiteCommand;
import io.github.aluria.common.entities.User;
import io.github.aluria.common.registries.UserRegistry;
import io.github.aluria.common.utils.CommonPlugin;
import lombok.Getter;
import net.md_5.bungee.api.ChatColor;

import java.util.Locale;

public class BungeeCommonPlugin extends CommonPlugin {

    @Getter
    private UserRegistry userRegistry;

    @Override
    public void onLoad() {
        this.userRegistry = new UserRegistry();
    }

    @Override
    public void onEnable() {
        startCommandManager();
    }

    private void startCommandManager() {
        final BungeeCommandManager manager = new BungeeCommandManager(this);
        manager.getCommandContexts().registerContext(
          User.class,
          context -> userRegistry.getUserById(context.getPlayer().getUniqueId())
        );

        manager.registerDependency(UserRegistry.class, userRegistry);

        manager.setFormat(MessageType.INFO, ChatColor.GREEN);
        manager.setFormat(MessageType.ERROR, ChatColor.RED);

        final Locale locale = new Locale("pt", "BR");

        final BungeeLocales locales = manager.getLocales();
        locales.setDefaultLocale(locale);
        locales.addMessageBundle(getClass().getClassLoader(), "messages", locale);

        manager.registerCommand(new WebsiteCommand());
    }
}
