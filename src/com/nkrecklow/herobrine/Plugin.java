package com.nkrecklow.herobrine;

import java.util.logging.Logger;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Plugin extends JavaPlugin {

    private Events listener;
    private Controller controller;
    private Actions actions;
    private Config config;

    @Override
    public void onEnable() {
        this.config = new Config(this);
        this.actions = new Actions(this);
        this.controller = new Controller(this);
        this.listener = new Events(this);
        this.config.loadConfig();
        this.getServer().getPluginManager().registerEvents(this.listener, this);
        getServer().getScheduler().scheduleAsyncRepeatingTask(this, new Runnable() {
            
            @Override
            public void run() {
                if (!controller.isDead()) {
                    controller.getEntity().setVelocity(controller.getEntity().getLocation().getDirection().multiply(0.7D));
                    if (config.canUseFireTrails() && controller.isAttacking()) {
                        Block location = controller.getEntity().getLocation().getBlock();
                        Block below = location.getLocation().subtract(0.0D, 1.0D, 0.0D).getBlock();
                        if (location.getType().equals(Material.AIR) && !below.getType().equals(Material.AIR)) {
                            below.setType(Material.FIRE);
                        }
                    }
                }
            }
        }, 0L, 20L);
    }
    
    public void log(String data) {
        Logger.getLogger("Minecraft").info("[Herobrine] " + data);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (cmd.getName().equals("hb")) {
            try {
                if (sender instanceof Player) {
                    if (args[0].equalsIgnoreCase("appear")) {
                        Player p = (Player) sender;
                        Player target = getServer().getPlayer(args[1]);
                        if (target == null) {
                            p.sendMessage(ChatColor.RED + "Invalid player!");
                            return true;
                        }
                        if (p.isOp()) {
                            if (this.controller.canSpawn(target.getWorld())) {
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
                        if (target == null) {
                            p.sendMessage(ChatColor.RED + "Invalid player!");
                            return true;
                        }
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
                            this.controller.getEntity().remove();
                            p.sendMessage(ChatColor.GREEN + "Herobrine has been removed!");
                        } else {
                            p.sendMessage(ChatColor.RED + "You do not have permission for this!");
                        }
                    } else if (args[0].equalsIgnoreCase("attack")) {
                        Player p = (Player) sender;
                        Player target = getServer().getPlayer(args[1]);
                        if (target == null) {
                            p.sendMessage(ChatColor.RED + "Invalid player!");
                            return true;
                        }
                        if (p.isOp()) {
                            if (this.controller.canSpawn(target.getWorld())) {
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
                    this.log("You must be a player to use this command!");
                }
            } catch (Exception ex) {
                if (sender instanceof Player) {
                    Player p = (Player) sender;
                    p.sendMessage(ChatColor.RED + "Type '/hb help' for help");
                } else {
                    this.log("You must be a player to use this command!");
                }
            }
        }
        return true;
    }
    
    public String formatMessage(String msg) {
        return "<" + ChatColor.RED + "Herobrine" + ChatColor.WHITE + "> " + msg;
    }

    public Config getSettings() {
        return this.config;
    }
    
    public Actions getActions() {
        return this.actions;
    }
    
    public Controller getController() {
        return this.controller;
    }
}