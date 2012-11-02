package com.nkrecklow.herobrine.base;

import com.nkrecklow.herobrine.Main;

public class GenericThread extends Thread {
    
    private Main main;
    
    public GenericThread(Main main) {
        this.main = main;
    }
    
    public Main getInstance() {
        return this.main;
    }
}
