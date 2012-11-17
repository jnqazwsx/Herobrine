package com.nkrecklow.herobrine.api.actions;

import com.nkrecklow.herobrine.Util;
import com.nkrecklow.herobrine.api.Action;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

public class CreatePyramid extends Action {

    public CreatePyramid() {
        super(Action.ActionType.CREATE_PYRAMID);
    }

    @Override
    public void callAction() {
        if (!(Boolean) super.getInstance().getConfiguration().getObject("modifyWorld")) {
            if (super.getSender() != null) {
                super.getSender().sendMessage(Util.formatString("Can't modify that world (\"" + super.getTarget().getWorld().getName() + "\")."));
            }
            return;
        }
        if (!(Boolean) super.getInstance().getConfiguration().getObject("buildPyramids")) {
            if (super.getSender() != null) {
                super.getSender().sendMessage(Util.formatString("Building pyramids has been disable in the configuration file."));
            }
            return;
        }
        Block[] blocks = new Block[14];
        blocks[0] = super.getInstance().getUtil().getNearbyLocation(super.getTarget().getLocation(), 10).add(0, 2, 0).getBlock();
        blocks[1] = blocks[0].getRelative(BlockFace.NORTH).getLocation().subtract(0, 1, 0).getBlock();
        blocks[2] = blocks[0].getRelative(BlockFace.SOUTH).getLocation().subtract(0, 1, 0).getBlock();
        blocks[3] = blocks[0].getRelative(BlockFace.WEST).getLocation().subtract(0, 1, 0).getBlock();
        blocks[4] = blocks[0].getRelative(BlockFace.EAST).getLocation().subtract(0, 1, 0).getBlock();
        blocks[5] = blocks[1].getRelative(BlockFace.NORTH).getLocation().subtract(0, 1, 0).getBlock();
        blocks[6] = blocks[2].getRelative(BlockFace.SOUTH).getLocation().subtract(0, 1, 0).getBlock();
        blocks[7] = blocks[3].getRelative(BlockFace.WEST).getLocation().subtract(0, 1, 0).getBlock();
        blocks[8] = blocks[4].getRelative(BlockFace.EAST).getLocation().subtract(0, 1, 0).getBlock();
        blocks[9] = blocks[0].getRelative(BlockFace.NORTH_EAST).getLocation().subtract(0, 2, 0).getBlock();
        blocks[10] = blocks[0].getRelative(BlockFace.SOUTH_EAST).getLocation().subtract(0, 2, 0).getBlock();
        blocks[11] = blocks[0].getRelative(BlockFace.SOUTH_WEST).getLocation().subtract(0, 2, 0).getBlock();
        blocks[12] = blocks[0].getRelative(BlockFace.NORTH_WEST).getLocation().subtract(0, 2, 0).getBlock();
        blocks[13] = blocks[0].getLocation().subtract(0, 1, 0).getBlock();
        boolean good = true;
        for (int index = 0; index < blocks.length; index++) {
            Block block = blocks[index];
            if (index >= 5 && index <= 12) {
                if (!super.getInstance().getUtil().canPlace(block.getLocation().subtract(0, 1, 0)) || super.getTarget().getLocation().distance(block.getLocation()) <= 0.9F) {
                    good = false;
                    break;
                }
            }
        }
        if (!good) {
            if (super.getSender() != null) {
                super.getSender().sendMessage(Util.formatString("Failed to find a proper pyramid location."));
            }
            return;
        }
        for (int index = 0; index < blocks.length; index++) {
            Block block = blocks[index];
            if (index == 0) {
                block.setType(Material.FIRE);
            } else if (index == 13) {
                block.setType(Material.NETHERRACK);
            } else {
                block.setType(Material.SANDSTONE);
            }
        }
        if (super.getSender() != null) {
            super.getSender().sendMessage(Util.formatString("Created a pyramid near " + super.getTarget().getName() + "."));
            super.getInstance().logEvent("Created a pyramid near " + super.getTarget().getName() + ".");
        }
    }
}
