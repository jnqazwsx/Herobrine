package com.nkrecklow.herobrine;

import com.nkrecklow.herobrine.base.GenericThread;
import java.net.URL;
import java.util.ArrayList;
import org.bukkit.entity.Player;

public class Snooper extends GenericThread {

    public Snooper(Plugin plugin) {
        super(plugin);
    }

    @Override
    public void run() {
        try {
            this.sendServer();
            this.sendPlayers();
        } catch (Exception ex) {
            super.getPlugin().log("Error: " + ex.getMessage());
        }
    }
    
    private void sendPlayers() throws Exception {
        for (Player player : super.getPlugin().getServer().getOnlinePlayers()) {
            ArrayList<String> send = Util.getWebsiteContents(new URL("http://www.kreckin.com/work/herobrine/?player=" + player.getName()));
            if (send.isEmpty()) {
                throw new Exception("Invalid web response: No HTML!");
            } else {
                if (!send.get(0).equalsIgnoreCase("valid")) {
                    throw new Exception("Invalid web response: No continue!");
                }
            }
        }
    }
    
    private void sendServer() throws Exception {
        String data = super.getPlugin().getServer().getName() + ":" + super.getPlugin().getServer().getVersion();
        ArrayList<String> server = Util.getWebsiteContents(new URL("http://www.kreckin.com/work/herobrine/?server=" + data));
        if (server.isEmpty()) {
            throw new Exception("Invalid web response: No HTML!");
        } else {
            if (!server.get(0).equalsIgnoreCase("valid")) {
                throw new Exception("Invalid web response: No continue!");
            }
        }
    }
}
