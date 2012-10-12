package com.nkrecklow.herobrine.actions;

import com.nkrecklow.herobrine.Main;
import com.nkrecklow.herobrine.api.Action;
import com.nkrecklow.herobrine.api.ActionType;
import java.util.Random;
import org.bukkit.Effect;
import org.bukkit.entity.Player;

public class PlaySound extends Action {

    public PlaySound() {
        super(ActionType.PLAY_SOUND, true);
    }
    
    @Override
    public void onAction(Main plugin, Player player) {
        player.getWorld().playEffect(player.getLocation(), Effect.values()[new Random().nextInt(Effect.values().length - 1)], 5);
        plugin.log("Played a sound near " + player.getName() + ".");
    }
}
