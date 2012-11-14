package com.nkrecklow.herobrine.api.actions;

import com.nkrecklow.herobrine.Util;
import com.nkrecklow.herobrine.api.Action;
import java.util.ArrayList;
import java.util.Random;
import net.minecraft.server.Packet62NamedSoundEffect;
import org.bukkit.craftbukkit.entity.CraftPlayer;

public class PlaySound extends Action {

    public PlaySound() {
        super(Action.ActionType.PLAY_SOUND);
    }

    @Override
    public void callAction() {
        ArrayList<String> sounds = new ArrayList<String>();
        sounds.add("step.stone");
        sounds.add("step.gravel");
        sounds.add("random.breath");
        Packet62NamedSoundEffect packet = new Packet62NamedSoundEffect(sounds.get(new Random().nextInt(sounds.size() - 1)), super.getTarget().getLocation().getX(), super.getTarget().getLocation().getY(), super.getTarget().getLocation().getZ(), 1F, 1F);
        ((CraftPlayer) super.getTarget()).getHandle().netServerHandler.sendPacket(packet);
        super.getInstance().logEvent("Played a sound near " + super.getTarget().getName() + ".");
        if (super.getSender() != null) {
            super.getSender().sendMessage(Util.formatString("Played a sound near " + super.getTarget().getName() + "."));
        }
    }
}
