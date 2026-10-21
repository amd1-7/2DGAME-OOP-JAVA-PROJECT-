package com.game.front;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import javafx.scene.image.Image;

final class Images {
    private static final Map<String, Image> CACHE = new HashMap<>();

    private Images() { }

    static Image charger(String chemin) {
        return CACHE.computeIfAbsent(chemin, c -> {
            try (InputStream in = Images.class.getResourceAsStream(c)) {
                return new Image(Objects.requireNonNull(in, "Image introuvable : " + c));
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        });
    }
}
