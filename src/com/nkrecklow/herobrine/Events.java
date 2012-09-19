package com.nkrecklow.herobrine;

import com.nkrecklow.herobrine.Herobrine;
import java.util.Random;
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
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

public class Events implements Listener {

    private Herobrine plugin;

    public Events(Herobrine plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        Entity e = event.getEntity();
        if (e.equals(this.plugin.hbEntity)) {
            if (event.getCause() != EntityDamageEvent.DamageCause.ENTITY_ATTACK) {
                event.setCancelled(true);
                e.setFireTicks(0);
            } else {
                event.setDamage(1);
            }
        }
    }

    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        Entity e = event.getEntity();
        if (event.getEntityType().equals(EntityType.ZOMBIE) && this.plugin.trackingEntity && this.plugin.isDead()) {
            this.plugin.hbEntity = e;
            this.plugin.trackingEntity = false;
        }
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        Entity e = event.getEntity();
        World w = event.getEntity().getWorld();
        if (e.equals(this.plugin.hbEntity)) {
            w.dropItemNaturally(e.getLocation(), new ItemStack(Material.GOLDEN_APPLE, 1));
            w.createExplosion(e.getLocation(), -1.0F);
            this.plugin.isAttacking = false;
            event.setDroppedExp(50);
            event.getDrops().clear();
            if (this.plugin.getSettings().sendMessages) {
                if (e.getLastDamageCause() instanceof EntityDamageByEntityEvent) {
                    EntityDamageByEntityEvent ev = (EntityDamageByEntityEvent) e.getLastDamageCause();
                    if (ev.getDamager() instanceof Player) {
                        Player p = (Player) ev.getDamager();
                        p.sendMessage(this.plugin.formatMessage("I will prevail!"));
                    }
                }
            }
        }
    }
    
    @EventHandler
    public void onBlockIgnite(BlockIgniteEvent event) {
        Block b = event.getBlock();
        if (event.getCause().equals(BlockIgniteEvent.IgniteCause.FLINT_AND_STEEL)) {
            Player p = event.getPlayer();
            World w = event.getBlock().getWorld();
            Block netherRack = b.getLocation().subtract(0.0D, 1.0D, 0.0D).getBlock();
            Block mossyCobble = b.getLocation().subtract(0.0D, 2.0D, 0.0D).getBlock();
            if (netherRack.getType().equals(Material.NETHERRACK) && mossyCobble.getType().equals(Material.MOSSY_COBBLESTONE) && this.plugin.isDead() && this.plugin.canSpawn(p.getWorld())) {
                this.plugin.isAttacking = true;
                if (this.plugin.getSettings().changeEnvironment) {
                    w.setStorm(true);
                    w.setTime(14200L);
                }
                if (this.plugin.getSettings().removeMossyCobblestone) {
                    mossyCobble.setType(Material.COBBLESTONE);
                }
                w.strikeLightning(b.getLocation());
                w.createExplosion(b.getLocation(), -1.0F);
                if (this.plugin.getSettings().sendMessages) {
                    for (Player aPlayer : this.plugin.getServer().getOnlinePlayers()) {
                        aPlayer.sendMessage(this.plugin.formatMessage("I am your God! Bow to me!"));
                    }
                }
                this.plugin.trackingEntity = true;
                w.spawnEntity(b.getLocation(), EntityType.ZOMBIE);
                Zombie z = (Zombie) this.plugin.hbEntity;
                z.setTarget(p);
            }
        }
    }
    
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player p = event.getPlayer();
        int eventChoice = new Random().nextInt(this.plugin.getSettings().innerChance + 1);
        if (eventChoice == 1) {
            if (this.plugin.getSettings().modifyWorld) {
                this.plugin.getActions().createTorch(p);
            }
        } else if (eventChoice == 2) {
            if (this.plugin.getSettings().modifyWorld) {
                this.plugin.getActions().createSign(p);
            }
        } else if (eventChoice == 3) {
            this.plugin.getActions().playSound(p);
        } else if (eventChoice == 4) {
            this.plugin.getActions().attackPlayer(p);
        } else if (eventChoice == 5) {
            this.plugin.getActions().appearNear(p);
        } else if (eventChoice == 6) {
            if (this.plugin.getSettings().modifyWorld) {
                this.plugin.getActions().buryPlayer(p);
            }
        }
    }
}