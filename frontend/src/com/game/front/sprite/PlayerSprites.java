package com.game.front.sprite;

import java.util.EnumMap;
import java.util.Map;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;

public final class PlayerSprites {
    public static final int KIT_BLUE = 0;
    public static final int KIT_RED = 1;
    public static final int KIT_GOALKEEPER_YELLOW = 2;
    public static final int KIT_GOALKEEPER_TURQUOISE = 3;
    public static final int KIT_COUNT = 4;
    public static final int CELL = 32;
    public static final int PLAYER_PIXELS = 24;

    private static final String SHEET_PATH = "players/joueurs_anim.png";
    private static final String[] KIT_NAMES = {"Bleu", "Rouge", "Gardien jaune", "Gardien turquoise"};

    private static final PlayerState[] ORDER = {
            PlayerState.IDLE, PlayerState.WALKING, PlayerState.RUNNING, PlayerState.SPRINTING,
            PlayerState.PASSING, PlayerState.SHOOTING, PlayerState.SUPER_SHOOTING,
            PlayerState.TACKLING, PlayerState.CELEBRATING
    };
    private static final int[] FRAMES = {4, 8, 6, 6, 6, 7, 12, 7, 12};
    private static final int[] FPS = {4, 10, 14, 18, 14, 14, 12, 12, 12};
    private static final boolean[] LOOP = {true, true, true, true, false, false, false, false, true};

    private static Image sheet;

    private PlayerSprites() {}

    public static synchronized Image sheet() {
        if (sheet == null) sheet = AssetLoader.loadImage(SHEET_PATH);
        return sheet;
    }

    public static String kitName(int kit) {
        return KIT_NAMES[normalizeKit(kit)];
    }

    public static int normalizeKit(int kit) {
        return Math.floorMod(kit, KIT_COUNT);
    }

    public static Map<PlayerState, Animation> load(int kit) {
        Map<PlayerState, Animation> animations = new EnumMap<>(PlayerState.class);
        for (int i = 0; i < ORDER.length; i++) animations.put(ORDER[i], buildAnimation(normalizeKit(kit), i));
        return animations;
    }

    private static Animation buildAnimation(int kit, int index) {
        return new Animation(buildFrames(kit, index), 1.0 / FPS[index], LOOP[index]);
    }

    private static Rectangle2D[] buildFrames(int kit, int index) {
        Rectangle2D[] frames = new Rectangle2D[FRAMES[index]];
        for (int column = 0; column < frames.length; column++) frames[column] = buildFrame(kit, index, column);
        return frames;
    }

    private static Rectangle2D buildFrame(int kit, int index, int column) {
        int row = kit * ORDER.length + index;
        return new Rectangle2D(column * CELL, row * CELL, CELL, CELL);
    }
}
