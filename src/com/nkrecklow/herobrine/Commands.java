package com.nkrecklow.herobrine;

import com.nkrecklow.herobrine.api.Action;
import com.nkrecklow.herobrine.api.basic.Generic;
import java.io.File;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands extends Generic implements CommandExecutor {

    public Commands(Main instance) {
        super(instance);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (args.length > 0) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (!player.isOp() && !player.hasPermission("herobrine.commands")) {
                    player.sendMessage(Util.formatString("You do not have permission for this."));
                    return true;
                }
                if (args[0].equalsIgnoreCase("appear")) {
                    if (args.length == 2) {
                        Player target = super.getInstance().getServer().getPlayer(args[1]);
                        if (target == null) {
                            player.sendMessage(Util.formatString("I can't seem to find that player."));
                            return true;
                        }
                        super.getInstance().getActionManager().runAction(Action.ActionType.APPEAR, target, player);
                    } else {
                        player.sendMessage(Util.formatString("Not a valid command! Type \"/hb appear username\"."));
                    }
                } else if (args[0].equalsIgnoreCase("bury")) {
                    if (args.length == 2) {
                        Player target = super.getInstance().getServer().getPlayer(args[1]);
                        if (target == null) {
                            player.sendMessage(Util.formatString("I can't seem to find that player."));
                            return true;
                        }
                        super.getInstance().getActionManager().runAction(Action.ActionType.BURY_PLAYER, target, player);
                    } else {
                        player.sendMessage(Util.formatString("Not a valid command! Type \"/hb bury username\"."));
                    }
                } else if (args[0].equalsIgnoreCase("kill")) {
                    if (super.getInstance().getMobController().isSpawned()) {
                        super.getInstance().getMobController().despawnMob();
                        player.sendMessage(Util.formatString("Herobrine has been killed."));
                    } else {
                        player.sendMessage(Util.formatString("Herobrine currently isn't alive."));
                    }
                } else if (args[0].equalsIgnoreCase("placesign")) {
                    if (args.length == 2) {
                        Player target = super.getInstance().getServer().getPlayer(args[1]);
                        if (target == null) {
                            player.sendMessage(Util.formatString("I can't seem to find that player."));
                            return true;
                        }
                        super.getInstance().getActionManager().runAction(Action.ActionType.PLACE_SIGN, target, player);
                    } else {
                        player.sendMessage(Util.formatString("Not a valid command! Type \"/hb placesign username\"."));
                    }
                } else if (args[0].equalsIgnoreCase("playsound")) {
                    if (args.length == 2) {
                        Player target = super.getInstance().getServer().getPlayer(args[1]);
                        if (target == null) {
                            player.sendMessage(Util.formatString("I can't seem to find that player."));
                            return true;
                        }
                        super.getInstance().getActionManager().runAction(Action.ActionType.PLAY_SOUND, target, player);
                    } else {
                        player.sendMessage(Util.formatString("Not a valid command! Type \"/hb playsound username\"."));
                    }
                } else if (args[0].equalsIgnoreCase("placetorch")) {
                    if (args.length == 2) {
                        Player target = super.getInstance().getServer().getPlayer(args[1]);
                        if (target == null) {
                            player.sendMessage(Util.formatString("I can't seem to find that player."));
                            return true;
                        }
                        super.getInstance().getActionManager().runAction(Action.ActionType.PLACE_TORCH, target, player);
                    } else {
                        player.sendMessage(Util.formatString("Not a valid command! Type \"/hb placetorch username\"."));
                    }
                } else if (args[0].equalsIgnoreCase("teleport")) {
                    if (args.length == 1) {
                        if (super.getInstance().getMobController().isSpawned()) {
                            player.teleport(super.getInstance().getMobController().getMob().getEntity().getLocation());
                            player.sendMessage(Util.formatString("Teleported to Herobrine."));
                            super.getInstance().logEvent("Teleported " + player.getName() + " to Herobrine.");
                        } else {
                            player.sendMessage(Util.formatString("Herobrine currently isn't alive."));
                        }
                    } else {
                        player.sendMessage(Util.formatString("Not a valid command! Type \"/hb createchest username\"."));
                    }
                } else if (args[0].equalsIgnoreCase("nightmare")) {
                    if (args.length == 2) {
                        Player target = super.getInstance().getServer().getPlayer(args[1]);
                        if (target == null) {
                            player.sendMessage(Util.formatString("I can't seem to find that player."));
                            return true;
                        }
                        super.getInstance().getActionManager().runAction(Action.ActionType.ENTER_NIGHTMARE, target, player);
                    } else {
                        player.sendMessage(Util.formatString("Not a valid command! Type \"/hb nightmare username\"."));
                    }
                } else if (args[0].equalsIgnoreCase("forceunleash")) {
                    if (new File(super.getInstance().getDataFolder() + "/living.yml").exists()) {
                        player.sendMessage(Util.formatString("Herobrine has already been unleashed!"));
                    } else {
                        try {
                            new File(super.getInstance().getDataFolder() + "/living.yml").createNewFile();
                            super.getInstance().log("Herobrine has been unleashed!");
                            player.sendMessage(Util.formatString("Herobrine has been unleashed!"));
                        } catch (Exception ex) {
                            super.getInstance().log("Error: " + ex.getMessage());
                            player.sendMessage(Util.formatString("Error: " + ex.getMessage()));
                        }
                    }
                } else if (args[0].equalsIgnoreCase("holysword")) {
                    player.getInventory().addItem(super.getInstance().getUtil().getHolySwordItem());
                    player.sendMessage(Util.formatString("Enjoy your \"Holy Sword\"."));
                } else if (args[0].equalsIgnoreCase("reload")) {
                    super.getInstance().getConfiguration().loadConfig();
                    player.sendMessage(Util.formatString("Reloaded configuration file!"));
                    super.getInstance().log("Reloaded configuration file!");
                } else if (args[0].equalsIgnoreCase("help")) {
                    player.sendMessage(Util.formatString("appear" + ChatColor.WHITE + " - Appear near a certain player."));
                    player.sendMessage(Util.formatString("bury" + ChatColor.WHITE + " - Bury a certain player alive."));
                    player.sendMessage(Util.formatString("placesign" + ChatColor.WHITE + " - Create a sign with a message."));
                    player.sendMessage(Util.formatString("playsound" + ChatColor.WHITE + " - Play a random sound."));
                    player.sendMessage(Util.formatString("placetorch" + ChatColor.WHITE + " - Place a torch nearby."));
                    player.sendMessage(Util.formatString("kill" + ChatColor.WHITE + " - Remove him in case of error."));
                    player.sendMessage(Util.formatString("reload" + ChatColor.WHITE + " - Reload the configuration file."));
                    player.sendMessage(Util.formatString("teleport" + ChatColor.WHITE + " - Teleport to Herobrine's location."));
                    player.sendMessage(Util.formatString("teleport" + ChatColor.WHITE + " - Teleport to Herobrine's location."));
                    player.sendMessage(Util.formatString("forceunleash" + ChatColor.WHITE + " - Force unleash Herobrine."));
                    player.sendMessage(Util.formatString("nightmare" + ChatColor.WHITE + " - Enter the \"Nightmare World\"."));
                    player.sendMessage(Util.formatString("holysword" + ChatColor.WHITE + " - Gift yourself a \"Holy Sword\"."));
                } else {
                    player.sendMessage(Util.formatString("Not a valid command! Type \"/hb help\" for help."));
                }
            } else {
                super.getInstance().log("You must be a player to use this command.");
            }
        } else {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                player.sendMessage(Util.formatString("Not a valid command! Type \"/hb help\" for help."));
            } else {
                super.getInstance().log("You must be a player to use this command.");
            }
        }
        return true;
    }
}
