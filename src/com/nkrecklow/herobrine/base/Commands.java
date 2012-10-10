package com.nkrecklow.herobrine.base;

import com.nkrecklow.herobrine.Main;
import com.nkrecklow.herobrine.api.Argument;
import com.nkrecklow.herobrine.arguments.HelpArgument;
import com.nkrecklow.herobrine.core.Generic;
import java.util.ArrayList;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands extends Generic implements CommandExecutor {

    private ArrayList<Argument> args;
    
    public Commands(Main plugin) {
        super(plugin);
        this.args = new ArrayList<Argument>();
        this.args.add(new HelpArgument());
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (sender instanceof Player) {
            
        } else {
            
        }
        return true;
    }
}
