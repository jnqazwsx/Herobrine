package com.nkrecklow.herobrine.mob;

import com.nkrecklow.herobrine.Main;
import com.nkrecklow.herobrine.Util;
import com.nkrecklow.herobrine.api.Action;
import com.nkrecklow.herobrine.misc.CustomItems;
import java.io.File;
import java.util.Random;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockIgniteEvent.IgniteCause;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.inventory.ItemStack;

public class MobListener implements Listener {

    private int altarUses;

    public MobListener() {
        this.altarUses = 0;
    }

    @EventHandler
    public void onBlockIgnite(BlockIgniteEvent event) {
        if (!Main.getInstance().getMobController().canSpawn(event.getBlock().getWorld()) || !event.getCause().equals(IgniteCause.FLINT_AND_STEEL)) {
            return;
        }
        if (!Main.getInstance().getConfiguration().canRunAction("UseAltar")) {
            return;
        }
        if ((Boolean) Main.getInstance().getConfiguration().getObject("ignoreCreativePlayers") && event.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {
            return;
        }
        if (new File(Main.getInstance().getDataFolder() + "/living.yml").exists()) {
            for (Player player : Main.getInstance().getServer().getOnlinePlayers()) {
                Main.getInstance().getMobController().getMob().sendMessage("I'm already here.", player);
            }
            return;
        }
        Block nether = event.getBlock().getLocation().subtract(0D, 1D, 0D).getBlock();
        Block moss = nether.getLocation().subtract(0D, 1D, 0D).getBlock();
        if (nether.getType().equals(Material.NETHERRACK) && moss.getType().equals(Material.MOSSY_COBBLESTONE)) {
            Main.getInstance().logEvent("Someone lit an altar.");
            if (this.altarUses < 2) {
                this.altarUses++;
                if (this.altarUses <= 1) {
                    for (Player player : Main.getInstance().getServer().getOnlinePlayers()) {
                        Main.getInstance().getMobController().getMob().sendMessage("How dare you challenge me...", player);
                    }
                } else {
                    for (Player player : Main.getInstance().getServer().getOnlinePlayers()) {
                        Main.getInstance().getMobController().getMob().sendMessage("You shouldn't do this...", player);
                    }
                }
            } else {
                nether.getWorld().createExplosion(nether.getLocation(), 4F);
                event.getPlayer().getWorld().setStorm(true);
                event.getPlayer().getWorld().setTime(14200L);
                for (Player player : Main.getInstance().getServer().getOnlinePlayers()) {
                    Main.getInstance().getMobController().getMob().sendMessage("I have been unleashed at last...", player);
                }
                try {
                    new File(Main.getInstance().getDataFolder() + "/living.yml").createNewFile();
                    Main.getInstance().log("Herobrine has been unleashed!");
                } catch (Exception ex) {
                    Main.getInstance().log("Error: " + ex.getMessage());
                }
                this.altarUses = 0;
            }
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (Util.shouldAct(event.getPlayer())) {
            Main.getInstance().getActionManager().runAction(Main.getInstance().getActionManager().getRandomType(), event.getPlayer(), null, true);
        }
        if (!Main.getInstance().getMobController().isSpawned() || !Main.getInstance().getMobController().canSpawn(event.getPlayer().getWorld())) {
            return;
        }
        if (Main.getInstance().getMobController().getMob().getTarget().equals(event.getPlayer().getName())) {
            if ((Boolean) Main.getInstance().getConfiguration().getObject("ignoreCreativePlayers") && event.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {
                Main.getInstance().getMobController().despawnMob();
                return;
            }
            Main.getInstance().getMobController().getMob().lookAtPlayer(event.getPlayer());
            if (event.getPlayer().getLocation().distance(Main.getInstance().getMobController().getMob().getEntity().getLocation()) <= 3D) {
                Main.getInstance().getMobController().despawnMob();
            }
        }
    }

    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        if (!Main.getInstance().getMobController().isSpawned()) {
            return;
        }
        if (Main.getInstance().getMobController().getMob().getTarget().equals(event.getPlayer().getName())) {
            if (!event.getTo().getWorld().equals(Main.getInstance().getMobController().getMob().getEntity().getWorld())) {
                Main.getInstance().getMobController().despawnMob();
            }
        }
    }

    @EventHandler
    public void onServerPing(ServerListPingEvent event) {
        if (!Main.getInstance().getConfiguration().canRunAction("MOTDMessages")) {
            return;
        }
        if (Util.shouldActIndifferent()) {
            event.setMotd(Main.getInstance().getConfiguration().getBookMessage());
            event.setMaxPlayers(0);
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (!Main.getInstance().getMobController().canSpawn(event.getPlayer().getWorld())) {
            return;
        }
        if (Util.shouldActIndifferent() && event.getInventory().getType().equals(InventoryType.CHEST)) {
            if (Main.getInstance().getConfiguration().canRunAction("StealItems") && new Random().nextBoolean()) {
                ItemStack item = event.getInventory().getItem(new Random().nextInt(26));
                if (item != null) {
                    event.getInventory().remove(item);
                    Main.getInstance().logEvent("Stole an item from " + event.getPlayer().getName() + "'s chest.");
                    return;
                }
            }
            if (Main.getInstance().getConfiguration().canRunAction("CreateBooks") && new Random().nextBoolean()) {
                if (event.getInventory().firstEmpty() != -1) {
                    event.getInventory().setItem(event.getInventory().firstEmpty(), CustomItems.createBook("Hello.", "Herobrine", Main.getInstance().getConfiguration().getBookMessage()));
                    Main.getInstance().logEvent("Placed a book into " + event.getPlayer().getName() + "'s chest.");
                    return;
                }
            }
            if (Main.getInstance().getConfiguration().canRunAction("GiftHeads") && new Random().nextBoolean()) {
                if (event.getInventory().firstEmpty() != -1) {
                    event.getInventory().setItem(event.getInventory().firstEmpty(), CustomItems.createPlayerSkull(event.getPlayer().getName()));
                    Main.getInstance().logEvent("Placed a skull into " + event.getPlayer().getName() + "'s chest.");
                }
            }
        }
    }

    @EventHandler
    public void onPlayerEnterBed(PlayerBedEnterEvent event) {
        if (Util.shouldAct(event.getPlayer())) {
            Main.getInstance().getActionManager().runAction(Action.ActionType.ENTER_NIGHTMARE, event.getPlayer(), null, false);
        }
    }
}
