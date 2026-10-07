package com.game;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.EnumMap;
import java.util.Map;

public final class PlayerSprites {
    public static final int KIT_BLUE = 0;
    public static final int KIT_RED = 1;
    public static final int KIT_GOALKEEPER_YELLOW = 2;
    public static final int KIT_GOALKEEPER_TURQUOISE = 3;

    public static final int KIT_COUNT = 4;

    public static final int CELL = 32;

    public static final int PLAYER_PIXELS = 24;

    private static final String SHEET_PATH = "players/joueurs_anim.png";

    private static final PlayerState[] ORDER = {
            PlayerState.IDLE, PlayerState.WALKING, PlayerState.RUNNING, PlayerState.SPRINTING,
            PlayerState.PASSING, PlayerState.SHOOTING, PlayerState.SUPER_SHOOTING,
            PlayerState.TACKLING, PlayerState.CELEBRATING
    };

    private static final int[] FRAMES = {4, 8, 6, 6, 6, 7, 12, 7, 12};

    private static final int[] FPS = {4, 10, 14, 18, 14, 14, 12, 12, 12};

    private static final boolean[] LOOP = {true, true, true, true, false, false, false, false, true};

    private static final String[] KIT_NAMES = {"Bleu", "Rouge", "Gardien jaune", "Gardien turquoise"};

    private static BufferedImage sheet;
    private static boolean loadAttempted = false;

    private PlayerSprites() {}

    public static String kitName(int kit) {
        return KIT_NAMES[Math.floorMod(kit, KIT_COUNT)];
    }

    // feuille joueurs_anim.png : cases de 32 px, une ligne par animation
    // ligne = tenue * 9 + position de l'animation dans ORDER ; images alignées de gauche à droite
    public static Map<PlayerState, Animation> load(int kit) {
        Map<PlayerState, Animation> animations = new EnumMap<>(PlayerState.class);
        BufferedImage img = getSheet();
        if (img == null) return animations;

        int k = Math.floorMod(kit, KIT_COUNT);

        for (int i = 0; i < ORDER.length; i++) {
            BufferedImage[] frames = new BufferedImage[FRAMES[i]];
            for (int f = 0; f < frames.length; f++) {
                int x = f * CELL;
                int y = (k * ORDER.length + i) * CELL;
                frames[f] = img.getSubimage(x, y, CELL, CELL);
            }

            animations.put(ORDER[i], new Animation(frames, 1.0 / FPS[i], LOOP[i]));
        }
        return animations;
    }

    private static synchronized BufferedImage getSheet() {
        if (!loadAttempted) {
            loadAttempted = true;
            try {
                BufferedImage img = AssetLoader.loadImage(SHEET_PATH);

                int maxFrames = 0;
                for (int n : FRAMES) maxFrames = Math.max(maxFrames, n);
                if (img.getWidth() < maxFrames * CELL || img.getHeight() < KIT_COUNT * ORDER.length * CELL) {
                    System.err.println("Feuille d'animations de taille inattendue : " + img.getWidth() + "x" + img.getHeight());
                } else {
                    sheet = img;
                }
            } catch (IOException e) {
                System.err.println("Erreur de chargement du sprite : " + e.getMessage());
            }
        }
        return sheet;
    }
}
