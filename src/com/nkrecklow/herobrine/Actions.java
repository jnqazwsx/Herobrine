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

    public void createTorch(Player p) {
        Block torch = p.getLocation().add(5.0D, 0.0D, 0.0D).getBlock();
        Block groundBlock = torch.getLocation().subtract(0.0D, 1.0D, 0.0D).getBlock();
        if (torch.getTypeId() == 0 && groundBlock.getTypeId() != 0) {
            torch.setType(Material.REDSTONE_TORCH_ON);
        }
    }

    public void createSign(Player p) {
        Block signPost = p.getLocation().add(5.0D, 0.0D, 0.0D).getBlock();
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
            } else if (signText == 7) {
                signBlock.setLine(1, "I told you,");
                signBlock.setLine(2, p.getName() + ".");
            } else if (signText == 8) {
                signBlock.setLine(1, "You don't know");
                signBlock.setLine(2, "what you did.");
            } else {
                signBlock.setLine(1, "I'm not");
                signBlock.setLine(2, "a myth.");
            }
        }
    }

    public void playSound(Player p) {
        p.getWorld().playEffect(p.getLocation(), Effect.CLICK2, 5);
    }

    public void attackPlayer(Player p) {
        if (plugin.isDead() && plugin.canSpawn(p.getWorld())) {
            World w = p.getWorld();
            w.createExplosion(p.getLocation().add(3.0D, 0.0D, 3.0D), -1.0F);
            this.plugin.trackingEntity = true;
            w.spawnEntity(p.getLocation().add(3.0D, 0.0D, 3.0D), EntityType.ZOMBIE);
            Zombie z = (Zombie) plugin.hbEntity;
            z.setTarget(p);
            this.plugin.isAttacking = true;
            if (this.plugin.getSettings().sendMessages) {
                p.sendMessage(this.plugin.formatMessage("Now you're mine!"));
            }
        }
    }

    public void appearNear(Player p) {
        if (this.plugin.isDead() && plugin.canSpawn(p.getWorld())) {
            World w = p.getWorld();
            Block b = p.getLocation().add(5.0D, 0.0D, 0.0D).getBlock();
            if (b.getType().equals(Material.AIR)) {
                this.plugin.trackingEntity = Boolean.valueOf(true);
                w.spawnEntity(p.getLocation().add(5.0D, 0.0D, 0.0D), EntityType.ZOMBIE);
                plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                    
                    @Override
                    public void run() {
                        plugin.hbEntity.remove();
                    }
                }, 60L);
            }
        }
    }

    public void buryPlayer(Player p) {
        final Block b1 = p.getLocation().subtract(0.0D, 1.0D, 0.0D).getBlock();
        Block b2 = b1.getLocation().subtract(0.0D, 1.0D, 0.0D).getBlock();
        Block b3 = b2.getLocation().subtract(0.0D, 1.0D, 0.0D).getBlock();
        if (b1.getTypeId() != 0 && b2.getTypeId() != 0 && b3.getTypeId() != 0) {
            final Material type = b1.getType();
            b1.setType(Material.AIR);
            b2.setType(Material.AIR);
            b3.setType(Material.AIR);
            this.plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                
                @Override
                public void run() {
                    b1.setType(type);
                }
            }, 20L);
        }
    }
}