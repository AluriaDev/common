package io.github.aluria.common.menu;

import io.github.aluria.common.menu.action.CloseAction;
import io.github.aluria.common.menu.action.OpenAction;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@Getter
public class Menu {

    private final String title;
    private final int size;

    private final MenuItem[] items;

    @Setter
    private CloseAction closeAction;

    @Setter
    private OpenAction openAction;

    public Menu(String title, int size) {
        this.title = title;
        this.size = size;
        this.items = new MenuItem[size];
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

    protected void render(Player player, MenuHolder holder) {
    }

    public final MenuHolder show(Player player) {
        final MenuHolder holder = new MenuHolder(this);
        holder.show(player);

        return holder;
    }
}
