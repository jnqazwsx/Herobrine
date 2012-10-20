package com.nkrecklow.herobrine;

import com.nkrecklow.herobrine.events.Actions;
import com.nkrecklow.herobrine.mob.Mob;
import com.nkrecklow.herobrine.mob.MobListener;
import com.topcat.npclib.NPCManager;
import com.topcat.npclib.entity.HumanNPC;
import java.util.Random;
import java.util.logging.Logger;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
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
        while (this.id.length() < 10) {
            this.id += Integer.toString(new Random().nextInt(9));
        }
        this.log("Using entity ID: #" + this.id + ".", false);
        this.getServer().getScheduler().scheduleAsyncRepeatingTask(this, new Runnable() {

            @Override
            public void run() {
                if (isHerobrineSpawned()) {
                    try {
                        if (!getHerobrine().getTarget().equals("")) {
                            if (getServer().getPlayer(getHerobrine().getTarget()) == null) {
                                despawnHerobrine();
                                return;
                            } else {
                                if (!getServer().getPlayer(getHerobrine().getTarget()).getWorld().equals(getHerobrine().getNpc().getBukkitEntity().getWorld())) {
                                    despawnHerobrine();
                                    return;
                                }
                                getHerobrine().getNpc().moveTo(Util.getLocationBehindPlayer(getServer().getPlayerExact(getHerobrine().getTarget()), 1));
                            }
                            int found = 0;
                            for (Entity entity : getHerobrine().getNpc().getBukkitEntity().getNearbyEntities(0.6D, 0.6D, 0.6D)) {
                                if (entity instanceof LivingEntity) {
                                    ((LivingEntity) entity).damage(1);
                                    found++;
                                }
                            }
                            if (found > 0) {
                                getHerobrine().getNpc().animateArmSwing();
                            }
                        }
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
                    } catch (Exception ex) {
                    }
                }  
            }
        }, 0L, 10L);
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
    
    public void despawnHerobrine() {
        if (this.isHerobrineSpawned()) {
            if (this.config.canSendMessages()) {
                for (Player player : this.getServer().getOnlinePlayers()) {
                    player.sendMessage(this.getMessageAsHerobrine(this.config.getMessage()));
                }
            }
            this.mob.getNpc().getBukkitEntity().getWorld().createExplosion(this.mob.getNpc().getBukkitEntity().getLocation(), -1F);
            this.killHerobrine();
        }
    }

    public void killHerobrine() {
        if (this.isHerobrineSpawned()) {
            this.manager.despawnById(this.id);
            this.mob = null;
        }
    }
    
    public void spawnHerobrine(Location loc) {
        if (this.mob == null) {
            this.mob = new Mob((HumanNPC) this.manager.spawnHumanNPC((String) this.config.getObject("entityName"), loc, this.id));
            this.mob.getNpc().moveTo(loc);
            this.mob.getNpc().setItemInHand(Material.GOLD_SWORD);
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
