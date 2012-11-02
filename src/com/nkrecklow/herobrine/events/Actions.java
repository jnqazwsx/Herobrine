package com.nkrecklow.herobrine.events;

import com.nkrecklow.herobrine.Main;
import com.nkrecklow.herobrine.actions.AppearNear;
import com.nkrecklow.herobrine.actions.BuryPlayer;
import com.nkrecklow.herobrine.actions.PlaceSign;
import com.nkrecklow.herobrine.actions.PlaceTorch;
import com.nkrecklow.herobrine.actions.PlaySound;
import com.nkrecklow.herobrine.base.Generic;
import com.nkrecklow.herobrine.misc.NamedItemStack;
import java.util.Random;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Actions extends Generic {

    private Class<? extends Action> actions[];

    public Actions(Main plugin) {
        super(plugin);
        this.actions = new Class[5];
        this.actions[0] = AppearNear.class;
        this.actions[1] = BuryPlayer.class;
        this.actions[2] = PlaceSign.class;
        this.actions[3] = PlaceTorch.class;
        this.actions[4] = PlaySound.class;
    }

    public void runAction(ActionType type, Player player) {
        if ((Boolean) super.main.getConfiguration().getObject("ignoreCreativePlayers") && player.getGameMode().equals(GameMode.CREATIVE)) {
            return;
        }
        if (!super.main.canSpawn(player.getWorld())) {
            return;
        }
        boolean stop = false;
        for (ItemStack item : player.getInventory().getContents()) {
            if (item != null) {
                NamedItemStack namedItem = new NamedItemStack(item);
                if (namedItem.getName().equals("Holy Cross")) {
                    stop = true;
                    break;
                }
            }
        }
        if (stop) {
            super.main.log("Stopped Herobrine from running an event for " + player.getName() + ".", true);
            return;
        }
        for (Class<? extends Action> action : this.actions) {
            try {
                Action instance = action.newInstance();
                if (instance.getType().equals(type)) {
                    instance.onAction(super.main, player);
                }
            } catch (Exception ex) {
                super.main.log("Error: " + ex.getMessage(), false);
            }
         }
    }

    public ActionType getRandomType() {
        ActionType type = null;
        while (type == null) {
            for (Class<? extends Action> action : this.actions) {
                if (new Random().nextInt(10) == 0) {
                    try {
                        type = action.newInstance().getType();
                    } catch (Exception ex) {
                        super.main.log("Error: " + ex.getMessage(), false);
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