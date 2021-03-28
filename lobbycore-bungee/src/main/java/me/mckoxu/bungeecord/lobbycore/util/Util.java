package me.mckoxu.bungeecord.lobbycore.util;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;

import java.net.InetSocketAddress;
import java.net.Socket;

public class Util {
    public static boolean pingServer(ServerInfo server) {
        if (server == null) {
            return false;
        }
        try {
            Socket s = new Socket();
            s.connect(new InetSocketAddress(server.getAddress().getAddress(), server.getAddress().getPort()), 120);
            s.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static ServerInfo getServer(String name) {
        try {
            return ProxyServer.getInstance().getServerInfo(name);
        } catch (Exception ex) {
            return null;
        }
    }
}
