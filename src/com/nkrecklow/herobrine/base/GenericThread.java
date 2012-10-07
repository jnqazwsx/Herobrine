package com.nkrecklow.herobrine.base;

import com.nkrecklow.herobrine.Main;

public abstract class GenericThread extends Thread {

    private Main plugin;

    public GenericThread(Main plugin) {
        this.plugin = plugin;
    }
    
    @Override
    public abstract void run();

    public Main getPlugin() {
        return this.plugin;
    }
}
