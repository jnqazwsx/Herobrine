package com.nkrecklow.herobrine.mob;

import com.nkrecklow.herobrine.Main;
import com.nkrecklow.herobrine.api.basic.Generic;
import com.topcat.npclib.NPCManager;
import com.topcat.npclib.entity.HumanNPC;
import java.util.Random;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.EntityType;

public class MobController extends Generic {

    private NPCManager manager;
    private Mob mob;
    
    public MobController(Main instance) {
        super(instance);
        this.manager = new NPCManager(super.getInstance());
    }
    
    public void despawnMob() {
        if (this.isSpawned()) {
            this.manager.despawnById("192051111942135");
            this.mob = null;
            super.getInstance().log("Despawned Herobrine!");
        }
    }

    public void spawnMob(Location loc, int duration) {
        if (this.mob == null) {
            this.mob = new Mob((HumanNPC) this.manager.spawnHumanNPC((String) super.getInstance().getConfiguration().getObject("entityName"), loc, "192051111942135"));
            this.mob.lookAtVirtualPlayer(loc);
            this.mob.getNpc().setItemInHand(Material.getMaterial((Integer) super.getInstance().getConfiguration().getObject("itemInHand")));
            if (new Random().nextBoolean()) {
                int amount = new Random().nextInt(5) + 1;
                for (int id = 0; id < amount; id++) {
                    this.mob.getEntity().getWorld().spawnEntity(this.mob.getEntity().getLocation(), EntityType.BAT);
                }
            }
            if (new Random().nextBoolean()) {
                this.mob.getEntity().setFireTicks(duration);
            }
            super.getInstance().log("Spawned Herobrine at X: " + loc.getBlockX() + ", Y: " + loc.getBlockY() + ", Z: " + loc.getBlockZ() + ".");
        }
    }
    
    public boolean canSpawn(World world) {
        return super.getInstance().getConfiguration().getAllowedWorlds().contains(world.getName());
    }
    
    public Mob getMob() {
        return this.mob;
    }
    
    public boolean isSpawned() {
        return this.mob != null;
    }
}
