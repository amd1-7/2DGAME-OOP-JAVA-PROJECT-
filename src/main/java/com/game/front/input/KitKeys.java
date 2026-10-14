package com.game.front.input;

import com.game.front.sprite.PlayerSprites;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public final class KitKeys {
    private KitKeys() {}

    public static int kitOf(KeyEvent event) {
        int fromCode = fromCode(event.getCode());
        return fromCode >= 0 ? fromCode : fromText(event.getText());
    }

    private static int fromCode(KeyCode code) {
        return switch (code) {
            case NUMPAD1, F1, DIGIT1 -> PlayerSprites.KIT_BLUE;
            case NUMPAD2, F2, DIGIT2 -> PlayerSprites.KIT_RED;
            case NUMPAD3, F3, DIGIT3 -> PlayerSprites.KIT_GOALKEEPER_YELLOW;
            case NUMPAD4, F4, DIGIT4 -> PlayerSprites.KIT_GOALKEEPER_TURQUOISE;
            default -> -1;
        };
    }

    private static int fromText(String text) {
        if (text == null) return -1;
        return switch (text) {
            case "1", "&" -> PlayerSprites.KIT_BLUE;
            case "2", "é" -> PlayerSprites.KIT_RED;
            case "3", "\"" -> PlayerSprites.KIT_GOALKEEPER_YELLOW;
            case "4", "'" -> PlayerSprites.KIT_GOALKEEPER_TURQUOISE;
            default -> -1;
        };
    }
}
