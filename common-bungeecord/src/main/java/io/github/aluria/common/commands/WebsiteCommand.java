package io.github.aluria.common.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import io.github.aluria.common.Constants;
import net.md_5.bungee.api.connection.ProxiedPlayer;

@CommandAlias("website|site")
public class WebsiteCommand extends BaseCommand {

    @Default
    public void handleWebsiteCommand(ProxiedPlayer player) {
        player.sendMessages(
          " ",
          " §6§lALURIA",
          " §fAjude o servidor a se manter online fazendo",
          " §fuma doação em nosso site: §7" + Constants.ALURIA_WEBSITE,
          " "
        );
    }
}
