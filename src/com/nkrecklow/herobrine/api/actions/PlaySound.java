package com.nkrecklow.herobrine.api.actions;

import com.nkrecklow.herobrine.api.Action;
import com.nkrecklow.herobrine.api.ActionType;
import java.util.Random;
import net.minecraft.server.Packet62NamedSoundEffect;
import org.bukkit.craftbukkit.entity.CraftPlayer;

public class PlaySound extends Action {

    public PlaySound() {
        super(ActionType.PLAY_SOUND);
    }

    @Override
    public void callAction() {
        int rand = new Random().nextInt(2);
        String sound = "step.stone";
        if (rand == 0) {
            sound = "step.gravel";
        } else if (rand == 1) {
            sound = "random.breath";
        }
        Packet62NamedSoundEffect packet = new Packet62NamedSoundEffect(sound, super.getTarget().getLocation().getX(), super.getTarget().getLocation().getY(), super.getTarget().getLocation().getZ(), 1F, 1F);
        ((CraftPlayer) super.getTarget()).getHandle().netServerHandler.sendPacket(packet);
        super.getInstance().logEvent("Played a sound near " + super.getTarget().getName() + ".");
        if (super.getSender() != null) {
            super.getSender().sendMessage(super.getInstance().getUtil().addPluginName("Played a sound near " + super.getTarget().getName() + "."));
        }
    }
}
