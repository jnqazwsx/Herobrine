package com.nkrecklow.herobrine.misc;

import com.nkrecklow.herobrine.Main;
import com.nkrecklow.herobrine.api.basic.Generic;
import java.util.ArrayList;
import java.util.Random;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.EntityType;

public class WorldGenerator extends Generic {

    private World world;
    private ArrayList<EntityType> allowedTypes;

    public WorldGenerator(Main instance) {
        super(instance);
        this.world = super.getInstance().getServer().getWorld("world_nightmare");
        this.allowedTypes = new ArrayList<EntityType>();
        this.allowedTypes.add(EntityType.BAT);
        this.allowedTypes.add(EntityType.CAVE_SPIDER);
        this.allowedTypes.add(EntityType.BLAZE);
        this.allowedTypes.add(EntityType.MAGMA_CUBE);
        this.allowedTypes.add(EntityType.SLIME);
        this.allowedTypes.add(EntityType.GIANT);
        this.allowedTypes.add(EntityType.PLAYER);
        this.allowedTypes.add(EntityType.GHAST);
    }

    public void loadWorld() {
        super.getInstance().log("Loading \"Nightmare World\", please wait...");
        WorldCreator creator = new WorldCreator("world_nightmare");
        creator.environment(World.Environment.THE_END);
        creator.generateStructures(false);
        creator.seed();
        creator.createWorld();
        this.world = super.getInstance().getServer().createWorld(creator);
        super.getInstance().log("The \"Nightmare World\" has been loaded!");
    }

    public EntityType getRandomEntity() {
        EntityType entity = null;
        while (entity == null) {
            entity = this.allowedTypes.get(new Random().nextInt(this.allowedTypes.size() - 1));
            if (entity.equals(EntityType.PLAYER)) {
                entity = null;
            } else {
                break;
            }
        }
        return entity;
    }

    public ArrayList<EntityType> getAllowedEntities() {
        return this.allowedTypes;
    }

    public World getWorld() {
        return this.world;
    }
}
