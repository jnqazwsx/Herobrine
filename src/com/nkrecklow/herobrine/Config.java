package com.nkrecklow.herobrine;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

public class Config extends Generic {

    private int actionChance;
    private List<String> messages, allowedWorlds, signMessages;
    private YamlConfiguration config;
    private ItemStack drop;
    
    public Config(Plugin plugin) {
        super(plugin);
        this.messages = new ArrayList<String>();
        this.allowedWorlds = new ArrayList<String>();
        this.signMessages = new ArrayList<String>();
    }
    
    public void loadConfig() {
        try {
            File dir = super.getPlugin().getDataFolder();
            File file = new File(dir + "/config.yml");
            if (!dir.exists()) {
                if (!dir.mkdir()) {
                    super.getPlugin().log("Failed to create directory!");
                    super.getPlugin().getServer().getPluginManager().disablePlugin(super.getPlugin());
                }
            }
            if (file.exists()) {
                this.config = YamlConfiguration.loadConfiguration(file);
                if (!super.getPlugin().getDescription().getVersion().equals(config.getString("Herobrine.configBuild"))) {
                    super.getPlugin().log("Outdated configuration file! Please delete it and restart!");
                    super.getPlugin().getServer().getPluginManager().disablePlugin(super.getPlugin());
                }
                this.actionChance = this.config.getInt("Herobrine.actionChance");
                this.messages = this.config.getStringList("Herobrine.messages");
                this.signMessages = this.config.getStringList("Herobrine.signMessages");
                this.allowedWorlds = this.config.getStringList("Herobrine.allowedWorlds");
                String dropString = this.config.getString("Herobrine.deathDrop");
                if (dropString.contains(":")) {
                    this.drop = new ItemStack(Integer.parseInt(dropString.split(":")[0]), Integer.parseInt(dropString.split(":")[1]));
                } else {
                    super.getPlugin().log("Invalid death item drop!");
                    super.getPlugin().getServer().getPluginManager().disablePlugin(super.getPlugin());
                }
                if (this.signMessages.isEmpty()) {
                    super.getPlugin().log("Must have atleast one sign message!");
                    super.getPlugin().getServer().getPluginManager().disablePlugin(super.getPlugin());
                }
                if (this.allowedWorlds.isEmpty()) {
                    super.getPlugin().log("Must be allowed in atleast one world!");
                    super.getPlugin().getServer().getPluginManager().disablePlugin(super.getPlugin());
                }
            } else {
                super.getPlugin().saveResource("config.yml", false);
            }
        } catch (Exception ex) {
            super.getPlugin().log("Failed to load configuration file!");
            super.getPlugin().log("Error: " + ex.getMessage());
            super.getPlugin().getServer().getPluginManager().disablePlugin(super.getPlugin());
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
    
    public String getSignMessage() {
        if (this.signMessages.size() > 1) {
            return this.signMessages.get(new Random().nextInt(this.signMessages.size() - 1));
        } else {
            return this.signMessages.get(0);
        }
    }
    
    public ItemStack getDrop() {
        return this.drop;
    }
    
    public Object getObject(String name) {
        return this.config.get("Herobrine." + name);
    }
}
