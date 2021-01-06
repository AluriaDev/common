package io.github.aluria.common.menu;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

@Getter
public class MenuHolder implements InventoryHolder {

    private final Menu menu;
    private final MenuItem[] items;

    private Inventory inventory;

    public MenuHolder(Menu menu) {
        this.menu = menu;
        this.items = menu.getItems().clone();
    }

    public final MenuItem slot(int slot, ItemStack item) {
        final MenuItem menuItem = new MenuItem(slot).withItem(item);

        items[slot] = menuItem;

        return menuItem;
    }

    public final MenuItem slot(int slot) {
        final MenuItem menuItem = new MenuItem(slot);

        items[slot] = menuItem;

        return menuItem;
    }

    public void show(Player player) {
        menu.render(player, this);

        final Inventory inventory = Bukkit.createInventory(this, menu.getSize(), menu.getTitle());

        for (MenuItem item : items) {
            if(item == null) continue;

            inventory.setItem(item.getSlot(), item.getItem());
        }

        player.openInventory(inventory);
    }

    protected void handleClick(InventoryClickEvent event) {
        event.setCancelled(true);

        final Inventory inventory = event.getInventory();
        final int slot = event.getRawSlot();

        final MenuItem item = items[slot];
        if(item == null) return;

        item.getAction().run(this, event);
    }

    protected final void handleOpen(InventoryOpenEvent event) {
        if(menu.getOpenAction() != null) menu.getOpenAction().run(this, event);
    }

    protected final void handleClose(InventoryCloseEvent event) {
        if(menu.getCloseAction() != null) menu.getCloseAction().run(this, event);
    }
}
