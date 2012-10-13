package com.nkrecklow.herobrine;

import com.nkrecklow.herobrine.events.Actions;
import com.nkrecklow.herobrine.mob.Mob;
import com.nkrecklow.herobrine.mob.MobListener;
import com.topcat.npclib.NPCManager;
import com.topcat.npclib.entity.HumanNPC;
import java.util.logging.Logger;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private NPCManager manager;
    private Mob mob;
    private MobListener listener;
    private Config config;
    private Actions actions;
    private Snooper snooper;
    
    @Override
    public void onEnable() {
        this.manager = new NPCManager(this);
        this.listener = new MobListener(this);
        this.config = new Config(this);
        this.actions = new Actions(this);
        this.snooper = new Snooper(this);
        this.getCommand("hb").setExecutor(new Commands(this));
        this.getServer().getPluginManager().registerEvents(this.listener, this);
        this.config.loadConfig();this.getServer().getScheduler().scheduleAsyncRepeatingTask(this, new Runnable() {

            @Override
            public void run() {
                if (isHerobrineSpawned()) {
                    getHerobrine().getNpc().getBukkitEntity().setVelocity(getHerobrine().getNpc().getBukkitEntity().getLocation().getDirection().multiply(0.5D));
                    if ((Boolean) config.getObject("fireTrails")) {
                        Block location = getHerobrine().getNpc().getBukkitEntity().getLocation().getBlock();
                        Block below = location.getLocation().subtract(0D, 1D, 0D).getBlock();
                        if (location.getType().equals(Material.AIR) && !below.getType().equals(Material.AIR)) {
                            location.setType(Material.FIRE);
                        }
                    }
                    if ((Boolean) config.getObject("smashTorches")) {
                        for (int x = -3; x < 3; x++) {
                            for (int z = -3; z < 3; z++) {
                                for (int y = -3; y < 3; y++) {
                                    Block block = getHerobrine().getNpc().getBukkitEntity().getWorld().getBlockAt(x, y, z);
                                    if (block.getType().equals(Material.TORCH)) {
                                        block.setType(Material.AIR);
                                        block.getWorld().dropItem(block.getLocation(), new ItemStack(Material.TORCH, 1));
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }, 0L, 20L);
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
    
    public void log(String data) {
        Logger.getLogger("Minecraft").info("[Herobrine] " + data);
    }

    public void killHerobrine() {
        if (this.isHerobrineSpawned()) {
            this.manager.despawnById("1");
            this.mob = null;
        }
    }
    
    public void spawnHerobrine(Location loc) {
        if (this.mob == null) {
            this.mob = new Mob((HumanNPC) this.manager.spawnHumanNPC("Herobrine", loc, "1"));
            this.mob.getNpc().moveTo(loc);
            this.mob.getNpc().setItemInHand(Material.GOLD_SWORD);
        }
    }
    
    public String getMessageAsHerobrine(String message) {
        return "<" + ChatColor.RED + "Herobrine" + ChatColor.WHITE + "> " + message;
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
