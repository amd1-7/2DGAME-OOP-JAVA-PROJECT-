package com.game;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public final class AssetLoader {
    private static final String[] BASES = {
            "frontend/resources/assets/",
            "resources/assets/",
            "../resources/assets/",
            "assets/"
    };

    private AssetLoader() {}

    public static BufferedImage loadImage(String relativePath) throws IOException {
        for (String base : BASES) {
            File f = new File(base + relativePath);
            if (f.isFile()) return ImageIO.read(f);
        }

        try (InputStream in = AssetLoader.class.getResourceAsStream("/assets/" + relativePath)) {
            if (in != null) return ImageIO.read(in);
        }
        throw new IOException("Image introuvable : " + relativePath + " (dossiers essayés : " + Arrays.toString(BASES)
                + "). Lance le jeu depuis la racine du projet.");
    }
}
