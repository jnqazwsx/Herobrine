package com.nkrecklow.herobrine.api.basic;

import com.nkrecklow.herobrine.Main;

public class Generic {

    private Main instance;
    
    public Generic(Main instance) {
        this.instance = instance;
    }
    
    public Main getInstance() {
        return this.instance;
    }
}
