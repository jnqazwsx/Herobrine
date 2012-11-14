package com.nkrecklow.herobrine;

import com.nkrecklow.herobrine.api.basic.Generic;
import com.nkrecklow.herobrine.misc.BookItem;
import com.nkrecklow.herobrine.misc.NamedItemStack;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Util extends Generic {

    public Util(Main main) {
        super(main);
    }

    public boolean isHolySwordItem(ItemStack item) {
        NamedItemStack namedItem = new NamedItemStack(item);
        return namedItem.getName().equals("Holy Sword");
    }

    public ItemStack getHolySwordItem() {
        NamedItemStack namedItem = new NamedItemStack(new ItemStack(Material.GOLD_SWORD, 1));
        namedItem.setName("Holy Sword");
        namedItem.setDescription("He doesn't dare come near", "when you carry this.");
        return namedItem.getItemStack();
    }

    public String addPluginName(String message) {
        return "[" + ChatColor.RED + "Herobrine" + ChatColor.WHITE + "] " + message;
    }

    public String addName(String message) {
        return "<" + ChatColor.RED + ((String) super.getInstance().getConfiguration().getObject("entityName")) + ChatColor.WHITE + "> " + message;
    }

    public ItemStack getNewBook() {
        BookItem book = new BookItem(new ItemStack(387, 1));
        book.setAuthor("Herobrine");
        book.setTitle("Hello.");
        book.setPages(new String[]{super.getInstance().getConfiguration().getBookMessage()});
        return book.getItemStack();
    }

    public boolean shouldAct(Player player) {
        int chance = super.getInstance().getConfiguration().getActionChance();
        if (player.getWorld().getTime() >= 13000 && player.getWorld().getTime() <= 14200 && (Boolean) super.getInstance().getConfiguration().getObject("moreOftenAtNight")) {
            chance = super.getInstance().getConfiguration().getActionChance() / 4;
        }
        int found = 0;
        for (Entity entity : player.getNearbyEntities(10D, 10D, 10D)) {
            if (entity instanceof Player) {
                found++;
            }
        }
        if (found <= 2) {
            chance = super.getInstance().getConfiguration().getActionChance() / 2;
        }
        if (player.getWorld().getName().equals("world_nightmare")) {
            chance = chance / 4;
        }
        return new Random().nextInt(chance) == 0;
    }

    public boolean shouldActIndifferent() {
        return new Random().nextInt(super.getInstance().getConfiguration().getActionChance()) == 0;
    }

    public boolean canPlace(Location loc) {
        return super.getInstance().getConfiguration().getAllowedBlocks().contains(loc.getBlock().getType());
    }

    public int getRandomInteger(int max) {
        if (new Random().nextBoolean()) {
            return -new Random().nextInt(max) + 1;
        } else {
            return new Random().nextInt(max) + 1;
        }
    }

    public Location getLocationInFrontOfPlayer(Player player, int blocks) {
        double rot = (player.getLocation().getYaw() - 90) % 360;
        if (rot < 0) {
            rot += 360.0;
        }
        if (0 <= rot && rot < 22.5) {
            return player.getLocation().getBlock().getRelative(BlockFace.NORTH, blocks).getLocation();
        } else if (22.5 <= rot && rot < 67.5) {
            return player.getLocation().getBlock().getRelative(BlockFace.NORTH_EAST, blocks).getLocation();
        } else if (67.5 <= rot && rot < 112.5) {
            return player.getLocation().getBlock().getRelative(BlockFace.EAST, blocks).getLocation();
        } else if (112.5 <= rot && rot < 157.5) {
            return player.getLocation().getBlock().getRelative(BlockFace.SOUTH_EAST, blocks).getLocation();
        } else if (157.5 <= rot && rot < 202.5) {
            return player.getLocation().getBlock().getRelative(BlockFace.SOUTH, blocks).getLocation();
        } else if (202.5 <= rot && rot < 247.5) {
            return player.getLocation().getBlock().getRelative(BlockFace.SOUTH_WEST, blocks).getLocation();
        } else if (247.5 <= rot && rot < 292.5) {
            return player.getLocation().getBlock().getRelative(BlockFace.WEST, blocks).getLocation();
        } else if (292.5 <= rot && rot < 337.5) {
            return player.getLocation().getBlock().getRelative(BlockFace.NORTH_WEST, blocks).getLocation();
        } else if (337.5 <= rot && rot < 360.0) {
            return player.getLocation().getBlock().getRelative(BlockFace.NORTH, blocks).getLocation();
        } else {
            return null;
        }
    }

    public ArrayList<String> getWebsiteContents(URL url) throws Exception {
        ArrayList<String> fileContents = new ArrayList<String>();
        if (url != null) {
            DataInputStream fileReader = new DataInputStream(new BufferedInputStream(url.openStream()));
            String fileLine = "";
            while ((fileLine = fileReader.readLine()) != null) {
                if (!fileLine.equals("")) {
                    fileContents.add(fileLine);
                }
            }
            fileReader.close();
        } else {
            throw new Exception();
        }
        return fileContents;
    }

    public Location getNearbyLocation(Location loc) {
        return loc.add(this.getRandomInteger(3), 0, this.getRandomInteger(3));
    }
}
