package com.game.front.match;

import com.game.front.ui.Palette;
import com.game.front.ui.Texte;
import com.game.front.ui.Zone;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/** Cadre d'un joueur en match : le nom de son pouvoir et son cercle de jauge. */
public class CadrePouvoirHud {
    private static final double RAYON = 17;
    private static final double MARGE_ANNEAU = 30;
    private static final double MARGE_TEXTE = 64;

    private final Zone zone;
    private final boolean aDroite;
    private final Color couleur;
    private final String nomPouvoir;
    private final String touche;
    private final JaugePouvoir jauge;

    public CadrePouvoirHud(Zone zone, boolean aDroite, Color couleur, String nomPouvoir, String touche,
                           JaugePouvoir jauge) {
        this.zone = zone;
        this.aDroite = aDroite;
        this.couleur = couleur;
        this.nomPouvoir = nomPouvoir;
        this.touche = touche;
        this.jauge = jauge;
    }

    public void mettreAJour(double dt) {
        jauge.mettreAJour(dt);
    }

    public void dessiner(GraphicsContext gc) {
        dessinerFond(gc);
        AnneauJauge.dessiner(gc, centreAnneauX(), zone.centreY(), RAYON, jauge);
        dessinerTextes(gc);
    }

    private double centreAnneauX() {
        return aDroite ? zone.x() + zone.l() - MARGE_ANNEAU : zone.x() + MARGE_ANNEAU;
    }

    private double departTexteX() {
        return aDroite ? zone.x() + zone.l() - MARGE_TEXTE : zone.x() + MARGE_TEXTE;
    }

    private void dessinerFond(GraphicsContext gc) {
        gc.setFill(Palette.FOND_CADRE);
        gc.fillRoundRect(zone.x(), zone.y(), zone.l(), zone.h(), 14, 14);
        gc.setStroke(couleur);
        gc.setLineWidth(3);
        gc.strokeRoundRect(zone.x(), zone.y(), zone.l(), zone.h(), 14, 14);
    }

    private void dessinerTextes(GraphicsContext gc) {
        double largeurMax = zone.l() - MARGE_TEXTE - 14;
        Font police = Texte.ajuster(FontWeight.BOLD, 18, nomPouvoir, largeurMax);
        ecrire(gc, nomPouvoir, zone.y() + 18, police, Palette.TEXTE);
        ecrire(gc, libelleEtat(), zone.y() + 37, Palette.normal(14), AnneauJauge.couleurPour(jauge.getPhase()));
    }

    private void ecrire(GraphicsContext gc, String texte, double y, Font police, Color couleurTexte) {
        if (aDroite) Texte.droite(gc, texte, departTexteX(), y, police, couleurTexte);
        else Texte.gauche(gc, texte, departTexteX(), y, police, couleurTexte);
    }

    private String libelleEtat() {
        return switch (jauge.getPhase()) {
            case PRET -> "PRÊT  [" + touche + "]";
            case ACTIF -> "ACTIF";
            case CHARGE -> "Charge en cours";
        };
    }
}
