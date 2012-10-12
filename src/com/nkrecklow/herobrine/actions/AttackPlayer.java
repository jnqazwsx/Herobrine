package com.nkrecklow.herobrine.actions;

import com.nkrecklow.herobrine.Main;
import com.nkrecklow.herobrine.api.Action;
import com.nkrecklow.herobrine.api.ActionType;
import java.util.Random;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class AttackPlayer extends Action {
    
    public AttackPlayer() {
        super(ActionType.ATTACK_PLAYER, false);
    }

    @Override
    public void onAction(Main plugin, Player player) {
        if (plugin.getController().isDead() && plugin.getController().canSpawn(player.getWorld())) {
            World world = player.getWorld();
            world.createExplosion(player.getLocation().add(new Random().nextInt(5), 0D, new Random().nextInt(5)), -1F);
            plugin.getController().setTracking(true);
            world.spawnEntity(player.getLocation().add(new Random().nextInt(5), 0D, new Random().nextInt(5)), EntityType.ZOMBIE);
            plugin.getController().setTarget(player);
            if (plugin.getConfiguration().canSendMessages()) {
                player.sendMessage(plugin.formatMessage(plugin.getConfiguration().getMessage()));
            }
            plugin.log("Attacked " + player.getName() + ".");
        }
    }
}