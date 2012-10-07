package com.nkrecklow.herobrine.events;

import com.nkrecklow.herobrine.Generic;
import com.nkrecklow.herobrine.Plugin;
import com.nkrecklow.herobrine.actions.AppearNear;
import com.nkrecklow.herobrine.actions.AttackPlayer;
import com.nkrecklow.herobrine.actions.BuryPlayer;
import com.nkrecklow.herobrine.actions.PlaceSign;
import com.nkrecklow.herobrine.actions.PlaceTorch;
import com.nkrecklow.herobrine.actions.PlaySound;
import com.nkrecklow.herobrine.actions.SendMessage;
import com.nkrecklow.herobrine.actions.SpawnZombies;
import com.nkrecklow.herobrine.actions.StealItem;
import java.util.Random;
import org.bukkit.entity.Player;

public class Actions extends Generic {

    private Class<? extends Action> actions[];
    
    public Actions(Plugin plugin) {
        super(plugin);
        this.actions = new Class[9];
        this.actions[0] = AppearNear.class;
        this.actions[1] = AttackPlayer.class;
        this.actions[2] = BuryPlayer.class;
        this.actions[3] = PlaceSign.class;
        this.actions[4] = PlaceTorch.class;
        this.actions[5] = PlaySound.class;
        this.actions[6] = SendMessage.class;
        this.actions[7] = SpawnZombies.class;
        this.actions[8] = StealItem.class;
    }
    
    public void runAction(ActionType type, Player player) {
        for (Class<? extends Action> action : this.actions) {
            try {
                Action actionC = action.newInstance();
                if (actionC.getActionType().equals(type)) {
                    actionC.setInstances(super.getPlugin(), player);
                    actionC.onAction();
                }
            } catch (Exception ex) {
                super.getPlugin().log("Error: " + ex.getMessage());
            }
        }
    }
    
    public ActionType getRandomActionType() {
        ActionType type = null;
        while (type == null) {
            for (Class<? extends Action> action : this.actions) {
                if (new Random().nextInt(10) == 0) {
                    try {
                        type = action.newInstance().getActionType();
                    } catch (Exception ex) {
                        super.getPlugin().log("Error: " + ex.getMessage());
                        type = null;
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
