package com.nkrecklow.herobrine.api.basic;

import com.nkrecklow.herobrine.Main;

public class Generic {

    private Main main;
    
    public Generic(Main main) {
        this.main = main;
    }
    
    public Main getInstance() {
        return this.main;
    }
}
