package io.github.aluria.common.scheduler.tick;

public interface Tickable {

  void onTick();

  void onTickAsync();
  
}
