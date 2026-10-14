package com.game.front.world;

public final class WorldLayout {
    public static final int MAP_WIDTH = 1600;
    public static final int MAP_HEIGHT = 1200;

    public static final double PITCH_LEFT = 150;
    public static final double PITCH_TOP = 100;
    public static final double PITCH_RIGHT = 1450;
    public static final double PITCH_BOTTOM = 1100;
    public static final double CENTER_X = (PITCH_LEFT + PITCH_RIGHT) / 2;
    public static final double CENTER_Y = (PITCH_TOP + PITCH_BOTTOM) / 2;

    public static final double GOAL_MOUTH = 220;
    public static final double GOAL_DEPTH = 70;
    public static final double GOAL_POST = 10;
    public static final double GOAL_BACK = 10;

    public static final double PLAYER_HITBOX = 32;
    public static final double PLAYER_START_OFFSET = 200;
    public static final double SHOT_POWER = 10;

    public static final double BALL_SIZE = 28;
    public static final double WALL_THICKNESS = 100;

    private WorldLayout() {}
}
