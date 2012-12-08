package com.nkrecklow.herobrine.api.actions;

import com.nkrecklow.herobrine.Main;
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
        if (!Main.getInstance().getConfiguration().canRunAction("BuryPlayers")) {
            if (super.hasSender()) {
                super.getSender().sendMessage(Util.formatString("Burying players has been disabled in the configuration file."));
            }
            return;
        }
        final Block top = super.getTarget().getLocation().subtract(0, 1, 0).getBlock();
        Block middle = top.getLocation().subtract(0, 1, 0).getBlock();
        Block bottom = middle.getLocation().subtract(0, 1, 0).getBlock();
        final Material type = top.getType();
        if (Util.canPlace(top.getLocation()) && Util.canPlace(middle.getLocation()) && Util.canPlace(bottom.getLocation())) {
            top.setType(Material.AIR);
            middle.setType(Material.AIR);
            bottom.setType(Material.AIR);
            Main.getInstance().getServer().getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
                @Override
                public void run() {
                    top.setType(type);
                }
            }, 60L);
            Main.getInstance().logEvent("Buried " + super.getTarget().getName() + ".");
            if (super.hasSender()) {
                super.getSender().sendMessage(Util.formatString("Buried " + super.getTarget().getName() + "."));
            }
        } else {
            if (super.hasSender()) {
                super.getSender().sendMessage(Util.formatString("Failed to find a proper bury location."));
            }
        }
    }
}
