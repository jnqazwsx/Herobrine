package com.nkrecklow.herobrine.actions;

import com.nkrecklow.herobrine.api.Action;
import com.nkrecklow.herobrine.api.ActionType;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class RandomDrop extends Action {
    
    public RandomDrop() {
        super(ActionType.RANDOM_DROP, true);
    }

    @Override
    public void onAction() {
        try {
            Material mat = Material.values()[super.getRandom().nextInt(Material.values().length - 1)];
            super.getPlayer().getWorld().dropItem(super.getPlayer().getLocation().add(super.getRandom().nextInt(5), 0, super.getRandom().nextInt(5)), new ItemStack(mat, 1));
            super.getPlugin().log("Dropped a " + mat + " near " + super.getPlayer().getName() + ".");
        } catch (Exception ex) {
            /*
             * Ignore possible errors, the drop could be an entity.
             */
        }
    }
}
