package com.nkrecklow.herobrine.misc;

import java.util.ArrayList;
import net.minecraft.server.NBTTagCompound;
import net.minecraft.server.NBTTagList;
import net.minecraft.server.NBTTagString;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

public class NamedItemStack {

    private CraftItemStack craftStack;
    private net.minecraft.server.ItemStack itemStack;

    public NamedItemStack(ItemStack item) {
        if (item instanceof CraftItemStack) {
            craftStack = (CraftItemStack) item;
            itemStack = craftStack.getHandle();
        } else if (item instanceof ItemStack) {
            craftStack = new CraftItemStack(item);
            itemStack = craftStack.getHandle();
        }
        NBTTagCompound tag = itemStack.tag;
        if (tag == null) {
            tag = new NBTTagCompound();
            tag.setCompound("display", new NBTTagCompound());
            itemStack.tag = tag;
        }
    }

    public void setName(String name) {
        NBTTagCompound tag = itemStack.tag.getCompound("display");
        tag.setString("Name", name);
        itemStack.tag.setCompound("display", tag);
    }

    public void addDescriptionLine(String lore) {
        NBTTagCompound tag = itemStack.tag.getCompound("display");
        NBTTagList list = tag.getList("Lore");
        if (list == null) {
            list = new NBTTagList();
        }
        list.add(new NBTTagString(lore));
        tag.set("Lore", list);
        itemStack.tag.setCompound("display", tag);
    }

    public void setDescription(String... desc) {
        NBTTagCompound tag = itemStack.tag.getCompound("display");
        NBTTagList list = new NBTTagList();
        for (String line: desc) {
            list.add(new NBTTagString(line));
        }
        tag.set("Lore", list);
        itemStack.tag.setCompound("display", tag);
    }
    
    public String getName() {
        NBTTagCompound tag = itemStack.tag.getCompound("display");
        return tag.getString("Name");
    }

    public String[] getDescription() {
        NBTTagCompound tag = itemStack.tag;
        NBTTagList list = tag.getCompound("display").getList("Lore");
        ArrayList<String> strings = new ArrayList<String>();
        String[] lines = new String[]{};
        for (int i = 0; i < strings.size(); i++) {
            strings.add(((NBTTagString) list.get(i)).data);
        }
        strings.toArray(lines);
        return lines;
    }

    public ItemStack getItemStack() {
        return craftStack;
    }
}
