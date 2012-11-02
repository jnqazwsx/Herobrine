package com.nkrecklow.herobrine.actions;

import com.nkrecklow.herobrine.events.Action;
import com.nkrecklow.herobrine.events.ActionType;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class BuryPlayer extends Action {

    public BuryPlayer() {
        super(ActionType.BURY_PLAYER);
    }

    @Override
    public void callAction() {
        if (!(Boolean) super.getInstance().getConfiguration().getObject("modifyWorld")) {
            super.getSender().sendMessage(super.getInstance().getUtil().addPluginName("Can't modify the world!"));
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
            super.getSender().sendMessage(super.getInstance().getUtil().addPluginName("Buried " + super.getTarget().getName() + "."));
        } else {
            super.getSender().sendMessage(super.getInstance().getUtil().addPluginName("Failed to find a proper bury location."));
        }
    }
}
