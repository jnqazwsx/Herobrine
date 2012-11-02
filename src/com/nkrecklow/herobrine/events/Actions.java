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

    public void runAction(ActionType type, Player target, Player sender) {
        if ((Boolean) super.getInstance().getConfiguration().getObject("ignoreCreativePlayers") && target.getGameMode().equals(GameMode.CREATIVE)) {
            sender.sendMessage(super.getInstance().getUtil().addPluginName(target.getName() + " is in creative mode."));
            return;
        }
        if (!super.getInstance().canSpawn(target.getWorld())) {
            return;
        }
        boolean stop = false;
        for (ItemStack item : target.getInventory().getContents()) {
            if (item != null) {
                NamedItemStack namedItem = new NamedItemStack(item);
                if (namedItem.getName().equals("Holy Cross")) {
                    stop = true;
                    break;
                }
            }
        }
        if (stop) {
            sender.sendMessage(super.getInstance().getUtil().addPluginName(target.getName() + " has a \"Holy Cross\", event stopped."));
            return;
        }
        for (Class<? extends Action> action : this.actions) {
            try {
                Action instance = action.newInstance();
                if (instance.getType().equals(type)) {
                    instance.prepareAction(super.getInstance(), target, sender);
                    instance.callAction();
                }
            } catch (Exception ex) {
                super.getInstance().log("Error: " + ex.getMessage());
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
                        super.getInstance().log("Error: " + ex.getMessage());
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