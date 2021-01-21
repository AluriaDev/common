package io.github.aluria.common.scheduler;

import io.github.aluria.common.BukkitCommonPlugin;
import io.github.aluria.common.scheduler.executor.BukkitExecutor;
import io.github.aluria.common.scheduler.executor.impl.AsynchronousBukkitExecutor;
import io.github.aluria.common.scheduler.executor.impl.MainThreadBukkitExecutor;
import org.bukkit.plugin.Plugin;

import java.util.concurrent.Callable;

public class Scheduler {

  private static final BukkitExecutor PRIMARY_THREAD_EXECUTOR = new MainThreadBukkitExecutor(BukkitCommonPlugin.getInstance());
  private static final BukkitExecutor ASYNCHRONOUS_EXECUTOR = new AsynchronousBukkitExecutor(BukkitCommonPlugin.getInstance());

  public static BukkitExecutor async() {
    return ASYNCHRONOUS_EXECUTOR;
  }

  public static BukkitExecutor sync() {
    return PRIMARY_THREAD_EXECUTOR;
  }

  public static BukkitExecutor newAsyncExecutor(Plugin plugin) {
    return new AsynchronousBukkitExecutor(plugin);
  }

  public static BukkitExecutor newSyncExecutor(Plugin plugin) {
    return new MainThreadBukkitExecutor(plugin);
  }
}
