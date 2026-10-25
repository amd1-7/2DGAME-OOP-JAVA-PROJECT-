package com.game.front.map;

import com.game.models.Goal;
import com.game.models.Murs;
import com.game.models.physics.Hitbox;
import com.game.models.physics.Vector2D;

public interface CarteStade {

    /** @return la largeur du terrain en pixels */
    double getLargeur();

    /** @return la hauteur du terrain en pixels */
    double getHauteur();

    /** @return le centre de l'engagement de la balle */
    Vector2D getPositionBalle();

    /** @return le centre du joueur 1 (équipe Gauche) au coup d'envoi */
    Vector2D getPositionJoueur1();

    /** @return le centre du joueur 2 (équipe Droite) au coup d'envoi */
    Vector2D getPositionJoueur2();

    /** @return les murs du terrain et les obstacles (sans les murs des buts) */
    Murs[] getMurs();

    /** @return le but de l'équipe Gauche */
    Goal getButGauche();

    /** @return le but de l'équipe Droite */
    Goal getButDroit();

    static Murs mur(double x, double y, double l, double h) {
        Vector2D coin = new Vector2D(x, y);
        return new Murs(coin, new Hitbox(coin, l, h));
    }
}
