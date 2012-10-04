package com.nkrecklow.herobrine;

import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;

public class Controller {

    private Plugin plugin;
    private boolean trackingEntity, isAttacking;
    private Zombie entity;
    
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
            this.entity.setTarget(player);
            if (!player.getGameMode().equals(GameMode.SURVIVAL)) {
                player.setGameMode(GameMode.SURVIVAL);
            }
        }
    }
    
    public void setEntity(Zombie entity) {
        this.entity = entity;
    }
    
    public Zombie getEntity() {
        return this.entity;
    }
    
    public boolean isDead() {
        return this.entity == null || this.entity.isDead();
    }
    
    public boolean canSpawn(World world) {
        return this.plugin.getSettings().getAllowedWorlds().contains(world.getName());
    }
    
    public boolean isAttacking() {
        return this.isAttacking;
    }
    
    public boolean isTracking() {
        return this.trackingEntity;
    }
}
