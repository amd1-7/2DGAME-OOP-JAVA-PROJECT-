package com.game.front.selection;

import com.game.engine.init.ChoixJoueur;
import com.game.engine.init.ChoixPouvoir;
import com.game.engine.init.Maillot;
import com.game.front.Fond;
import com.game.front.ui.Ressources;
import javafx.scene.canvas.GraphicsContext;

/**
 * Enchaîne les pages : pouvoir du joueur 1, maillot du joueur 1, pouvoir du joueur 2, maillot du joueur 2.
 * Le joueur 2 ne peut pas prendre le maillot du joueur 1.
 */
public class ParcoursSelection {
    private static final int NB_JOUEURS = 2;
    private static final int ETAPES = NB_JOUEURS * 2;

    private final Fond fond;
    private final ChoixPouvoir[] pouvoirs = new ChoixPouvoir[NB_JOUEURS];
    private final Maillot[] maillots = new Maillot[NB_JOUEURS];
    private PagePouvoir pagePouvoir;
    private PageMaillot pageMaillot;
    private PageSelection page;
    private int etape;
    private boolean termine;
    private boolean annule;

    public ParcoursSelection(Fond fond) {
        this.fond = fond;
        this.page = creerPage();
    }

    private PageSelection creerPage() {
        int joueur = etape / 2;
        if (etape % 2 == 0) {
            pagePouvoir = new PagePouvoir(joueur);
            return pagePouvoir;
        }
        pageMaillot = new PageMaillot(joueur, maillotInterdit(joueur));
        return pageMaillot;
    }

    private Maillot maillotInterdit(int joueur) {
        return joueur == 0 ? null : maillots[0];
    }

    /** À appeler à chaque image : passe à la page suivante (ou précédente) quand la page courante le demande. */
    public void mettreAJour() {
        if (page.estAnnulee()) reculer();
        else if (page.estTerminee()) avancer();
    }

    private void avancer() {
        memoriser();
        etape++;
        if (etape >= ETAPES) termine = true;
        else page = creerPage();
    }

    private void reculer() {
        if (etape == 0) {
            annule = true;
            return;
        }
        etape--;
        page = creerPage();
    }

    private void memoriser() {
        int joueur = etape / 2;
        if (etape % 2 == 0) pouvoirs[joueur] = pagePouvoir.choix();
        else maillots[joueur] = pageMaillot.choix();
    }

    public void dessiner(GraphicsContext gc, double temps) {
        gc.drawImage(Ressources.image("/assets/stade/menu.png"), 0, 0);
        fond.dessinerBallons(gc);
        page.dessiner(gc, temps);
    }

    public void survoler(double x, double y) {
        page.survoler(x, y);
    }

    public void cliquer(double x, double y) {
        page.cliquer(x, y);
    }

    public void naviguer(int sens) {
        page.naviguer(sens);
    }

    public void valider() {
        page.valider();
    }

    public boolean estTermine() {
        return termine;
    }

    public boolean estAnnule() {
        return annule;
    }

    public ChoixJoueur[] resultat() {
        ChoixJoueur[] choix = new ChoixJoueur[NB_JOUEURS];
        for (int i = 0; i < NB_JOUEURS; i++) choix[i] = new ChoixJoueur(pouvoirs[i], maillots[i]);
        return choix;
    }
}
