package io.github.aluria.common.utils.nbt;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import net.minecraft.server.v1_12_R1.NBTTagCompound;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.function.Consumer;

@UtilityClass
public final class NBTCompoundUtil {

    public void changeCompoundValue(ItemStack itemStack, Consumer<NBTTagCompound> compoundConsumer) {
        if (itemStack == null) return;
        final net.minecraft.server.v1_12_R1.ItemStack nmsItemStack = CraftItemStack.asNMSCopy(itemStack);

        NBTTagCompound tagCompound = nmsItemStack.getTag();
        if (tagCompound == null) tagCompound = new NBTTagCompound();

        compoundConsumer.accept(tagCompound);
        nmsItemStack.setTag(tagCompound);

        final ItemMeta itemMeta = CraftItemStack.getItemMeta(nmsItemStack);
        itemStack.setItemMeta(itemMeta);
    }

    public boolean hasCompoundEntryTag(ItemStack itemStack, @NonNull String key) {
        if (itemStack == null) return false;
        final net.minecraft.server.v1_12_R1.ItemStack nmsCopy = CraftItemStack.asNMSCopy(itemStack);
        return nmsCopy != null && nmsCopy.hasTag() && nmsCopy.getTag().hasKey(key);
    }
}

