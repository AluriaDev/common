package io.github.aluria.common.scheduler.executor.impl;

import io.github.aluria.common.scheduler.executor.AbstractBukkitExecutor;
import org.bukkit.plugin.Plugin;

public class MainThreadBukkitExecutor extends AbstractBukkitExecutor {

    public MainThreadBukkitExecutor(Plugin plugin) {
        super(plugin);
    }

    @Override
    public void runTaskTimer(Runnable runnable, long delay, long period) {
        plugin.getServer().getScheduler().runTaskTimer(plugin, runnable, delay, period);
    }

    @Override
    public void runTaskLater(Runnable runnable, long delay) {
        plugin.getServer().getScheduler().runTaskLater(plugin, runnable, delay);
    }

    @Override
    public void runTask(Runnable runnable) {
        plugin.getServer().getScheduler().runTask(plugin, runnable);
    }
}
