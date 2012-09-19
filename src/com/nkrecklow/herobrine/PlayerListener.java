package com.nkrecklow.herobrine;

import java.util.Random;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerListener implements Listener {

    private Herobrine plugin;
    private PossibleActions actions;

    public PlayerListener(Herobrine plugin) {
        this.plugin = plugin;
        this.actions = new PossibleActions(this.plugin);
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player p = event.getPlayer();
        int eventChoice = new Random().nextInt(this.plugin.innerChance + 1);
        if (eventChoice == 1) {
            if (this.plugin.modifyWorld) {
                this.actions.createTorch(p);
            }
        } else if (eventChoice == 2) {
            if (this.plugin.modifyWorld) {
                this.actions.createSign(p);
            }
        } else if (eventChoice == 3) {
            this.actions.playSound(p);
        } else if (eventChoice == 4) {
            this.actions.attackPlayer(p);
        } else if (eventChoice == 5) {
            this.actions.appearNear(p);
        } else if (eventChoice == 6) {
            if (this.plugin.modifyWorld) {
                this.actions.buryPlayer(p);
            }
        }
    }
}