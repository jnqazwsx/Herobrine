package com.nkrecklow.herobrine.base;

import com.nkrecklow.herobrine.Plugin;

public abstract class GenericThread extends Thread {

    private Plugin plugin;

    public GenericThread(Plugin plugin) {
        this.plugin = plugin;
    }
    
    @Override
    public abstract void run();

    public Plugin getPlugin() {
        return this.plugin;
    }
}
