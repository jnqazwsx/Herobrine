package com.nkrecklow.herobrine.mob;

import com.nkrecklow.herobrine.Main;
import com.nkrecklow.herobrine.Util;
import com.nkrecklow.herobrine.api.MobTargettingThread;
import com.nkrecklow.herobrine.base.Generic;
import com.nkrecklow.herobrine.events.ActionsUtil;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockIgniteEvent.IgniteCause;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerMoveEvent;

public class MobListener extends Generic implements Listener {

    public MobListener(Main main) {
        super(main);
    }

    @EventHandler
    public void onBlockIgnite(BlockIgniteEvent event) {
        if (!event.getCause().equals(IgniteCause.FLINT_AND_STEEL)) {
            return;
        }
        if (super.main.isHerobrineSpawned() || !super.main.canSpawn(event.getBlock().getWorld())) {
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
            super.main.spawnHerobrine(event.getBlock().getLocation());
            super.main.getHerobrine().setTarget(event.getPlayer().getName());
            event.getBlock().getWorld().setTime(14200L);
            event.getBlock().getWorld().setStorm(true);
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (ActionsUtil.shouldAct(super.main, event.getPlayer())) {
            super.main.getActions().runAction(super.main.getActions().getRandomActionType(), event.getPlayer());
        }
        if (!super.main.isHerobrineSpawned() || !super.main.canSpawn(event.getPlayer().getWorld())) {
            return;
        }
        if (super.main.getHerobrine().getTarget().equals(event.getPlayer().getName())) {
            if (event.getTo().distance(super.main.getHerobrine().getNpc().getBukkitEntity().getLocation()) >= 25) {
                super.main.despawnHerobrine();
                return;
            }
            if ((Boolean) super.main.getConfiguration().getObject("ignoreCreativePlayers") && event.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {
                event.getPlayer().setGameMode(GameMode.SURVIVAL);
            }
            //MobTargettingThread target = new MobTargettingThread(super.main);
            //target.target(super.main.getHerobrine().getNpc().getBukkitEntity(), event.getPlayer());
            super.main.getHerobrine().getNpc().moveTo(Util.getLocationBehindPlayer(event.getPlayer(), 1));
            super.main.getHerobrine().getNpc().getBukkitEntity().getLocation().setPitch(event.getPlayer().getLocation().getPitch());
            super.main.getHerobrine().getNpc().getBukkitEntity().getLocation().setYaw(event.getPlayer().getLocation().getYaw());
        }
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if (!super.main.isHerobrineSpawned() || !super.main.canSpawn(event.getEntity().getWorld())) {
            return;
        }
        if (event.getEntityType().equals(EntityType.PLAYER)) {
            if (((Player) event.getEntity()).getName().equals(super.main.getHerobrine().getTarget())) {
                super.main.despawnHerobrine();
            }
        }
        if (super.main.getHerobrine().getNpc().getBukkitEntity().equals(event.getEntity())) {
            super.main.killHerobrine();
            event.getEntity().getWorld().dropItem(event.getEntity().getLocation(), super.main.getConfiguration().getItemDrop());
            event.setDroppedExp(0);
            event.getDrops().clear();
            if (super.main.getConfiguration().canSendMessages()) {
                for (Player player : super.main.getServer().getOnlinePlayers()) {
                    player.sendMessage(super.main.getMessageAsHerobrine(super.main.getConfiguration().getMessage()));
                }
            }
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (!super.main.isHerobrineSpawned() || !super.main.canSpawn(event.getEntity().getWorld())) {
            return;
        }
        if (super.main.getHerobrine().getNpc().getBukkitEntity().equals(event.getEntity())) {
            if (!event.getCause().equals(DamageCause.ENTITY_ATTACK)) {
                event.getEntity().setFireTicks(0);
                event.setCancelled(true);
            } else {
                event.setDamage(1);
            }
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (ActionsUtil.shouldActIndifferent(super.main) && event.getInventory().getType().equals(InventoryType.CHEST)) {
            if (event.getInventory().firstEmpty() != -1) {
                event.getInventory().setItem(event.getInventory().firstEmpty(), ActionsUtil.getNewBook());
            }
        }
    }

    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent event) {
        if (ActionsUtil.shouldActIndifferent(super.main) && event.getInventory().getType().equals(InventoryType.CHEST)) {
            if (event.getInventory().firstEmpty() != -1) {
                event.getInventory().setItem(event.getInventory().firstEmpty(), ActionsUtil.getNewBook());
            }
        }
    }
}
