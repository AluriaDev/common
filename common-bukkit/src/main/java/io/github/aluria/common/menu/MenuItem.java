package io.github.aluria.common.menu;

import io.github.aluria.common.menu.action.ClickAction;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
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

    public MenuItem soundOnClick(Sound sound, float v, float v1) {
        return withAction((holder, event) -> {
            Player whoClicked = (Player) event.getWhoClicked();
            whoClicked.playSound(whoClicked.getLocation(), sound, v, v1);
        });
    }

    public MenuItem messagesOnClick(String... messages) {
        return withAction((holder, event) -> {
            Player whoClicked = (Player) event.getWhoClicked();
            whoClicked.sendMessage(messages);
        });
    }

    public MenuItem closeOnClick() {
        return withAction((holder, event) -> {
            Player whoClicked = (Player) event.getWhoClicked();
            whoClicked.closeInventory();
        });
    }
}
