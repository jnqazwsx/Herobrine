package com.nkrecklow.herobrine.api.actions;

import com.nkrecklow.herobrine.Util;
import com.nkrecklow.herobrine.api.Action;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class BuryPlayer extends Action {

    public BuryPlayer() {
        super(Action.ActionType.BURY_PLAYER, true);
    }

    @Override
    public void callAction() {
        if (!super.getInstance().getConfiguration().canRunAction("BuryPlayer")) {
            if (super.getSender() != null) {
                super.getSender().sendMessage(Util.formatString("Burying players has been disabled in the configuration file."));
            }
            return;
        }
        final Block top = super.getTarget().getLocation().subtract(0D, 1D, 0D).getBlock();
        Block middle = top.getLocation().subtract(0D, 1D, 0D).getBlock();
        Block bottom = middle.getLocation().subtract(0D, 1D, 0D).getBlock();
        final Material type = top.getType();
        if (super.getInstance().getUtil().canPlace(top.getLocation()) && super.getInstance().getUtil().canPlace(middle.getLocation()) && super.getInstance().getUtil().canPlace(bottom.getLocation())) {
            top.setType(Material.AIR);
            middle.setType(Material.AIR);
            bottom.setType(Material.AIR);
            super.getInstance().getServer().getScheduler().scheduleSyncDelayedTask(super.getInstance(), new Runnable() {
                @Override
                public void run() {
                    top.setType(type);
                }
            }, 60L);
            super.getInstance().logEvent("Buried " + super.getTarget().getName() + ".");
            if (super.getSender() != null) {
                super.getSender().sendMessage(Util.formatString("Buried " + super.getTarget().getName() + "."));
            }
        } else {
            if (super.getSender() != null) {
                super.getSender().sendMessage(Util.formatString("Failed to find a proper bury location."));
            }
        }
    }
}
