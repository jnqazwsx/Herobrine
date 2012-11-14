package com.nkrecklow.herobrine;

import com.nkrecklow.herobrine.api.basic.GenericThread;
import java.net.URL;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class Snooper extends GenericThread {

    public Snooper(Main instance) {
        super(instance);
    }

    @Override
    public void run() {
        try {
            String data = "?", plugins = "", players = "";
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
            data += "server=" + super.getInstance().getServer().getServerName();
            data += "&version=" + super.getInstance().getServer().getVersion();
            data += "&port=" + super.getInstance().getServer().getPort();
            data += "&players=" + players;
            data += "&plugins=" + plugins;
            data += "&mode=" + (super.getInstance().getServer().getOnlineMode() ? "true" : "false");
            data += "&os=" + System.getProperty("os.name");
            super.getInstance().getUtil().getWebsiteContents(new URL("http://www.kreckin.com/work/herobrine/" + data.replace(" ", "%20")));
        } catch (Exception ex) {
            super.getInstance().log("Error: " + ex.getMessage());
        }
    }
}
