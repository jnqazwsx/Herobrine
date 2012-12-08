package com.nkrecklow.herobrine.api.actions;

import com.nkrecklow.herobrine.Main;
import com.nkrecklow.herobrine.Util;
import com.nkrecklow.herobrine.api.Action;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class CreateTNTTrap extends Action {

    public CreateTNTTrap() {
        super(Action.ActionType.CREATE_TNT_TRAP, true);
    }
    
    @Override
    public void callAction() {
        if (!Main.getInstance().getConfiguration().canRunAction("CreateTNTTraps")) {
            if (super.hasSender()) {
                super.getSender().sendMessage(Util.formatString("Creating TNT traps has been disable in the configuration file."));
            }
            return;
        }
        Block plate = Util.getNearbyLocation(super.getTarget().getLocation(), 10).getBlock();
        Block ground = plate.getLocation().subtract(0, 1, 0).getBlock();
        Block tnt = ground.getLocation().subtract(0, 1, 0).getBlock();
        if (plate.getType().equals(Material.AIR) && Util.canPlace(ground.getLocation()) && Util.canPlace(tnt.getLocation())) {
            plate.setTypeId(70);
            tnt.setType(Material.TNT);
        } else {
            if (super.hasSender()) {
                super.getSender().sendMessage(Util.formatString("Failed to find a proper TNT trap location."));
            }
            return;
        }
        Main.getInstance().logEvent("Created a TNT trap near " + super.getTarget().getName() + ".");
        if (super.hasSender()) {
            super.getSender().sendMessage(Util.formatString("Created a TNT trap near " + super.getTarget().getName() + "."));
        }
    }
}
