package com.nkrecklow.herobrine.actions;

import com.nkrecklow.herobrine.Main;
import com.nkrecklow.herobrine.Util;
import com.nkrecklow.herobrine.events.Action;
import com.nkrecklow.herobrine.events.ActionType;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class BuryPlayer extends Action {

    public BuryPlayer() {
        super(ActionType.BURY_PLAYER, true);
    }

    @Override
    public void onAction(Main main, Player player) {
        if (!(Boolean) main.getConfiguration().getObject("modifyWorld")) {
            return;
        }
        final Block top = player.getLocation().subtract(0D, 1D, 0D).getBlock();
        Block middle = top.getLocation().subtract(0D, 1D, 0D).getBlock();
        Block bottom = middle.getLocation().subtract(0D, 1D, 0D).getBlock();
        final Material type = top.getType();
        if (Util.canPlace(main, top.getLocation()) && Util.canPlace(main, middle.getLocation()) && Util.canPlace(main, bottom.getLocation())) {
            top.setType(Material.AIR);
            middle.setType(Material.AIR);
            bottom.setType(Material.AIR);
            main.getServer().getScheduler().scheduleSyncDelayedTask(main, new Runnable() {
                
                @Override
                public void run() {
                    top.setType(type);
                }
            }, 60L);
            main.log("Buried " + player.getName() + ".", true);
        }
    }
}
