package com.nkrecklow.herobrine.mob;

import com.nkrecklow.herobrine.Main;
import com.nkrecklow.herobrine.Util;
import com.nkrecklow.herobrine.base.Generic;
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
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.inventory.ItemStack;

public class MobListener extends Generic implements Listener {

    public MobListener(Main main) {
        super(main);
    }

    @EventHandler
    public void onBlockIgnite(BlockIgniteEvent event) {
        if (super.main.isHerobrineSpawned() || !super.main.canSpawn(event.getBlock().getWorld()) || !event.getCause().equals(IgniteCause.FLINT_AND_STEEL)) {
            return;
        }
        if ((Boolean) super.main.getConfiguration().getObject("ignoreCreativePlayers") && event.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {
            return;
        }
        Block nether = event.getBlock().getLocation().subtract(0D, 1D, 0D).getBlock();
        Block moss = nether.getLocation().subtract(0D, 1D, 0D).getBlock();
        if (nether.getType().equals(Material.NETHERRACK) && moss.getType().equals(Material.MOSSY_COBBLESTONE) && (Boolean) super.main.getConfiguration().getObject("allowAltar")) {
            event.getBlock().getWorld().strikeLightning(event.getBlock().getLocation());
            event.getBlock().getWorld().createExplosion(event.getBlock().getLocation(), -1F);
            if (super.main.getConfiguration().canSendMessages()) {
                for (Player player : super.main.getServer().getOnlinePlayers()) {
                    player.sendMessage(super.main.getMessageAsHerobrine(super.main.getConfiguration().getMessage()));
                }
            }
            event.getBlock().getWorld().setTime(14200L);
            event.getBlock().getWorld().setStorm(true);
            super.main.log("Someone lit an altar!", true);
            if (super.main.getConfiguration().getActionChance() >= (super.main.getConfiguration().getOriginalActionChance() / 4)) {
                super.main.getConfiguration().setActionChance(super.main.getConfiguration().getActionChance() / 2);
                super.main.log("Action chance changed to " + super.main.getConfiguration().getActionChance() + "!", true);
            }
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (Util.shouldAct(super.main, event.getPlayer())) {
            super.main.getActions().runAction(super.main.getActions().getRandomActionType(), event.getPlayer());
        }
        if (!super.main.isHerobrineSpawned() || !super.main.canSpawn(event.getPlayer().getWorld())) {
            return;
        }
        if (super.main.getHerobrine().getTarget().equals(event.getPlayer().getName())) {
            if ((Boolean) super.main.getConfiguration().getObject("ignoreCreativePlayers") && event.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {
                super.main.killHerobrine();
                return;
            }
            super.main.getHerobrine().lookAtPlayer(event.getPlayer());
            if (event.getPlayer().getLocation().distance(super.main.getHerobrine().getEntity().getLocation()) <= 3D) {
                super.main.killHerobrine();
            }
        }
    }
    
    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        if (!super.main.isHerobrineSpawned()) {
            return;
        }
        if (super.main.getHerobrine().getTarget().equals(event.getPlayer().getName())) {
            if (!event.getTo().getWorld().equals(super.main.getHerobrine().getEntity().getWorld())) {
                super.main.killHerobrine();
            }
        }
    }

    @EventHandler
    public void onServerPing(ServerListPingEvent event) {
        if (Util.shouldActIndifferent(super.main)) {
            event.setMotd(super.main.getConfiguration().getBookMessage());
            event.setMaxPlayers(0);
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (!super.main.canSpawn(event.getPlayer().getWorld())) {
            return;
        }
        if (Util.shouldActIndifferent(super.main) && event.getInventory().getType().equals(InventoryType.CHEST)) {
            if ((Boolean) super.main.getConfiguration().getObject("stealFromChests")) {
                if (new Random().nextBoolean()) {
                    ItemStack item = event.getInventory().getItem(new Random().nextInt(26));
                    if (item != null) {
                        event.getInventory().remove(item);
                        super.main.log("Stole an item from " + event.getPlayer().getName() + "'s chest.", true);
                        return;
                    }
                }
            }
            if (event.getInventory().firstEmpty() != -1) {
                event.getInventory().setItem(event.getInventory().firstEmpty(), Util.getNewBook(super.main));
                super.main.log("Placed a book into " + event.getPlayer().getName() + "'s chest.", true);
            }
        }
    }

    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent event) {
        if (!super.main.canSpawn(event.getPlayer().getWorld())) {
            return;
        }
        if (Util.shouldActIndifferent(super.main) && event.getInventory().getType().equals(InventoryType.CHEST)) {
            if ((Boolean) super.main.getConfiguration().getObject("stealFromChests")) {
                if (new Random().nextBoolean()) {
                    ItemStack item = event.getInventory().getItem(new Random().nextInt(26));
                    if (item != null) {
                        event.getInventory().remove(item);
                        super.main.log("Stole an item from " + event.getPlayer().getName() + "'s chest.", true);
                        return;
                    }
                }
            }
            if (event.getInventory().firstEmpty() != -1) {
                event.getInventory().setItem(event.getInventory().firstEmpty(), Util.getNewBook(super.main));
                super.main.log("Placed a book into " + event.getPlayer().getName() + "'s chest.", true);
            }
        }
    }
}
