package io.github.aluria.common.commands;

import io.github.aluria.common.BungeeCommonPlugin;
import io.github.aluria.common.entities.User;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class DiscordCommand extends Command {

    private final BungeeCommonPlugin plugin;

    public DiscordCommand(BungeeCommonPlugin plugin) {
        super("discord", "", "dc");

        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(!(sender instanceof ProxiedPlayer)) {
            sender.sendMessage(TextComponent.fromLegacyText("§cApenas jogadores podem executar este comando."));
            return;
        }

        final ProxiedPlayer player = (ProxiedPlayer) sender;

        final User user = plugin.getUserRegistry().getUserById(player.getUniqueId());
        if(user == null) return;

        if(user.isDiscordSynchronized()) {

        } else {

        }
    }

}
