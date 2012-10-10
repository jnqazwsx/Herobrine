package com.nkrecklow.herobrine.actions;

import com.nkrecklow.herobrine.api.Action;
import com.nkrecklow.herobrine.api.ActionType;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;

public class PlaceSign extends Action {

    public PlaceSign() {
        super(ActionType.PLACE_SIGN);
    }
    
    @Override
    public void onAction() {
        if (!(Boolean) super.getPlugin().getConfiguration().getObject("modifyWorld")) {
            return;
        }
        Block signPost = super.getPlayer().getLocation().add(super.getRandom().nextInt(5), 0D, super.getRandom().nextInt(5)).getBlock();
        Block groundBlock = signPost.getLocation().subtract(0D, 1D, 0D).getBlock();
        if (signPost.getType().equals(Material.AIR) && !groundBlock.getType().equals(Material.AIR)) {
            signPost.setType(Material.SIGN_POST);
            Sign signBlock = (Sign) signPost.getState();
            signBlock.setLine(1, super.getPlugin().getConfiguration().getSignMessage());
            signBlock.update();
            super.getPlugin().log("Placed a sign by " + super.getPlayer().getName() + ".");
        }
    }
}
