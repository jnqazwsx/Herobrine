package com.nkrecklow.herobrine;

import java.util.Random;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

public class Events implements Listener {

    private Plugin plugin;

    public Events(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        Entity entity = event.getEntity();
        if (entity.equals(this.plugin.getController().getEntity())) {
            if (!event.getCause().equals(EntityDamageEvent.DamageCause.ENTITY_ATTACK)) {
                event.setCancelled(true);
                entity.setFireTicks(0);
            } else {
                event.setDamage(1);
            }
        }
    }

    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        if (event.getEntityType().equals(EntityType.ZOMBIE) && this.plugin.getController().isTracking() && this.plugin.getController().isDead()) {
            this.plugin.getController().setEntity(event.getEntity());
            this.plugin.getController().setTracking(false);
        }
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        Entity entity = event.getEntity();
        World world = event.getEntity().getWorld();
        if (entity.equals(this.plugin.getController().getEntity())) {
            world.dropItemNaturally(entity.getLocation(), new ItemStack(Material.GOLDEN_APPLE, 1));
            world.createExplosion(entity.getLocation(), -1F);
            this.plugin.getController().setAttacking(false);
            event.setDroppedExp(0);
            event.getDrops().clear();
            if (this.plugin.getSettings().canSendMessages()) {
                if (entity.getLastDamageCause() instanceof EntityDamageByEntityEvent) {
                    EntityDamageByEntityEvent ev = (EntityDamageByEntityEvent) entity.getLastDamageCause();
                    if (ev.getDamager() instanceof Player) {
                        Player p = (Player) ev.getDamager();
                        p.sendMessage(this.plugin.formatMessage(this.plugin.getSettings().getMessage()));
                    }
                }
            }
        }
    }
    
    @EventHandler
    public void onBlockIgnite(BlockIgniteEvent event) {
        Block block = event.getBlock();
        if (event.getCause().equals(BlockIgniteEvent.IgniteCause.FLINT_AND_STEEL)) {
            World world = event.getBlock().getWorld();
            Block netherRack = block.getLocation().subtract(0D, 1D, 0D).getBlock();
            Block mossyCobble = block.getLocation().subtract(0D, 2D, 0D).getBlock();
            if (netherRack.getType().equals(Material.NETHERRACK) && mossyCobble.getType().equals(Material.MOSSY_COBBLESTONE) && this.plugin.getController().isDead() && this.plugin.getController().canSpawn(event.getPlayer().getWorld())) {
                this.plugin.getController().setAttacking(true);
                if ((Boolean) this.plugin.getSettings().getObject("changeTime")) {
                    world.setStorm(true);
                    world.setTime(14200L);
                }
                world.strikeLightning(block.getLocation());
                world.createExplosion(block.getLocation(), -1F);
                if (this.plugin.getSettings().canSendMessages()) {
                    for (Player aPlayer : this.plugin.getServer().getOnlinePlayers()) {
                        aPlayer.sendMessage(this.plugin.formatMessage(this.plugin.getSettings().getMessage()));
                    }
                }
                this.plugin.getController().setTracking(true);
                world.spawnEntity(block.getLocation(), EntityType.ZOMBIE);
                this.plugin.getController().setTarget(event.getPlayer());
            }
        }
    }
    
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (!event.getPlayer().getGameMode().equals(GameMode.SURVIVAL) && (Boolean) this.plugin.getSettings().getObject("ignoreCreativePlayers")) {
            return;
        }
        int eventChoice = new Random().nextInt(this.plugin.getSettings().getActionChance() + 1);
        if (eventChoice == 1) {
            this.plugin.getActions().createTorch(event.getPlayer());
        } else if (eventChoice == 2) {
            this.plugin.getActions().createSign(event.getPlayer());
        } else if (eventChoice == 3) {
            this.plugin.getActions().playSound(event.getPlayer());
        } else if (eventChoice == 4) {
            this.plugin.getActions().appearNear(event.getPlayer());
        } else if (eventChoice == 5) {
            this.plugin.getActions().buryPlayer(event.getPlayer());
        } else if (eventChoice == 6) {
            this.plugin.getActions().sendMessage(event.getPlayer());
        } else if (eventChoice == 7) {
            this.plugin.getActions().spawnZombies(event.getPlayer());
        }
    }
}
