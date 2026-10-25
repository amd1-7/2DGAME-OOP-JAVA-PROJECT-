package com.game.models.powers;

import javafx.application.Platform;

final class JavaFxTestSupport {
    private static boolean demarre = false;

    private JavaFxTestSupport() { }

    static synchronized void init() {
        if (demarre) return;
        try {
            Platform.startup(() -> { });
        } catch (IllegalStateException dejaDemarre) {
        }
        demarre = true;
    }
}
