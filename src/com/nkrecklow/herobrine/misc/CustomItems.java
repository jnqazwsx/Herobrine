package com.nkrecklow.herobrine.misc;

import net.minecraft.server.NBTTagCompound;
import org.bukkit.Material;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

public class CustomItems {

    public static ItemStack createItem(Material mat, String name, String... desc) {
        NamedItemStack item = new NamedItemStack(new ItemStack(mat));
        item.setName(name);
        item.setDescription(desc);
        return item.getItemStack();
    }

    public static ItemStack createBook(String title, String author, String... desc) {
        BookItem book = new BookItem(new ItemStack(387, 1));
        book.setAuthor(author);
        book.setTitle(title);
        book.setPages(desc);
        return book.getItemStack();
    }

    public static ItemStack createPlayerSkull(String nick) {
        CraftItemStack craftStack = new CraftItemStack(new ItemStack(Material.SKULL_ITEM, 1, (short) 3));
        net.minecraft.server.ItemStack itemStack = craftStack.getHandle();
        if (itemStack.tag == null) {
            itemStack.tag = new NBTTagCompound();
        }
        itemStack.tag.setString("SkullOwner", nick);
        return craftStack;
    }
}
