package io.github.aluria.common.commands;

import io.github.aluria.common.Constants;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class WebsiteCommand extends Command {

    public WebsiteCommand() {
        super("website", "", "site");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        sender.sendMessages(
          " ",
          " §6§lALURIA",
          " §fAjude o servidor a se manter online fazendo",
          " §fuma doação em nosso site: §7" + Constants.ALURIA_WEBSITE,
          " "
        );
    }
}
