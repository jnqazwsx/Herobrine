package com.nkrecklow.herobrine;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;

public class Config {

    private int actionChance;
    private List<String> messages, allowedWorlds, signMessages, bookMessages, allowedBlocks, actions;
    private YamlConfiguration config;
    
    public Config() {
        this.messages = new ArrayList<String>();
        this.allowedWorlds = new ArrayList<String>();
        this.signMessages = new ArrayList<String>();
        this.bookMessages = new ArrayList<String>();
        this.allowedBlocks = new ArrayList<String>();
        this.actions = new ArrayList<String>();
    }
    
    public void loadConfig() {
        try {
            File dir = Main.getInstance().getDataFolder();
            File file = new File(dir + "/config.yml");
            if (!dir.exists()) {
                if (!dir.mkdir()) {
                    Main.getInstance().log("Failed to create directory!");
                }
            }
            if (file.exists()) {
                this.config = YamlConfiguration.loadConfiguration(file);
                this.loadSettings();
            } else {
                Main.getInstance().saveResource("config.yml", false);
            }
            if (this.config == null) {
                this.config = YamlConfiguration.loadConfiguration(file);
                this.loadSettings();
            }
        } catch (Exception ex) {
            Main.getInstance().log("Error: " + ex.getMessage());
        }
    }
    
    private void loadSettings() throws Exception {
        String error = "";
        if (!Main.getInstance().getDescription().getVersion().equals(this.config.getString("Herobrine.configBuild"))) {
            error = "Outdated configuration file! Please delete it and restart!";
        }
        this.actionChance = this.config.getInt("Herobrine.actionChance");
        this.messages = this.config.getStringList("Herobrine.messages");
        this.signMessages = this.config.getStringList("Herobrine.signMessages");
        this.allowedWorlds = this.config.getStringList("Herobrine.allowedWorlds");
        this.bookMessages = this.config.getStringList("Herobrine.bookMessages");
        this.allowedBlocks = this.config.getStringList("Herobrine.allowedBlocks");
        this.actions = this.config.getStringList("Herobrine.allowedActions");
        if (this.actions.isEmpty()) {
            error = "Must be allowed atleast one action!";
        }
        if (this.allowedBlocks.isEmpty()) {
            error = "Must have atleast one allowed block!";
        }
        if (this.bookMessages.isEmpty()) {
            error = "Must have atleast one book message!";
        }
        if (this.signMessages.isEmpty()) {
            error = "Must have atleast one sign message!";
        }
        if (this.allowedWorlds.isEmpty()) {
            error = "Must be allowed in atleast one world!";
        }
        if (!error.equals("")) {
            Main.getInstance().getServer().getPluginManager().disablePlugin(Main.getInstance());
        }
    }

    public List<String> getAllowedWorlds() {
        return this.allowedWorlds;
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
            Main.getInstance().log("You need to delete Herobrine's configuration file and restart/reload!");
            Main.getInstance().getServer().getPluginManager().disablePlugin(Main.getInstance());
            return null;
        }
    }
    
    public boolean canRunAction(String name) {
        return this.actions.contains(name);
    }
}
