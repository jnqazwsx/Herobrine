package com.nkrecklow.herobrine.actions;

import com.nkrecklow.herobrine.api.Action;
import com.nkrecklow.herobrine.api.ActionType;
import org.bukkit.inventory.ItemStack;

public class StealItem extends Action {

    public StealItem() {
        super(ActionType.STEAL_ITEM);
    }
    
    @Override
    public void onAction() {
        if ((Boolean) super.getPlugin().getConfiguration().getObject("modifyInventories")) {
            return;
        }
        ItemStack item = null;
        while (item == null) {
            item = super.getPlayer().getInventory().getItem(super.getRandom().nextInt(super.getPlayer().getInventory().getSize() - 1));
        }
        super.getPlayer().getInventory().remove(item);
        super.getPlayer().sendMessage(super.getPlugin().formatMessage("Missing something, like a " + item.getType().toString().replace("_", " ").toLowerCase() + "?"));
        super.getPlugin().log("Stole from " + super.getPlayer().getName() + ".");
    }
}
