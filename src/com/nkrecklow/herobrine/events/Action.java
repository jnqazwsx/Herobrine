package com.nkrecklow.herobrine.events;

import com.nkrecklow.herobrine.Main;
import org.bukkit.entity.Player;

public abstract class Action {

    private ActionType type;

    public Action(ActionType type) {
        this.type = type;
    }
    
    public abstract void onAction(Main main, Player player);

    public ActionType getActionType() {
        return this.type;
    }
}
