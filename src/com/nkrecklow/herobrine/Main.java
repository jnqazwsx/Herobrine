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

    private static Main instance;
    private MobListener listener;
    private Config config;
    private ActionManager actions;
    private WorldGenerator world;
    private MobController controller;

    @Override
    public void onEnable() {
        Main.instance = this;
        this.listener = new MobListener();
        this.controller = new MobController();
        this.config = new Config();
        this.actions = new ActionManager();
        this.world = new WorldGenerator();
        this.getCommand("hb").setExecutor(new Commands());
        this.getServer().getPluginManager().registerEvents(this.listener, this);
        this.config.loadConfig();
        this.world.loadWorld();
        if ((Boolean) this.config.getObject("collectStats")) {
            this.getServer().getScheduler().scheduleAsyncRepeatingTask(this, new Runnable() {
                
                @Override
                public void run() {
                    new Snooper().start();
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
    
    public Config getConfiguration() {
        return this.config;
    }

    public ActionManager getActionManager() {
        return this.actions;
    }

    public WorldGenerator getWorldGenerator() {
        return this.world;
    }
    
    public static Main getInstance() {
        return Main.instance;
    }
}
