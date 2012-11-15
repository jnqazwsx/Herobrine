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
        Block[] blocks = new Block[9];
        blocks[0] = super.getInstance().getUtil().getNearbyLocation(super.getTarget().getLocation()).add(0, 2, 0).getBlock();
        blocks[1] = blocks[0].getRelative(BlockFace.NORTH).getLocation().subtract(0, 1, 0).getBlock();
        blocks[2] = blocks[0].getRelative(BlockFace.SOUTH).getLocation().subtract(0, 1, 0).getBlock();
        blocks[3] = blocks[0].getRelative(BlockFace.WEST).getLocation().subtract(0, 1, 0).getBlock();
        blocks[4] = blocks[0].getRelative(BlockFace.EAST).getLocation().subtract(0, 1, 0).getBlock();
        blocks[5] = blocks[1].getRelative(BlockFace.NORTH).getLocation().subtract(0, 1, 0).getBlock();
        blocks[6] = blocks[2].getRelative(BlockFace.SOUTH).getLocation().subtract(0, 1, 0).getBlock();
        blocks[7] = blocks[3].getRelative(BlockFace.WEST).getLocation().subtract(0, 1, 0).getBlock();
        blocks[8] = blocks[4].getRelative(BlockFace.EAST).getLocation().subtract(0, 1, 0).getBlock();
        for (int index = 0; index < blocks.length; index++) {
            Block block = blocks[index];
            if (index == 0) {
                block.setType(Material.BEACON);
            } else {
                block.setType(Material.SANDSTONE);
            }
        }
    }
}
