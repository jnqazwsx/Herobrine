package com.nkrecklow.herobrine.mob;

import com.nkrecklow.herobrine.Main;
import com.nkrecklow.herobrine.api.basic.Generic;
import com.topcat.npclib.NPCManager;
import com.topcat.npclib.entity.HumanNPC;
import java.util.Random;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.potion.PotionEffectType;

public class MobController extends Generic {

    private NPCManager manager;
    private Mob mob;

    public MobController(Main instance) {
        super(instance);
        this.manager = new NPCManager(super.getInstance());
    }

    public void despawnMob() {
        if (this.isSpawned()) {
            this.mob.getEntity().getWorld().playEffect(this.mob.getEntity().getLocation(), Effect.SMOKE, 1);
            super.getInstance().getServer().getPlayer(this.mob.getTarget()).removePotionEffect(PotionEffectType.WITHER);
            this.manager.despawnById("192051111942135");
            this.mob = null;
            super.getInstance().log("Despawned Herobrine!");
        }
    }

    public void spawnMob(Location loc) {
        if (this.mob == null) {
            String name = (String) super.getInstance().getConfiguration().getObject("entityName");
            if (super.getInstance().getUtil().shouldActIndifferent()) {
                name = "steaks4uce";
            }
            this.mob = new Mob((HumanNPC) this.manager.spawnHumanNPC(name, loc, "192051111942135"));
            this.mob.lookAtVirtualPlayer(loc);
            this.mob.getNpc().setItemInHand(Material.getMaterial((Integer) super.getInstance().getConfiguration().getObject("itemInHand")));
            this.mob.getNpc().updateEquipment();
            int amount = new Random().nextInt(2) + 1;
            for (int id = 0; id < amount; id++) {
                this.mob.getEntity().getWorld().spawnEntity(this.mob.getEntity().getLocation(), EntityType.BAT);
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
