package io.github.aluria.common.menu;

import io.github.aluria.common.menu.action.ClickAction;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.inventory.ItemStack;

@Getter
@RequiredArgsConstructor
public class MenuItem {

    private final int slot;

    private ItemStack item;
    private ClickAction action;

    public MenuItem withItem(ItemStack item) {
        this.item = item;
        return this;
    }

    public MenuItem withAction(ClickAction action) {
        if(this.action == null) {
            this.action = action;
        } else {
            this.action.and(action);
        }

        return this;
    }
}
