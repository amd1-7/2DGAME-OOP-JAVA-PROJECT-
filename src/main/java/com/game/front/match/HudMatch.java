package com.game.front.match;

import com.game.engine.MatchManager;
import com.game.engine.init.ChoixJoueur;
import com.game.engine.init.Touches;
import com.game.front.map.Carte;
import com.game.front.ui.Zone;
import com.game.models.personnage.AccesPouvoir;
import com.game.models.personnage.Personnage;
import javafx.scene.canvas.GraphicsContext;

/** Les deux cadres de pouvoir, un à chaque extrémité de l'écran. */
public class HudMatch {
    private static final double LARGEUR_CADRE = 290;
    private static final double HAUTEUR_CADRE = 50;
    private static final double MARGE = 8;
    private static final double HAUT = 5;

    private final CadrePouvoirHud[] cadres;

    public HudMatch(MatchManager match) {
        Personnage[] joueurs = match.getPersonnages();
        ChoixJoueur[] choix = match.getChoix();
        Touches[] touches = Touches.duo();
        cadres = new CadrePouvoirHud[joueurs.length];
        for (int i = 0; i < joueurs.length; i++) {
            cadres[i] = creerCadre(i, joueurs[i], choix[i], touches[i]);
        }
    }

    private CadrePouvoirHud creerCadre(int numero, Personnage joueur, ChoixJoueur choix, Touches touches) {
        boolean aDroite = numero == 1;
        JaugePouvoir jauge = new JaugePouvoir(AccesPouvoir.de(joueur));
        return new CadrePouvoirHud(zonePour(aDroite), aDroite, choix.maillot().getCouleurUi(),
                choix.pouvoir().getNom(), touches.pouvoir().getName(), jauge);
    }

    private Zone zonePour(boolean aDroite) {
        double x = aDroite ? Carte.LARGEUR_ECRAN - MARGE - LARGEUR_CADRE : MARGE;
        return new Zone(x, HAUT, LARGEUR_CADRE, HAUTEUR_CADRE);
    }

    public void mettreAJour(double dt) {
        for (CadrePouvoirHud cadre : cadres) cadre.mettreAJour(dt);
    }

    public void dessiner(GraphicsContext gc) {
        for (CadrePouvoirHud cadre : cadres) cadre.dessiner(gc);
    }
}
