package com.nkrecklow.herobrine.actions;

import com.nkrecklow.herobrine.api.Action;
import com.nkrecklow.herobrine.api.ActionType;

public class RandomLightning extends Action {

    public RandomLightning() {
        super(ActionType.RANDOM_LIGHTING);
    }
    
    @Override
    public void onAction() {
        super.getPlayer().getWorld().strikeLightning(super.getPlayer().getLocation().add(super.getRandom().nextInt(5), 0, super.getRandom().nextInt(5)));
    }
}
