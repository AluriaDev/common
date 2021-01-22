package io.github.aluria.common.utils.hologram;

import net.minecraft.server.v1_12_R1.*;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;

public class HologramArmorStand extends EntityArmorStand {
  public HologramArmorStand(World world) {
    super(world);

    super.setInvisible(true);
    super.setSmall(true);
    super.setArms(false);
    super.setNoGravity(true);
    super.setBasePlate(true);
    super.setMarker(true);
    super.collides = false;
  }

  public HologramArmorStand(org.bukkit.World world) {
    this(((CraftWorld) world).getHandle());
  }

  public void spawn() {
    broadcastSpawnPacket();
  }

  @Override
  public void B_() {
    // Workaround to force EntityTrackerEntry to send a teleport packet immediately after spawning this entity.
    if (this.onGround) {
      this.onGround = false;
    }
  }

  @Override
  public void inactiveTick() {
    // Workaround to force EntityTrackerEntry to send a teleport packet immediately after spawning this entity.
    if (this.onGround) {
      this.onGround = false;
    }
  }

  @Override
  public void b(NBTTagCompound nbttagcompound) {
    // Do not save NBT.
  }

  @Override
  public boolean c(NBTTagCompound nbttagcompound) {
    // Do not save NBT.
    return false;
  }

  @Override
  public boolean d(NBTTagCompound nbttagcompound) {
    // Do not save NBT.
    return false;
  }

  @Override
  public NBTTagCompound save(NBTTagCompound nbttagcompound) {
    // Do not save NBT.
    return nbttagcompound;
  }

  @Override
  public void f(NBTTagCompound nbttagcompound) {
    // Do not load NBT.
  }

  @Override
  public void a(NBTTagCompound nbttagcompound) {
    // Do not load NBT.
  }

  @Override
  public boolean isInvulnerable(DamageSource source) {
    /*
     * The field Entity.invulnerable is private.
     * It's only used while saving NBTTags, but since the entity would be killed
     * on chunk unload, we prefer to override isInvulnerable().
     */
    return true;
  }

  @Override
  public boolean isCollidable() {
    return false;
  }

  @Override
  public void setCustomName(String customName) {
    super.setCustomName(customName);
  }

  @Override
  public void setCustomNameVisible(boolean visible) {
    super.setCustomNameVisible(true);
  }

  @Override
  public EnumInteractionResult a(EntityHuman human, Vec3D vec3d, EnumHand enumhand) {
    // Prevent stand being equipped
    return EnumInteractionResult.PASS;
  }

  @Override
  public boolean c(int i, ItemStack item) {
    // Prevent stand being equipped
    return false;
  }

  @Override
  public void setSlot(EnumItemSlot enumitemslot, ItemStack itemstack) {
    // Prevent stand being equipped
  }

  @Override
  public void a(AxisAlignedBB boundingBox) {
    // Do not change it!
  }

  public void forceSetBoundingBox(AxisAlignedBB boundingBox) {
    super.a(boundingBox);
  }

  @Override
  public void die() {
    super.die();

    this.broadcastDestroyPacket();
  }

  @Override
  public void a(SoundEffect soundeffect, float f, float f1) {
    // Remove sounds.
  }

  public void setLocationNMS(double x, double y, double z, boolean broadcastLocationPacket) {
    super.setPosition(x, y, z);
    if (broadcastLocationPacket) {
      broadcastLocationPacketNMS();
    }
  }

  public void broadcastSpawnPacket() {
    PacketPlayOutSpawnEntityLiving spawn = new PacketPlayOutSpawnEntityLiving(this);

    for (Object obj : super.world.players) {
      if (!(obj instanceof EntityPlayer)) {
        continue;
      }

      EntityPlayer nmsPlayer = (EntityPlayer) obj;

      nmsPlayer.playerConnection.sendPacket(spawn);
    }
  }

  public void broadcastDestroyPacket() {
    PacketPlayOutEntityDestroy destroy = new PacketPlayOutEntityDestroy(this.getId());

    for (Object obj : super.world.players) {
      if (!(obj instanceof EntityPlayer)) {
        continue;
      }

      EntityPlayer nmsPlayer = (EntityPlayer) obj;
      nmsPlayer.playerConnection.sendPacket(destroy);
    }
  }

  private void broadcastLocationPacketNMS() {
    PacketPlayOutEntityTeleport teleportPacket = new PacketPlayOutEntityTeleport(this);

    for (Object obj : super.world.players) {
      if (!(obj instanceof EntityPlayer)) {
        continue;
      }

      EntityPlayer nmsPlayer = (EntityPlayer) obj;
      nmsPlayer.playerConnection.sendPacket(teleportPacket);
    }
  }

  private double square(double num) {
    return num * num;
  }
}
