package io.github.aluria.common.listeners;

import io.github.aluria.common.BukkitCommonPlugin;
import io.github.aluria.common.entities.User;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashSet;

@RequiredArgsConstructor
public class ConnectionListener implements Listener {

    private final BukkitCommonPlugin plugin;

    @EventHandler(ignoreCancelled = true)
    public void onPlayerJoin(PlayerJoinEvent event) {
        plugin.getUserRegistry().registerUser(create(event.getPlayer()));
    }

    private User create(Player player) {
        final Timestamp now = Timestamp.from(Instant.now());

        return User.builder()
          .id(player.getUniqueId())

          .username(player.getName().toLowerCase())
          .realName(player.getName())
          .lastAddress(player.getAddress().getHostName())

          .firstLoginDate(now)
          .lastLoginDate(now)

          .friends(new HashSet<>())

          .build();
    }
}
