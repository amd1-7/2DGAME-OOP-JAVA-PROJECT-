package com.game.engine.init;

import javafx.scene.input.KeyCode;

public record Touches(KeyCode haut, KeyCode bas, KeyCode gauche, KeyCode droite,
                      KeyCode tir, KeyCode passe, KeyCode tacle, KeyCode pouvoir) {

    public static final Touches J1 = new Touches(KeyCode.Z, KeyCode.S, KeyCode.Q, KeyCode.D,
            KeyCode.E, KeyCode.R, KeyCode.SPACE, KeyCode.T);

    public static final Touches J2 = new Touches(KeyCode.UP, KeyCode.DOWN, KeyCode.LEFT, KeyCode.RIGHT,
            KeyCode.M, KeyCode.L, KeyCode.K, KeyCode.J);

    public static Touches[] duo() {
        return new Touches[]{J1, J2};
    }
}
