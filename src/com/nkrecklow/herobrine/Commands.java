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
                if (args[0].equalsIgnoreCase("appear")) {
                    Player player = (Player) sender;
                    Player target = this.plugin.getServer().getPlayer(args[1]);
                    if (target == null) {
                        player.sendMessage(ChatColor.RED + "Invalid player!");
                        return true;
                    }
                    if (player.isOp()) {
                        if (this.plugin.getController().canSpawn(target.getWorld())) {
                            this.plugin.getActions().appearNear(target);
                            player.sendMessage(ChatColor.GREEN + "Herobrine has appeared near " + target.getName() + "!");
                        } else {
                            player.sendMessage(ChatColor.RED + "Herobrine is not allowed to spawn in " + target.getName() + "'s world!");
                            player.sendMessage(ChatColor.RED + "Please enable monsters in that world to continue!");
                        }
                    } else {
                        player.sendMessage(ChatColor.RED + "You do not have permission for this!");
                    }
                } else if (args[0].equalsIgnoreCase("bury")) {
                    Player player = (Player) sender;
                    Player target = this.plugin.getServer().getPlayer(args[1]);
                    if (target == null) {
                        player.sendMessage(ChatColor.RED + "Invalid player!");
                        return true;
                    }
                    if (player.isOp()) {
                        if (target.isOnline()) {
                            this.plugin.getActions().buryPlayer(target);
                            player.sendMessage(ChatColor.GREEN + "Herobrine has buried " + target.getName() + "!");
                        } else {
                            player.sendMessage(ChatColor.RED + "Player not found!");
                        }
                    }
                } else if (args[0].equalsIgnoreCase("remove")) {
                    Player player = (Player) sender;
                    if (player.isOp()) {
                        this.plugin.getController().getEntity().remove();
                        player.sendMessage(ChatColor.GREEN + "Herobrine has been removed!");
                    } else {
                        player.sendMessage(ChatColor.RED + "You do not have permission for this!");
                    }
                } else if (args[0].equalsIgnoreCase("attack")) {
                    Player player = (Player) sender;
                    Player target = this.plugin.getServer().getPlayer(args[1]);
                    if (target == null) {
                        player.sendMessage(ChatColor.RED + "Invalid player!");
                        return true;
                    }
                    if (player.isOp()) {
                        if (this.plugin.getController().canSpawn(target.getWorld())) {
                            this.plugin.getActions().attackPlayer(target);
                            player.sendMessage(ChatColor.GREEN + "Herobrine is now attacking " + target.getName() + "!");
                        } else {
                            player.sendMessage(ChatColor.RED + "Herobrine is not allowed to spawn in " + target.getName() + "'s world!");
                            player.sendMessage(ChatColor.RED + "Please enable monsters in that world to continue!");
                        }
                    } else {
                        player.sendMessage(ChatColor.RED + "You do not have permission for this!");
                    }
                } else if (args[0].equalsIgnoreCase("help")) {
                    Player player = (Player) sender;
                    ChatColor t = ChatColor.RED;
                    ChatColor w = ChatColor.WHITE;
                    player.sendMessage(t + "attack" + w + " - Attack a certain player.");
                    player.sendMessage(t + "appear" + w + " - Appear near a certain player.");
                    player.sendMessage(t + "bury" + w + " - Bury a certain player alive.");
                    player.sendMessage(t + "remove" + w + " - Remove him in case of error.");
                } else {
                    Player player = (Player) sender;
                    player.sendMessage(ChatColor.RED + "Not a valid command!");
                    player.sendMessage(ChatColor.RED + "Type '/hb help' for help");
                }
            } else {
                this.plugin.log("You must be a player to use this command!");
            }
        } catch (Exception ex) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                player.sendMessage(ChatColor.RED + "Type '/hb help' for help");
            } else {
                this.plugin.log("You must be a player to use this command!");
            }
        }
        return true;
    }
}
