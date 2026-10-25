package com.game.engine.init;

import com.game.front.ui.Ressources;
import com.game.models.input.ControleurHumain;
import com.game.models.personnage.Personnage;
import com.game.models.physics.Direction;
import com.game.models.physics.Hitbox;
import com.game.models.physics.Vector2D;
import com.game.view.spritesheet.Annimation;
import javafx.scene.image.Image;

public class FabriqueJoueurs {
    public static final double TAILLE_HITBOX = 32;

    private static final String FEUILLE = "/assets/players/joueurs_anim.png";
    private static final String GLACONS = "/assets/players/joueur_glacons.png";

    private final Image feuille = Ressources.image(FEUILLE);
    private final Image glacons = Ressources.image(GLACONS);

    public Personnage[] creerDuo(ChoixJoueur[] choix, ControleurHumain[] controleurs, Vector2D[] departs) {
        Personnage[] joueurs = new Personnage[choix.length];
        for (int i = 0; i < choix.length; i++) {
            joueurs[i] = creer(choix[i], controleurs[i], departs[i]);
        }
        lierAdversaires(choix, joueurs);
        return joueurs;
    }

    private Personnage creer(ChoixJoueur choix, ControleurHumain controleur, Vector2D coin) {
        Annimation animation = creerAnimation(choix.maillot());
        return choix.pouvoir().creer(coin, creerHitbox(coin), controleur, animation);
    }

    private Hitbox creerHitbox(Vector2D coin) {
        return new Hitbox(coin, TAILLE_HITBOX, TAILLE_HITBOX);
    }

    private Annimation creerAnimation(Maillot maillot) {
        return new Annimation(maillot.getCouleur(), feuille, glacons);
    }

    private void lierAdversaires(ChoixJoueur[] choix, Personnage[] joueurs) {
        for (int i = 0; i < joueurs.length; i++) {
            choix[i].pouvoir().lier(joueurs[i], joueurs[joueurs.length - 1 - i]);
        }
    }

    public static Vector2D coinDepart(Vector2D centre) {
        return centre.sub(new Vector2D(TAILLE_HITBOX / 2, TAILLE_HITBOX / 2));
    }

    public static Vector2D regardDepart(int numero) {
        return (numero == 0 ? Direction.RIGHT : Direction.LEFT).getVector();
    }
}
