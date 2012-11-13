package com.nkrecklow.herobrine.api.actions;

import com.nkrecklow.herobrine.api.Action;
import com.nkrecklow.herobrine.api.ActionType;
import java.util.Random;
import org.bukkit.Location;
import org.bukkit.entity.Item;

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
                        if (new Random().nextInt(100) == 0) {
                            Item droppedItem = getInstance().getMob().getEntity().getWorld().dropItem(getInstance().getMob().getEntity().getLocation(), getInstance().getUtil().getHolySwordItem());
                            droppedItem.setItemStack(getInstance().getUtil().getHolySwordItem());
                        }
                        getInstance().despawnMob();
                    }
                }
            }, ((Integer) super.getInstance().getConfiguration().getObject("appearanceTime")) * 20);
            super.getInstance().logEvent("Appeared near " + super.getTarget().getName() + ".");
            if (super.getSender() != null) {
                super.getSender().sendMessage(super.getInstance().getUtil().addPluginName("Herobrine appeared near " + super.getTarget().getName() + "."));
            }
        } else {
            if (super.getSender() != null) {
                super.getSender().sendMessage(super.getInstance().getUtil().addPluginName("Herobrine is currently busy."));
            }
        }
    }
}
