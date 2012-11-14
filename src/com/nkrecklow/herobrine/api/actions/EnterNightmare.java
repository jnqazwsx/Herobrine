package com.nkrecklow.herobrine.api.actions;

import com.nkrecklow.herobrine.Util;
import com.nkrecklow.herobrine.api.Action;

public class EnterNightmare extends Action {

    public EnterNightmare() {
        super(Action.ActionType.ENTER_NIGHTMARE);
    }

    @Override
    public void callAction() {
        if (!super.getInstance().getWorldGenerator().isEnabled()) {
            if (super.getSender() != null) {
                super.getSender().sendMessage(Util.formatString("Sorry, the \"Nightmare World\" has been disabled in the configuration!"));
            }
            return;
        }
        if (!super.getInstance().getWorldGenerator().exists()) {
            if (super.getSender() != null) {
                super.getSender().sendMessage(Util.formatString("Sorry, the \"Nightmare World\" hasn't been generated, restart the server to generate it!"));
            }
            return;
        }
        super.getTarget().teleport(super.getInstance().getWorldGenerator().getWorld().getSpawnLocation());
        super.getInstance().logEvent("Teleported \"" + super.getTarget().getName() + "\" to the \"Nightmare World\"!");
        super.getTarget().sendMessage(Util.formatString("Welcome, " + super.getTarget().getName() + " to the \"Nightmare World\"!"));
        if (super.getSender() != null) {
            super.getSender().sendMessage(Util.formatString("Teleported \"" + super.getTarget().getName() + "\" to the \"Nightmare World\"!"));
        }
    }
}
