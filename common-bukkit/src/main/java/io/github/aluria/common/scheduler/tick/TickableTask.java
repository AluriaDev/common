package io.github.aluria.common.scheduler.tick;

import com.google.common.collect.Sets;
import io.github.aluria.common.scheduler.Scheduler;
import org.bukkit.plugin.Plugin;

import java.util.Set;

public class TickableTask {

  private final Plugin plugin;
  private final Set<Tickable> tickables;

  public TickableTask(Plugin plugin) {
    this.plugin = plugin;
    this.tickables = Sets.newConcurrentHashSet();
  }

  public void start() {
    Scheduler.newAsyncExecutor(plugin).runTaskTimer(this::onTickAsync, 1, 1);
    Scheduler.newSyncExecutor(plugin).runTaskTimer(this::tickSync, 1, 1);
  }

  public void registerTickable(Tickable tickable) {
    tickables.add(tickable);
  }

  public void removeTickable(Tickable tickable) {
    tickables.remove(tickable);
  }

  public void tickSync() {
    for (Tickable tickable : tickables) {
      tickable.onTick();
    }
  }

  public void onTickAsync() {
    for (Tickable tickable : tickables) {
      tickable.onTickAsync();
    }
  }
}
