package com.nkrecklow.herobrine.actions;

import com.nkrecklow.herobrine.api.Action;
import com.nkrecklow.herobrine.api.ActionType;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class BuryPlayer extends Action {

    public BuryPlayer() {
        super(ActionType.BURY_PLAYER);
    }
    
    @Override
    public void onAction() {
        if (!(Boolean) super.getPlugin().getConfiguration().getObject("modifyWorld")) {
            return;
        }
        final Block top = super.getPlayer().getLocation().subtract(0D, 1D, 0D).getBlock();
        Block middle = top.getLocation().subtract(0D, 1D, 0D).getBlock();
        Block bottom = middle.getLocation().subtract(0D, 1D, 0D).getBlock();
        final Material type = top.getType();
        if (!top.getType().equals(Material.AIR) && !middle.getType().equals(Material.AIR) && !bottom.getType().equals(Material.AIR)) {
            top.setType(Material.AIR);
            middle.setType(Material.AIR);
            bottom.setType(Material.AIR);
            super.getPlugin().getServer().getScheduler().scheduleSyncDelayedTask(super.getPlugin(), new Runnable() {
                
                @Override
                public void run() {
                    top.setType(type);
                }
            }, 60L);
            super.getPlugin().log("Buried " + super.getPlayer().getName() + ".");
        }
    }
}
