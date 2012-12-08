package com.nkrecklow.herobrine.api.actions;

import com.nkrecklow.herobrine.Main;
import com.nkrecklow.herobrine.Util;
import com.nkrecklow.herobrine.api.Action;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;

public class PlaceSign extends Action {

    public PlaceSign() {
        super(Action.ActionType.PLACE_SIGN, true);
    }

    @Override
    public void callAction() {
        if (!Main.getInstance().getConfiguration().canRunAction("PlaceSigns")) {
            if (super.hasSender()) {
                super.getSender().sendMessage(Util.formatString("Placing signs has been disable in the configuration file."));
            }
            return;
        }
        Block signPost = Util.getNearbyLocation(super.getTarget().getLocation(), 5).getBlock();
        Block below = signPost.getLocation().subtract(0D, 1D, 0D).getBlock();
        String msg = Main.getInstance().getConfiguration().getSignMessage();
        if (signPost.getType().equals(Material.AIR) && Util.canPlace(below.getLocation())) {
            signPost.setType(Material.SIGN_POST);
            Sign signBlock = (Sign) signPost.getState();
            signBlock.setLine(1, msg);
            signBlock.update();
            Main.getInstance().logEvent("Placed a sign by " + super.getTarget().getName() + " (\"" + msg + "\").");
            if (super.hasSender()) {
                super.getSender().sendMessage(Util.formatString("Placed a sign by " + super.getTarget().getName() + " (\"" + msg + "\")."));
            }
        } else {
            if (super.hasSender()) {
                super.getSender().sendMessage(Util.formatString("Failed to find a proper sign location."));
            }
        }
    }
}
