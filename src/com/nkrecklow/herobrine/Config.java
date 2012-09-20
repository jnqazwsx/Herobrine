package com.nkrecklow.herobrine;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;
import org.bukkit.configuration.file.YamlConfiguration;

public class Config {

    private int actionChance;
    private boolean changeTime, fireTrails, sendMessages, modifyWorld;
    private List<String> messages, allowedWorlds;
    private String build;
    private Plugin plugin;
    
    public Config(Plugin plugin) {
        this.plugin = plugin;
        this.actionChance = 100000;
        this.changeTime = true;
        this.fireTrails = false;
        this.sendMessages = true;
        this.modifyWorld = true;
        this.messages = new ArrayList<String>();
        this.allowedWorlds = new ArrayList<String>();
        this.build = this.plugin.getDescription().getVersion();
    }
    
    public void loadConfig() {
        try {
            File dir = new File(this.plugin.getDataFolder() + "\\");
            File file = new File(dir + "config.yml");
            if (!dir.exists()) {
                if (!dir.mkdir()) {
                    Logger.getLogger("Minecraft").info("[Herobrine] Failed to create directory!");
                    this.plugin.getServer().getPluginManager().disablePlugin(this.plugin);
                }
            }
            if (file.exists()) {
                YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
                if (!this.build.equals(config.getString("Herobrine.configBuild"))) {
                    Logger.getLogger("Minecraft").info("[Herobrine] Outdated configuration file!");
                }
                this.actionChance = config.getInt("Herobrine.actionChance");
                this.sendMessages = config.getBoolean("Herobrine.sendMessages");
                this.modifyWorld = config.getBoolean("Herobrine.modifyWorld");
                this.changeTime = config.getBoolean("Herobrine.changeTime");
                this.fireTrails = config.getBoolean("Herobrine.fireTrails");
                this.messages = config.getStringList("Herobrine.messages");
                this.allowedWorlds = config.getStringList("Herobrine.allowedWorlds");
                if (this.messages.isEmpty()) {
                    this.sendMessages = false;
                }
                if (this.allowedWorlds.isEmpty()) {
                    Logger.getLogger("Minecraft").info("[Herobrine] Must be allowed in atleast one world!");
                    this.plugin.getServer().getPluginManager().disablePlugin(this.plugin);
                }
            } else {
                this.plugin.saveResource("config.yml", true);
            }
        } catch (Exception ex) {
            Logger.getLogger("Minecraft").info("[Herobrine] Failed to load configuration file!");
            this.plugin.getServer().getPluginManager().disablePlugin(this.plugin);
        }
    }
    
    public int getActionChance() {
        return this.actionChance;
    }
    
    public boolean canChangeTime() {
        return this.changeTime;
    }
    
    public boolean canUseFireTrails() {
        return this.fireTrails;
    }
    
    public boolean canModifyWorld() {
        return this.modifyWorld;
    }
    
    public boolean canSendMessages() {
        return this.sendMessages;
    }
    
    public List<String> getAllowedWorlds() {
        return this.allowedWorlds;
    }
    
    public String getMessage() {
        if (this.messages.size() > 1) {
            return this.messages.get(new Random().nextInt(this.messages.size() - 1));
        } else {
            return this.messages.get(0);
        }
    }
}
