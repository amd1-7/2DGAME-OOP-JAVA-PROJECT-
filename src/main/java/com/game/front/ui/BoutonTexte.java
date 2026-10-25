package com.game.front.ui;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/** Bouton rectangulaire avec un libellé. */
public class BoutonTexte {
    private final Zone zone;
    private final String libelle;
    private boolean survol;

    public BoutonTexte(Zone zone, String libelle) {
        this.zone = zone;
        this.libelle = libelle;
    }

    public void survoler(double x, double y) {
        survol = zone.contient(x, y);
    }

    public boolean contient(double x, double y) {
        return zone.contient(x, y);
    }

    /** @param principal true pour le bouton doré (action principale), false pour un bouton discret */
    public void dessiner(GraphicsContext gc, boolean principal) {
        dessinerFond(gc, principal);
        double taille = principal ? 28 : 22;
        Texte.centrer(gc, libelle, zone.centreX(), zone.centreY(), Palette.gras(taille), couleurTexte(principal));
    }

    private void dessinerFond(GraphicsContext gc, boolean principal) {
        gc.setFill(couleurFond(principal));
        gc.fillRoundRect(zone.x(), zone.y(), zone.l(), zone.h(), 16, 16);
        gc.setStroke(Palette.BORDURE);
        gc.setLineWidth(3);
        gc.strokeRoundRect(zone.x(), zone.y(), zone.l(), zone.h(), 16, 16);
    }

    private Color couleurFond(boolean principal) {
        if (principal) return survol ? Color.rgb(255, 224, 80) : Palette.BORDURE;
        return survol ? Color.rgb(40, 52, 90, 0.95) : Palette.FOND_CADRE;
    }

    private Color couleurTexte(boolean principal) {
        return principal ? Palette.ENCRE : Palette.TEXTE;
    }
}
