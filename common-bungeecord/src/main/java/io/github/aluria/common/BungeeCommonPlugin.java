package io.github.aluria.common;

import io.github.aluria.common.commands.DiscordCommand;
import io.github.aluria.common.registries.UserRegistry;
import io.github.aluria.common.utils.CommonPlugin;
import lombok.Getter;

public class BungeeCommonPlugin extends CommonPlugin {

    @Getter
    private UserRegistry userRegistry;

    @Override
    public void onLoad() {
        this.userRegistry = new UserRegistry();
    }

    @Override
    public void onEnable() {
        registerCommands(
          new DiscordCommand(this)
        );
    }
}
