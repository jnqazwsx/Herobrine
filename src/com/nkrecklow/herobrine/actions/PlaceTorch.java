package com.nkrecklow.herobrine.actions;

import com.nkrecklow.herobrine.Main;
import com.nkrecklow.herobrine.api.Action;
import com.nkrecklow.herobrine.api.ActionType;
import java.util.Random;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class PlaceTorch extends Action {

    public PlaceTorch() {
        super(ActionType.PLACE_TORCH, true);
    }
    
    @Override
    public void onAction(Main plugin, Player player) {
        if (!(Boolean) plugin.getConfiguration().getObject("modifyWorld")) {
            return;
        }
        Block torch = player.getLocation().add(new Random().nextInt(5), 0D, new Random().nextInt(5)).getBlock();
        Block groundBlock = torch.getLocation().subtract(0D, 1D, 0D).getBlock();
        if (torch.getType().equals(Material.AIR) && !groundBlock.getType().equals(Material.AIR)) {
            if (new Random().nextBoolean()) {
                torch.setType(Material.REDSTONE_TORCH_ON);
            } else {
                torch.setType(Material.TORCH);
            }
            plugin.log("Placed a torch near " + player.getName() + ".");
        }
    }
}
