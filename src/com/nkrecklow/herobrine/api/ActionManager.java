package com.nkrecklow.herobrine.api;

import com.nkrecklow.herobrine.Main;
import com.nkrecklow.herobrine.Util;
import com.nkrecklow.herobrine.api.actions.*;
import com.nkrecklow.herobrine.api.basic.Generic;
import com.nkrecklow.herobrine.misc.CustomItems;
import java.io.File;
import java.util.Random;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class ActionManager extends Generic {

    private Class<? extends Action> actions[];

    public ActionManager(Main instance) {
        super(instance);
        this.actions = new Class[10];
        this.actions[0] = AppearNear.class;
        this.actions[1] = BuryPlayer.class;
        this.actions[2] = PlaceSign.class;
        this.actions[3] = PlaceTorch.class;
        this.actions[4] = PlaySound.class;
        this.actions[5] = EnterNightmare.class;
        this.actions[6] = CreatePyramid.class;
        this.actions[7] = TNTTrap.class;
        this.actions[8] = SuffocatePlayer.class;
        this.actions[9] = PossessPlayer.class;
    }

    public void runAction(Action.ActionType type, Player target, Player sender, boolean random) {
         if (!new File(super.getInstance().getDataFolder() + "/living.yml").exists()) {
            if (sender != null) {
                sender.sendMessage(Util.formatString("Herobrine has not yet been unleashed."));
                sender.sendMessage("You can force unleash him using: /hb forceunleash");
            }
            return;
        }
        if ((Boolean) super.getInstance().getConfiguration().getObject("ignoreCreativePlayers") && target.getGameMode().equals(GameMode.CREATIVE)) {
            if (sender != null) {
                sender.sendMessage(Util.formatString(target.getName() + " is in creative mode."));
            }
            return;
        }
        if (!super.getInstance().getMobController().canSpawn(target.getWorld())) {
            if (sender != null) {
                sender.sendMessage(Util.formatString(target.getName() + "'s world (\"" + target.getWorld().getName() + "\") doesn't allow Herobrine."));
            }
            return;
        }
        if (target.getInventory().contains(CustomItems.createItem(Material.GOLD_SWORD, "Holy Sword", "He doesn't dare come near", "when you carry this."))) {
            if (sender != null) {
                sender.sendMessage(Util.formatString(target.getName() + " has a \"Holy Sword\", event stopped."));
            }
            return;
        }
        if (!target.isOp() && target.hasPermission("herobrine.ignore")) {
            if (sender != null) {
                sender.sendMessage(Util.formatString(target.getName() + " has the permission \"herobrine.ignore\", event stopped."));
            }
            return;
        }
        for (Class<? extends Action> action : this.actions) {
            try {
                Action instance = action.newInstance();
                if (random) {
                    if (instance.getType().equals(type) && instance.isRandom()) {
                        instance.prepareAction(super.getInstance(), target, sender);
                        instance.callAction();
                        super.getInstance().log("Running: " + type.toString());
                        break;
                    }
                } else {
                    if (instance.getType().equals(type)) {
                        instance.prepareAction(super.getInstance(), target, sender);
                        instance.callAction();
                        super.getInstance().log("Running: " + type.toString());
                        break;
                    }
                }
            } catch (Exception ex) {
                super.getInstance().log("Error: " + ex.getMessage());
            }
        }
    }

    public Action.ActionType getRandomType() {
        Action.ActionType type = null;
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