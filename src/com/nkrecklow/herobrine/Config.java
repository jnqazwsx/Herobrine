package com.nkrecklow.herobrine;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.bukkit.configuration.file.YamlConfiguration;

public class Config {

    private int actionChance;
    private List<String> messages, allowedWorlds;
    private Plugin plugin;
    private YamlConfiguration config;
    
    public Config(Plugin plugin) {
        this.plugin = plugin;
        this.messages = new ArrayList<String>();
        this.allowedWorlds = new ArrayList<String>();
    }
    
    public void loadConfig() {
        try {
            File dir = this.plugin.getDataFolder();
            File file = new File(dir + "/config.yml");
            if (!dir.exists()) {
                if (!dir.mkdir()) {
                    this.plugin.log("Failed to create directory!");
                    this.plugin.getServer().getPluginManager().disablePlugin(this.plugin);
                }
            }
            if (file.exists()) {
                this.config = YamlConfiguration.loadConfiguration(file);
                if (!this.plugin.getDescription().getVersion().equals(config.getString("Herobrine.configBuild"))) {
                    this.plugin.log("Outdated configuration file! Please delete it and restart!");
                    this.plugin.getServer().getPluginManager().disablePlugin(this.plugin);
                }
                this.actionChance = this.config.getInt("Herobrine.actionChance");
                this.messages = this.config.getStringList("Herobrine.messages");
                this.allowedWorlds = this.config.getStringList("Herobrine.allowedWorlds");
                if (this.allowedWorlds.isEmpty()) {
                    this.plugin.log("Must be allowed in atleast one world!");
                    this.plugin.getServer().getPluginManager().disablePlugin(this.plugin);
                }
            } else {
                this.plugin.saveResource("config.yml", false);
            }
        } catch (Exception ex) {
            this.plugin.log("Failed to load configuration file!");
            this.plugin.log("Error: " + ex.getMessage());
            this.plugin.getServer().getPluginManager().disablePlugin(this.plugin);
        }
    }
    
    public boolean canSendMessages() {
        return (Boolean) this.getObject("sendMessages") && this.messages.size() > 0;
    }
    
    public List<String> getAllowedWorlds() {
        return this.allowedWorlds;
    }
    
    public int getActionChance() {
        return this.actionChance;
    }

    public String getMessage() {
        if (this.messages.size() > 1) {
            return this.messages.get(new Random().nextInt(this.messages.size() - 1));
        } else {
            return this.messages.get(0);
        }
    }
    
    public Object getObject(String name) {
        return this.config.get("Herobrine." + name);
    }
}
