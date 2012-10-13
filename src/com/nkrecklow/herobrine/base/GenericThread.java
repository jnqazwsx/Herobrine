package com.nkrecklow.herobrine.base;

import com.nkrecklow.herobrine.Main;

public class GenericThread extends Thread {
    
    public Main main;
    
    public GenericThread(Main main) {
        this.main = main;
    }
}
