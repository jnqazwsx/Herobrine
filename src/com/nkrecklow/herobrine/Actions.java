package com.nkrecklow.herobrine;

import java.util.Random;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Actions {

    private Plugin plugin;

    public Actions(Plugin plugin) {
        this.plugin = plugin;
    }

    public void createTorch(Player player) {
        if (!this.plugin.getSettings().canModifyWorld() || !this.plugin.getController().canSpawn(player.getWorld())) {
            return;
        }        
        Block torch = player.getLocation().add(5D, 0D, 0D).getBlock();
        Block groundBlock = torch.getLocation().subtract(0D, 1D, 0D).getBlock();
        if (torch.getType().equals(Material.AIR) && !groundBlock.getType().equals(Material.AIR)) {
            torch.setType(Material.REDSTONE_TORCH_ON);
            this.plugin.log("Placed a torch near " + player.getName() + ".");
        }
    }

    public void createSign(Player player) {
        if (!this.plugin.getSettings().canModifyWorld() || !this.plugin.getController().canSpawn(player.getWorld())) {
            return;
        }
        Block signPost = player.getLocation().add(5D, 0D, 0D).getBlock();
        Block groundBlock = signPost.getLocation().subtract(0D, 1D, 0D).getBlock();
        if (signPost.getType().equals(Material.AIR) && !groundBlock.getType().equals(Material.AIR)) {
            signPost.setType(Material.SIGN_POST);
            Sign signBlock = (Sign) signPost.getState();
            int signText = new Random().nextInt(7);
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
            } else {
                signBlock.setLine(1, "I'm not");
                signBlock.setLine(2, "a myth.");
            }
            signBlock.update();
            this.plugin.log("Placed a sign by " + player.getName() + ".");
        }
    }

    public void playSound(Player player) {
        if (!this.plugin.getController().canSpawn(player.getWorld())) {
            return;
        }
        player.getWorld().playEffect(player.getLocation(), Effect.CLICK2, 5);
    }

    public void attackPlayer(Player player) {
        if (this.plugin.getController().isDead() && this.plugin.getController().canSpawn(player.getWorld())) {
            World world = player.getWorld();
            world.createExplosion(player.getLocation().add(3D, 0D, 3D), -1F);
            this.plugin.getController().setTracking(true);
            world.spawnEntity(player.getLocation().add(3D, 0D, 3D), EntityType.ZOMBIE);
            this.plugin.getController().setTarget(player);
            this.plugin.getController().setAttacking(true);
            if (this.plugin.getSettings().canSendMessages()) {
                player.sendMessage(this.plugin.formatMessage(this.plugin.getSettings().getMessage()));
            }
            this.plugin.log("Attacked " + player.getName() + ".");
        }
    }
    
    public void sendMessage(Player player) {
        if (this.plugin.getController().canSpawn(player.getWorld())) {
            player.sendMessage(this.plugin.formatMessage(this.plugin.getSettings().getMessage()));
        }
    }

    public void appearNear(Player player) {
        if (this.plugin.getController().isDead() && this.plugin.getController().canSpawn(player.getWorld())) {
            World world = player.getWorld();
            Block block = player.getLocation().add(5D, 0D, 0D).getBlock();
            if (block.getType().equals(Material.AIR)) {
                this.plugin.getController().setTracking(true);
                world.spawnEntity(player.getLocation().add(5D, 0D, 0D), EntityType.ZOMBIE);
                this.plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                    
                    @Override
                    public void run() {
                        plugin.getController().getEntity().remove();
                    }
                }, 60L);
                this.plugin.log("Appeared near " + player.getName() + ".");
            }
        }
    }

    public void buryPlayer(Player player) {
        if (!this.plugin.getSettings().canModifyWorld() || !this.plugin.getController().canSpawn(player.getWorld())) {
            return;
        }
        final Block top = player.getLocation().subtract(0D, 1D, 0D).getBlock();
        Block middle = top.getLocation().subtract(0D, 1D, 0D).getBlock();
        Block bottom = middle.getLocation().subtract(0D, 1D, 0D).getBlock();
        if (!top.getType().equals(Material.AIR) && !middle.getType().equals(Material.AIR) && !bottom.getType().equals(Material.AIR)) {
            final Material type = top.getType();
            top.setType(Material.AIR);
            middle.setType(Material.AIR);
            bottom.setType(Material.AIR);
            this.plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                
                @Override
                public void run() {
                    top.setType(type);
                }
            }, 60L);
            this.plugin.log("Buried " + player.getName() + ".");
        }
    }
    
    public void modifyInventory(Player player) {
        if (this.plugin.getController().canSpawn(player.getWorld()) && this.plugin.getSettings().canModifyInventories()) {
            if (new Random().nextInt(4) == 0) {
                ItemStack item = null;
                while (item == null) {
                    item = player.getInventory().getItem(new Random().nextInt(player.getInventory().getSize() - 1));
                }
                player.sendMessage(this.plugin.formatMessage("Missing something, like a " + item.getType().toString() + "?"));
            }
        }
    }
}
