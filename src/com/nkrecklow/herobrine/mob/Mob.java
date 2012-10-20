package com.nkrecklow.herobrine.mob;

import com.topcat.npclib.entity.HumanNPC;
import org.bukkit.event.Listener;

public class Mob implements Listener {

    private HumanNPC npc;
    private String target;

    public Mob(HumanNPC npc) {
        this.npc = npc;
        this.target = "";
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
}
