package com.nkrecklow.herobrine.entity;

import com.nkrecklow.herobrine.Generic;
import com.nkrecklow.herobrine.Plugin;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

public class Events extends Generic implements Listener {

    public Events(Plugin plugin) {
        super(plugin);
    }
    
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (!event.getPlayer().getGameMode().equals(GameMode.SURVIVAL) && (Boolean) super.getPlugin().getConfiguration().getObject("ignoreCreativePlayers")) {
            return;
        }
        if (super.getPlugin().getController().canSpawn(event.getPlayer().getWorld())) {
            super.getPlugin().getActions().runAction(super.getPlugin().getActions().getRandomActionType(), event.getPlayer());
        }
    }
    
    @EventHandler
    public void onBlockIgnite(BlockIgniteEvent event) {
        Block block = event.getBlock();
        if (event.getCause().equals(BlockIgniteEvent.IgniteCause.FLINT_AND_STEEL)) {
            World world = event.getBlock().getWorld();
            Block netherRack = block.getLocation().subtract(0D, 1D, 0D).getBlock();
            Block mossyCobble = block.getLocation().subtract(0D, 2D, 0D).getBlock();
            if ((Boolean) super.getPlugin().getConfiguration().getObject("allowAltar") && netherRack.getType().equals(Material.NETHERRACK) && mossyCobble.getType().equals(Material.MOSSY_COBBLESTONE) && super.getPlugin().getController().isDead() && super.getPlugin().getController().canSpawn(event.getPlayer().getWorld())) {
                if ((Boolean) super.getPlugin().getConfiguration().getObject("changeTime")) {
                    world.setStorm(true);
                    world.setTime(14200L);
                }
                world.strikeLightning(block.getLocation());
                world.createExplosion(block.getLocation(), -1F);
                if (super.getPlugin().getConfiguration().canSendMessages()) {
                    for (Player aPlayer : super.getPlugin().getServer().getOnlinePlayers()) {
                        aPlayer.sendMessage(super.getPlugin().formatMessage(super.getPlugin().getConfiguration().getMessage()));
                    }
                }
                super.getPlugin().getController().setTracking(true);
                world.spawnEntity(block.getLocation(), EntityType.ZOMBIE);
                super.getPlugin().getController().setTarget(event.getPlayer());
            }
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        Entity entity = event.getEntity();
        if (entity.equals(super.getPlugin().getController().getEntity())) {
            if (!event.getCause().equals(DamageCause.ENTITY_ATTACK)) {
                event.setDamage(0);
                entity.setFireTicks(0);
                event.setCancelled(true);
            } else {
                event.setDamage((Integer) super.getPlugin().getConfiguration().getObject("damageAmount"));
                if (entity.getLastDamageCause() instanceof EntityDamageByEntityEvent) {
                    EntityDamageByEntityEvent ev = (EntityDamageByEntityEvent) entity.getLastDamageCause();
                    if (ev.getDamager() instanceof Player) {
                        super.getPlugin().getController().setTarget((Player) ev.getDamager());
                    }
                }
            }
        }
    }

    @EventHandler
    public void onEntitySpawn(CreatureSpawnEvent event) {
        if (!event.getSpawnReason().equals(SpawnReason.EGG)) {
            if (event.getEntityType().equals(EntityType.ZOMBIE) && super.getPlugin().getController().isTracking() && super.getPlugin().getController().isDead()) {
                super.getPlugin().getController().setEntity((Zombie) event.getEntity());
                super.getPlugin().getController().setTracking(false);
            }
        }
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        Entity entity = event.getEntity();
        World world = event.getEntity().getWorld();
        if (entity.equals(super.getPlugin().getController().getEntity())) {
            world.dropItemNaturally(entity.getLocation(), new ItemStack(Material.DIAMOND, 1));
            world.createExplosion(entity.getLocation(), -1F);
            event.setDroppedExp(0);
            event.getDrops().clear();
            if (super.getPlugin().getConfiguration().canSendMessages()) {
                if (entity.getLastDamageCause() instanceof EntityDamageByEntityEvent) {
                    EntityDamageByEntityEvent ev = (EntityDamageByEntityEvent) entity.getLastDamageCause();
                    if (ev.getDamager() instanceof Player) {
                        Player p = (Player) ev.getDamager();
                        p.sendMessage(super.getPlugin().formatMessage(super.getPlugin().getConfiguration().getMessage()));
                    }
                }
            }
        }
    }
}
