package com.game.front.bridge;

import com.game.models.Goal;

public class GoalWatcher {
    private boolean wasInside;

    public boolean scoredNow(Goal scoringGoal) {
        boolean inside = scoringGoal != null;
        boolean scored = inside && !wasInside;
        wasInside = inside;
        return scored;
    }
}
