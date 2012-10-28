package com.nkrecklow.herobrine.actions;

import com.nkrecklow.herobrine.Main;
import com.nkrecklow.herobrine.Util;
import com.nkrecklow.herobrine.events.Action;
import com.nkrecklow.herobrine.events.ActionType;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class Tunnel extends Action {

    public Tunnel() {
        super(ActionType.TUNNEL);
    }

    @Override
    public void onAction(Main main, Player player) {
        if (!(Boolean) main.getConfiguration().getObject("modifyWorld")) {
            return;
        }
        Location top = Util.getNearbyLocation(player.getLocation());
        if (Util.canPlace(main, top)) {
            for (int y = (top.getBlockY() - 1); y > 3; y--) {
                Block block = top.add(0, y, 0).getBlock();
                block.setType(Material.GLASS);
            }
        }
    }
}
