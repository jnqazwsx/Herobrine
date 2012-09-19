package com.nkrecklow.herobrine;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockIgniteEvent;

public class BlockListener implements Listener {

    private Herobrine plugin;

    public BlockListener(Herobrine plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockIgnite(BlockIgniteEvent event) {
        Block b = event.getBlock();
        if (event.getCause().equals(BlockIgniteEvent.IgniteCause.FLINT_AND_STEEL)) {
            Player p = event.getPlayer();
            World w = event.getBlock().getWorld();
            Block netherRack = b.getLocation().subtract(0.0D, 1.0D, 0.0D).getBlock();
            Block mossyCobble = b.getLocation().subtract(0.0D, 2.0D, 0.0D).getBlock();
            if (netherRack.getType().equals(Material.NETHERRACK) && mossyCobble.getType().equals(Material.MOSSY_COBBLESTONE) && this.plugin.isDead() && this.plugin.canSpawn(p.getWorld())) {
                this.plugin.isAttacking = true;
                if (this.plugin.changeEnvironment) {
                    w.setStorm(true);
                    w.setTime(14200L);
                }
                if (this.plugin.removeMossyCobblestone) {
                    mossyCobble.setType(Material.COBBLESTONE);
                }
                w.strikeLightning(b.getLocation());
                w.createExplosion(b.getLocation(), -1.0F);
                if (this.plugin.sendMessages) {
                    for (Player aPlayer : this.plugin.getServer().getOnlinePlayers()) {
                        aPlayer.sendMessage(this.plugin.formatMessage("I am your God! Bow to me!"));
                    }
                }
                this.plugin.trackingEntity = true;
                w.spawnEntity(b.getLocation(), EntityType.ZOMBIE);
                Zombie z = (Zombie) this.plugin.hbEntity;
                z.setTarget(p);
            }
        }
    }
}