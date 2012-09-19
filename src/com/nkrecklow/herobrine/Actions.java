package com.nkrecklow.herobrine;

import java.util.Random;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;

public class Actions {

    private Herobrine plugin;

    public Actions(Herobrine plugin) {
        this.plugin = plugin;
    }

    public void createTorch(Player player) {
        Block torch = player.getLocation().add(5.0D, 0.0D, 0.0D).getBlock();
        Block groundBlock = torch.getLocation().subtract(0.0D, 1.0D, 0.0D).getBlock();
        if (torch.getTypeId() == 0 && groundBlock.getTypeId() != 0) {
            torch.setType(Material.REDSTONE_TORCH_ON);
        }
    }

    public void createSign(Player player) {
        Block signPost = player.getLocation().add(5.0D, 0.0D, 0.0D).getBlock();
        Block groundBlock = signPost.getLocation().subtract(0.0D, 1.0D, 0.0D).getBlock();
        if (signPost.getTypeId() == 0 && groundBlock.getTypeId() != 0) {
            signPost.setType(Material.SIGN_POST);
            BlockState signState = signPost.getState();
            Sign signBlock = (Sign) signState;
            Random signOptions = new Random();
            int signText = signOptions.nextInt(9);
            if (signText == 1) {
                signBlock.setLine(1, "I'm watching.");
            } else if (signText == 2) {
                signBlock.setLine(1, "Stop.");
            } else if (signText == 3) {
                signBlock.setLine(1, "You'll join");
                signBlock.setLine(2, "me soon.");
            } else if (signText == 4) {
                signBlock.setLine(1, "You can't");
                signBlock.setLine(2, "escape.");
            } else if (signText == 5) {
                signBlock.setLine(1, "Remember me?");
            } else if (signText == 6) {
                signBlock.setLine(1, "I'm alive.");
            } else if (signText == 8) {
                signBlock.setLine(1, "You don't know");
                signBlock.setLine(2, "what you did.");
            } else {
                signBlock.setLine(1, "I'm not");
                signBlock.setLine(2, "a myth.");
            }
        }
    }

    public void playSound(Player player) {
        player.getWorld().playEffect(player.getLocation(), Effect.CLICK2, 5);
    }

    public void attackPlayer(Player player) {
        if (plugin.isDead() && plugin.canSpawn(player.getWorld())) {
            World world = player.getWorld();
            world.createExplosion(player.getLocation().add(3.0D, 0.0D, 3.0D), -1.0F);
            this.plugin.trackingEntity = true;
            world.spawnEntity(player.getLocation().add(3.0D, 0.0D, 3.0D), EntityType.ZOMBIE);
            Zombie zombie = (Zombie) plugin.hbEntity;
            zombie.setTarget(player);
            this.plugin.isAttacking = true;
            if (this.plugin.getSettings().sendMessages) {
                player.sendMessage(this.plugin.formatMessage("Now you're mine!"));
            }
        }
    }

    public void appearNear(Player player) {
        if (this.plugin.isDead() && plugin.canSpawn(player.getWorld())) {
            World world = player.getWorld();
            Block block = player.getLocation().add(5.0D, 0.0D, 0.0D).getBlock();
            if (block.getType().equals(Material.AIR)) {
                this.plugin.trackingEntity = Boolean.valueOf(true);
                world.spawnEntity(player.getLocation().add(5.0D, 0.0D, 0.0D), EntityType.ZOMBIE);
                plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                    
                    @Override
                    public void run() {
                        plugin.hbEntity.remove();
                    }
                }, 60L);
            }
        }
    }

    public void buryPlayer(Player player) {
        final Block top = player.getLocation().subtract(0.0D, 1.0D, 0.0D).getBlock();
        Block middle = top.getLocation().subtract(0.0D, 1.0D, 0.0D).getBlock();
        Block bottom = middle.getLocation().subtract(0.0D, 1.0D, 0.0D).getBlock();
        if (top.getTypeId() != 0 && middle.getTypeId() != 0 && bottom.getTypeId() != 0) {
            final Material type = top.getType();
            top.setType(Material.AIR);
            middle.setType(Material.AIR);
            bottom.setType(Material.AIR);
            this.plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                
                @Override
                public void run() {
                    top.setType(type);
                }
            }, 20L);
        }
    }
}