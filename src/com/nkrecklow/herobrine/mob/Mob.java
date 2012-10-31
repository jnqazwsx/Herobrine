package com.nkrecklow.herobrine.mob;

import com.topcat.npclib.entity.HumanNPC;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class Mob implements Listener {

    private HumanNPC npc;
    private String target;
    private MobPosition position;
    
    public Mob(HumanNPC npc, MobPosition position) {
        this.npc = npc;
        this.target = "";
        this.setPosition(position);
    }
    
    public final void setPosition(MobPosition position) {
        this.position = position;
        this.getVanillaEntity().setSneaking(this.position.equals(MobPosition.SNEAKING));
    }
    
    public void lookAtVirtualPlayer(Location loc) {
        loc.setY(loc.getY() + 1.5D);
        this.npc.lookAtPoint(loc);
    }
    
    public void lookAtPlayer(Player player) {
        this.lookAtVirtualPlayer(player.getLocation());
    }

    public void setTarget(String target) {
        this.target = target;
    }
    
    public String getTarget() {
        return this.target;
    }

    public HumanNPC getNpc() {
        return this.npc;
    }
    
    public net.minecraft.server.Entity getVanillaEntity() {
        return this.npc.getEntity();
    }
    
    public Entity getEntity() {
        return this.npc.getBukkitEntity();
    }
    
    public MobPosition getPosition() {
        return this.position;
    }
}
