package com.nkrecklow.herobrine.base;

import com.nkrecklow.herobrine.Main;
import com.nkrecklow.herobrine.api.ActionType;
import com.nkrecklow.herobrine.core.Generic;
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
                    player.sendMessage(ChatColor.RED + "You do not have permission for this!");
                    return true;
                }
                if (args[0].equalsIgnoreCase("appear")) {
                    if (args.length == 2) {
                        Player target = super.getPlugin().getServer().getPlayer(args[1]);
                        if (target == null) {
                            player.sendMessage(ChatColor.RED + "I can't seem to find that player!");
                            return true;
                        }
                        if (super.getPlugin().getController().canSpawn(target.getWorld())) {
                            if (super.getPlugin().getController().isDead()) {
                                super.getPlugin().getActions().runAction(ActionType.APPEAR, target);
                                player.sendMessage(ChatColor.GREEN + "Herobrine has appeared near " + target.getName() + "!");
                            } else {
                                player.sendMessage(ChatColor.RED + "Herobrine is currently doing something!");
                            }
                        } else {
                            player.sendMessage(ChatColor.RED + target.getName() + "'s world (" + target.getWorld().getName() + ") is not in Herobrine's configuration!");
                        }
                    } else {
                        player.sendMessage(ChatColor.RED + "Not a valid command! Type \"/hb appear username\"!");
                    }
                } else if (args[0].equalsIgnoreCase("bury")) {
                    if (args.length == 2) {
                        Player target = super.getPlugin().getServer().getPlayer(args[1]);
                        if (target == null) {
                            player.sendMessage(ChatColor.RED + "I can't seem to find that player!");
                            return true;
                        }
                        if (super.getPlugin().getController().canSpawn(target.getWorld())) {
                            super.getPlugin().getActions().runAction(ActionType.BURY_PLAYER, target);
                            player.sendMessage(ChatColor.GREEN + "Herobrine has buried " + target.getName() + "!");
                        } else {
                            player.sendMessage(ChatColor.RED + target.getName() + "'s world (" + target.getWorld().getName() + ") is not in Herobrine's configuration!");
                        }
                    } else {
                        player.sendMessage(ChatColor.RED + "Not a valid command! Type \"/hb bury username\"!");
                    }
                } else if (args[0].equalsIgnoreCase("kill")) {
                    if (!super.getPlugin().getController().isDead()) {
                        super.getPlugin().getController().getEntity().remove();
                        player.sendMessage(ChatColor.GREEN + "Herobrine has been killed!");
                    } else {
                        player.sendMessage(ChatColor.RED + "Herobrine currently isn't alive!");
                    }
                } else if (args[0].equalsIgnoreCase("attack")) {
                    if (args.length == 2) {
                        Player target = super.getPlugin().getServer().getPlayer(args[1]);
                        if (target == null) {
                            player.sendMessage(ChatColor.RED + "I can't seem to find that player!");
                            return true;
                        }
                        if (super.getPlugin().getController().canSpawn(target.getWorld())) {
                            if (super.getPlugin().getController().isDead()) {
                                super.getPlugin().getActions().runAction(ActionType.ATTACK_PLAYER, target);
                                player.sendMessage(ChatColor.GREEN + "Herobrine is now attacking " + target.getName() + "!");
                            } else {
                                player.sendMessage(ChatColor.RED + "Herobrine is currently doing something!");
                            }
                        } else {
                            player.sendMessage(ChatColor.RED + target.getName() + "'s world (" + target.getWorld().getName() + ") is not in Herobrine's configuration!");
                        }
                    } else {
                        player.sendMessage(ChatColor.RED + "Not a valid command! Type \"/hb attack username\"!");
                    }
                } else if (args[0].equalsIgnoreCase("placesign")) {
                    if (args.length == 2) {
                        Player target = super.getPlugin().getServer().getPlayer(args[1]);
                        if (target == null) {
                            player.sendMessage(ChatColor.RED + "I can't seem to find that player!");
                            return true;
                        }
                        if (super.getPlugin().getController().canSpawn(target.getWorld())) {
                            super.getPlugin().getActions().runAction(ActionType.PLACE_SIGN, target);
                            player.sendMessage(ChatColor.GREEN + "Herobrine created a sign by " + target.getName() + "!");
                        } else {
                            player.sendMessage(ChatColor.RED + target.getName() + "'s world (" + target.getWorld().getName() + ") is not in Herobrine's configuration!");
                        }
                    } else {
                        player.sendMessage(ChatColor.RED + "Not a valid command! Type \"/hb placesign username\"!");
                    }
                } else if (args[0].equalsIgnoreCase("talkto")) {
                    if (args.length == 2) {
                        Player target = super.getPlugin().getServer().getPlayer(args[1]);
                        if (target == null) {
                            player.sendMessage(ChatColor.RED + "I can't seem to find that player!");
                            return true;
                        }
                        if (super.getPlugin().getController().canSpawn(target.getWorld())) {
                            super.getPlugin().getActions().runAction(ActionType.SEND_MESSAGE, target);
                            player.sendMessage(ChatColor.GREEN + "Herobrine talked to " + target.getName() + "!");
                        } else {
                            player.sendMessage(ChatColor.RED + target.getName() + "'s world (" + target.getWorld().getName() + ") is not in Herobrine's configuration!");
                        }
                    } else {
                        player.sendMessage(ChatColor.RED + "Not a valid command! Type \"/hb talkto username\"!");
                    }
                } else if (args[0].equalsIgnoreCase("playsound")) {
                    if (args.length == 2) {
                        Player target = super.getPlugin().getServer().getPlayer(args[1]);
                        if (target == null) {
                            player.sendMessage(ChatColor.RED + "I can't seem to find that player!");
                            return true;
                        }
                        if (super.getPlugin().getController().canSpawn(target.getWorld())) {
                            super.getPlugin().getActions().runAction(ActionType.PLAY_SOUND, target);
                            player.sendMessage(ChatColor.GREEN + "Herobrine played a sound for " + target.getName() + "!");
                        } else {
                            player.sendMessage(ChatColor.RED + target.getName() + "'s world (" + target.getWorld().getName() + ") is not in Herobrine's configuration!");
                        }
                    } else {
                        player.sendMessage(ChatColor.RED + "Not a valid command! Type \"/hb playsound username\"!");
                    }
                } else if (args[0].equalsIgnoreCase("placetorch")) {
                    if (args.length == 2) {
                        Player target = super.getPlugin().getServer().getPlayer(args[1]);
                        if (target == null) {
                            player.sendMessage(ChatColor.RED + "I can't seem to find that player!");
                            return true;
                        }
                        if (super.getPlugin().getController().canSpawn(target.getWorld())) {
                            super.getPlugin().getActions().runAction(ActionType.PLACE_TORCH, target);
                            player.sendMessage(ChatColor.GREEN + "Herobrine created a torch by " + target.getName() + "!");
                        } else {
                            player.sendMessage(ChatColor.RED + target.getName() + "'s world (" + target.getWorld().getName() + ") is not in Herobrine's configuration!");
                        }
                    } else {
                        player.sendMessage(ChatColor.RED + "Not a valid command! Type \"/hb placetorch username\"!");
                    }
                } else if (args[0].equalsIgnoreCase("spawnzombies")) {
                    if (args.length == 2) {
                        Player target = super.getPlugin().getServer().getPlayer(args[1]);
                        if (target == null) {
                            player.sendMessage(ChatColor.RED + "I can't seem to find that player!");
                            return true;
                        }
                        if (super.getPlugin().getController().canSpawn(target.getWorld())) {
                            super.getPlugin().getActions().runAction(ActionType.SPAWN_ZOMBIES, target);
                            player.sendMessage(ChatColor.GREEN + "Spawned zombies by " + target.getName() + "!");
                        } else {
                            player.sendMessage(ChatColor.RED + target.getName() + "'s world (" + target.getWorld().getName() + ") is not in Herobrine's configuration!");
                        }
                    } else {
                        player.sendMessage(ChatColor.RED + "Not a valid command! Type \"/hb spawnzombies username\"!");
                    }
                } else if (args[0].equalsIgnoreCase("help")) {
                    player.sendMessage(ChatColor.RED + "attack" + ChatColor.WHITE + " - Attack a certain player.");
                    player.sendMessage(ChatColor.RED + "appear" + ChatColor.WHITE + " - Appear near a certain player.");
                    player.sendMessage(ChatColor.RED + "bury" + ChatColor.WHITE + " - Bury a certain player alive.");
                    player.sendMessage(ChatColor.RED + "placesign" + ChatColor.WHITE + " - Create a sign with a message.");
                    player.sendMessage(ChatColor.RED + "talkto" + ChatColor.WHITE + " - Send a message.");
                    player.sendMessage(ChatColor.RED + "playsound" + ChatColor.WHITE + " - Play a random sound.");
                    player.sendMessage(ChatColor.RED + "placetorch" + ChatColor.WHITE + " - Place a torch nearby.");
                    player.sendMessage(ChatColor.RED + "spawnzombies" + ChatColor.WHITE + " - Spawn zombies.");
                    player.sendMessage(ChatColor.RED + "kill" + ChatColor.WHITE + " - Remove him in case of error.");
                } else {
                    player.sendMessage(ChatColor.RED + "Not a valid command! Type \"/hb help\" for help!");
                }
            } else {
                super.getPlugin().log("You must be a player to use this command!");
            }
        } else {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                player.sendMessage(ChatColor.RED + "Not a valid command! Type \"/hb help\" for help!");
            } else {
                super.getPlugin().log("You must be a player to use this command!");
            }
        }
        return true;
    }
}
