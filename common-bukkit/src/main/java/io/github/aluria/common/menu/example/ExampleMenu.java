package io.github.aluria.common.menu.example;

import io.github.aluria.common.menu.Menu;
import io.github.aluria.common.menu.MenuHolder;
import org.bukkit.entity.Player;

public class ExampleMenu extends Menu {

    public ExampleMenu(String title, int size) {
        super(title, size);

        this.setCloseAction((holder, event) -> {

        });

        this.setOpenAction((holder, event) -> {

        });
    }

    @Override
    protected void render(Player player, MenuHolder holder) {

    }
}
