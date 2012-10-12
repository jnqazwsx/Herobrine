package com.nkrecklow.herobrine.base;

import com.nkrecklow.herobrine.Main;
import com.nkrecklow.herobrine.actions.AppearNear;
import com.nkrecklow.herobrine.actions.AttackPlayer;
import com.nkrecklow.herobrine.actions.BuryPlayer;
import com.nkrecklow.herobrine.actions.PlaceSign;
import com.nkrecklow.herobrine.actions.PlaceTorch;
import com.nkrecklow.herobrine.actions.PlaySound;
import com.nkrecklow.herobrine.actions.RandomDrop;
import com.nkrecklow.herobrine.actions.RandomFire;
import com.nkrecklow.herobrine.actions.RandomLightning;
import com.nkrecklow.herobrine.actions.SendMessage;
import com.nkrecklow.herobrine.actions.SpawnZombies;
import com.nkrecklow.herobrine.api.Action;
import com.nkrecklow.herobrine.api.ActionType;
import com.nkrecklow.herobrine.core.Generic;
import java.util.Random;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class Actions extends Generic {

    private Class<? extends Action> actions[];
    private ActionType lastAction;
    
    public Actions(Main plugin) {
        super(plugin);
        this.actions = new Class[11];
        this.actions[0] = AppearNear.class;
        this.actions[1] = BuryPlayer.class;
        this.actions[2] = PlaceSign.class;
        this.actions[3] = PlaceTorch.class;
        this.actions[4] = PlaySound.class;
        this.actions[5] = SendMessage.class;
        this.actions[6] = SpawnZombies.class;
        this.actions[7] = RandomLightning.class;
        this.actions[8] = RandomFire.class;
        this.actions[9] = RandomDrop.class;
        this.actions[10] = AttackPlayer.class;
    }
    
    public void runAction(ActionType type, Player player) {
        if (!player.getGameMode().equals(GameMode.SURVIVAL) && (Boolean) super.getPlugin().getConfiguration().getObject("ignoreCreativePlayers")) {
            return;
        }
        for (Class<? extends Action> action : this.actions) {
            try {
                Action actionC = action.newInstance();
                if (actionC.getActionType().equals(type)) {
                    actionC.onAction(super.getPlugin(), player);
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
                        Action actionI = action.newInstance();
                        if (actionI.isRandom()) {
                            if (this.lastAction == null) {
                                type = actionI.getActionType();
                                this.lastAction = actionI.getActionType();
                            } else if (!this.lastAction.equals(actionI.getActionType())) {
                                type = actionI.getActionType();
                            }
                        }
                    } catch (Exception ex) {
                        super.getPlugin().log("Error: " + ex.getMessage());
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
