package com.nkrecklow.herobrine.api;


import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class MobTargettingThread {
    
    private net.minecraft.server.Entity ec;
    private int id;
    private Entity entity;
    private Player player;
    private Plugin plugin;

    public MobTargettingThread(Plugin plugin) {
        this.plugin = plugin;
    }

    public void target(final Entity entity, final Player player) {
        this.entity = entity;
        this.player = player;
        this.ec = ((CraftEntity) this.entity).getHandle();
        this.id = Bukkit.getScheduler().scheduleSyncRepeatingTask(this.plugin, new Runnable() {
            @Override public void run() {
                if (!entity.isDead() && !player.isDead()) {
                    Location playerL = player.getLocation();
                    Location entityL = entity.getLocation();
                    World world = entity.getWorld();
                    double pX = playerL.getX();
                    double pZ = playerL.getZ();
                    double eX = entityL.getX();
                    double eZ = entityL.getZ();
                    double movX = eX - 0.3;
                    double movZ = eZ - 0.3;
                    if ((eX - pX) < 0) {
                        movX = eX + 0.3;
                    }
                    if ((eZ - pZ) < 0) {
                        movZ = eZ + 0.3;
                    }
                    ec.setPosition(movX, player.getLocation().getBlockY(), movZ);
                } else {
                    cancel();
                }
            }
        }, 1, 1);
    }
    
    public void cancel() {
        this.plugin.getServer().getScheduler().cancelTask(this.id);
    }
}