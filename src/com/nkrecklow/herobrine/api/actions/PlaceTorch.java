package com.nkrecklow.herobrine.api.actions;

import com.nkrecklow.herobrine.Main;
import com.nkrecklow.herobrine.Util;
import com.nkrecklow.herobrine.api.Action;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class PlaceTorch extends Action {

    public PlaceTorch() {
        super(Action.ActionType.PLACE_TORCH, true);
    }

    @Override
    public void callAction() {
        if (!Main.getInstance().getConfiguration().canRunAction("PlaceTorches")) {
            if (super.getSender() != null) {
                super.getSender().sendMessage(Util.formatString("Placing torches has been disable in the configuration file."));
            }
            return;
        }
        Block torch = Util.getNearbyLocation(super.getTarget().getLocation(), 5).getBlock();
        Block below = torch.getLocation().subtract(0D, 1D, 0D).getBlock();
        if (torch.getType().equals(Material.AIR) && Util.canPlace(below.getLocation())) {
            torch.setType(Material.REDSTONE_TORCH_ON);
            Main.getInstance().logEvent("Placed a torch near " + super.getTarget().getName() + ".");
            if (super.getSender() != null) {
                super.getSender().sendMessage(Util.formatString("Placed a torch near " + super.getTarget().getName() + "."));
            }
        } else {
            if (super.getSender() != null) {
                super.getSender().sendMessage(Util.formatString("Failed to find a proper torch location."));
            }
        }
    }
}
