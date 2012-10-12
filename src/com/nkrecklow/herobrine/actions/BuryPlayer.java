package com.nkrecklow.herobrine.actions;

import com.nkrecklow.herobrine.Main;
import com.nkrecklow.herobrine.api.Action;
import com.nkrecklow.herobrine.api.ActionType;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class BuryPlayer extends Action {

    public BuryPlayer() {
        super(ActionType.BURY_PLAYER, true);
    }
    
    @Override
    public void onAction(Main plugin, Player player) {
        if (!(Boolean) plugin.getConfiguration().getObject("modifyWorld")) {
            return;
        }
        final Block top = player.getLocation().subtract(0D, 1D, 0D).getBlock();
        Block middle = top.getLocation().subtract(0D, 1D, 0D).getBlock();
        Block bottom = middle.getLocation().subtract(0D, 1D, 0D).getBlock();
        final Material type = top.getType();
        if (!top.getType().equals(Material.AIR) && !middle.getType().equals(Material.AIR) && !bottom.getType().equals(Material.AIR)) {
            top.setType(Material.AIR);
            middle.setType(Material.AIR);
            bottom.setType(Material.AIR);
            plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                
                @Override
                public void run() {
                    top.setType(type);
                }
            }, 60L);
            plugin.log("Buried " + player.getName() + ".");
        }
    }
}
