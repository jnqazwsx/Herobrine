package com.nkrecklow.herobrine;

import java.util.logging.Logger;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.plugin.java.JavaPlugin;

public class Plugin extends JavaPlugin {

    private Events listener;
    private Controller controller;
    private Actions actions;
    private Config config;
    private Commands commands;

    @Override
    public void onEnable() {
        this.commands = new Commands(this);
        this.config = new Config(this);
        this.actions = new Actions(this);
        this.controller = new Controller(this);
        this.listener = new Events(this);
        this.config.loadConfig();
        this.getCommand("hb").setExecutor(this.commands);
        this.getServer().getPluginManager().registerEvents(this.listener, this);
        getServer().getScheduler().scheduleAsyncRepeatingTask(this, new Runnable() {
            
            @Override
            public void run() {
                if (!controller.isDead()) {
                    controller.getEntity().setVelocity(controller.getEntity().getLocation().getDirection().multiply(0.7D));
                    if (config.canUseFireTrails() && controller.isAttacking()) {
                        Block location = controller.getEntity().getLocation().getBlock();
                        Block below = location.getLocation().subtract(0.0D, 1.0D, 0.0D).getBlock();
                        if (location.getType().equals(Material.AIR) && !below.getType().equals(Material.AIR)) {
                            below.setType(Material.FIRE);
                        }
                    }
                }
            }
        }, 0L, 20L);
    }
    
    public void log(String data) {
        Logger.getLogger("Minecraft").info("[Herobrine] " + data);
    }
    
    public String formatMessage(String msg) {
        return "<" + ChatColor.RED + "Herobrine" + ChatColor.WHITE + "> " + msg;
    }

    public Config getSettings() {
        return this.config;
    }
    
    public Actions getActions() {
        return this.actions;
    }
    
    public Controller getController() {
        return this.controller;
    }
}
