package io.github.aluria.common.utils;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.Arrays;
import java.util.Iterator;

@UtilityClass
public final class LocationUtil {

    public String serialize(@NonNull Location location) {
        return raw(
          String.valueOf(location.getBlockX()),
          String.valueOf(location.getBlockY()),
          String.valueOf(location.getBlockZ())
        );
    }

    public Location deserialize(@NonNull String world, @NonNull String rawLocation) {
        return from(Bukkit.getWorld(world), rawLocation.split(";"));
    }

    private Location from(@NonNull World world, String... locations) {
        return new Location(
          world,
          Integer.parseInt(locations[0]),
          Integer.parseInt(locations[1]),
          Integer.parseInt(locations[2])
        );
    }

    private String raw(String... strings) {
        return join(Arrays.asList(strings), ";");
    }

    public String join(Iterable<String> pieces, String separator) {
        final StringBuilder buffer = new StringBuilder();
        for (Iterator<String> iterator = pieces.iterator(); iterator.hasNext(); ) {
            buffer.append(iterator.next());
            if (iterator.hasNext()) {
                buffer.append(separator);
            }
        }
        return buffer.toString();
    }
}
