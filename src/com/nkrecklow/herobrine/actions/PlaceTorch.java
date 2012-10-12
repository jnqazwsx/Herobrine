package com.nkrecklow.herobrine.actions;

import com.nkrecklow.herobrine.api.Action;
import com.nkrecklow.herobrine.api.ActionType;
import java.util.Random;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class PlaceTorch extends Action {

    public PlaceTorch() {
        super(ActionType.PLACE_TORCH, true);
    }
    
    @Override
    public void onAction() {
        if (!(Boolean) super.getPlugin().getConfiguration().getObject("modifyWorld")) {
            return;
        }
        Block torch = super.getPlayer().getLocation().add(super.getRandom().nextInt(5), 0D, super.getRandom().nextInt(5)).getBlock();
        Block groundBlock = torch.getLocation().subtract(0D, 1D, 0D).getBlock();
        if (torch.getType().equals(Material.AIR) && !groundBlock.getType().equals(Material.AIR)) {
            if (new Random().nextBoolean()) {
                torch.setType(Material.REDSTONE_TORCH_ON);
            } else {
                torch.setType(Material.TORCH);
            }
            super.getPlugin().log("Placed a torch near " + super.getPlayer().getName() + ".");
        }
    }
}
