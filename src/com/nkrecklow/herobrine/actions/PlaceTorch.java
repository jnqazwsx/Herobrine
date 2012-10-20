package com.nkrecklow.herobrine.actions;

import com.nkrecklow.herobrine.Main;
import com.nkrecklow.herobrine.Util;
import com.nkrecklow.herobrine.events.Action;
import com.nkrecklow.herobrine.events.ActionType;
import java.util.Random;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class PlaceTorch extends Action {

    public PlaceTorch() {
        super(ActionType.PLACE_TORCH, true);
    }
    
    @Override
    public void onAction(Main main, Player player) {
        if (!(Boolean) main.getConfiguration().getObject("modifyWorld")) {
            return;
        }
        Block torch = player.getLocation().add(new Random().nextInt(5), 0D, new Random().nextInt(5)).getBlock();
        Block below = torch.getLocation().subtract(0D, 1D, 0D).getBlock();
        if (torch.getType().equals(Material.AIR) && Util.canPlace(main, below.getLocation())) {
            torch.setType(Material.REDSTONE_TORCH_ON);
            main.log("Placed a torch near " + player.getName() + ".", true);
        }
    }
}
