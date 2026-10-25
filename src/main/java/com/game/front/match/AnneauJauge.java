package com.game.front.match;

import com.game.front.ui.Palette;
import com.game.front.ui.Texte;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;

/** Dessine le cercle de jauge : rouge pendant la charge, vert avec une coche quand le pouvoir est prêt. */
public final class AnneauJauge {
    private static final double EPAISSEUR = 6;

    private AnneauJauge() { }

    public static Color couleurPour(JaugePouvoir.Phase phase) {
        return switch (phase) {
            case CHARGE -> Color.CRIMSON;
            case ACTIF -> Color.ORANGE;
            case PRET -> Color.LIMEGREEN;
        };
    }

    public static void dessiner(GraphicsContext gc, double cx, double cy, double rayon, JaugePouvoir jauge) {
        dessinerPiste(gc, cx, cy, rayon);
        dessinerArc(gc, cx, cy, rayon, jauge);
        if (jauge.estPret()) dessinerValidation(gc, cx, cy);
        else dessinerSecondes(gc, cx, cy, jauge);
    }

    private static void dessinerPiste(GraphicsContext gc, double cx, double cy, double rayon) {
        gc.setLineWidth(EPAISSEUR);
        gc.setStroke(Color.rgb(255, 255, 255, 0.15));
        gc.strokeOval(cx - rayon, cy - rayon, 2 * rayon, 2 * rayon);
    }

    private static void dessinerArc(GraphicsContext gc, double cx, double cy, double rayon, JaugePouvoir jauge) {
        gc.setLineWidth(EPAISSEUR);
        gc.setStroke(couleurPour(jauge.getPhase()));
        gc.strokeArc(cx - rayon, cy - rayon, 2 * rayon, 2 * rayon, 90, -360 * jauge.progression(), ArcType.OPEN);
    }

    private static void dessinerValidation(GraphicsContext gc, double cx, double cy) {
        gc.setLineWidth(4);
        gc.setStroke(Color.LIMEGREEN);
        gc.strokeLine(cx - 7, cy, cx - 2, cy + 6);
        gc.strokeLine(cx - 2, cy + 6, cx + 8, cy - 6);
    }

    private static void dessinerSecondes(GraphicsContext gc, double cx, double cy, JaugePouvoir jauge) {
        String texte = String.valueOf((int) Math.ceil(jauge.secondesRestantes()));
        Texte.centrer(gc, texte, cx, cy, Palette.gras(13), Palette.TEXTE);
    }
}
