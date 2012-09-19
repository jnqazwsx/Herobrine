package com.nkrecklow.herobrine;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Random;
import java.util.logging.Logger;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public class Herobrine extends JavaPlugin {

    private static final Logger log = Logger.getLogger("Minecraft");
    private Events listener = new Events(this);
    public Boolean trackingEntity = false, isAttacking = false;
    public Entity hbEntity;
    private Actions actions = new Actions(this);
    private Config config = new Config();

    @Override
    public void onEnable() {
        String mainDirectory = "plugins/Herobrine";
        File configFile = new File(mainDirectory + File.separator + "Settings.properties");
        Properties settingsFile = new Properties();
        new File(mainDirectory).mkdir();
        if (!configFile.exists()) {
            try {
                configFile.createNewFile();
                FileOutputStream out = new FileOutputStream(configFile);
                settingsFile.put("modify-world", Boolean.toString(this.config.modifyWorld));
                settingsFile.put("send-messages", Boolean.toString(this.config.sendMessages));
                settingsFile.put("change-environment", Boolean.toString(this.config.changeEnvironment));
                settingsFile.put("remove-mossystone", Boolean.toString(this.config.removeMossyCobblestone));
                settingsFile.put("action-chance", Integer.toString(this.config.innerChance));
                settingsFile.put("fire-trails", Boolean.toString(this.config.fireTrails));
                settingsFile.store(out, "Configuration file for Herobrine 2.0");
            } catch (IOException ex) {
                Herobrine.log.info("[Herobrine] Failed to create the configuration file!");
            }
        } else {
            try {
                FileInputStream in = new FileInputStream(configFile);
                try {
                    settingsFile.load(in);
                    this.config.modifyWorld = Boolean.valueOf(settingsFile.getProperty("modify-world"));
                    this.config.sendMessages = Boolean.valueOf(settingsFile.getProperty("send-messages"));
                    this.config.changeEnvironment = Boolean.valueOf(settingsFile.getProperty("change-environment"));
                    this.config.removeMossyCobblestone = Boolean.valueOf(settingsFile.getProperty("remove-mossystone"));
                    this.config.innerChance = Integer.parseInt(settingsFile.getProperty("action-chance"));
                    this.config.fireTrails = Boolean.valueOf(settingsFile.getProperty("fire-trails"));
                } catch (IOException ex) {
                    Herobrine.log.info("[Herobrine] Failed to load the configuration file!");
                    getServer().getPluginManager().disablePlugin(this);
                }
            } catch (FileNotFoundException ex) {
                Herobrine.log.info("[Herobrine] Failed to load the configuration file!");
                getServer().getPluginManager().disablePlugin(this);
            }
        }
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(this.listener, this);
        getServer().getScheduler().scheduleAsyncRepeatingTask(this, new Runnable() {
            
            @Override
            public void run() {
                if (!isDead()) {
                    hbEntity.setVelocity(hbEntity.getLocation().getDirection().multiply(0.6D));
                    if (new Random().nextInt(4) == 0) {
                        hbEntity.setVelocity(new Vector(hbEntity.getVelocity().getBlockX(), 1.0D, hbEntity.getVelocity().getZ()));
                    }
                    if (config.fireTrails && isAttacking) {
                        Block b = hbEntity.getLocation().getBlock();
                        Block g = b.getLocation().subtract(0.0D, 1.0D, 0.0D).getBlock();
                        if (b.getType().equals(Material.AIR) && !g.getType().equals(Material.AIR)) {
                            b.setType(Material.FIRE);
                        }
                    }
                }
            }
        }, 0L, 20L);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (cmd.getName().equals("hb")) {
            try {
                if (sender instanceof Player) {
                    if (args[0].equalsIgnoreCase("appear")) {
                        Player p = (Player) sender;
                        Player target = getServer().getPlayer(args[1]);
                        if (p.isOp()) {
                            if (this.canSpawn(target.getWorld())) {
                                this.actions.appearNear(target);
                                p.sendMessage(ChatColor.GREEN + "Herobrine has appeared near " + target.getName() + "!");
                            } else {
                                p.sendMessage(ChatColor.RED + "Herobrine is not allowed to spawn in " + target.getName() + "'s world!");
                                p.sendMessage(ChatColor.RED + "Please enable monsters in that world to continue!");
                            }
                        } else {
                            p.sendMessage(ChatColor.RED + "You do not have permission for this!");
                        }
                    } else if (args[0].equalsIgnoreCase("bury")) {
                        Player p = (Player) sender;
                        Player target = getServer().getPlayer(args[1]);
                        if (p.isOp()) {
                            if (target.isOnline()) {
                                this.actions.buryPlayer(target);
                                p.sendMessage(ChatColor.GREEN + "Herobrine has buried " + target.getName() + "!");
                            } else {
                                p.sendMessage(ChatColor.RED + "Player not found!");
                            }
                        }
                    } else if (args[0].equalsIgnoreCase("remove")) {
                        Player p = (Player) sender;
                        if (p.isOp()) {
                            this.hbEntity.remove();
                            p.sendMessage(ChatColor.GREEN + "Herobrine has been removed!");
                        } else {
                            p.sendMessage(ChatColor.RED + "You do not have permission for this!");
                        }
                    } else if (args[0].equalsIgnoreCase("attack")) {
                        Player p = (Player) sender;
                        Player target = getServer().getPlayer(args[1]);
                        if (p.isOp()) {
                            if (this.canSpawn(target.getWorld())) {
                                this.actions.attackPlayer(target);
                                p.sendMessage(ChatColor.GREEN + "Herobrine is now attacking " + target.getName() + "!");
                            } else {
                                p.sendMessage(ChatColor.RED + "Herobrine is not allowed to spawn in " + target.getName() + "'s world!");
                                p.sendMessage(ChatColor.RED + "Please enable monsters in that world to continue!");
                            }
                        } else {
                            p.sendMessage(ChatColor.RED + "You do not have permission for this!");
                        }
                    } else if (args[0].equalsIgnoreCase("help")) {
                        Player p = (Player) sender;
                        ChatColor t = ChatColor.RED;
                        ChatColor w = ChatColor.WHITE;
                        p.sendMessage(t + "attack" + w + " - Attack a certain player.");
                        p.sendMessage(t + "appear" + w + " - Appear near a certain player.");
                        p.sendMessage(t + "bury" + w + " - Bury a certain player alive.");
                        p.sendMessage(t + "remove" + w + " - Remove him in case of error.");
                    } else {
                        Player p = (Player) sender;
                        p.sendMessage(ChatColor.RED + "Not a valid command!");
                        p.sendMessage(ChatColor.RED + "Type '/hb help' for help");
                    }
                } else {
                    Herobrine.log.info("[Herobrine] You must be a player to use this command!");
                }
            } catch (Exception ex) {
                if (sender instanceof Player) {
                    Player p = (Player) sender;
                    p.sendMessage(ChatColor.RED + "Type '/hb help' for help");
                } else {
                    Herobrine.log.info("[Herobrine] You must be a player to use this command!");
                }
            }
        }
        return true;
    }
    
    public String formatMessage(String msg) {
        return "<" + ChatColor.RED + "> " + msg;
    }

    public boolean isDead() {
        return this.hbEntity == null || this.hbEntity.isDead();
    }

    public boolean canSpawn(World w) {
        return w.getAllowMonsters();
    }
    
    public Config getSettings() {
        return this.config;
    }
    
    public Actions getActions() {
        return this.actions;
    }
}