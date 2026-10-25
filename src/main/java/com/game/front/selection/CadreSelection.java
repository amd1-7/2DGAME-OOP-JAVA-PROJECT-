package com.game.front.selection;

import com.game.front.ui.BoutonFleche;
import com.game.front.ui.Palette;
import com.game.front.ui.Texte;
import com.game.front.ui.Zone;
import javafx.scene.canvas.GraphicsContext;

/**
 * Le cadre fixe façon écran de choix d'équipe : un grand cadre, un petit onglet fixe au-dessus avec le titre,
 * et deux flèches qui font changer ce qu'il contient.
 */
public class CadreSelection {
    private static final Zone CADRE = new Zone(300, 270, 600, 250);
    private static final Zone ONGLET = new Zone(470, 222, 260, 54);
    private static final double DUREE_FONDU = 0.2;

    private final String titre;
    private final BoutonFleche gauche = new BoutonFleche(new Zone(170, 350, 100, 90), -1);
    private final BoutonFleche droite = new BoutonFleche(new Zone(930, 350, 100, 90), 1);
    private double temps;
    private double debutChangement = -1;

    public CadreSelection(String titre) {
        this.titre = titre;
    }

    public Zone zoneContenu() {
        return CADRE;
    }

    public void dessiner(GraphicsContext gc, double temps) {
        this.temps = temps;
        dessinerCadre(gc);
        dessinerOnglet(gc);
        gauche.dessiner(gc);
        droite.dessiner(gc);
    }

    private void dessinerCadre(GraphicsContext gc) {
        gc.setFill(Palette.FOND_CADRE);
        gc.fillRoundRect(CADRE.x(), CADRE.y(), CADRE.l(), CADRE.h(), 26, 26);
        gc.setStroke(Palette.BORDURE);
        gc.setLineWidth(5);
        gc.strokeRoundRect(CADRE.x(), CADRE.y(), CADRE.l(), CADRE.h(), 26, 26);
    }

    private void dessinerOnglet(GraphicsContext gc) {
        gc.setFill(Palette.BORDURE);
        gc.fillRoundRect(ONGLET.x(), ONGLET.y(), ONGLET.l(), ONGLET.h(), 16, 16);
        Texte.centrer(gc, titre, ONGLET.centreX(), ONGLET.centreY(), Palette.gras(24), Palette.ENCRE);
    }

    public void survoler(double x, double y) {
        gauche.survoler(x, y);
        droite.survoler(x, y);
    }

    /** @return -1 si la flèche gauche est sous la souris, +1 pour la droite, 0 sinon */
    public int fleche(double x, double y) {
        if (gauche.contient(x, y)) return gauche.getSens();
        if (droite.contient(x, y)) return droite.getSens();
        return 0;
    }

    public void signalerChangement() {
        debutChangement = temps;
    }

    /** Opacité du contenu : il apparaît en fondu quand on change d'élément. */
    public double opaciteContenu() {
        double ecoule = temps - debutChangement;
        return Math.max(0.15, Math.min(1, ecoule / DUREE_FONDU));
    }
}
