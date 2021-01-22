package io.github.aluria.common.utils.hologram;

import lombok.Getter;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
public class Hologram {

  private String id;
  private Location location;
  private List<HologramArmorStand> stands;

  public Hologram(Location location) {
    this.id = Long.toHexString(System.nanoTime());
    this.location = location;
    this.stands = Collections.synchronizedList(new ArrayList<>());
  }

  public void spawn() {
    double y = 0;

    for (HologramArmorStand stand : stands) {
      stand.setLocationNMS(location.getX(), location.getY() - y, location.getZ(), true);
      y += 0.02;
    }
  }

  public void teleport(Location location) {
    this.location = location;

    double y = 0;
    for (HologramArmorStand stand : stands) {
      stand.setLocationNMS(location.getX(), location.getY() - y, location.getZ(), true);
      y += 0.02;
    }
  }

  public void destroy() {
    stands.forEach(HologramArmorStand::die);
  }

  public void addLines(String... lines) {
    for (String line : lines) {
      HologramArmorStand stand = new HologramArmorStand(location.getWorld());
      stand.setCustomName(line);
      stand.setCustomNameVisible(true);
      stand.broadcastSpawnPacket();
      stands.add(stand);


      stand.setLocationNMS(location.getX(), location.getY() - (0.02 * stands.size()), location.getZ(), true);
    }
  }

  public void deleteLine(int index) {
    HologramArmorStand stand = this.getStand(index);
    if (stand != null) {
      stands.remove(stand);
    }
  }

  public void updateLine(int index, String content) {
    HologramArmorStand stand = this.getStand(index);
    if (stand != null) {
      stand.setCustomName(content);
    }
  }

  public String getLine(int index) {
    HologramArmorStand stand = this.getStand(index);
    if (stand == null) {
      return null;
    }

    return stand.getCustomName();
  }

  private HologramArmorStand getStand(int index) {
    try {
      return stands.get(index);
    } catch (Exception e) {
      return null;
    }
  }
}
