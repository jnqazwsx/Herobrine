package com.nkrecklow.herobrine.api.actions;

import com.nkrecklow.herobrine.Util;
import com.nkrecklow.herobrine.api.Action;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class TNTTrap extends Action {

    public TNTTrap() {
        super(Action.ActionType.CREATE_TNT_TRAP, true);
    }
    
    @Override
    public void callAction() {
        if (!super.getInstance().getConfiguration().canRunAction("TNTTrap")) {
            if (super.getSender() != null) {
                super.getSender().sendMessage(Util.formatString("TNT traps has been disable in the configuration file."));
            }
            return;
        }
        Block plate = super.getInstance().getUtil().getNearbyLocation(super.getTarget().getLocation(), 10).getBlock();
        Block ground = plate.getLocation().subtract(0, 1, 0).getBlock();
        Block tnt = ground.getLocation().subtract(0, 1, 0).getBlock();
        if (plate.getType().equals(Material.AIR) && super.getInstance().getUtil().canPlace(ground.getLocation()) && super.getInstance().getUtil().canPlace(tnt.getLocation())) {
            plate.setTypeId(70);
            tnt.setType(Material.TNT);
        } else {
            if (super.getSender() != null) {
                super.getSender().sendMessage(Util.formatString("Failed to find a proper TNT trap location."));
            }
            return;
        }
        if (super.getSender() != null) {
            super.getSender().sendMessage(Util.formatString("Created a TNT trap near " + super.getTarget().getName() + "."));
            super.getInstance().logEvent("Created a TNT trap near " + super.getTarget().getName() + ".");
        }
    }
}
