package me.mckoxu.bungeecord.lobbycore.command;

import me.mckoxu.bungeecord.lobbycore.LobbyCore;
import me.mckoxu.bungeecord.lobbycore.util.ChatUtil;
import me.mckoxu.bungeecord.lobbycore.util.TitleUtil;
import me.mckoxu.bungeecord.lobbycore.util.Util;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class LobbyCommand extends Command {
    public LobbyCommand() {
        super("lobby", "lobbycore.lobby", "hub");
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        if (commandSender instanceof ProxiedPlayer) {
            ProxiedPlayer player = (ProxiedPlayer) commandSender;
            ServerInfo server = Util.getServer(LobbyCore.getConfig().getString("servers.lobby"));
            TitleUtil.createTitle(
                    LobbyCore.getConfig().getString("connect.connecting.title")
                            .replace("{SERVER}", server.getName().toUpperCase()),
                    LobbyCore.getConfig().getString("connect.connecting.subtitle")
                            .replace("{SERVER}", server.getName().toUpperCase()))
                    .send(player);
            if (Util.pingServer(server)) {
                if (!player.getServer().getInfo().getName().equalsIgnoreCase(server.getName())) {
                    ServerInfo target = ProxyServer.getInstance().getServerInfo(server.getName());
                    player.connect(target);
                } else {
                    TitleUtil.createTitle(
                            LobbyCore.getConfig().getString("connect.alreadyonlobby.title"),
                            LobbyCore.getConfig().getString("connect.alreadyonlobby.subtitle"))
                            .send(player);
                }
            } else {
                player.disconnect(new TextComponent(ChatUtil.color(LobbyCore.getConfig().getString("messages.lobbyoffline"))));
            }
        } else {
            commandSender.sendMessage(new TextComponent(ChatColor.RED + "This command can only be run by a player!"));
        }
    }
}
