package com.nkrecklow.herobrine;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor {

    private Plugin plugin;
    
    public Commands(Plugin plugin) {
        this.plugin = plugin;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (cmd.getName().equals("hb")) {
            try {
                if (sender instanceof Player) {
                    if (args[0].equalsIgnoreCase("appear")) {
                        Player p = (Player) sender;
                        Player target = this.plugin.getServer().getPlayer(args[1]);
                        if (target == null) {
                            p.sendMessage(ChatColor.RED + "Invalid player!");
                            return true;
                        }
                        if (p.isOp()) {
                            if (this.plugin.getController().canSpawn(target.getWorld())) {
                                this.plugin.getActions().appearNear(target);
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
                        Player target = this.plugin.getServer().getPlayer(args[1]);
                        if (target == null) {
                            p.sendMessage(ChatColor.RED + "Invalid player!");
                            return true;
                        }
                        if (p.isOp()) {
                            if (target.isOnline()) {
                                this.plugin.getActions().buryPlayer(target);
                                p.sendMessage(ChatColor.GREEN + "Herobrine has buried " + target.getName() + "!");
                            } else {
                                p.sendMessage(ChatColor.RED + "Player not found!");
                            }
                        }
                    } else if (args[0].equalsIgnoreCase("remove")) {
                        Player p = (Player) sender;
                        if (p.isOp()) {
                            this.plugin.getController().getEntity().remove();
                            p.sendMessage(ChatColor.GREEN + "Herobrine has been removed!");
                        } else {
                            p.sendMessage(ChatColor.RED + "You do not have permission for this!");
                        }
                    } else if (args[0].equalsIgnoreCase("attack")) {
                        Player p = (Player) sender;
                        Player target = this.plugin.getServer().getPlayer(args[1]);
                        if (target == null) {
                            p.sendMessage(ChatColor.RED + "Invalid player!");
                            return true;
                        }
                        if (p.isOp()) {
                            if (this.plugin.getController().canSpawn(target.getWorld())) {
                                this.plugin.getActions().attackPlayer(target);
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
                    this.plugin.log("You must be a player to use this command!");
                }
            } catch (Exception ex) {
                if (sender instanceof Player) {
                    Player p = (Player) sender;
                    p.sendMessage(ChatColor.RED + "Type '/hb help' for help");
                } else {
                    this.plugin.log("You must be a player to use this command!");
                }
            }
        }
        return true;
    }
}
