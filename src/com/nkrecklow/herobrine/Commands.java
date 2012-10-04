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
        try {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (args[1].equalsIgnoreCase("appear")) {
                    Player target = this.plugin.getServer().getPlayer(args[2]);
                    if (target == null) {
                        player.sendMessage(ChatColor.RED + "I can't seem to find that player!");
                        return true;
                    }
                    if (player.isOp()) {
                        if (this.plugin.getController().canSpawn(target.getWorld())) {
                            this.plugin.getActions().appearNear(target);
                            player.sendMessage(ChatColor.GREEN + "Herobrine has appeared near " + target.getName() + "!");
                        } else {
                            player.sendMessage(ChatColor.RED + "Herobrine is not allowed to spawn in " + target.getName() + "'s world!");
                        }
                    } else {
                        player.sendMessage(ChatColor.RED + "You do not have permission for this!");
                    }
                } else if (args[1].equalsIgnoreCase("bury")) {
                    Player target = this.plugin.getServer().getPlayer(args[2]);
                    if (target == null) {
                        player.sendMessage(ChatColor.RED + "I can't seem to find that player!");
                        return true;
                    }
                    if (player.isOp()) {
                        if (target.isOnline()) {
                            this.plugin.getActions().buryPlayer(target);
                            player.sendMessage(ChatColor.GREEN + "Herobrine has buried " + target.getName() + "!");
                        } else {
                            player.sendMessage(ChatColor.RED + "Player not found!");
                        }
                    } else {
                        player.sendMessage(ChatColor.RED + "You do not have permission for this!");
                    }
                } else if (args[1].equalsIgnoreCase("kill")) {
                    if (player.isOp()) {
                        if (!this.plugin.getController().isDead()) {
                            this.plugin.getController().getEntity().remove();
                            player.sendMessage(ChatColor.GREEN + "Herobrine has been killed!");
                        } else {
                            player.sendMessage(ChatColor.RED + "Herobrine currently isn't alive!");
                        }
                    } else {
                        player.sendMessage(ChatColor.RED + "You do not have permission for this!");
                    }
                } else if (args[1].equalsIgnoreCase("attack")) {
                    Player target = this.plugin.getServer().getPlayer(args[2]);
                    if (target == null) {
                        player.sendMessage(ChatColor.RED + "I can't seem to find that player!");
                        return true;
                    }
                    if (player.isOp()) {
                        if (this.plugin.getController().canSpawn(target.getWorld())) {
                            this.plugin.getActions().attackPlayer(target);
                            player.sendMessage(ChatColor.GREEN + "Herobrine is now attacking " + target.getName() + "!");
                        } else {
                            player.sendMessage(ChatColor.RED + "Herobrine is not allowed to spawn in " + target.getName() + "'s world!");
                        }
                    } else {
                        player.sendMessage(ChatColor.RED + "You do not have permission for this!");
                    }
                } else if (args[1].equalsIgnoreCase("help")) {
                    player.sendMessage(ChatColor.RED + "attack" + ChatColor.WHITE + " - Attack a certain player.");
                    player.sendMessage(ChatColor.RED + "appear" + ChatColor.WHITE + " - Appear near a certain player.");
                    player.sendMessage(ChatColor.RED + "bury" + ChatColor.WHITE + " - Bury a certain player alive.");
                    player.sendMessage(ChatColor.RED + "kill" + ChatColor.WHITE + " - Remove him in case of error.");
                } else {
                    player.sendMessage(ChatColor.RED + "Not a valid command! Type \"/hb help\" for help!");
                }
            } else {
                this.plugin.log("You must be a player to use this command!");
            }
        } catch (Exception ex) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                player.sendMessage(ChatColor.RED + "Type \"/hb help\" for help");
            } else {
                this.plugin.log("Not a valid command! Type \"/hb help\" for help!");
            }
        }
        return true;
    }
}
