package com.nkrecklow.herobrine.api.actions;

import com.nkrecklow.herobrine.Util;
import com.nkrecklow.herobrine.api.Action;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class SuffocatePlayer extends Action {

    public SuffocatePlayer() {
        super(Action.ActionType.SUFFOCATE_PLAYER, true);
    }
    
    @Override
    public void callAction() {
        if (super.getInstance().getConfiguration().canRunAction("SuffocatePlayer")) {
            if (super.getSender() != null) {
                super.getSender().sendMessage(Util.formatString("Suffocating players has been disable in the configuration file."));
            }
            return;
        }
        Block middle = super.getTarget().getLocation().getBlock();
        Block top = middle.getLocation().add(0, 1, 0).getBlock();
        Block bottom = middle.getLocation().subtract(0, 1, 0).getBlock();
        if (middle.getType().equals(Material.AIR) && top.getType().equals(Material.AIR) && super.getInstance().getUtil().canPlace(bottom.getLocation())) {
            middle.setType(Material.SAND);
            top.setType(Material.SAND);
        } else {
            if (super.getSender() != null) {
                super.getSender().sendMessage(Util.formatString("Failed to find a proper location."));
            }
            return;
        }
        if (super.getSender() != null) {
            super.getSender().sendMessage(Util.formatString("Tried to suffocate " + super.getTarget().getName() + "."));
            super.getInstance().logEvent("Tried to suffocate " + super.getTarget().getName() + ".");
        }
    }
}
