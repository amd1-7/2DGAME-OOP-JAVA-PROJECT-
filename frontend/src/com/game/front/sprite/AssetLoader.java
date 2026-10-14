package com.game.front.sprite;

import java.io.IOException;
import java.io.InputStream;

import javafx.scene.image.Image;

public final class AssetLoader {
    private static final String ROOT = "/assets/";

    private AssetLoader() {}

    public static Image loadImage(String relativePath) {
        String path = ROOT + relativePath;
        try (InputStream in = AssetLoader.class.getResourceAsStream(path)) {
            return readImage(in, path);
        } catch (IOException e) {
            throw new IllegalStateException("Lecture impossible : " + path, e);
        }
    }

    private static Image readImage(InputStream in, String path) {
        if (in == null) throw new IllegalStateException("Image introuvable : " + path);
        return new Image(in);
    }
}
