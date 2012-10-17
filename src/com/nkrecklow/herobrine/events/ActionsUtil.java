package com.nkrecklow.herobrine.events;

import com.nkrecklow.herobrine.Main;
import com.nkrecklow.herobrine.api.BookItem;
import java.util.Random;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ActionsUtil {

    public static ItemStack getNewBook() {
        BookItem book = new BookItem(new ItemStack(387, 1));
        book.setAuthor("Herobrine");
        book.setTitle("Hello.");
        book.setPages(new String[] { "Hello." });
        return book.getItemStack();
    }
    
    public static boolean shouldAct(Main main, Player player) {
        int chance = main.getConfiguration().getActionChance();
        if (player.getWorld().getTime() >= 13000 && player.getWorld().getTime() <= 14200 && (Boolean) main.getConfiguration().getObject("moreOftenAtNight")) {
            chance = main.getConfiguration().getActionChance() / 4;
        }
        if (chance == main.getConfiguration().getActionChance()) {
            int found = 0;
            for (Entity entity : player.getNearbyEntities(10D, 10D, 10D)) {
                if (entity instanceof Player) {
                    found++;
                }
            }
            if (found <= 2) {
                chance = main.getConfiguration().getActionChance() / 2;
            }
        }
        return new Random().nextInt(chance) == 0;
    }
    
    public static boolean shouldActIndifferent(Main main) {
        return new Random().nextInt(main.getConfiguration().getActionChance()) == 0;
    }
}
