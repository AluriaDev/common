package io.github.aluria.common.utils.delay;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class DelayMap {

  private final Map<String, Delay> delayMap = new ConcurrentHashMap<>();

  public long getRemainingTime(String id, String cooldownName) {
    Delay delay = this.getDelay(id, cooldownName);
    if (delay == null) {
      return 0;
    }

    if (delay.isExpired()) {
      delayMap.remove(id + cooldownName);
      return 0;
    }

    return delay.getRemainingTime();
  }

  public void addTime(String id, String cooldownName, long time, TimeUnit unit) {
    Delay delay = this.getDelay(id, cooldownName);
    if (delay == null) {
      delay = new Delay(time, unit);
      this.put(id, cooldownName, delay);
    } else {
      delay.addTime(time, unit);
    }
  }

  public void put(String id, String cooldownName, long time, TimeUnit unit) {
    delayMap.put(id + cooldownName, new Delay(time, unit));
  }

  public void clear(String id, String cooldownName) {
    removeDelay(id, cooldownName);
  }

  public void clearAll(String id) {
    delayMap.keySet()
      .stream()
      .filter(string -> string.startsWith(id))
      .forEach(delayMap::remove);
  }

  private Delay getDelay(String id, String cooldownName) {
    Delay delay = delayMap.get(id + cooldownName);
    if (delay == null) {
      return null;
    }

    if (delay.isExpired()) {
      delayMap.remove(id + cooldownName);
      return null;
    }

    return delay;
  }

  public void put(String id, String cooldownName, Delay delay) {
    delayMap.put(id + cooldownName, delay);
  }

  private void removeDelay(String id, String cooldownName) {
    delayMap.remove(id + cooldownName);
  }

  @Data
  @AllArgsConstructor
  public class Delay {

    private long expiringTime;

    public Delay(long time, TimeUnit unit) {
      this.expiringTime = System.currentTimeMillis() + (unit.toSeconds(time) * 1000);
    }

    public long getRemainingTime() {
      return expiringTime - System.currentTimeMillis();
    }

    public void addTime(long time, TimeUnit unit) {
      expiringTime += (unit.toSeconds(time) * 1000);
    }

    public boolean isExpired() {
      return System.currentTimeMillis() >= expiringTime;
    }
  }

}
