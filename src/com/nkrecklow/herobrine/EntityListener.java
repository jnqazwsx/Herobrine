package com.nkrecklow.herobrine;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

public class EntityListener implements Listener {

    private Herobrine plugin;

    public EntityListener(Herobrine plugin) {
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
            if (this.plugin.sendMessages) {
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
}