package com.nkrecklow.herobrine.mob;

import com.nkrecklow.herobrine.Main;
import com.nkrecklow.herobrine.api.Action;
import com.nkrecklow.herobrine.api.basic.Generic;
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

public class MobListener extends Generic implements Listener {

    private int altarUses;
    
    public MobListener(Main instance) {
        super(instance);
        this.altarUses = 0;
    }

    @EventHandler
    public void onBlockIgnite(BlockIgniteEvent event) {
        if (!super.getInstance().getMobController().canSpawn(event.getBlock().getWorld()) || !event.getCause().equals(IgniteCause.FLINT_AND_STEEL)) {
            return;
        }
        if (new File(super.getInstance().getDataFolder() + "/living.yml").exists()) {
            for (Player player : super.getInstance().getServer().getOnlinePlayers()) {
                    super.getInstance().getMobController().getMob().sendMessage("I'm already here.", player);
                }
            return;
        }
        if ((Boolean) super.getInstance().getConfiguration().getObject("ignoreCreativePlayers") && event.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {
            return;
        }
        Block nether = event.getBlock().getLocation().subtract(0D, 1D, 0D).getBlock();
        Block moss = nether.getLocation().subtract(0D, 1D, 0D).getBlock();
        if (nether.getType().equals(Material.NETHERRACK) && moss.getType().equals(Material.MOSSY_COBBLESTONE) && (Boolean) super.getInstance().getConfiguration().getObject("allowAltar")) {
            super.getInstance().logEvent("Someone lit an altar.");
            if (this.altarUses < 2) {
                this.altarUses++;
                if (this.altarUses <= 1) {
                    for (Player player : super.getInstance().getServer().getOnlinePlayers()) {
                        super.getInstance().getMobController().getMob().sendMessage("How dare you challenge me...", player);
                    }
                } else {
                    for (Player player : super.getInstance().getServer().getOnlinePlayers()) {
                        super.getInstance().getMobController().getMob().sendMessage("You shouldn't do this...", player);
                    }
                }
            } else {
                nether.getWorld().createExplosion(nether.getLocation(), 4F);
                event.getPlayer().getWorld().setStorm(true);
                event.getPlayer().getWorld().setTime(14200L);
                for (Player player : super.getInstance().getServer().getOnlinePlayers()) {
                    super.getInstance().getMobController().getMob().sendMessage("I have been unleashed at last...", player);
                }
                try {
                    new File(super.getInstance().getDataFolder() + "/living.yml").createNewFile();
                    super.getInstance().log("Herobrine has been unleashed!");
                } catch (Exception ex) {
                    super.getInstance().log("Error: " + ex.getMessage());
                }
                this.altarUses = 0;
            }
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (super.getInstance().getUtil().shouldAct(event.getPlayer())) {
            super.getInstance().getActionManager().runAction(super.getInstance().getActionManager().getRandomType(), event.getPlayer(), null, true);
        }
        if (!super.getInstance().getMobController().isSpawned() || !super.getInstance().getMobController().canSpawn(event.getPlayer().getWorld())) {
            return;
        }
        if (super.getInstance().getMobController().getMob().getTarget().equals(event.getPlayer().getName())) {
            if ((Boolean) super.getInstance().getConfiguration().getObject("ignoreCreativePlayers") && event.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {
                super.getInstance().getMobController().despawnMob();
                return;
            }
            super.getInstance().getMobController().getMob().lookAtPlayer(event.getPlayer());
            if (event.getPlayer().getLocation().distance(super.getInstance().getMobController().getMob().getEntity().getLocation()) <= 3D) {
                super.getInstance().getMobController().despawnMob();
            }
        }
    }

    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        if (!super.getInstance().getMobController().isSpawned()) {
            return;
        }
        if (super.getInstance().getMobController().getMob().getTarget().equals(event.getPlayer().getName())) {
            if (!event.getTo().getWorld().equals(super.getInstance().getMobController().getMob().getEntity().getWorld())) {
                super.getInstance().getMobController().despawnMob();
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
        if (!super.getInstance().getMobController().canSpawn(event.getPlayer().getWorld())) {
            return;
        }
        if (super.getInstance().getUtil().shouldActIndifferent() && event.getInventory().getType().equals(InventoryType.CHEST)) {
            if (!super.getInstance().getConfiguration().canRunAction("StealItems")) {
                if (new Random().nextBoolean()) {
                    ItemStack item = event.getInventory().getItem(new Random().nextInt(26));
                    if (item != null) {
                        event.getInventory().remove(item);
                        super.getInstance().logEvent("Stole an item from " + event.getPlayer().getName() + "'s chest.");
                        return;
                    }
                }
            }
            if (!super.getInstance().getConfiguration().canRunAction("CreateBooks")) {
                if (event.getInventory().firstEmpty() != -1) {
                    event.getInventory().setItem(event.getInventory().firstEmpty(), CustomItems.createBook("Hello.", "Herobrine", super.getInstance().getConfiguration().getBookMessage()));
                    super.getInstance().logEvent("Placed a book into " + event.getPlayer().getName() + "'s chest.");
                }
            }
        }
    }

    @EventHandler
    public void onPlayerEnterBed(PlayerBedEnterEvent event) {
        if (super.getInstance().getUtil().shouldAct(event.getPlayer())) {
            super.getInstance().getActionManager().runAction(Action.ActionType.ENTER_NIGHTMARE, event.getPlayer(), null, false);
        }
    }
}
