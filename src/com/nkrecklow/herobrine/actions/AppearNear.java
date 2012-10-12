package com.nkrecklow.herobrine.actions;

import com.nkrecklow.herobrine.Main;
import com.nkrecklow.herobrine.api.Action;
import com.nkrecklow.herobrine.api.ActionType;
import java.util.Random;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class AppearNear extends Action {

    public AppearNear() {
        super(ActionType.APPEAR, true);
    }
    
    @Override
    public void onAction(final Main plugin, Player player) {
        World world = player.getWorld();
        Block block = player.getLocation().add(new Random().nextInt(5), 0D, new Random().nextInt(5)).getBlock();
        if (plugin.getController().isDead() && plugin.getController().canSpawn(player.getWorld())) {
            if (block.getType().equals(Material.AIR)) {
                plugin.getController().setTracking(true);
                world.spawnEntity(player.getLocation().add(new Random().nextInt(5), 0D, new Random().nextInt(5)), EntityType.ZOMBIE);
                if (!plugin.getController().isDead()) {
                    plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {

                        @Override
                        public void run() {
                            plugin.getController().getEntity().setHealth(0);
                            plugin.getController().getEntity().remove();
                        }
                    }, 40L);
                    plugin.log("Appeared near " + player.getName() + ".");
                }
            }
        }
    }
}
