package com.nkrecklow.herobrine.actions;

import com.nkrecklow.herobrine.Main;
import com.nkrecklow.herobrine.api.Action;
import com.nkrecklow.herobrine.api.ActionType;
import java.util.Random;
import org.bukkit.entity.Player;

public class RandomLightning extends Action {

    public RandomLightning() {
        super(ActionType.RANDOM_LIGHTING, true);
    }
    
    @Override
    public void onAction(Main plugin, Player player) {
        if (!(Boolean) plugin.getConfiguration().getObject("fireTrails")) {
            return;
        }
        player.getWorld().strikeLightning(player.getLocation().add(new Random().nextInt(5), 0, new Random().nextInt(5)));
        plugin.log("Lightning struck near " + player.getName() + ".");
    }
}
