package com.game.front.ui;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/** Flèche cliquable (gauche ou droite) qui fait défiler le contenu d'un cadre. */
public class BoutonFleche {
    private static final double DEMI_HAUTEUR = 20;

    private final Zone zone;
    private final int sens;
    private boolean survol;

    /** @param sens -1 pour la flèche gauche, +1 pour la flèche droite */
    public BoutonFleche(Zone zone, int sens) {
        this.zone = zone;
        this.sens = sens;
    }

    public void survoler(double x, double y) {
        survol = zone.contient(x, y);
    }

    public boolean contient(double x, double y) {
        return zone.contient(x, y);
    }

    public int getSens() {
        return sens;
    }

    public void dessiner(GraphicsContext gc) {
        dessinerFond(gc);
        dessinerTriangle(gc);
    }

    private void dessinerFond(GraphicsContext gc) {
        gc.setFill(survol ? Color.rgb(250, 204, 21, 0.95) : Palette.FOND_CADRE);
        gc.fillRoundRect(zone.x(), zone.y(), zone.l(), zone.h(), 18, 18);
        gc.setStroke(Palette.BORDURE);
        gc.setLineWidth(3);
        gc.strokeRoundRect(zone.x(), zone.y(), zone.l(), zone.h(), 18, 18);
    }

    private void dessinerTriangle(GraphicsContext gc) {
        double cx = zone.centreX();
        double cy = zone.centreY();
        double pointe = cx + sens * DEMI_HAUTEUR * 0.9;
        double base = cx - sens * DEMI_HAUTEUR * 0.5;
        gc.setFill(survol ? Palette.ENCRE : Palette.BORDURE);
        gc.fillPolygon(new double[]{pointe, base, base},
                new double[]{cy, cy - DEMI_HAUTEUR, cy + DEMI_HAUTEUR}, 3);
    }
}
