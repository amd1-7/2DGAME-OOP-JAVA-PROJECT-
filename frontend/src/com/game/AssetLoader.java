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
            File file = new File(base + relativePath);
            if (file.isFile()) {
                return ImageIO.read(file);
            }
        }

        try (InputStream in = AssetLoader.class.getResourceAsStream("/assets/" + relativePath)) {
            if (in != null) {
                return ImageIO.read(in);
            }
        }

        throw new IOException("Ressource graphique introuvable : " + relativePath 
                + " (Recherchée dans : " + Arrays.toString(BASES) + ")");
    }
}