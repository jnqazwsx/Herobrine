package com.nkrecklow.herobrine.actions;

import com.nkrecklow.herobrine.Main;
import com.nkrecklow.herobrine.api.Action;
import com.nkrecklow.herobrine.api.ActionType;
import java.util.Random;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class RandomFire extends Action {

    public RandomFire() {
        super(ActionType.RANDOM_FIRE, true);
    }
    
    @Override
    public void onAction(Main plugin, Player player) {
        if (!(Boolean) plugin.getConfiguration().getObject("fireTrails")) {
            return;
        }
        Block block = player.getWorld().getBlockAt(player.getLocation().add(new Random().nextInt(5), 0, new Random().nextInt(5)));
        Block ground = player.getWorld().getBlockAt(block.getLocation().subtract(0, 1, 0));
        if (!ground.getType().equals(Material.AIR)) {
            block.setType(Material.FIRE);
        }
        plugin.log("Placed fire near " + player.getName() + ".");
    }
}
