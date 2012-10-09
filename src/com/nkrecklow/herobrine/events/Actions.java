package com.nkrecklow.herobrine.events;

import com.nkrecklow.herobrine.Main;
import com.nkrecklow.herobrine.actions.AppearNear;
import com.nkrecklow.herobrine.actions.AttackPlayer;
import com.nkrecklow.herobrine.actions.BuryPlayer;
import com.nkrecklow.herobrine.actions.PlaceSign;
import com.nkrecklow.herobrine.actions.PlaceTorch;
import com.nkrecklow.herobrine.actions.PlaySound;
import com.nkrecklow.herobrine.actions.RandomLightning;
import com.nkrecklow.herobrine.actions.SendMessage;
import com.nkrecklow.herobrine.actions.SpawnZombies;
import com.nkrecklow.herobrine.actions.StealItem;
import com.nkrecklow.herobrine.base.Generic;
import java.util.Random;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class Actions extends Generic {

    private Class<? extends Action> actions[];
    
    public Actions(Main plugin) {
        super(plugin);
        this.actions = new Class[10];
        this.actions[0] = AppearNear.class;
        this.actions[1] = AttackPlayer.class;
        this.actions[2] = BuryPlayer.class;
        this.actions[3] = PlaceSign.class;
        this.actions[4] = PlaceTorch.class;
        this.actions[5] = PlaySound.class;
        this.actions[6] = SendMessage.class;
        this.actions[7] = SpawnZombies.class;
        this.actions[8] = StealItem.class;
        this.actions[9] = RandomLightning.class;
    }
    
    public void runAction(ActionType type, Player player) {
        if (!player.getGameMode().equals(GameMode.SURVIVAL) && (Boolean) super.getPlugin().getConfiguration().getObject("ignoreCreativePlayers")) {
            return;
        }
        if (!super.getPlugin().getController().canSpawn(player.getWorld())) {
            return;
        }
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
