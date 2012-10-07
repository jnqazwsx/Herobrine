package com.nkrecklow.herobrine.actions;

import com.nkrecklow.herobrine.events.Action;
import com.nkrecklow.herobrine.events.ActionType;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;

public class PlaceSign extends Action {

    public PlaceSign() {
        super(ActionType.PLACE_SIGN_NEAR_PLAYER);
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
            int signText = super.getRandom().nextInt(6);
            if (signText == 1) {
                signBlock.setLine(1, "I'm watching.");
            } else if (signText == 2) {
                signBlock.setLine(1, "Stop.");
            } else if (signText == 3) {
                signBlock.setLine(1, "You'll join");
                signBlock.setLine(2, "me soon.");
            } else if (signText == 4) {
                signBlock.setLine(1, "You can't");
                signBlock.setLine(2, "escape.");
            } else if (signText == 5) {
                signBlock.setLine(1, "Remember me?");
            } else {
                signBlock.setLine(1, "I'm alive.");
            }
            signBlock.update();
            super.getPlugin().log("Placed a sign by " + super.getPlayer().getName() + ".");
        }
    }
}
