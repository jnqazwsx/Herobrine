package com.nkrecklow.herobrine.actions;

import com.nkrecklow.herobrine.Main;
import com.nkrecklow.herobrine.Util;
import com.nkrecklow.herobrine.events.Action;
import com.nkrecklow.herobrine.events.ActionType;
import com.nkrecklow.herobrine.events.ActionsUtil;
import java.util.ArrayList;
import java.util.Random;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class RandomChest extends Action {

    private ArrayList<Integer> ids;
    
    public RandomChest() {
        super(ActionType.RANDOM_CHEST, true);
        this.ids = new ArrayList<Integer>();
        this.ids.add(76);
        this.ids.add(50);
        this.ids.add(260);
        this.ids.add(259);
        this.ids.add(270);
        this.ids.add(271);
        this.ids.add(269);
        this.ids.add(268);
        this.ids.add(319);
        this.ids.add(365);
        this.ids.add(363);
    }

    @Override
    public void onAction(Main main, Player player) {
        Block chest = Util.getNearbyLocation(player.getLocation()).getBlock();
        Block below = chest.getLocation().subtract(0, 1, 0).getBlock();
        if (chest.getType().equals(Material.AIR) && !below.getType().equals(Material.AIR)) {
            chest.setType(Material.CHEST);
            Chest inv = (Chest) chest.getState();
            int add = (new Random().nextInt(3) + 1);
            for (int toAdd = 0; toAdd < add; toAdd++) {
                inv.getInventory().addItem(new ItemStack(this.ids.get(new Random().nextInt(this.ids.size() - 1)), 1));
            }
            inv.getInventory().addItem(ActionsUtil.getNewBook(main));
            main.log("Created a chest with contents near " + player.getName() + "!");
        }
    }
}
