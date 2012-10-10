package com.nkrecklow.herobrine;

import com.nkrecklow.herobrine.base.Commands;
import com.nkrecklow.herobrine.base.Controller;
import com.nkrecklow.herobrine.base.Events;
import com.nkrecklow.herobrine.base.Actions;
import java.util.Random;
import java.util.logging.Logger;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private Events listener;
    private Controller controller;
    private Actions actions;
    private Config config;
    private Commands commands;
    private Snooper snooper;

    @Override
    public void onEnable() {
        this.getServer().getScheduler().cancelTasks(this);
        this.commands = new Commands(this);
        this.config = new Config(this);
        this.actions = new Actions(this);
        this.controller = new Controller(this);
        this.listener = new Events(this);
        this.snooper = new Snooper(this);
        this.config.loadConfig();
        this.getCommand("hb").setExecutor(this.commands);
        this.getServer().getPluginManager().registerEvents(this.listener, this);
        this.getServer().getScheduler().scheduleAsyncRepeatingTask(this, new Runnable() {

            @Override
            public void run() {
                if (!controller.isDead()) {
                    controller.getEntity().setVelocity(controller.getEntity().getLocation().getDirection().multiply(0.5D));
                    if ((Boolean) config.getObject("fireTrails")) {
                        Block location = controller.getEntity().getLocation().getBlock();
                        Block below = location.getLocation().subtract(0D, 1D, 0D).getBlock();
                        if (location.getType().equals(Material.AIR) && !below.getType().equals(Material.AIR)) {
                            below.setType(Material.FIRE);
                        }
                    }
                }
            }
        }, 0L, 20L);
        if ((Boolean) this.config.getObject("collectStats")) {
            this.getServer().getScheduler().scheduleAsyncRepeatingTask(this, new Runnable() {

                @Override
                public void run() {
                    snooper.run();
                }
            }, 0L, 3600L);
        }
    }
    
    @Override
    public void onDisable() {
        this.getServer().getScheduler().cancelTasks(this);
    }
    
    public void log(String data) {
        Logger.getLogger("Minecraft").info("[Herobrine] " + data);
    }
    
    public String formatMessage(String msg) {
        return "<" + ChatColor.RED + "Herobrine" + ChatColor.WHITE + "> " + msg;
    }
    
    public Commands getCommands() {
        return this.commands;
    }

    public Config getConfiguration() {
        return this.config;
    }
    
    public Actions getActions() {
        return this.actions;
    }
    
    public Controller getController() {
        return this.controller;
    }
    
    public Snooper getSnooper() {
        return this.snooper;
    }
    
    public Events getEvents() {
        return this.listener;
    }
}
