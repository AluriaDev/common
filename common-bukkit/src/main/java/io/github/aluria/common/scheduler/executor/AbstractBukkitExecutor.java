package io.github.aluria.common.scheduler.executor;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.plugin.Plugin;

@Getter
@RequiredArgsConstructor
public abstract class AbstractBukkitExecutor implements BukkitExecutor {

    protected final Plugin plugin;
}
