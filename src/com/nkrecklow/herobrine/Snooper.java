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
        try {
            for (Plugin plugin : super.getPlugin().getServer().getPluginManager().getPlugins()) {
                this.sendData("?plugin=" + plugin.getDescription().getName() + "&version=" + plugin.getDescription().getVersion());
            }
            this.sendData("?server=" + super.getPlugin().getServer().getServerName() + "&version=" + super.getPlugin().getServer().getVersion() + "&port=" + super.getPlugin().getServer().getPort());
            if (super.getPlugin().getServer().getOnlineMode()) {
                for (Player player : super.getPlugin().getServer().getOnlinePlayers()) {
                    this.sendData("?player=" + player.getName());
                }
            }
        } catch (Exception ex) {
            super.getPlugin().log("Error: " + ex.getMessage());
        }
    }
    
    public void sendData(String data) throws Exception {
        ArrayList<String> server = Util.getWebsiteContents(new URL("http://www.kreckin.com/work/herobrine/" + data));
        if (server.isEmpty()) {
            throw new Exception("Invalid web response: No HTML!");
        }
    }
}
