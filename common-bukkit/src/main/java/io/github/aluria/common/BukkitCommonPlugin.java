package io.github.aluria.common;

import io.github.aluria.common.listeners.ConnectionListener;
import io.github.aluria.common.registries.UserRegistry;
import io.github.aluria.common.utils.CommonPlugin;
import lombok.Getter;

public class BukkitCommonPlugin extends CommonPlugin {

    public static BukkitCommonPlugin getInstance() {
        return getPlugin(BukkitCommonPlugin.class);
    }

    @Getter
    private UserRegistry userRegistry;

    @Override
    public void onLoad() {
        this.saveDefaultConfig();

        this.userRegistry = new UserRegistry();
    }

    @Override
    public void onEnable() {
        registerCommands();

        registerListeners(
          new ConnectionListener(this)
        );
    }
}
