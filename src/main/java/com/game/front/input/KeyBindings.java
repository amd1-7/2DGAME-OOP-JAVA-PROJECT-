package com.game.front.input;

import java.util.HashMap;
import java.util.Map;

import com.game.models.Ball;
import com.game.models.input.ControleurHumain;
import com.game.models.physics.Direction;

import javafx.scene.input.KeyCode;

public final class KeyBindings {
    private KeyBindings() {}

    public static ControleurHumain createController() {
        return new ControleurHumain(moveKeys(), actionKeys());
    }

    private static Map<KeyCode, Direction> moveKeys() {
        Map<KeyCode, Direction> keys = new HashMap<>();
        bind(keys, Direction.UP, KeyCode.Z, KeyCode.UP);
        bind(keys, Direction.DOWN, KeyCode.S, KeyCode.DOWN);
        bind(keys, Direction.LEFT, KeyCode.Q, KeyCode.LEFT);
        bind(keys, Direction.RIGHT, KeyCode.D, KeyCode.RIGHT);
        return keys;
    }

    private static void bind(Map<KeyCode, Direction> keys, Direction direction, KeyCode first, KeyCode second) {
        keys.put(first, direction);
        keys.put(second, direction);
    }

    private static Map<KeyCode, Ball.Etat> actionKeys() {
        Map<KeyCode, Ball.Etat> keys = new HashMap<>();
        keys.put(KeyCode.SPACE, Ball.Etat.TIRE);
        keys.put(KeyCode.V, Ball.Etat.PASSE);
        return keys;
    }
}
