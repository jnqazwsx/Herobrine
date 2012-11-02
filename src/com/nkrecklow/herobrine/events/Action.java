package com.nkrecklow.herobrine.events;

import com.nkrecklow.herobrine.Main;
import org.bukkit.entity.Player;

public abstract class Action {

    private ActionType type;
    private Player target, sender;
    private Main main;
    
    public Action(ActionType type) {
        this.type = type;
    }
    
    public void prepareAction(Main main, Player target, Player sender) {
        this.main = main;
        this.target = target;
        this.sender = sender;
    }
    
    public abstract void callAction();
    
    public Player getTarget() {
        return this.target;
    }
    
    public Player getSender() {
        return this.sender;
    }
    
    public Main getInstance() {
        return this.main;
    }

    public ActionType getType() {
        return this.type;
    }
}
