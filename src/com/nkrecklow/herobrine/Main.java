package com.nkrecklow.herobrine;

import com.nkrecklow.herobrine.api.Actions;
import com.nkrecklow.herobrine.misc.WorldGenerator;
import com.nkrecklow.herobrine.mob.Mob;
import com.nkrecklow.herobrine.mob.MobListener;
import com.topcat.npclib.NPCManager;
import com.topcat.npclib.entity.HumanNPC;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Logger;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private NPCManager manager;
    private Mob mob;
    private MobListener listener;
    private Config config;
    private Actions actions;
    private Util util;
    private String id;
    private WorldGenerator world;

    @Override
    public void onEnable() {
        this.id = "";
        this.manager = new NPCManager(this);
        this.listener = new MobListener(this);
        this.config = new Config(this);
        this.actions = new Actions(this);
        this.util = new Util(this);
        this.world = new WorldGenerator(this);
        this.getCommand("hb").setExecutor(new Commands(this));
        this.getServer().getPluginManager().registerEvents(this.listener, this);
        this.config.loadConfig();
        while (this.id.length() < 20) {
            this.id += Integer.toString(new Random().nextInt(9));
        }
        this.log("Using entity ID: #" + this.id + ".");
        if (this.world.isEnabled()) {
            if (!this.world.exists()) {
                this.world.generateWorld();
            }
        }
        if ((Boolean) this.config.getObject("collectStats")) {
            this.getServer().getScheduler().scheduleAsyncRepeatingTask(this, new Runnable() {
                
                @Override
                public void run() {
                    new Snooper(Main.this).start();
                }
            }, 0L, 3600L);
        }
        if (this.world.isEnabled() && this.world.exists()) {
            this.getServer().getScheduler().scheduleAsyncRepeatingTask(this, new Runnable() {
                
                @Override
                public void run() {
                    for (LivingEntity entity : world.getWorld().getLivingEntities()) {
                        if (!world.getAllowedEntities().contains(entity.getType())) {
                            entity.remove();
                            world.getWorld().spawnEntity(entity.getLocation(), world.getRandomEntity());
                        }
                    }
                }
            }, 0L, 100L);
        }
    }

    public void logEvent(String data) {
        if ((Boolean) this.config.getObject("logEvents")) {
            this.log(data);
        }
    }

    public void log(String data) {
        Logger.getLogger("Minecraft").info(ChatColor.stripColor(this.util.addPluginName(data)));
    }

    public void despawnMob() {
        if (this.isSpawned()) {
            this.manager.despawnById(this.id);
            this.mob = null;
            this.log("Despawned Herobrine!");
        }
    }

    public void spawnMob(Location loc) {
        if (this.mob == null) {
            this.mob = new Mob((HumanNPC) this.manager.spawnHumanNPC((String) this.config.getObject("entityName"), loc, this.id));
            this.mob.getNpc().moveTo(loc);
            this.mob.lookAtVirtualPlayer(loc);
            this.mob.getNpc().setItemInHand(Material.getMaterial((Integer) this.config.getObject("itemInHand")));
            this.log("Spawned Herobrine at X: " + loc.getBlockX() + ", Y: " + loc.getBlockY() + ", Z: " + loc.getBlockZ() + ".");
        }
    }

    public boolean canSpawn(World world) {
        return this.config.getAllowedWorlds().contains(world.getName());
    }

    public boolean isSpawned() {
        return this.mob != null;
    }

    public Mob getMob() {
        return this.mob;
    }
    
    public Util getUtil() {
        return this.util;
    }

    public Config getConfiguration() {
        return this.config;
    }

    public Actions getActions() {
        return this.actions;
    }
    
    public WorldGenerator getWorldGenerator() {
        return this.world;
    }
}
