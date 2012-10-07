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
        Block torch = player.getLocation().add(new Random().nextInt(5), 0D, new Random().nextInt(5)).getBlock();
        Block groundBlock = torch.getLocation().subtract(0D, 1D, 0D).getBlock();
        if (!(Boolean) this.plugin.getConfiguration().getObject("modifyWorld") || !this.plugin.getController().canSpawn(player.getWorld())) {
            return;
        }
        if (torch.getType().equals(Material.AIR) && !groundBlock.getType().equals(Material.AIR)) {
            if (new Random().nextBoolean()) {
                torch.setType(Material.REDSTONE_TORCH_ON);
            } else {
                torch.setType(Material.TORCH);
            }
            this.plugin.log("Placed a torch near " + player.getName() + ".");
        }
    }
    
    public void spawnZombies(Player player) {
        if (!(Boolean) this.plugin.getConfiguration().getObject("spawnZombies")) {
            return;
        }
        int amount = new Random().nextInt(3);
        for (int id = 0; id < amount; id++) {
            player.getWorld().spawnCreature(player.getLocation().add(new Random().nextInt(5), 0, new Random().nextInt(5)), EntityType.ZOMBIE);
        }
        if (amount > 0) {
            this.plugin.log("Spawned " + amount + " zombies near " + player.getName() + ".");
        }
    }

    public void createSign(Player player) {
        if (!(Boolean) this.plugin.getConfiguration().getObject("modifyWorld") || !this.plugin.getController().canSpawn(player.getWorld())) {
            return;
        }
        Block signPost = player.getLocation().add(new Random().nextInt(5), 0D, new Random().nextInt(5)).getBlock();
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
            } else {
                signBlock.setLine(1, "I'm alive.");
            }
            signBlock.update();
            this.plugin.log("Placed a sign by " + player.getName() + ".");
        }
    }

    public void playSound(Player player) {
        if (!this.plugin.getController().canSpawn(player.getWorld())) {
            return;
        }
        player.getWorld().playEffect(player.getLocation(), Effect.values()[new Random().nextInt(Effect.values().length - 1)], 5);
    }

    public void attackPlayer(Player player) {
        if (!this.plugin.getController().canSpawn(player.getWorld())) {
            return;
        }
        World world = player.getWorld();
        if (this.plugin.getController().isDead() && this.plugin.getController().canSpawn(player.getWorld())) {
            world.createExplosion(player.getLocation().add(new Random().nextInt(5), 0D, new Random().nextInt(5)), -1F);
            this.plugin.getController().setTracking(true);
            world.spawnEntity(player.getLocation().add(new Random().nextInt(5), 0D, new Random().nextInt(5)), EntityType.ZOMBIE);
            this.plugin.getController().setTarget(player);
            if (this.plugin.getConfiguration().canSendMessages()) {
                player.sendMessage(this.plugin.formatMessage(this.plugin.getConfiguration().getMessage()));
            }
            this.plugin.log("Attacked " + player.getName() + ".");
        }
    }
    
    public void sendMessage(Player player) {
        if (this.plugin.getController().canSpawn(player.getWorld()) && this.plugin.getConfiguration().canSendMessages()) {
            if (new Random().nextBoolean()) {
                player.sendMessage(this.plugin.formatMessage(this.plugin.getConfiguration().getMessage()));
            } else {
                player.sendMessage("<" + this.plugin.getServer().getOnlinePlayers()[new Random().nextInt(this.plugin.getServer().getOnlinePlayers().length - 1)].getName() + "> Hey there " + player.getName() + "!");
            }
            this.plugin.log("Sent a message to " + player.getName() + ".");
        }
    }

    public void appearNear(Player player) {
        if (!this.plugin.getController().canSpawn(player.getWorld())) {
            return;
        }
        World world = player.getWorld();
        Block block = player.getLocation().add(new Random().nextInt(5), 0D, new Random().nextInt(5)).getBlock();
        if (this.plugin.getController().isDead() && this.plugin.getController().canSpawn(player.getWorld())) {
            if (block.getType().equals(Material.AIR)) {
                this.plugin.getController().setTracking(true);
                world.spawnEntity(player.getLocation().add(new Random().nextInt(5), 0D, new Random().nextInt(5)), EntityType.ZOMBIE);
                if (!this.plugin.getController().isDead()) {
                    this.plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {

                        @Override
                        public void run() {
                            plugin.getController().getEntity().setHealth(0);
                            plugin.getController().getEntity().remove();
                        }
                    }, 40L);
                    this.plugin.log("Appeared near " + player.getName() + ".");
                }
            }
        }
    }

    public void buryPlayer(Player player) {
        if (!this.plugin.getController().canSpawn(player.getWorld()) && (Boolean) this.plugin.getConfiguration().getObject("modifyWorld")) {
            return;
        }
        final Block top = player.getLocation().subtract(0D, 1D, 0D).getBlock();
        Block middle = top.getLocation().subtract(0D, 1D, 0D).getBlock();
        Block bottom = middle.getLocation().subtract(0D, 1D, 0D).getBlock();
        final Material type = top.getType();
        if (!(Boolean) this.plugin.getConfiguration().getObject("modifyWorld") || !this.plugin.getController().canSpawn(player.getWorld())) {
            return;
        }
        if (!top.getType().equals(Material.AIR) && !middle.getType().equals(Material.AIR) && !bottom.getType().equals(Material.AIR)) {
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
        if (this.plugin.getController().canSpawn(player.getWorld()) && (Boolean) this.plugin.getConfiguration().getObject("modifyInventories")) {
            ItemStack item = null;
            while (item == null) {
                item = player.getInventory().getItem(new Random().nextInt(player.getInventory().getSize() - 1));
            }
            player.sendMessage(this.plugin.formatMessage("Missing something, like a " + item.getType().toString() + "?"));
            this.plugin.log("Stole from " + player.getName() + ".");
        }
    }
}
