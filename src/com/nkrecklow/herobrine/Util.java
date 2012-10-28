package com.nkrecklow.herobrine;

import com.nkrecklow.herobrine.api.BookItem;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Util {
    
    public static ItemStack getNewBook(Main main) {
        BookItem book = new BookItem(new ItemStack(387, 1));
        book.setAuthor("Herobrine");
        book.setTitle("Hello.");
        book.setPages(new String[] { main.getConfiguration().getBookMessage() });
        return book.getItemStack();
    }
    
    public static boolean shouldAct(Main main, Player player) {
        int chance = main.getConfiguration().getActionChance();
        if (player.getWorld().getTime() >= 13000 && player.getWorld().getTime() <= 14200 && (Boolean) main.getConfiguration().getObject("moreOftenAtNight")) {
            chance = main.getConfiguration().getActionChance() / 4;
        }
        if (chance == main.getConfiguration().getActionChance()) {
            int found = 0;
            for (Entity entity : player.getNearbyEntities(10D, 10D, 10D)) {
                if (entity instanceof Player) {
                    found++;
                }
            }
            if (found <= 2) {
                chance = main.getConfiguration().getActionChance() / 2;
            }
        }
        return new Random().nextInt(chance) == 0;
    }
    
    public static boolean shouldActIndifferent(Main main) {
        return new Random().nextInt(main.getConfiguration().getActionChance()) == 0;
    }

    public static boolean canPlace(Main main, Location loc) {
        return main.getConfiguration().getAllowedBlocks().contains(loc.getBlock().getType());
    }

    public static int getRandomInteger(int max) {
        if (new Random().nextBoolean()) {
            return -new Random().nextInt(max) + 1;
        } else {
            return new Random().nextInt(max) + 1;
        }
    }

    public static Location getLocationInFrontOfPlayer(Player player, int blocks) {
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

    public static ArrayList<String> getWebsiteContents(URL url) throws Exception {
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

    public static Location getNearbyLocation(Location loc) {
        return loc.add(Util.getRandomInteger(3), 0, Util.getRandomInteger(3));
    }
}
