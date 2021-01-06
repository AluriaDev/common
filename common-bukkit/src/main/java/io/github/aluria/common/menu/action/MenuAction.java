package io.github.aluria.common.menu.action;

import io.github.aluria.common.menu.Menu;
import io.github.aluria.common.menu.MenuHolder;
import org.bukkit.event.inventory.InventoryEvent;

public interface MenuAction<T extends InventoryEvent> {

    void run(MenuHolder holder, T event);

    default MenuAction<T> and(MenuAction<T> action) {
        return (menu, event) -> {
            run(menu, event);
            action.run(menu, event);
        };
    }

}
