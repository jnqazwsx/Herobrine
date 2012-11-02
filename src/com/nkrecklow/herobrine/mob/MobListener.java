package com.nkrecklow.herobrine.mob;

import com.nkrecklow.herobrine.Main;
import com.nkrecklow.herobrine.Util;
import com.nkrecklow.herobrine.base.Generic;
import com.nkrecklow.herobrine.misc.NamedItemStack;
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
        if (super.getInstance().isSpawned() || !super.getInstance().canSpawn(event.getBlock().getWorld()) || !event.getCause().equals(IgniteCause.FLINT_AND_STEEL)) {
            return;
        }
        if ((Boolean) super.getInstance().getConfiguration().getObject("ignoreCreativePlayers") && event.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {
            return;
        }
        Block nether = event.getBlock().getLocation().subtract(0D, 1D, 0D).getBlock();
        Block moss = nether.getLocation().subtract(0D, 1D, 0D).getBlock();
        if (nether.getType().equals(Material.NETHERRACK) && moss.getType().equals(Material.MOSSY_COBBLESTONE) && (Boolean) super.getInstance().getConfiguration().getObject("allowAltar")) {
            if (event.getPlayer().getItemInHand() != null) {
                NamedItemStack item = new NamedItemStack(event.getPlayer().getItemInHand());
                if (!item.getName().equals("Eye of Herobrine")) {
                    return;
                } else {
                    event.getPlayer().setItemInHand(null);
                }
            } else {
                return;
            }
            event.getBlock().getWorld().strikeLightning(event.getBlock().getLocation());
            event.getBlock().getWorld().createExplosion(event.getBlock().getLocation(), -1F);
            if (super.getInstance().getConfiguration().canSendMessages()) {
                for (Player player : super.getInstance().getServer().getOnlinePlayers()) {
                    player.sendMessage(super.getInstance().getUtil().addName(super.getInstance().getConfiguration().getMessage()));
                }
            }
            event.getBlock().getWorld().setTime(14200L);
            event.getBlock().getWorld().setStorm(true);
            super.getInstance().logEvent("Someone lit an altar!");
            if (super.getInstance().getConfiguration().getActionChance() >= (super.getInstance().getConfiguration().getOriginalActionChance() / 4)) {
                super.getInstance().getConfiguration().setActionChance(super.getInstance().getConfiguration().getActionChance() / 2);
                super.getInstance().log("Action chance changed to " + super.getInstance().getConfiguration().getActionChance() + "!");
            }
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (super.getInstance().getUtil().shouldAct(event.getPlayer())) {
            super.getInstance().getActions().runAction(super.getInstance().getActions().getRandomType(), event.getPlayer(), null);
        }
        if (!super.getInstance().isSpawned() || !super.getInstance().canSpawn(event.getPlayer().getWorld())) {
            return;
        }
        if (super.getInstance().getMob().getTarget().equals(event.getPlayer().getName())) {
            if ((Boolean) super.getInstance().getConfiguration().getObject("ignoreCreativePlayers") && event.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {
                super.getInstance().despawnMob();
                return;
            }
            super.getInstance().getMob().lookAtPlayer(event.getPlayer());
            if (event.getPlayer().getLocation().distance(super.getInstance().getMob().getEntity().getLocation()) <= 3D) {
                super.getInstance().despawnMob();
            }
        }
    }
    
    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        if (!super.getInstance().isSpawned()) {
            return;
        }
        if (super.getInstance().getMob().getTarget().equals(event.getPlayer().getName())) {
            if (!event.getTo().getWorld().equals(super.getInstance().getMob().getEntity().getWorld())) {
                super.getInstance().despawnMob();
            }
        }
    }

    @EventHandler
    public void onServerPing(ServerListPingEvent event) {
        if (super.getInstance().getUtil().shouldActIndifferent()) {
            event.setMotd(super.getInstance().getConfiguration().getBookMessage());
            event.setMaxPlayers(0);
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (!super.getInstance().canSpawn(event.getPlayer().getWorld())) {
            return;
        }
        if (super.getInstance().getUtil().shouldActIndifferent() && event.getInventory().getType().equals(InventoryType.CHEST)) {
            if ((Boolean) super.getInstance().getConfiguration().getObject("stealFromChests")) {
                if (new Random().nextBoolean()) {
                    ItemStack item = event.getInventory().getItem(new Random().nextInt(26));
                    if (item != null) {
                        event.getInventory().remove(item);
                        super.getInstance().logEvent("Stole an item from " + event.getPlayer().getName() + "'s chest.");
                        return;
                    }
                }
            }
            if (event.getInventory().firstEmpty() != -1) {
                event.getInventory().setItem(event.getInventory().firstEmpty(), super.getInstance().getUtil().getNewBook());
                super.getInstance().logEvent("Placed a book into " + event.getPlayer().getName() + "'s chest.");
            }
        }
    }

    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent event) {
        if (!super.getInstance().canSpawn(event.getPlayer().getWorld())) {
            return;
        }
        if (super.getInstance().getUtil().shouldActIndifferent() && event.getInventory().getType().equals(InventoryType.CHEST)) {
            if ((Boolean) super.getInstance().getConfiguration().getObject("stealFromChests")) {
                if (new Random().nextBoolean()) {
                    ItemStack item = event.getInventory().getItem(new Random().nextInt(26));
                    if (item != null) {
                        event.getInventory().remove(item);
                        super.getInstance().logEvent("Stole an item from " + event.getPlayer().getName() + "'s chest.");
                        return;
                    }
                }
            }
            if (event.getInventory().firstEmpty() != -1) {
                event.getInventory().setItem(event.getInventory().firstEmpty(), super.getInstance().getUtil().getNewBook());
                super.getInstance().logEvent("Placed a book into " + event.getPlayer().getName() + "'s chest.");
            }
        }
    }
}
