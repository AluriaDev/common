package io.github.aluria.common.utils;

import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@UtilityClass
public class Players {

    public static List<Player> selectPlayersWithFilter(Predicate<Player> predicate) {
        return Bukkit.getOnlinePlayers()
          .parallelStream()
          .filter(predicate)
          .collect(Collectors.toList());
    }

    @Nullable
    public OfflinePlayer getOfflinePlayer(String playerName) {
        for (OfflinePlayer it : Bukkit.getOfflinePlayers()) {
            if (it != null) {
                if (it.getName().equalsIgnoreCase(playerName)) {
                    return it;
                }
            }
        }
        return null;
    }

    @Nullable
    public Player getPlayerFromName(String playerName) {
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            if (onlinePlayer.getName().equalsIgnoreCase(playerName)) {
                return onlinePlayer;
            }
        }

        return null;
    }
}
