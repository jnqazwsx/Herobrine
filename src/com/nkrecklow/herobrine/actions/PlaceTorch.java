package com.nkrecklow.herobrine.actions;

import com.nkrecklow.herobrine.events.Action;
import com.nkrecklow.herobrine.events.ActionType;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class PlaceTorch extends Action {

    public PlaceTorch() {
        super(ActionType.PLACE_TORCH);
    }

    @Override
    public void callAction() {
        if (!(Boolean) super.getInstance().getConfiguration().getObject("modifyWorld")) {
            if (super.getSender() != null) {
                super.getSender().sendMessage(super.getInstance().getUtil().addPluginName("Can't modify that world (\"" + super.getTarget().getWorld().getName() + "\")."));
            }
            return;
        }
        Block torch = super.getInstance().getUtil().getNearbyLocation(super.getTarget().getLocation()).getBlock();
        Block below = torch.getLocation().subtract(0D, 1D, 0D).getBlock();
        if (torch.getType().equals(Material.AIR) && super.getInstance().getUtil().canPlace(below.getLocation())) {
            torch.setType(Material.REDSTONE_TORCH_ON);
            super.getInstance().logEvent("Placed a torch near " + super.getTarget().getName() + ".");
            if (super.getSender() != null) {
                super.getSender().sendMessage(super.getInstance().getUtil().addPluginName("Placed a torch near " + super.getTarget().getName() + "."));
            }
        } else {
            if (super.getSender() != null) {
                super.getSender().sendMessage(super.getInstance().getUtil().addPluginName("Failed to find a proper torch location."));
            }
        }
    }
}
