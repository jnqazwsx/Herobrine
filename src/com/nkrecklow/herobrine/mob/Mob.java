package com.nkrecklow.herobrine.mob;

import com.nkrecklow.herobrine.Main;
import com.nkrecklow.herobrine.api.MobTargettingThread;
import com.nkrecklow.herobrine.base.Generic;
import com.topcat.npclib.entity.HumanNPC;
import org.bukkit.event.Listener;

public class Mob extends Generic implements Listener {

    private HumanNPC npc;
    private String target;
    private MobTargettingThread thread;
    
    public Mob(Main main, HumanNPC npc) {
        super(main);
        this.npc = npc;
        this.target = "";
    }

    public void setTarget(String target) {
        this.target = target;
        if (this.thread != null) {
            this.thread.cancel();
            this.thread = null;
        }
        this.thread = new MobTargettingThread(super.main);
        this.thread.target(this.npc.getBukkitEntity(), super.main.getServer().getPlayer(this.target));
    }
    
    public String getTarget() {
        return this.target;
    }
    
    public HumanNPC getNpc() {
        return this.npc;
    }
}
