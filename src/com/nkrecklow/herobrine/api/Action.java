package com.nkrecklow.herobrine.api;

import com.nkrecklow.herobrine.Main;
import java.util.Random;
import org.bukkit.entity.Player;

public abstract class Action {

    private Main plugin;
    private Player player;
    private ActionType type;
    private Random random;
    private boolean isRandom;
    
    public Action(ActionType type, boolean isRandom) {
        this.type = type;
        this.random = new Random();
        this.isRandom = isRandom;
    }
    
    public abstract void onAction();
    
    public void setInstances(Main plugin, Player player) {
        this.player = player;
        this.plugin = plugin;
    }

    public Player getPlayer() {
        return this.player;
    }
    
    public Main getPlugin() {
        return this.plugin;
    }

    public ActionType getActionType() {
        return this.type;
    }
    
    public Random getRandom() {
        return this.random;
    }
    
    public boolean isRandom() {
        return this.isRandom;
    }
}
