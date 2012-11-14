package com.nkrecklow.herobrine.api;

import com.nkrecklow.herobrine.Main;
import org.bukkit.entity.Player;

public abstract class Action {

    private ActionType type;
    private Player target, sender;
    private Main instance;

    public Action(ActionType type) {
        this.type = type;
    }

    public void prepareAction(Main instance, Player target, Player sender) {
        this.instance = instance;
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
        return this.instance;
    }

    public ActionType getType() {
        return this.type;
    }

    public enum ActionType {

        APPEAR,
        BURY_PLAYER,
        PLACE_SIGN,
        PLACE_TORCH,
        ENTER_NIGHTMARE,
        PLAY_SOUND;
    }
}
