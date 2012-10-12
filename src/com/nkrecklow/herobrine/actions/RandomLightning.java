package com.nkrecklow.herobrine.actions;

import com.nkrecklow.herobrine.api.Action;
import com.nkrecklow.herobrine.api.ActionType;

public class RandomLightning extends Action {

    public RandomLightning() {
        super(ActionType.RANDOM_LIGHTING);
    }
    
    @Override
    public void onAction() {
        if (!(Boolean) super.getPlugin().getConfiguration().getObject("fireTrails")) {
            return;
        }
        super.getPlayer().getWorld().strikeLightning(super.getPlayer().getLocation().add(super.getRandom().nextInt(5), 0, super.getRandom().nextInt(5)));
        super.getPlugin().log("Lightning struck near " + super.getPlayer().getName() + ".");
    }
}
