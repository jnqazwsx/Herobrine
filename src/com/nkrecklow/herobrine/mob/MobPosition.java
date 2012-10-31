package com.nkrecklow.herobrine.mob;

import java.util.ArrayList;

public enum MobPosition {

    STANDING,
    SNEAKING;
    
    private static ArrayList<MobPosition> positions = new ArrayList<MobPosition>();
    
    static {
        MobPosition.positions.add(MobPosition.STANDING);
        MobPosition.positions.add(MobPosition.SNEAKING);
    }
    
    public static ArrayList<MobPosition> getPositions() {
        return MobPosition.positions;
    }
}
