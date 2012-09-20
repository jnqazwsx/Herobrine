package com.nkrecklow.herobrine;

import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;

public class Controller {

    private Plugin plugin;
    private boolean trackingEntity, isAttacking;
    private Entity entity;
    
    public Controller(Plugin plugin) {
        this.plugin = plugin;
        this.trackingEntity = false;
        this.isAttacking = false;
    }
    
    public void setTracking(boolean trackingEntity) {
        this.trackingEntity = trackingEntity;
    }
    
    public void setAttacking(boolean isAttacking) {
        this.isAttacking = isAttacking;
    }

    public void setTarget(Player player) {
        if (!this.isDead() && this.isAttacking) {
            Zombie zombie = (Zombie) this.entity;
            zombie.setTarget(player);
        }
    }
    
    public void setEntity(Entity entity) {
        this.entity = entity;
    }
    
    public Entity getEntity() {
        return this.entity;
    }
    
    public boolean isDead() {
        return this.entity == null || this.entity.isDead();
    }
    
    public boolean canSpawn(World world) {
        if (this.plugin.getSettings().getAllowedWorlds().contains(world.getName())) {
            return world.getAllowMonsters();
        } else {
            return false;
        }
    }
    
    public boolean isAttacking() {
        return this.isAttacking;
    }
    
    public boolean isTracking() {
        return this.trackingEntity;
    }
}
