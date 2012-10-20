package com.nkrecklow.herobrine.actions;

import com.nkrecklow.herobrine.Main;
import com.nkrecklow.herobrine.events.Action;
import com.nkrecklow.herobrine.events.ActionType;
import java.util.Random;
import net.minecraft.server.Packet62NamedSoundEffect;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class PlaySound extends Action {

    public PlaySound() {
        super(ActionType.PLAY_SOUND, true);
    }
    
    @Override
    public void onAction(Main plugin, Player player) {
        int rand = new Random().nextInt(2);
        String sound = "step.stone";
        if (rand == 0) {
            sound = "step.gravel";
        } else if (rand == 1) {
            sound = "random.breath";
        }
        Packet62NamedSoundEffect packet = new Packet62NamedSoundEffect(sound, player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ(), 1F, 1F);
        ((CraftPlayer)player).getHandle().netServerHandler.sendPacket(packet);
        plugin.log("Played a sound near " + player.getName() + ".", true);
    }
}
