package com.nkrecklow.herobrine.misc;

import com.nkrecklow.herobrine.Main;
import com.nkrecklow.herobrine.api.basic.Generic;
import org.bukkit.World;
import org.bukkit.WorldCreator;

public class WorldGenerator extends Generic {

    private World world;
    
    public WorldGenerator(Main main) {
        super(main);
        this.world = super.getInstance().getServer().getWorld("world_nightmare");
    }
    
    public void generateWorld() {
        if (this.world == null) {
            WorldCreator creator = new WorldCreator("world_nightmare");
            creator.environment(World.Environment.THE_END);
            creator.generateStructures(false);
            super.getInstance().log("Loading \"Nightmare World\", please wait...");
            creator.createWorld();
            super.getInstance().log("The \"Nightmare World\" has been loaded!");
            this.world = super.getInstance().getServer().createWorld(creator);
        }
    }
    
    public boolean exists() {
        return this.world != null;
    }
    
    public boolean isEnabled() {
        return (Boolean) super.getInstance().getConfiguration().getObject("nightmareWorldEnabled");
    }
    
    public World getWorld() {
        return this.world;
    }
}
