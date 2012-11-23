package com.nkrecklow.herobrine.api.actions;

import com.nkrecklow.herobrine.Util;
import com.nkrecklow.herobrine.api.Action;

public class EnterNightmare extends Action {

    public EnterNightmare() {
        super(Action.ActionType.ENTER_NIGHTMARE, false);
    }

    @Override
    public void callAction() {
        if (!super.getInstance().getConfiguration().canRunAction("Nightmares")) {
            if (super.getSender() != null) {
                super.getSender().sendMessage(Util.formatString("Nightmares have been disable in the configuration file."));
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
