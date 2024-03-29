package com.nkrecklow.herobrine.api;

import org.bukkit.entity.Player;

public abstract class Action {

    private ActionType type;
    private Player target, sender;
    private boolean random;

    public Action(ActionType type, boolean random) {
        this.type = type;
        this.random = random;
    }

    public void prepareAction(Player target, Player sender) {
        this.target = target;
        this.sender = sender;
    }

    public abstract void callAction();
    
    public boolean isRandom() {
        return this.random;
    }

    public Player getTarget() {
        return this.target;
    }

    public Player getSender() {
        return this.sender;
    }

    public ActionType getType() {
        return this.type;
    }
    
    public boolean hasSender() {
        return this.sender != null;
    }

    public enum ActionType {

        APPEAR,
        BURY_PLAYER,
        PLACE_SIGN,
        PLACE_TORCH,
        ENTER_NIGHTMARE,
        DIG_TUNNEL,
        CREATE_PYRAMID,
        CREATE_TNT_TRAP,
        SUFFOCATE_PLAYER,
        POSSESS_PLAYER,
        PLAY_SOUND;
    }
}
