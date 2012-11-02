package com.nkrecklow.herobrine.actions;

import com.nkrecklow.herobrine.events.Action;
import com.nkrecklow.herobrine.events.ActionType;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;

public class PlaceSign extends Action {

    public PlaceSign() {
        super(ActionType.PLACE_SIGN);
    }
    
    @Override
    public void callAction() {
        if (!(Boolean) super.getInstance().getConfiguration().getObject("modifyWorld")) {
            super.getSender().sendMessage(super.getInstance().getUtil().addPluginName("Can't modify that world (\"" + super.getTarget().getWorld().getName() + "\")."));
            return;
        }
        Block signPost = super.getInstance().getUtil().getNearbyLocation(super.getTarget().getLocation()).getBlock();
        Block below = signPost.getLocation().subtract(0D, 1D, 0D).getBlock();
        String msg = super.getInstance().getConfiguration().getSignMessage();
        if (signPost.getType().equals(Material.AIR) && super.getInstance().getUtil().canPlace(below.getLocation())) {
            signPost.setType(Material.SIGN_POST);
            Sign signBlock = (Sign) signPost.getState();
            signBlock.setLine(1, msg);
            signBlock.update();
            super.getInstance().logEvent("Placed a sign by " + super.getTarget().getName() + " (\"" + msg + "\").");
            super.getSender().sendMessage(super.getInstance().getUtil().addPluginName("Placed a sign by " + super.getTarget().getName() + " (\"" + msg + "\")."));
        } else {
            super.getSender().sendMessage(super.getInstance().getUtil().addPluginName("Failed to find a proper sign location."));
        }
    }
}
