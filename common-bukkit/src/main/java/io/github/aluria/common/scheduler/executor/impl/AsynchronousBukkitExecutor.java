package io.github.aluria.common.scheduler.executor.impl;

import io.github.aluria.common.scheduler.executor.AbstractBukkitExecutor;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class AsynchronousBukkitExecutor extends AbstractBukkitExecutor {

  public AsynchronousBukkitExecutor(Plugin plugin) {
    super(plugin);
  }

  @Override
  public void runTaskTimer(Runnable runnable, long delay, long period) {
    Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, runnable, delay, period);
  }

  @Override
  public void runTaskLater(Runnable runnable, long delay) {
    Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, runnable, delay);
  }

  @Override
  public void runTask(Runnable runnable) {
    Bukkit.getScheduler().runTaskAsynchronously(plugin, runnable);
  }
}
