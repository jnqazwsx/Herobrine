package com.nkrecklow.herobrine;

public class Config {

    public int innerChance;
    public Boolean removeMossyCobblestone, changeEnvironment, fireTrails, sendMessages, modifyWorld;
    
    public Config() {
        this.innerChance = 100000;
        this.removeMossyCobblestone = true;
        this.changeEnvironment = true;
        this.fireTrails = true;
        this.sendMessages = true;
        this.modifyWorld = true;
    }
}
