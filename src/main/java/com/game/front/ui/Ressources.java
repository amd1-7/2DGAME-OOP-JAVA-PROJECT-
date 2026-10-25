package com.game.front.ui;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import javafx.scene.image.Image;

/** Chargement (avec cache) des images du dossier resources. */
public final class Ressources {
    private static final Map<String, Image> CACHE = new HashMap<>();

    private Ressources() { }

    public static Image image(String chemin) {
        Image image = CACHE.get(chemin);
        if (image == null) {
            image = lire(chemin);
            CACHE.put(chemin, image);
        }
        return image;
    }

    private static Image lire(String chemin) {
        try (InputStream in = Ressources.class.getResourceAsStream(chemin)) {
            return new Image(Objects.requireNonNull(in, "Image introuvable : " + chemin));
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
