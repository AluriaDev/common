package io.github.aluria.common.utils.scoreboard;

import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class ScoreboardDisplayManager {

  private static final ScoreboardDisplayManager INSTANCE = new ScoreboardDisplayManager();

  public static ScoreboardDisplayManager getInstance() {
    return INSTANCE;
  }

  private final Map<UUID, ScoreboardDisplay> sidebarMap = new ConcurrentHashMap<>();

  public ScoreboardDisplay getPlayerDisplay(Player player) {
    return sidebarMap.get(player.getUniqueId());
  }

  public void setPlayerDisplay(Player player , ScoreboardDisplay display){
    this.sidebarMap.put(player.getUniqueId() , display);
  }

  public void removePlayerDisplay(Player player) {
    sidebarMap.remove(player.getUniqueId());
  }

  public Map<UUID, ScoreboardDisplay> rawMap() {
    return sidebarMap;
  }
}
