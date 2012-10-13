package com.nkrecklow.herobrine.actions;

import com.nkrecklow.herobrine.Main;
import com.nkrecklow.herobrine.Util;
import com.nkrecklow.herobrine.events.Action;
import com.nkrecklow.herobrine.events.ActionType;
import java.util.Random;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class AppearNear extends Action {

    public AppearNear() {
        super(ActionType.APPEAR, true);
    }
    
    @Override
    public void onAction(final Main main, Player player) {
        if (!main.isHerobrineSpawned()) {
            Location loc = Util.getLocationInFrontOfPlayer(player, new Random().nextInt(3) + 3);
            loc.setY(player.getWorld().getHighestBlockYAt(loc));
            main.spawnHerobrine(loc);
            main.getServer().getScheduler().scheduleSyncDelayedTask(main, new Runnable() {

                @Override
                public void run() {
                    main.killHerobrine();
                }
            }, 40L);
            main.log("Appeared near " + player.getName() + ".");
        }
    }
}
