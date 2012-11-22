package com.nkrecklow.herobrine.api.actions;

import com.nkrecklow.herobrine.Util;
import com.nkrecklow.herobrine.api.Action;
import com.nkrecklow.herobrine.misc.CustomItems;
import java.util.Random;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class AppearNear extends Action {

    public AppearNear() {
        super(Action.ActionType.APPEAR, true);
    }

    @Override
    public void callAction() {
        if (super.getInstance().getMobController().isSpawned()) {
            if (super.getSender() != null) {
                super.getSender().sendMessage(Util.formatString("Herobrine is currently busy."));
            }
            return;
        }
        if (super.getInstance().getConfiguration().canRunAction("AppearNear")) {
            if (super.getSender() != null) {
                super.getSender().sendMessage(Util.formatString("Appearing has been disabled in the configuration file."));
            }
            return;
        }
        Location loc = super.getInstance().getUtil().getLocationInFrontOfPlayer(super.getTarget(), new Random().nextInt(10) + 3);
        int duration = (((Integer) super.getInstance().getConfiguration().getObject("appearanceTime")) * 20) + (new Random().nextInt(5) * 20);
        loc.setY(loc.getWorld().getHighestBlockYAt(loc));
        super.getInstance().getMobController().spawnMob(loc);
        super.getInstance().getMobController().getMob().setTarget(super.getTarget().getName());
        super.getInstance().getMobController().getMob().lookAtPlayer(super.getTarget());
        super.getInstance().getServer().getScheduler().scheduleSyncDelayedTask(super.getInstance(), new Runnable() {
            
            @Override
            public void run() {
                if (new Random().nextInt(100) == 0) {
                    Item droppedItem = getInstance().getMobController().getMob().getEntity().getWorld().dropItem(getInstance().getMobController().getMob().getEntity().getLocation(), new ItemStack(Material.STONE, 1));
                    droppedItem.setItemStack(CustomItems.createItem(Material.GOLD_SWORD, "Holy Sword", "He doesn't dare come near", "when you carry this."));
                }
                getInstance().getMobController().getMob().getEntity().getWorld().playEffect(getInstance().getMobController().getMob().getEntity().getLocation(), Effect.SMOKE, 5);
                getInstance().getMobController().despawnMob();
            }
        }, duration);
        super.getTarget().addPotionEffect(new PotionEffect(PotionEffectType.WITHER, duration, 1));
        super.getInstance().logEvent("Appeared near " + super.getTarget().getName() + ".");
        if (super.getSender() != null) {
            super.getSender().sendMessage(Util.formatString("Herobrine appeared near " + super.getTarget().getName() + "."));
        }
    }
}
