package com.nkrecklow.herobrine.actions;

import com.nkrecklow.herobrine.Main;
import com.nkrecklow.herobrine.api.Action;
import com.nkrecklow.herobrine.api.ActionType;
import org.bukkit.entity.Player;

public class SendMessage extends Action {

    public SendMessage() {
        super(ActionType.SEND_MESSAGE, true);
    }
    
    @Override
    public void onAction(Main plugin, Player player) {
        if (!plugin.getConfiguration().canSendMessages()) {
            return;
        }
        player.sendMessage(plugin.formatMessage(plugin.getConfiguration().getMessage()));
        plugin.log("Sent a message to " + player.getName() + ".");
    }
}
