package com.nkrecklow.herobrine;

import com.nkrecklow.herobrine.events.Actions;
import com.nkrecklow.herobrine.mob.Mob;
import com.nkrecklow.herobrine.mob.MobListener;
import com.nkrecklow.herobrine.mob.MobPosition;
import com.topcat.npclib.NPCManager;
import com.topcat.npclib.entity.HumanNPC;
import java.util.Random;
import java.util.logging.Logger;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private NPCManager manager;
    private Mob mob;
    private MobListener listener;
    private Config config;
    private Actions actions;
    private Snooper snooper;
    private String id;

    @Override
    public void onEnable() {
        this.id = "";
        this.manager = new NPCManager(this);
        this.listener = new MobListener(this);
        this.config = new Config(this);
        this.actions = new Actions(this);
        this.snooper = new Snooper(this);
        this.getCommand("hb").setExecutor(new Commands(this));
        this.getServer().getPluginManager().registerEvents(this.listener, this);
        this.config.loadConfig();
        while (this.id.length() < 20) {
            this.id += Integer.toString(new Random().nextInt(9));
        }
        this.log("Using entity ID: #" + this.id + ".", false);
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

    public void log(String data, boolean event) {
        if (event) {
            if ((Boolean) this.config.getObject("logEvents")) {
                Logger.getLogger("Minecraft").info("[Herobrine] " + data);
            }
        } else {
            Logger.getLogger("Minecraft").info("[Herobrine] " + data);
        }
    }

    public void killHerobrine() {
        if (this.isHerobrineSpawned()) {
            this.manager.despawnById(this.id);
            this.mob = null;
            this.log("Despawned Herobrine!", false);
        }
    }

    public void spawnHerobrine(Location loc) {
        if (this.mob == null) {
            this.mob = new Mob((HumanNPC) this.manager.spawnHumanNPC((String) this.config.getObject("entityName"), loc, this.id), Util.getRandomPosition());
            this.mob.getNpc().moveTo(loc);
            this.mob.lookAtVirtualPlayer(loc);
            this.mob.getNpc().setItemInHand(Material.getMaterial((Integer) this.config.getObject("itemInHand")));
            this.log("Spawned Herobrine at X: " + loc.getBlockX() + ", Y: " + loc.getBlockY() + ", Z: " + loc.getBlockZ() + ".", false);
        }
    }

    public String getMessageAsHerobrine(String message) {
        return "<" + ChatColor.RED + ((String) this.config.getObject("entityName")) + ChatColor.WHITE + "> " + message;
    }

    public boolean canSpawn(World world) {
        return this.config.getAllowedWorlds().contains(world.getName());
    }

    public boolean isHerobrineSpawned() {
        return this.mob != null;
    }

    public Mob getHerobrine() {
        return this.mob;
    }

    public Config getConfiguration() {
        return this.config;
    }

    public Actions getActions() {
        return this.actions;
    }
}
