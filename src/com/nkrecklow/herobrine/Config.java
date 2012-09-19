package com.nkrecklow.herobrine;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class Config {

    public int innerChance;
    public Boolean removeMossyCobblestone, changeEnvironment, fireTrails, sendMessages, modifyWorld;
    private Herobrine plugin;
    
    public Config(Herobrine plugin) {
        this.plugin = plugin;
        this.innerChance = 100000;
        this.removeMossyCobblestone = true;
        this.changeEnvironment = true;
        this.fireTrails = true;
        this.sendMessages = true;
        this.modifyWorld = true;
        String mainDirectory = "plugins/Herobrine";
        File configFile = new File(mainDirectory + File.separator + "Settings.properties");
        Properties settingsFile = new Properties();
        new File(mainDirectory).mkdir();
        if (!configFile.exists()) {
            try {
                configFile.createNewFile();
                FileOutputStream out = new FileOutputStream(configFile);
                settingsFile.put("modify-world", Boolean.toString(this.modifyWorld));
                settingsFile.put("send-messages", Boolean.toString(this.sendMessages));
                settingsFile.put("change-environment", Boolean.toString(this.changeEnvironment));
                settingsFile.put("remove-mossystone", Boolean.toString(this.removeMossyCobblestone));
                settingsFile.put("action-chance", Integer.toString(this.innerChance));
                settingsFile.put("fire-trails", Boolean.toString(this.fireTrails));
                settingsFile.store(out, "Configuration file for Herobrine 2.2");
            } catch (IOException ex) {
                
            }
        } else {
            try {
                FileInputStream in = new FileInputStream(configFile);
                try {
                    settingsFile.load(in);
                    this.modifyWorld = Boolean.valueOf(settingsFile.getProperty("modify-world"));
                    this.sendMessages = Boolean.valueOf(settingsFile.getProperty("send-messages"));
                    this.changeEnvironment = Boolean.valueOf(settingsFile.getProperty("change-environment"));
                    this.removeMossyCobblestone = Boolean.valueOf(settingsFile.getProperty("remove-mossystone"));
                    this.innerChance = Integer.parseInt(settingsFile.getProperty("action-chance"));
                    this.fireTrails = Boolean.valueOf(settingsFile.getProperty("fire-trails"));
                } catch (IOException ex) {
                    this.plugin.getServer().getPluginManager().disablePlugin(this.plugin);
                }
            } catch (FileNotFoundException ex) {
                this.plugin.getServer().getPluginManager().disablePlugin(this.plugin);
            }
        }
    }
}
