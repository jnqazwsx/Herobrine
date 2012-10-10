package com.nkrecklow.herobrine.actions;

import com.nkrecklow.herobrine.api.Action;
import com.nkrecklow.herobrine.api.ActionType;

public class SendMessage extends Action {

    public SendMessage() {
        super(ActionType.SEND_MESSAGE);
    }
    
    @Override
    public void onAction() {
        if (!super.getPlugin().getConfiguration().canSendMessages()) {
            return;
        }
        super.getPlayer().sendMessage(super.getPlugin().formatMessage(super.getPlugin().getConfiguration().getMessage()));
        super.getPlugin().log("Sent a message to " + super.getPlayer().getName() + ".");
    }
}
