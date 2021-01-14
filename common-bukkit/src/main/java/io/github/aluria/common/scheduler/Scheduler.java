package io.github.aluria.common.scheduler;

import io.github.aluria.common.BukkitCommonPlugin;
import io.github.aluria.common.scheduler.executor.BukkitExecutor;
import io.github.aluria.common.scheduler.executor.impl.AsynchronousBukkitExecutor;
import io.github.aluria.common.scheduler.executor.impl.MainThreadBukkitExecutor;

public class Scheduler {

  private static final BukkitExecutor PRIMARY_THREAD_EXECUTOR = new MainThreadBukkitExecutor(BukkitCommonPlugin.getInstance());
  private static final BukkitExecutor ASYNCHRONOUS_EXECUTOR = new AsynchronousBukkitExecutor(BukkitCommonPlugin.getInstance());

  public static BukkitExecutor async() {
    return ASYNCHRONOUS_EXECUTOR;
  }

  public static BukkitExecutor sync() {
    return PRIMARY_THREAD_EXECUTOR;
  }
}
