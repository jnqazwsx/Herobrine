package com.nkrecklow.herobrine;

import com.nkrecklow.herobrine.api.ActionManager;
import com.nkrecklow.herobrine.misc.WorldGenerator;
import com.nkrecklow.herobrine.mob.MobController;
import com.nkrecklow.herobrine.mob.MobListener;
import java.util.logging.Logger;
import org.bukkit.ChatColor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private MobListener listener;
    private Config config;
    private ActionManager actions;
    private Util util;
    private WorldGenerator world;
    private MobController controller;

    @Override
    public void onEnable() {
        this.listener = new MobListener(this);
        this.controller = new MobController(this);
        this.config = new Config(this);
        this.actions = new ActionManager(this);
        this.util = new Util(this);
        this.world = new WorldGenerator(this);
        this.getCommand("hb").setExecutor(new Commands(this));
        this.getServer().getPluginManager().registerEvents(this.listener, this);
        this.config.loadConfig();
        this.world.loadWorld();
        if ((Boolean) this.config.getObject("collectStats")) {
            this.getServer().getScheduler().scheduleAsyncRepeatingTask(this, new Runnable() {
                
                @Override
                public void run() {
                    new Snooper(Main.this).start();
                }
            }, 0L, 600L);
        }
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

    public void logEvent(String data) {
        if ((Boolean) this.config.getObject("logEvents")) {
            this.log(data);
        }
    }

    public void log(String data) {
        Logger.getLogger("Minecraft").info(ChatColor.stripColor(Util.formatString(data)));
    }

    public MobController getMobController() {
        return this.controller;
    }

    public Util getUtil() {
        return this.util;
    }

    public Config getConfiguration() {
        return this.config;
    }

    public ActionManager getActionManager() {
        return this.actions;
    }

    public WorldGenerator getWorldGenerator() {
        return this.world;
    }
}
