package com.nkrecklow.herobrine.actions;

import com.nkrecklow.herobrine.events.Action;
import com.nkrecklow.herobrine.events.ActionType;
import org.bukkit.inventory.ItemStack;

public class StealItem extends Action {

    public StealItem() {
        super(ActionType.STEAL_ITEM_FROM_PLAYER);
    }
    
    @Override
    public void onAction() {
        if ((Boolean) super.getPlugin().getConfiguration().getObject("modifyInventories")) {
            ItemStack item = null;
            while (item == null) {
                item = super.getPlayer().getInventory().getItem(super.getRandom().nextInt(super.getPlayer().getInventory().getSize() - 1));
            }
            super.getPlayer().getInventory().remove(item);
            super.getPlayer().sendMessage(super.getPlugin().formatMessage("Missing something, like a " + item.getType().toString().replace("_", "").toLowerCase() + "?"));
            super.getPlugin().log("Stole from " + super.getPlayer().getName() + ".");
        }
    }
}
