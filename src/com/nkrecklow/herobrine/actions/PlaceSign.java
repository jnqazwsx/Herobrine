package com.nkrecklow.herobrine.actions;

import com.nkrecklow.herobrine.Main;
import com.nkrecklow.herobrine.events.Action;
import com.nkrecklow.herobrine.events.ActionType;
import java.util.Random;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;

public class PlaceSign extends Action {

    public PlaceSign() {
        super(ActionType.PLACE_SIGN, true);
    }
    
    @Override
    public void onAction(Main main, Player player) {
        if (!(Boolean) main.getConfiguration().getObject("modifyWorld")) {
            return;
        }
        Block signPost = player.getLocation().add(new Random().nextInt(5), 0D, new Random().nextInt(5)).getBlock();
        Block groundBlock = signPost.getLocation().subtract(0D, 1D, 0D).getBlock();
        String msg = main.getConfiguration().getSignMessage();
        if (signPost.getType().equals(Material.AIR) && !groundBlock.getType().equals(Material.AIR)) {
            signPost.setType(Material.SIGN_POST);
            Sign signBlock = (Sign) signPost.getState();
            signBlock.setLine(1, msg);
            signBlock.update();
            main.log("Placed a sign by " + player.getName() + " (\"" + msg + "\").");
        }
    }
}
