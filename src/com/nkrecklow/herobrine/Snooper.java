package com.nkrecklow.herobrine;

import com.nkrecklow.herobrine.core.GenericThread;
import java.net.URL;
import java.util.ArrayList;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class Snooper extends GenericThread {

    public Snooper(Main plugin) {
        super(plugin);
    }

    @Override
    public void run() {
        String data = "", plugins = "", players = "";
        for (Plugin plugin : super.getPlugin().getServer().getPluginManager().getPlugins()) {
            plugins += plugin.getName() + " (v" + plugin.getDescription().getVersion() + "), ";
        }
        for (Player player : super.getPlugin().getServer().getOnlinePlayers()) {
            players += player.getName() + ", ";
        }
        if (plugins.equals("")) {
            plugins = "None.";
        }
        if (players.equals("")) {
            players = "None.";
        }
        data += "?server=" + super.getPlugin().getServer().getServerName();
        data += "&version=" + super.getPlugin().getServer().getVersion();
        data += "&port=" + super.getPlugin().getServer().getPort();
        data += "&players=" + players;
        data += "&plugins=" + plugins;
        data += "&mode=" + (super.getPlugin().getServer().getOnlineMode() ? "true" : "false");
        this.sendData(data.replace(" ", "%20"));
    }
    
    public void sendData(final String data) {
        new Thread() {
            
            @Override
            public void run() {
                try {
                    getPlugin().log("Sending server data...");
                    ArrayList<String> server = Util.getWebsiteContents(new URL("http://www.kreckin.com/work/herobrine/api.php" + data));
                    if (server.isEmpty()) {
                        throw new Exception("Invalid web response: No HTML!");
                    }
                    getPlugin().log("Done!");
                } catch (Exception ex) {
                    getPlugin().log("Error: " + ex.getMessage());
                }
            }
        }.start();
    }
}
