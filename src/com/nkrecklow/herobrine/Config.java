package com.nkrecklow.herobrine;

import com.nkrecklow.herobrine.base.Generic;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;

public class Config extends Generic {

    private int actionChance, originalActionChance;
    private List<String> messages, allowedWorlds, signMessages, bookMessages, allowedBlocks;
    private YamlConfiguration config;
    
    public Config(Main plugin) {
        super(plugin);
        this.messages = new ArrayList<String>();
        this.allowedWorlds = new ArrayList<String>();
        this.signMessages = new ArrayList<String>();
        this.bookMessages = new ArrayList<String>();
        this.allowedBlocks = new ArrayList<String>();
    }
    
    public void loadConfig() {
        try {
            File dir = super.main.getDataFolder();
            File file = new File(dir + "/config.yml");
            if (!dir.exists()) {
                if (!dir.mkdir()) {
                    super.main.log("Failed to create directory!", false);
                }
            }
            if (file.exists()) {
                this.config = YamlConfiguration.loadConfiguration(file);
                this.loadSettings();
            } else {
                super.main.saveResource("config.yml", false);
            }
            if (this.config == null) {
                this.config = YamlConfiguration.loadConfiguration(file);
                this.loadSettings();
            }
        } catch (Exception ex) {
            super.main.log("Failed to load configuration file!", false);
            super.main.log("Error: " + ex.getMessage(), false);
        }
    }
    
    private void loadSettings() throws Exception {
        boolean disable = false;
        if (!super.main.getDescription().getVersion().equals(this.config.getString("Herobrine.configBuild"))) {
            super.main.log("Outdated configuration file! Please delete it and restart!", false);
            disable = true;
        }
        this.actionChance = this.config.getInt("Herobrine.actionChance");
        this.originalActionChance = this.actionChance;
        this.messages = this.config.getStringList("Herobrine.messages");
        this.signMessages = this.config.getStringList("Herobrine.signMessages");
        this.allowedWorlds = this.config.getStringList("Herobrine.allowedWorlds");
        this.bookMessages = this.config.getStringList("Herobrine.bookMessages");
        this.allowedBlocks = this.config.getStringList("Herobrine.allowedBlocks");
        if (this.allowedBlocks.isEmpty()) {
            super.main.log("Must have atleast one allowed block!", false);
            disable = true;
        }
        if (this.bookMessages.isEmpty()) {
            super.main.log("Must have atleast one book message!", false);
            disable = true;
        }
        if (this.signMessages.isEmpty()) {
            super.main.log("Must have atleast one sign message!", false);
            disable = true;
        }
        if (this.allowedWorlds.isEmpty()) {
            super.main.log("Must be allowed in atleast one world!", false);
            disable = true;
        }
        if (disable) {
            super.main.log("Because of a startup error, I am disabling.", false);
            super.main.getServer().getPluginManager().disablePlugin(super.main);
        }
    }
    
    public void setActionChance(int actionChance) {
        this.actionChance = actionChance;
    }
    
    public boolean canSendMessages() {
        return (Boolean) this.getObject("sendMessages") && this.messages.size() > 0;
    }
    
    public List<String> getAllowedWorlds() {
        return this.allowedWorlds;
    }
    
    public int getOriginalActionChance() {
        return this.originalActionChance;
    }
    
    public int getActionChance() {
        return this.actionChance;
    }
    
    public List<Material> getAllowedBlocks() {
        List<Material> mats = new ArrayList<Material>();
        for (String item : this.allowedBlocks) {
            mats.add(Material.getMaterial(Integer.parseInt(item)));
        }
        return mats;
    }
    
    public String getBookMessage() {
        if (this.bookMessages.size() > 1) {
            return this.bookMessages.get(new Random().nextInt(this.bookMessages.size() - 1));
        } else {
            return this.bookMessages.get(0);
        }
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

    public Object getObject(String name) {
        try {
            return this.config.get("Herobrine." + name);
        } catch (Exception ex) {
            super.main.log("You need to delete Herobrine's configuration file and restart/reload!", false);
            super.main.getServer().getPluginManager().disablePlugin(super.main);
            return null;
        }
    }
}
