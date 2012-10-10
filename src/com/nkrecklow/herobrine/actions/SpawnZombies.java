package com.nkrecklow.herobrine.actions;

import com.nkrecklow.herobrine.api.Action;
import com.nkrecklow.herobrine.api.ActionType;
import java.util.Random;
import org.bukkit.entity.EntityType;

public class SpawnZombies extends Action {

    public SpawnZombies() {
        super(ActionType.SPAWN_ZOMBIES);
    }
    
    @Override
    public void onAction() {
        if (!(Boolean) super.getPlugin().getConfiguration().getObject("spawnZombies")) {
            return;
        }
        int amount = 0;
        while (amount == 0) {
            amount = new Random().nextInt(3);
        }
        for (int id = 0; id < amount; id++) {
            super.getPlayer().getWorld().spawnCreature(super.getPlayer().getLocation().add(super.getRandom().nextInt(5), 0, super.getRandom().nextInt(5)), EntityType.ZOMBIE);
        }
        super.getPlugin().log("Spawned " + amount + " zombies near " + super.getPlayer().getName() + ".");
    }
}
