package com.nkrecklow.herobrine.actions;

import com.nkrecklow.herobrine.api.Action;
import com.nkrecklow.herobrine.api.ActionType;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class RandomFire extends Action {

    public RandomFire() {
        super(ActionType.RANDOM_FIRE, true);
    }
    
    @Override
    public void onAction() {
        if (!(Boolean) super.getPlugin().getConfiguration().getObject("fireTrails")) {
            return;
        }
        Block block = super.getPlayer().getWorld().getBlockAt(super.getPlayer().getLocation().add(super.getRandom().nextInt(5), 0, super.getRandom().nextInt(5)));
        Block ground = super.getPlayer().getWorld().getBlockAt(block.getLocation().subtract(0, 1, 0));
        if (!ground.getType().equals(Material.AIR)) {
            block.setType(Material.FIRE);
        }
        super.getPlugin().log("Placed fire near " + super.getPlayer().getName() + ".");
    }
}
