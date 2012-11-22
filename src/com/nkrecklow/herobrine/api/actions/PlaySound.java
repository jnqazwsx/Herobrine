package com.nkrecklow.herobrine.api.actions;

import com.nkrecklow.herobrine.Util;
import com.nkrecklow.herobrine.api.Action;
import java.util.ArrayList;
import java.util.Random;
import net.minecraft.server.Packet62NamedSoundEffect;
import org.bukkit.craftbukkit.entity.CraftPlayer;

public class PlaySound extends Action {

    public PlaySound() {
        super(Action.ActionType.PLAY_SOUND, true);
    }

    @Override
    public void callAction() {
        if (!super.getInstance().getConfiguration().canRunAction("PlaySound")) {
            if (super.getSender() != null) {
                super.getSender().sendMessage(Util.formatString("Playing sounds has been disable in the configuration file."));
            }
            return;
        }
        ArrayList<String> sounds = new ArrayList<String>();
        sounds.add("step.stone1");
        sounds.add("step.gravel1");
        sounds.add("step.grass1");
        sounds.add("step.wood1");
        sounds.add("step.cloth1");
        sounds.add("step.sand1");
        sounds.add("portal.portal");
        sounds.add("portal.travel");
        sounds.add("random.breath");
        sounds.add("random.burp");
        sounds.add("random.chestopen");
        sounds.add("random.chestclosed");
        sounds.add("random.door_close");
        sounds.add("random.break");
        sounds.add("random.door_open");
        sounds.add("random.eat1");
        Packet62NamedSoundEffect packet = new Packet62NamedSoundEffect(sounds.get(new Random().nextInt(sounds.size() - 1)), super.getTarget().getLocation().getX(), super.getTarget().getLocation().getY(), super.getTarget().getLocation().getZ(), 1F, 1F);
        ((CraftPlayer) super.getTarget()).getHandle().netServerHandler.sendPacket(packet);
        super.getInstance().logEvent("Played a sound near " + super.getTarget().getName() + ".");
        if (super.getSender() != null) {
            super.getSender().sendMessage(Util.formatString("Played a sound near " + super.getTarget().getName() + "."));
        }
    }
}
