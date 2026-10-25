package com.game.front.selection;

import com.game.front.ui.BoutonTexte;
import com.game.front.ui.Palette;
import com.game.front.ui.Texte;
import com.game.front.ui.Zone;
import java.util.List;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/** Page générique : un joueur choisit un élément d'une liste avec les flèches, puis confirme. */
public abstract class PageChoix<T> implements PageSelection {
    private final int joueur;
    private final List<T> options;
    private final CadreSelection cadre;
    private final BoutonTexte confirmer = new BoutonTexte(new Zone(480, 575, 240, 60), "CONFIRMER");
    private final BoutonTexte retour = new BoutonTexte(new Zone(60, 600, 160, 50), "RETOUR");
    private int index;
    private boolean terminee;
    private boolean annulee;

    protected PageChoix(int joueur, String titre, List<T> options) {
        this.joueur = joueur;
        this.options = options;
        this.cadre = new CadreSelection(titre);
    }

    public T choix() {
        return options.get(index);
    }

    /** Dessine l'élément courant à l'intérieur du cadre fixe. */
    protected abstract void dessinerContenu(GraphicsContext gc, Zone zone, T option, double temps);

    @Override
    public void dessiner(GraphicsContext gc, double temps) {
        dessinerEntete(gc);
        cadre.dessiner(gc, temps);
        dessinerContenuAnime(gc, temps);
        dessinerPastilles(gc);
        confirmer.dessiner(gc, true);
        retour.dessiner(gc, false);
    }

    private void dessinerEntete(GraphicsContext gc) {
        Texte.centrer(gc, "JOUEUR " + (joueur + 1), 600, 100, Palette.gras(60), Palette.couleurJoueur(joueur));
        Texte.centrer(gc, "Clique sur les flèches pour choisir", 600, 160, Palette.normal(22), Palette.TEXTE_DOUX);
    }

    private void dessinerContenuAnime(GraphicsContext gc, double temps) {
        gc.setGlobalAlpha(cadre.opaciteContenu());
        dessinerContenu(gc, cadre.zoneContenu(), choix(), temps);
        gc.setGlobalAlpha(1);
    }

    private void dessinerPastilles(GraphicsContext gc) {
        double depart = 600 - (options.size() - 1) * 14;
        for (int i = 0; i < options.size(); i++) {
            gc.setFill(i == index ? Palette.BORDURE : Color.rgb(255, 255, 255, 0.3));
            gc.fillOval(depart + i * 28 - 6, 538, 12, 12);
        }
    }

    @Override
    public void survoler(double x, double y) {
        cadre.survoler(x, y);
        confirmer.survoler(x, y);
        retour.survoler(x, y);
    }

    @Override
    public void cliquer(double x, double y) {
        int sens = cadre.fleche(x, y);
        if (sens != 0) naviguer(sens);
        else if (confirmer.contient(x, y)) valider();
        else if (retour.contient(x, y)) annulee = true;
    }

    @Override
    public void naviguer(int sens) {
        index = Math.floorMod(index + sens, options.size());
        cadre.signalerChangement();
    }

    @Override
    public void valider() {
        terminee = true;
    }

    @Override
    public boolean estTerminee() {
        return terminee;
    }

    @Override
    public boolean estAnnulee() {
        return annulee;
    }
}
