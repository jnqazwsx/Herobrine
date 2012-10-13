package com.nkrecklow.herobrine.actions;

import com.nkrecklow.herobrine.Main;
import com.nkrecklow.herobrine.Util;
import com.nkrecklow.herobrine.events.Action;
import com.nkrecklow.herobrine.events.ActionType;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class AttackPlayer extends Action {
    
    public AttackPlayer() {
        super(ActionType.ATTACK_PLAYER, false);
    }

    @Override
    public void onAction(Main main, Player player) {
        if (!main.isHerobrineSpawned()) {
            Location loc = Util.getNearbyLocation(player.getLocation());
            loc.setY(player.getWorld().getHighestBlockYAt(loc) + 1D);
            main.spawnHerobrine(loc);
            main.getHerobrine().setTarget(player.getName());
            player.getWorld().createExplosion(loc, -1F);
            if (main.getConfiguration().canSendMessages()) {
                player.sendMessage(main.getMessageAsHerobrine(main.getConfiguration().getMessage()));
            }
            main.log("Attacked " + player.getName() + ".");
        }
    }
}