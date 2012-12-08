package com.nkrecklow.herobrine.mob;

import com.nkrecklow.herobrine.Main;
import com.topcat.npclib.NPCManager;
import com.topcat.npclib.entity.HumanNPC;
import java.util.Random;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.potion.PotionEffectType;

public class MobController {

    private NPCManager manager;
    private Mob mob;

    public MobController() {
        this.manager = new NPCManager(Main.getInstance());
    }

    public void despawnMob() {
        if (this.isSpawned()) {
            this.mob.getEntity().getWorld().playEffect(this.mob.getEntity().getLocation(), Effect.SMOKE, 1);
            if (!this.mob.getTarget().equals("")) {
                Main.getInstance().getServer().getPlayer(this.mob.getTarget()).removePotionEffect(PotionEffectType.WITHER);
            }
            this.manager.despawnById("192051111942135");
            this.mob = null;
            Main.getInstance().log("Despawned Herobrine!");
        }
    }

    public void spawnMob(Location loc) {
        if (!this.isSpawned()) {
            this.mob = new Mob((HumanNPC) this.manager.spawnHumanNPC((String) Main.getInstance().getConfiguration().getObject("entityName"), loc, "192051111942135"));
            this.mob.lookAtVirtualPlayer(loc);
            this.mob.getNpc().setItemInHand(Material.getMaterial((Integer) Main.getInstance().getConfiguration().getObject("itemInHand")));
            this.mob.getNpc().updateEquipment();
            int amount = new Random().nextInt(2) + 1;
            for (int id = 0; id < amount; id++) {
                this.mob.getEntity().getWorld().spawnEntity(this.mob.getEntity().getLocation(), EntityType.BAT);
            }
            Main.getInstance().log("Spawned Herobrine at X: " + loc.getBlockX() + ", Y: " + loc.getBlockY() + ", Z: " + loc.getBlockZ() + ".");
        }
    }

    public boolean canSpawn(World world) {
        return Main.getInstance().getConfiguration().getAllowedWorlds().contains(world.getName());
    }

    public Mob getMob() {
        return this.mob;
    }

    public boolean isSpawned() {
        return this.mob != null;
    }
}
