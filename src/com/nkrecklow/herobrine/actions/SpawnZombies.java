package com.nkrecklow.herobrine.actions;

import com.nkrecklow.herobrine.events.Action;
import com.nkrecklow.herobrine.events.ActionType;
import java.util.Random;
import org.bukkit.entity.EntityType;

public class SpawnZombies extends Action {

    public SpawnZombies() {
        super(ActionType.SPAWN_ZOMBIES_NEAR_PLAYER);
    }
    
    @Override
    public void onAction() {
        if (!(Boolean) super.getPlugin().getConfiguration().getObject("spawnZombies")) {
            return;
        }
        int amount = new Random().nextInt(3);
        for (int id = 0; id < amount; id++) {
            super.getPlayer().getWorld().spawnCreature(super.getPlayer().getLocation().add(super.getRandom().nextInt(5), 0, super.getRandom().nextInt(5)), EntityType.ZOMBIE);
        }
        if (amount > 0) {
            super.getPlugin().log("Spawned " + amount + " zombies near " + super.getPlayer().getName() + ".");
        }
    }
}
