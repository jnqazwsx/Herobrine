package com.nkrecklow.herobrine.actions;

import com.nkrecklow.herobrine.Util;
import com.nkrecklow.herobrine.events.Action;
import com.nkrecklow.herobrine.events.ActionType;
import java.util.Random;
import org.bukkit.Location;

public class AppearNear extends Action {

    public AppearNear() {
        super(ActionType.APPEAR);
    }

    @Override
    public void callAction() {
        if (!super.getInstance().isSpawned()) {
            Location loc = super.getInstance().getUtil().getLocationInFrontOfPlayer(super.getTarget(), new Random().nextInt(10) + 3);
            loc.setY(super.getTarget().getWorld().getHighestBlockYAt(loc));
            super.getInstance().spawnMob(loc);
            super.getInstance().getMob().setTarget(super.getTarget().getName());
            super.getInstance().getMob().lookAtPlayer(super.getTarget());
            super.getInstance().getServer().getScheduler().scheduleSyncDelayedTask(super.getInstance(), new Runnable() {

                @Override
                public void run() {
                    if (getInstance().isSpawned()) {
                        getInstance().despawnMob();
                    }
                }
            }, ((Integer) super.getInstance().getConfiguration().getObject("appearanceTime")) * 20);
            super.getInstance().logEvent("Appeared near " + super.getTarget().getName() + ".");
            super.getSender().sendMessage(super.getInstance().getUtil().addPluginName("Herobrine appeared near " + super.getTarget().getName() + "."));
        } else {
            super.getSender().sendMessage(super.getInstance().getUtil().addPluginName("Herobrine is currently busy."));
        }
    }
}
