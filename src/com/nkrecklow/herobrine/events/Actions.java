package com.nkrecklow.herobrine.events;

import com.nkrecklow.herobrine.Main;
import com.nkrecklow.herobrine.actions.AppearNear;
import com.nkrecklow.herobrine.actions.AttackPlayer;
import com.nkrecklow.herobrine.actions.BuryPlayer;
import com.nkrecklow.herobrine.actions.PlaceSign;
import com.nkrecklow.herobrine.actions.PlaceTorch;
import com.nkrecklow.herobrine.actions.PlaySound;
import com.nkrecklow.herobrine.actions.RandomChest;
import com.nkrecklow.herobrine.base.Generic;
import java.util.Random;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class Actions extends Generic {

    private Class<? extends Action> actions[];

    public Actions(Main plugin) {
        super(plugin);
        this.actions = new Class[7];
        this.actions[0] = AppearNear.class;
        this.actions[1] = BuryPlayer.class;
        this.actions[2] = PlaceSign.class;
        this.actions[3] = PlaceTorch.class;
        this.actions[4] = PlaySound.class;
        this.actions[5] = AttackPlayer.class;
        this.actions[6] = RandomChest.class;
    }
    
    public void runAction(ActionType type, Player player) {
        if ((Boolean) super.main.getConfiguration().getObject("ignoreCreativePlayers") && player.getGameMode().equals(GameMode.CREATIVE)) {
            return;
        }
        if (!super.main.canSpawn(player.getWorld())) {
            return;
        }
        for (Class<? extends Action> action : this.actions) {
            try {
                Action actionC = action.newInstance();
                if (actionC.getActionType().equals(type)) {
                    actionC.onAction(super.main, player);
                }
            } catch (Exception ex) {
                super.main.log("Error: " + ex.getMessage());
            }
        }
    }
    
    public ActionType getRandomActionType() {
        ActionType type = null;
        while (type == null) {
            for (Class<? extends Action> action : this.actions) {
                if (new Random().nextInt(10) == 0) {
                    try {
                        Action actionI = action.newInstance();
                        if (actionI.isRandom()) {
                            type = actionI.getActionType();
                        }
                    } catch (Exception ex) {
                        super.main.log("Error: " + ex.getMessage());
                        continue;
                    }
                }
            }
        }
        return type;
    }
    
    public Class<? extends Action>[] getActions() {
        return this.actions;
    }
}
