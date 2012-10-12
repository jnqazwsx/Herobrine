package com.nkrecklow.herobrine.actions;

import com.nkrecklow.herobrine.Main;
import com.nkrecklow.herobrine.api.Action;
import com.nkrecklow.herobrine.api.ActionType;
import java.util.Random;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class SpawnZombies extends Action {

    public SpawnZombies() {
        super(ActionType.SPAWN_ZOMBIES, true);
    }
    
    @Override
    public void onAction(Main plugin, Player player) {
        if (!(Boolean) plugin.getConfiguration().getObject("spawnZombies")) {
            return;
        }
        int amount = 0;
        while (amount == 0) {
            amount = new Random().nextInt(3);
        }
        for (int id = 0; id < amount; id++) {
            player.getWorld().spawnCreature(player.getLocation().add(new Random().nextInt(5), 0, new Random().nextInt(5)), EntityType.ZOMBIE);
        }
        plugin.log("Spawned " + amount + " zombies near " + player.getName() + ".");
    }
}
