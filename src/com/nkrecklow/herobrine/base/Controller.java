package com.nkrecklow.herobrine.base;

import com.nkrecklow.herobrine.Main;
import com.nkrecklow.herobrine.core.Generic;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;

public class Controller extends Generic {

    private boolean trackingEntity;
    private Zombie entity;
    
    public Controller(Main plugin) {
        super(plugin);
        this.trackingEntity = false;
    }
    
    public void setTracking(boolean trackingEntity) {
        this.trackingEntity = trackingEntity;
    }

    public void setTarget(Player player) {
        if (!this.isDead()) {
            this.entity.setTarget(player);
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
        return super.getPlugin().getConfiguration().getAllowedWorlds().contains(world.getName());
    }
 
    public boolean isTracking() {
        return this.trackingEntity;
    }
}
