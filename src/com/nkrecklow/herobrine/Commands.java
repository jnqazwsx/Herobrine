package com.nkrecklow.herobrine;

import com.nkrecklow.herobrine.base.Generic;
import com.nkrecklow.herobrine.events.ActionType;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands extends Generic implements CommandExecutor {

    public Commands(Main plugin) {
        super(plugin);
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (args.length > 0) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (!player.isOp()) {
                    player.sendMessage("[" + ChatColor.RED + "Herobrine" + ChatColor.WHITE + "] " + "You do not have permission for this!");
                    return true;
                }
                if (args[0].equalsIgnoreCase("appear")) {
                    if (args.length == 2) {
                        Player target = super.main.getServer().getPlayer(args[1]);
                        if (target == null) {
                            player.sendMessage("[" + ChatColor.RED + "Herobrine" + ChatColor.WHITE + "] " + "I can't seem to find that player!");
                            return true;
                        }
                        if (super.main.canSpawn(target.getWorld())) {
                            if (!super.main.isHerobrineSpawned()) {
                                super.main.getActions().runAction(ActionType.APPEAR, target);
                                player.sendMessage("[" + ChatColor.RED + "Herobrine" + ChatColor.WHITE + "] " + "Herobrine has appeared near " + target.getName() + "!");
                            } else {
                                player.sendMessage("[" + ChatColor.RED + "Herobrine" + ChatColor.WHITE + "] " + "Herobrine is currently doing something!");
                            }
                        } else {
                            player.sendMessage("[" + ChatColor.RED + "Herobrine" + ChatColor.WHITE + "] " + target.getName() + "'s world (" + target.getWorld().getName() + ") is not in Herobrine's configuration!");
                        }
                    } else {
                        player.sendMessage("[" + ChatColor.RED + "Herobrine" + ChatColor.WHITE + "] " + "Not a valid command! Type \"/hb appear username\"!");
                    }
                } else if (args[0].equalsIgnoreCase("bury")) {
                    if (args.length == 2) {
                        Player target = super.main.getServer().getPlayer(args[1]);
                        if (target == null) {
                            player.sendMessage("[" + ChatColor.RED + "Herobrine" + ChatColor.WHITE + "] " + "I can't seem to find that player!");
                            return true;
                        }
                        if (super.main.canSpawn(target.getWorld())) {
                            super.main.getActions().runAction(ActionType.BURY_PLAYER, target);
                            player.sendMessage("[" + ChatColor.RED + "Herobrine" + ChatColor.WHITE + "] " + "Herobrine has buried " + target.getName() + "!");
                        } else {
                            player.sendMessage("[" + ChatColor.RED + "Herobrine" + ChatColor.WHITE + "] " + target.getName() + "'s world (" + target.getWorld().getName() + ") is not in Herobrine's configuration!");
                        }
                    } else {
                        player.sendMessage("[" + ChatColor.RED + "Herobrine" + ChatColor.WHITE + "] " + "Not a valid command! Type \"/hb bury username\"!");
                    }
                } else if (args[0].equalsIgnoreCase("kill")) {
                    if (super.main.isHerobrineSpawned()) {
                        super.main.killHerobrine();
                        player.sendMessage("[" + ChatColor.RED + "Herobrine" + ChatColor.WHITE + "] " + "Herobrine has been killed!");
                    } else {
                        player.sendMessage("[" + ChatColor.RED + "Herobrine" + ChatColor.WHITE + "] " + "Herobrine currently isn't alive!");
                    }
                } else if (args[0].equalsIgnoreCase("placesign")) {
                    if (args.length == 2) {
                        Player target = super.main.getServer().getPlayer(args[1]);
                        if (target == null) {
                            player.sendMessage("[" + ChatColor.RED + "Herobrine" + ChatColor.WHITE + "] " + "I can't seem to find that player!");
                            return true;
                        }
                        if (super.main.canSpawn(target.getWorld())) {
                            super.main.getActions().runAction(ActionType.PLACE_SIGN, target);
                            player.sendMessage("[" + ChatColor.RED + "Herobrine" + ChatColor.WHITE + "] " + "Herobrine created a sign by " + target.getName() + "!");
                        } else {
                            player.sendMessage("[" + ChatColor.RED + "Herobrine" + ChatColor.WHITE + "] " + target.getName() + "'s world (" + target.getWorld().getName() + ") is not in Herobrine's configuration!");
                        }
                    } else {
                        player.sendMessage("[" + ChatColor.RED + "Herobrine" + ChatColor.WHITE + "] " + "Not a valid command! Type \"/hb placesign username\"!");
                    }
                } else if (args[0].equalsIgnoreCase("playsound")) {
                    if (args.length == 2) {
                        Player target = super.main.getServer().getPlayer(args[1]);
                        if (target == null) {
                            player.sendMessage("[" + ChatColor.RED + "Herobrine" + ChatColor.WHITE + "] " + "I can't seem to find that player!");
                            return true;
                        }
                        if (super.main.canSpawn(target.getWorld())) {
                            super.main.getActions().runAction(ActionType.PLAY_SOUND, target);
                            player.sendMessage("[" + ChatColor.RED + "Herobrine" + ChatColor.WHITE + "] " + "Herobrine played a sound for " + target.getName() + "!");
                        } else {
                            player.sendMessage("[" + ChatColor.RED + "Herobrine" + ChatColor.WHITE + "] " + target.getName() + "'s world (" + target.getWorld().getName() + ") is not in Herobrine's configuration!");
                        }
                    } else {
                        player.sendMessage("[" + ChatColor.RED + "Herobrine" + ChatColor.WHITE + "] " + "Not a valid command! Type \"/hb playsound username\"!");
                    }
                } else if (args[0].equalsIgnoreCase("placetorch")) {
                    if (args.length == 2) {
                        Player target = super.main.getServer().getPlayer(args[1]);
                        if (target == null) {
                            player.sendMessage("[" + ChatColor.RED + "Herobrine" + ChatColor.WHITE + "] " + "I can't seem to find that player!");
                            return true;
                        }
                        if (super.main.canSpawn(target.getWorld())) {
                            super.main.getActions().runAction(ActionType.PLACE_TORCH, target);
                            player.sendMessage("[" + ChatColor.RED + "Herobrine" + ChatColor.WHITE + "] " + "Herobrine created a torch by " + target.getName() + "!");
                        } else {
                            player.sendMessage("[" + ChatColor.RED + "Herobrine" + ChatColor.WHITE + "] " + target.getName() + "'s world (" + target.getWorld().getName() + ") is not in Herobrine's configuration!");
                        }
                    } else {
                        player.sendMessage("[" + ChatColor.RED + "Herobrine" + ChatColor.WHITE + "] " + "Not a valid command! Type \"/hb placetorch username\"!");
                    }
                } else if (args[0].equalsIgnoreCase("attack")) {
                    if (args.length == 2) {
                        Player target = super.main.getServer().getPlayer(args[1]);
                        if (target == null) {
                            player.sendMessage("[" + ChatColor.RED + "Herobrine" + ChatColor.WHITE + "] " + "I can't seem to find that player!");
                            return true;
                        }
                        if (super.main.canSpawn(target.getWorld())) {
                            if (!super.main.isHerobrineSpawned()) {
                                super.main.getActions().runAction(ActionType.ATTACK_PLAYER, target);
                                player.sendMessage("[" + ChatColor.RED + "Herobrine" + ChatColor.WHITE + "] " + "Herobrine is now attacking " + target.getName() + "!");
                            } else {
                                player.sendMessage("[" + ChatColor.RED + "Herobrine" + ChatColor.WHITE + "] " + "Herobrine is currently doing something!");
                            }
                        } else {
                            player.sendMessage("[" + ChatColor.RED + "Herobrine" + ChatColor.WHITE + "] " + target.getName() + "'s world (" + target.getWorld().getName() + ") is not in Herobrine's configuration!");
                        }
                    } else {
                        player.sendMessage("[" + ChatColor.RED + "Herobrine" + ChatColor.WHITE + "] " + "Not a valid command! Type \"/hb attack username\"!");
                    }
                } else if (args[0].equalsIgnoreCase("createchest")) {
                    if (args.length == 2) {
                        Player target = super.main.getServer().getPlayer(args[1]);
                        if (target == null) {
                            player.sendMessage("[" + ChatColor.RED + "Herobrine" + ChatColor.WHITE + "] " + "I can't seem to find that player!");
                            return true;
                        }
                        if (super.main.canSpawn(target.getWorld())) {
                            super.main.getActions().runAction(ActionType.RANDOM_CHEST, target);
                            player.sendMessage("[" + ChatColor.RED + "Herobrine" + ChatColor.WHITE + "] " + "Herobrine created a chest by " + target.getName() + "!");
                        } else {
                            player.sendMessage("[" + ChatColor.RED + "Herobrine" + ChatColor.WHITE + "] " + target.getName() + "'s world (" + target.getWorld().getName() + ") is not in Herobrine's configuration!");
                        }
                    } else {
                        player.sendMessage("[" + ChatColor.RED + "Herobrine" + ChatColor.WHITE + "] " + "Not a valid command! Type \"/hb createchest username\"!");
                    }
                } else if (args[0].equalsIgnoreCase("reload")) {
                    super.main.getConfiguration().loadConfig();
                    player.sendMessage("[" + ChatColor.RED + "Herobrine" + ChatColor.WHITE + "] " + "Reload configuration file!");
                } else if (args[0].equalsIgnoreCase("help")) {
                    player.sendMessage("[" + ChatColor.RED + "Herobrine" + ChatColor.WHITE + "] " + "attack" + ChatColor.WHITE + " - Attack a certain player.");
                    player.sendMessage("[" + ChatColor.RED + "Herobrine" + ChatColor.WHITE + "] " + "appear" + ChatColor.WHITE + " - Appear near a certain player.");
                    player.sendMessage("[" + ChatColor.RED + "Herobrine" + ChatColor.WHITE + "] " + "bury" + ChatColor.WHITE + " - Bury a certain player alive.");
                    player.sendMessage("[" + ChatColor.RED + "Herobrine" + ChatColor.WHITE + "] " + "placesign" + ChatColor.WHITE + " - Create a sign with a message.");
                    player.sendMessage("[" + ChatColor.RED + "Herobrine" + ChatColor.WHITE + "] " + "playsound" + ChatColor.WHITE + " - Play a random sound.");
                    player.sendMessage("[" + ChatColor.RED + "Herobrine" + ChatColor.WHITE + "] " + "placetorch" + ChatColor.WHITE + " - Place a torch nearby.");
                    player.sendMessage("[" + ChatColor.RED + "Herobrine" + ChatColor.WHITE + "] " + "createchest" + ChatColor.WHITE + " - Create a chest with contents.");
                    player.sendMessage("[" + ChatColor.RED + "Herobrine" + ChatColor.WHITE + "] " + "kill" + ChatColor.WHITE + " - Remove him in case of error.");
                    player.sendMessage("[" + ChatColor.RED + "Herobrine" + ChatColor.WHITE + "] " + "reload" + ChatColor.WHITE + " - Reload the configuration file.");
                } else {
                    player.sendMessage("[" + ChatColor.RED + "Herobrine" + ChatColor.WHITE + "] " + "Not a valid command! Type \"/hb help\" for help!");
                }
            } else {
                super.main.log("You must be a player to use this command!");
            }
        } else {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                player.sendMessage("[" + ChatColor.RED + "Herobrine" + ChatColor.WHITE + "] " + "Not a valid command! Type \"/hb help\" for help!");
            } else {
                super.main.log("You must be a player to use this command!");
            }
        }
        return true;
    }
}
