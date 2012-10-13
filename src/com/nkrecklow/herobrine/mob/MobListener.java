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
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
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
        if (new Random().nextInt(super.main.getConfiguration().getActionChance()) == 0) {
            super.main.getActions().runAction(super.main.getActions().getRandomActionType(), event.getPlayer());
        }
        if (!super.main.isHerobrineSpawned() || !super.main.canSpawn(event.getPlayer().getWorld())) {
            return;
        }
        if (super.main.getHerobrine().getTarget().equals(event.getPlayer().getName())) {
            if ((Boolean) super.main.getConfiguration().getObject("ignoreCreativePlayers") && event.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {
                event.getPlayer().setGameMode(GameMode.SURVIVAL);
            }
            super.main.getHerobrine().getNpc().moveTo(event.getFrom());
            if (super.main.getHerobrine().getNpc().getBukkitEntity().getLocation().distance(event.getTo()) <= 0.75) {
                event.getPlayer().damage(1);
                super.main.getHerobrine().getNpc().animateArmSwing();
            }
        }
    }
    
    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if (!super.main.isHerobrineSpawned() || !super.main.canSpawn(event.getEntity().getWorld())) {
            return;
        }
        if (super.main.getHerobrine().getNpc().getBukkitEntity().equals(event.getEntity())) {
            super.main.killHerobrine();
            event.getEntity().getWorld().dropItem(event.getEntity().getLocation(), super.main.getConfiguration().getItemDrop());
            event.setDroppedExp(0);
            event.getDrops().clear();
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
                event.setDamage(2);
            }
        }
    }
}
