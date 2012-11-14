package com.nkrecklow.herobrine.misc;

import org.bukkit.Material;
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
}
