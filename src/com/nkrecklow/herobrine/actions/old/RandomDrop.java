package com.nkrecklow.herobrine.actions.old;

import com.nkrecklow.herobrine.Main;
import com.nkrecklow.herobrine.api.Action;
import com.nkrecklow.herobrine.api.ActionType;
import java.util.Random;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class RandomDrop extends Action {
    
    public RandomDrop() {
        super(ActionType.RANDOM_DROP, true);
    }

    @Override
    public void onAction(Main plugin, Player player) {
        try {
            Material mat = Material.values()[new Random().nextInt(Material.values().length - 1)];
            player.getWorld().dropItem(player.getLocation().add(new Random().nextInt(5), 0, new Random().nextInt(5)), new ItemStack(mat, 1));
            plugin.log("Dropped a " + mat + " near " + player.getName() + ".");
        } catch (Exception ex) {
            /*
             * Ignore possible errors, the drop could be an entity.
             */
        }
    }
}
