package io.github.aluria.common.event.wrappers;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class EventWrapper extends Event {

  private static final HandlerList HANDLER_LIST = new HandlerList();

  public static HandlerList getHandlerList() {
    return HANDLER_LIST;
  }

  @Override
  public HandlerList getHandlers() {
    return HANDLER_LIST;
  }
}