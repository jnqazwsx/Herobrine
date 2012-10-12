package com.nkrecklow.herobrine.actions;

import com.nkrecklow.herobrine.api.Action;
import com.nkrecklow.herobrine.api.ActionType;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;

public class AppearNear extends Action {

    public AppearNear() {
        super(ActionType.APPEAR, true);
    }
    
    @Override
    public void onAction() {
        World world = super.getPlayer().getWorld();
        Block block = super.getPlayer().getLocation().add(super.getRandom().nextInt(5), 0D, super.getRandom().nextInt(5)).getBlock();
        if (super.getPlugin().getController().isDead() && super.getPlugin().getController().canSpawn(super.getPlayer().getWorld())) {
            if (block.getType().equals(Material.AIR)) {
                super.getPlugin().getController().setTracking(true);
                world.spawnEntity(super.getPlayer().getLocation().add(super.getRandom().nextInt(5), 0D, super.getRandom().nextInt(5)), EntityType.ZOMBIE);
                if (!super.getPlugin().getController().isDead()) {
                    super.getPlugin().getServer().getScheduler().scheduleSyncDelayedTask(super.getPlugin(), new Runnable() {

                        @Override
                        public void run() {
                            getPlugin().getController().getEntity().setHealth(0);
                            getPlugin().getController().getEntity().remove();
                        }
                    }, 40L);
                    super.getPlugin().log("Appeared near " + super.getPlayer().getName() + ".");
                }
            }
        }
    }
}
