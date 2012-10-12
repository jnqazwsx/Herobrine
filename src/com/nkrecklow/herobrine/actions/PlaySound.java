package com.nkrecklow.herobrine.actions;

import com.nkrecklow.herobrine.api.Action;
import com.nkrecklow.herobrine.api.ActionType;
import org.bukkit.Effect;

public class PlaySound extends Action {

    public PlaySound() {
        super(ActionType.PLAY_SOUND);
    }
    
    @Override
    public void onAction() {
        super.getPlayer().getWorld().playEffect(super.getPlayer().getLocation(), Effect.values()[super.getRandom().nextInt(Effect.values().length - 1)], 5);
        super.getPlugin().log("Played a sound near " + super.getPlayer().getName() + ".");
    }
}
