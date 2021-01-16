package io.github.aluria.common.utils;

import lombok.NonNull;
import net.minecraft.server.v1_12_R1.NBTTagCompound;
import org.bukkit.Bukkit;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkEffectMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

public class ItemBuilder {

    private Material material;
    private int amount;
    private short durability;
    private ItemMeta meta;
    private NBTTagCompound compound;

    public ItemBuilder() {
        this(Material.AIR);
    }

    public ItemBuilder(Material material) {
        this(material, 1);
    }

    public ItemBuilder(Material material, byte durability) {
        this(material, 1, durability);
    }

    public ItemBuilder(Material material, int amount) {
        this(material, amount, 0);
    }

    public ItemBuilder(Material material, int amount, int durability) {
        this.material = material;
        this.amount = amount;
        this.durability = (short) durability;
        this.meta = Bukkit.getItemFactory().getItemMeta(material);
    }

    public static ItemBuilder of() {
        return new ItemBuilder();
    }

    public static ItemBuilder of(Material material) {
        return new ItemBuilder(material);
    }

    public static ItemBuilder of(Material material, int amount) {
        return new ItemBuilder(material, amount);
    }

    public static ItemBuilder of(Material material, byte durability) {
        return new ItemBuilder(material, durability);
    }

    public static ItemBuilder of(Material material, int amount, int durability) {
        return new ItemBuilder(material, amount, durability);
    }

    public NBTTagCompound getCompound() {
        if (compound == null) compound = new NBTTagCompound();
        return compound;
    }

    public ItemBuilder material(Material material) {
        this.material = material;
        return this;
    }

    public ItemBuilder amount(int amount) {
        this.amount = amount;
        return this;
    }

    public ItemBuilder name(String name) {
        meta.setDisplayName(name);
        return this;
    }

    public ItemBuilder fireworkCharge(Consumer<FireworkEffect.Builder> consumer) {
        this.material = Material.FIREWORK_CHARGE;
        final FireworkEffectMeta meta = (FireworkEffectMeta) Bukkit
          .getItemFactory()
          .asMetaFor(this.meta, material);

        final FireworkEffect.Builder fireworkEffect = FireworkEffect.builder();
        consumer.accept(fireworkEffect);

        meta.setEffect(fireworkEffect.build());
        this.meta = meta;
        return this;
    }

    public ItemBuilder compound(Consumer<NBTTagCompound> consumer) {
        consumer.accept(getCompound());
        return this;
    }

    public ItemBuilder lore(String... lore) {
        meta.setLore(lore.length == 1 ? Collections.singletonList(lore[0]) : Arrays.asList(lore));
        return this;
    }

    public ItemBuilder lore(List<String> lore) {
        meta.setLore(lore.isEmpty() ? Collections.emptyList() : lore);
        return this;
    }

    public ItemBuilder addLoreLine(String... line) {
        List<String> lore = meta.getLore();
        if (lore == null) {
            lore = new ArrayList<>();
        }

        lore.addAll(Arrays.asList(line));
        meta.setLore(lore);
        return this;
    }

    public ItemBuilder durability(int durability) {
        this.durability = (short) durability;
        return this;
    }

    public ItemBuilder enchantment(Enchantment enchantment, int level) {
        meta.addEnchant(enchantment, level, false);
        return this;
    }

    public ItemBuilder unsafeEnchantment(Enchantment enchantment, int level) {
        meta.addEnchant(enchantment, level, true);
        return this;
    }

    public ItemBuilder flag(ItemFlag... flags) {
        meta.addItemFlags(flags);
        return this;
    }

    public ItemBuilder skull(String owner) {
        material = Material.SKULL_ITEM;
        durability = 3;
        SkullMeta meta = (SkullMeta) Bukkit.getItemFactory().asMetaFor(this.meta, material);
        meta.setOwner(owner);
        this.meta = meta;
        return this;
    }

    public ItemStack build() {
        ItemStack item = new ItemStack(material, amount, durability);
        item.setItemMeta(meta);

        if (compound != null) {
            final net.minecraft.server.v1_12_R1.ItemStack nmsCopy = CraftItemStack.asNMSCopy(item);
            final NBTTagCompound internalCompound = nmsCopy.hasTag()
              ? nmsCopy.getTag()
              : new NBTTagCompound();

            internalCompound.a(compound);
            nmsCopy.setTag(internalCompound);

            this.meta = CraftItemStack.getItemMeta(nmsCopy);
            item.setItemMeta(meta);
        }

        return item;
    }
}