package com.nkrecklow.herobrine;

import com.nkrecklow.herobrine.events.Actions;
import com.nkrecklow.herobrine.misc.NamedItemStack;
import com.nkrecklow.herobrine.mob.Mob;
import com.nkrecklow.herobrine.mob.MobListener;
import com.topcat.npclib.NPCManager;
import com.topcat.npclib.entity.HumanNPC;
import java.util.Random;
import java.util.logging.Logger;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private NPCManager manager;
    private Mob mob;
    private MobListener listener;
    private Config config;
    private Actions actions;
    private Snooper snooper;
    private Util util;
    private String id;

    @Override
    public void onEnable() {
        this.id = "";
        this.manager = new NPCManager(this);
        this.listener = new MobListener(this);
        this.config = new Config(this);
        this.actions = new Actions(this);
        this.snooper = new Snooper(this);
        this.util = new Util(this);
        this.getCommand("hb").setExecutor(new Commands(this));
        this.getServer().getPluginManager().registerEvents(this.listener, this);
        this.config.loadConfig();
        while (this.id.length() < 20) {
            this.id += Integer.toString(new Random().nextInt(9));
        }
        this.log("Using entity ID: #" + this.id + ".");
        if ((Boolean) this.config.getObject("collectStats")) {
            this.getServer().getScheduler().scheduleAsyncRepeatingTask(this, new Runnable() {
                @Override
                public void run() {
                    try {
                        snooper.start();
                    } catch (Exception ex) {
                    }
                }
            }, 0L, 3600L);
        }
    }

    public void logEvent(String data) {
        if ((Boolean) this.config.getObject("logEvents")) {
            this.log(data);
        }
    }

    public void log(String data) {
        Logger.getLogger("Minecraft").info(this.util.addPluginName(data));
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
            this.mob = new Mob((HumanNPC) this.manager.spawnHumanNPC((String) this.config.getObject("entityName"), loc, this.id), this.util.getRandomPosition());
            this.mob.getNpc().moveTo(loc);
            this.mob.lookAtVirtualPlayer(loc);
            this.mob.getNpc().setItemInHand(Material.getMaterial((Integer) this.config.getObject("itemInHand")));
            this.log("Spawned Herobrine at X: " + loc.getBlockX() + ", Y: " + loc.getBlockY() + ", Z: " + loc.getBlockZ() + ".");
            if (new Random().nextInt(1000) > 0) {
                boolean type = new Random().nextBoolean();
                ItemStack item = new ItemStack((type ? Material.GOLD_NUGGET : Material.SPIDER_EYE), 1);
                NamedItemStack namedItem = new NamedItemStack(item);
                if (type) {
                    namedItem.setName("Eye of Herobrine");
                    namedItem.setDescription("Some say it's evil.", "Others say it's blessed.");
                } else {
                    namedItem.setName("Holy Cross");
                    namedItem.setDescription("He doesn't dare come near", "when you carry this.");
                }
                Item droppedItem = this.mob.getEntity().getWorld().dropItem(loc, namedItem.getItemStack());
                droppedItem.setItemStack(namedItem.getItemStack());
            }
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
}
