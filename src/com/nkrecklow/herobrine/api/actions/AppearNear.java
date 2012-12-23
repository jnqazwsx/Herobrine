package com.nkrecklow.herobrine.api.actions;

import com.nkrecklow.herobrine.Main;
import com.nkrecklow.herobrine.Util;
import com.nkrecklow.herobrine.api.Action;
import java.util.Random;
import org.bukkit.Location;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class AppearNear extends Action {

    public AppearNear() {
        super(Action.ActionType.APPEAR, true);
    }

    @Override
    public void callAction() {
        if (Main.getInstance().getMobController().isSpawned()) {
            if (super.hasSender()) {
                super.getSender().sendMessage(Util.formatString("Herobrine is currently busy."));
            }
            return;
        }
        if (!Main.getInstance().getConfiguration().canRunAction("AppearNearPlayers")) {
            if (super.hasSender()) {
                super.getSender().sendMessage(Util.formatString("Appearing has been disabled in the configuration file."));
            }
            return;
        }
        Location loc = Util.getLocationInFrontOfPlayer(super.getTarget(), new Random().nextInt(10) + 3);
        int duration = (((Integer) Main.getInstance().getConfiguration().getObject("appearanceTime")) * 20) + (new Random().nextInt(5) * 20);
        loc.setY(loc.getWorld().getHighestBlockYAt(loc));
        Main.getInstance().getMobController().spawnMob(loc);
        Main.getInstance().getMobController().getMob().setTarget(super.getTarget().getName());
        Main.getInstance().getMobController().getMob().lookAtPlayer(super.getTarget());
        Main.getInstance().getServer().getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
            
            @Override
            public void run() {
                if (Main.getInstance().getMobController().isSpawned()) {
                    Main.getInstance().getMobController().despawnMob();
                }
            }
        }, duration);
        super.getTarget().addPotionEffect(new PotionEffect(PotionEffectType.WITHER, duration, 0));
        Main.getInstance().logEvent("Appeared near " + super.getTarget().getName() + ".");
        if (super.hasSender()) {
            super.getSender().sendMessage(Util.formatString("Appeared near " + super.getTarget().getName() + "."));
        }
    }
}
