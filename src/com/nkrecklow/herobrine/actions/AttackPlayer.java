package com.nkrecklow.herobrine.actions;

import com.nkrecklow.herobrine.events.Action;
import com.nkrecklow.herobrine.events.ActionType;
import org.bukkit.World;
import org.bukkit.entity.EntityType;

public class AttackPlayer extends Action {
    
    public AttackPlayer() {
        super(ActionType.ATTACK_PLAYER);
    }

    @Override
    public void onAction() {
        if (super.getPlugin().getController().isDead() && super.getPlugin().getController().canSpawn(super.getPlayer().getWorld())) {
            World world = super.getPlayer().getWorld();
            world.createExplosion(super.getPlayer().getLocation().add(super.getRandom().nextInt(5), 0D, super.getRandom().nextInt(5)), -1F);
            super.getPlugin().getController().setTracking(true);
            world.spawnEntity(super.getPlayer().getLocation().add(super.getRandom().nextInt(5), 0D, super.getRandom().nextInt(5)), EntityType.ZOMBIE);
            super.getPlugin().getController().setTarget(super.getPlayer());
            if (super.getPlugin().getConfiguration().canSendMessages()) {
                super.getPlayer().sendMessage(super.getPlugin().formatMessage(super.getPlugin().getConfiguration().getMessage()));
            }
            super.getPlugin().log("Attacked " + super.getPlayer().getName() + ".");
        }
    }
}
