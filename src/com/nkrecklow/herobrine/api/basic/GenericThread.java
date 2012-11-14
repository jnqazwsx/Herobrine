package com.nkrecklow.herobrine.api.basic;

import com.nkrecklow.herobrine.Main;

public class GenericThread extends Thread {
    
    private Main instance;
    
    public GenericThread(Main instance) {
        this.instance = instance;
    }
    
    public Main getInstance() {
        return this.instance;
    }
}
