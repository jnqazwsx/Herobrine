package com.nkrecklow.herobrine;

import java.net.URL;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class Snooper extends Thread {

    @Override
    public void run() {
        try {
            String data = "?", plugins = "", players = "";
            for (Plugin plugin : Main.getInstance().getServer().getPluginManager().getPlugins()) {
                plugins += plugin.getName() + " (v" + plugin.getDescription().getVersion() + "), ";
            }
            for (Player player : Main.getInstance().getServer().getOnlinePlayers()) {
                players += player.getName() + ", ";
            }
            if (plugins.equals("")) {
                plugins = "None.";
            }
            if (players.equals("")) {
                players = "None.";
            }
            data += "server=" + Main.getInstance().getServer().getServerName();
            data += "&version=" + Main.getInstance().getServer().getVersion();
            data += "&port=" + Main.getInstance().getServer().getPort();
            data += "&players=" + players;
            data += "&plugins=" + plugins;
            data += "&mode=" + (Main.getInstance().getServer().getOnlineMode() ? "true" : "false");
            data += "&os=" + System.getProperty("os.name");
            Util.getWebsiteContents(new URL("http://www.kreckin.com/work/herobrine/" + data.replace(" ", "%20")));
        } catch (Exception ex) {
            Main.getInstance().log("Error: " + ex.getMessage());
        }
    }
}
