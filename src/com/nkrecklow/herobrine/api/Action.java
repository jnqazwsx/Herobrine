package com.nkrecklow.herobrine.api;

import com.nkrecklow.herobrine.Main;
import org.bukkit.entity.Player;

public abstract class Action {

    private ActionType type;
    private boolean isRandom;
    
    public Action(ActionType type, boolean isRandom) {
        this.type = type;
        this.isRandom = isRandom;
    }
    
    public abstract void onAction(Main plugin, Player player);

    public ActionType getActionType() {
        return this.type;
    }

    public boolean isRandom() {
        return this.isRandom;
    }
}
