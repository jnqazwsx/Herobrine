package com.nkrecklow.herobrine.actions;

import com.nkrecklow.herobrine.Main;
import com.nkrecklow.herobrine.api.Action;
import com.nkrecklow.herobrine.api.ActionType;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class RandomWalls extends Action {

    public RandomWalls() {
        super(ActionType.RANDOM_WALLS, true);
    }
    
    @Override
    public void onAction(Main plugin, Player player) {
        for (int x = -1; x < 2; x++) {
            Block block = player.getWorld().getBlockAt(player.getLocation().add(x, 0, player.getLocation().getBlockZ() + 1));
            if (block.getType().equals(Material.AIR)) {
                this.setPillar(block, Material.SOUL_SAND);
            }
        }
    }
    
    private void setPillar(Block block, Material type) {
        block.setType(type);
    }
}
