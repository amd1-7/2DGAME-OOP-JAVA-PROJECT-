package com.game;

import java.util.List;

public class Goal {
    public enum Side { LEFT, RIGHT }

    public static final float MOUTH = 220f;

    public static final float DEPTH = 70f;

    public static final float POST = 10f;

    public static final float GOAL_AREA_DEPTH = 90f, GOAL_AREA_HALF = 120f;

    public static final float PENALTY_DEPTH = 260f, PENALTY_HALF = 250f;

    private final Side side;
    private final Hitbox scoringZone;
    private final Hitbox goalArea;
    private final Hitbox penaltyArea;
    private final List<Hitbox> solids;

    // poteaux + filet du fond = solides ; scoringZone = intérieur de la cage (le GoalHitbox du sujet)
    public Goal(Side side, float lineX, float centerY) {
        this.side = side;
        float top = centerY - MOUTH / 2;
        float bottom = centerY + MOUTH / 2;

        if (side == Side.LEFT) {
            float outer = lineX - DEPTH;
            solids = List.of(
                    new Hitbox(outer, top - POST, DEPTH, POST),
                    new Hitbox(outer, bottom, DEPTH, POST),
                    new Hitbox(outer, top - POST, POST, MOUTH + 2 * POST));
            scoringZone = new Hitbox(outer + POST, top, DEPTH - POST, MOUTH);
            goalArea = new Hitbox(lineX, centerY - GOAL_AREA_HALF, GOAL_AREA_DEPTH, 2 * GOAL_AREA_HALF);
            penaltyArea = new Hitbox(lineX, centerY - PENALTY_HALF, PENALTY_DEPTH, 2 * PENALTY_HALF);
        } else {
            solids = List.of(
                    new Hitbox(lineX, top - POST, DEPTH, POST),
                    new Hitbox(lineX, bottom, DEPTH, POST),
                    new Hitbox(lineX + DEPTH - POST, top - POST, POST, MOUTH + 2 * POST));
            scoringZone = new Hitbox(lineX, top, DEPTH - POST, MOUTH);
            goalArea = new Hitbox(lineX - GOAL_AREA_DEPTH, centerY - GOAL_AREA_HALF, GOAL_AREA_DEPTH, 2 * GOAL_AREA_HALF);
            penaltyArea = new Hitbox(lineX - PENALTY_DEPTH, centerY - PENALTY_HALF, PENALTY_DEPTH, 2 * PENALTY_HALF);
        }
    }

    public Side getSide() { return side; }

    public Hitbox getScoringZone() { return scoringZone; }

    public Hitbox getGoalArea() { return goalArea; }

    public Hitbox getPenaltyArea() { return penaltyArea; }

    public List<Hitbox> getSolids() { return solids; }

    public boolean isBallInside(Hitbox ball) { return scoringZone.containsFully(ball); }
}
