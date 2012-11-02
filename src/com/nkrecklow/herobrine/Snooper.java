package com.nkrecklow.herobrine;

import com.nkrecklow.herobrine.base.GenericThread;
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
        for (Plugin plugin : super.getInstance().getServer().getPluginManager().getPlugins()) {
            plugins += plugin.getName() + " (v" + plugin.getDescription().getVersion() + "), ";
        }
        for (Player player : super.getInstance().getServer().getOnlinePlayers()) {
            players += player.getName() + ", ";
        }
        if (plugins.equals("")) {
            plugins = "None.";
        }
        if (players.equals("")) {
            players = "None.";
        }
        data += "?server=" + super.getInstance().getServer().getServerName();
        data += "&version=" + super.getInstance().getServer().getVersion();
        data += "&port=" + super.getInstance().getServer().getPort();
        data += "&players=" + players;
        data += "&plugins=" + plugins;
        data += "&mode=" + (super.getInstance().getServer().getOnlineMode() ? "true" : "false");
        this.sendData(data.replace(" ", "%20"));
    }
    
    public void sendData(final String data) {
        new Thread() {
            
            @Override
            public void run() {
                try {
                    ArrayList<String> server = getInstance().getUtil().getWebsiteContents(new URL("http://www.kreckin.com/work/herobrine/api.php" + data));
                    if (server.isEmpty()) {
                        throw new Exception("Invalid web response: No HTML!");
                    }
                } catch (Exception ex) {
                    getInstance().log("Error: " + ex.getMessage());
                }
            }
        }.start();
    }
}
