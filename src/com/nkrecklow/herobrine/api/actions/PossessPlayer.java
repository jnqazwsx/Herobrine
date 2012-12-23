package com.nkrecklow.herobrine.api.actions;

import com.nkrecklow.herobrine.Main;
import com.nkrecklow.herobrine.Util;
import com.nkrecklow.herobrine.api.Action;
import java.util.Random;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PossessPlayer extends Action {

    public PossessPlayer() {
        super(Action.ActionType.POSSESS_PLAYER, true);
    }

    @Override
    public void callAction() {
        if (!Main.getInstance().getConfiguration().canRunAction("PossessPlayers")) {
            if (super.getSender() != null) {
                super.getSender().sendMessage(Util.formatString("The possession of players has been disable in the configuration file."));
            }
            return;
        }
        int time = (new Random().nextInt(5) + 5) * 20;
        super.getTarget().addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, time, 1));
        super.getTarget().addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, time, 1));
        super.getTarget().addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, time, 1));
        super.getTarget().addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, time, 1));
        super.getTarget().addPotionEffect(new PotionEffect(PotionEffectType.SLOW, time, 1));
        super.getTarget().addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, time, 1));
        Main.getInstance().logEvent("Possessed " + super.getTarget().getName() + ".");
        if (super.getSender() != null) {
            super.getSender().sendMessage(Util.formatString("Possessed " + super.getTarget().getName() + "."));
        }
    }
}
