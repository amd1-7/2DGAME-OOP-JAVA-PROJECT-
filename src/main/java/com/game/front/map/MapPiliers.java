package com.game.front.map;

import static com.game.front.map.CarteStade.mur;

import com.game.models.Equipe;
import com.game.models.Goal;
import com.game.models.Murs;
import com.game.models.physics.Direction;
import com.game.models.physics.Vector2D;

/** Map Piliers : terrain 1200x700  coordonnées en dur. */
public class MapPiliers implements CarteStade {

    private final double largeur = 1200;
    private final double hauteur = 700;
    private final Vector2D positionBalle = new Vector2D(600, 380);
    private final Vector2D positionJoueur1 = new Vector2D(300, 380);
    private final Vector2D positionJoueur2 = new Vector2D(900, 380);
    private final Goal butGauche = new Goal(Equipe.Gauche, new Vector2D(100, 380), Direction.RIGHT, 200, 60, 20, 20);
    private final Goal butDroit = new Goal(Equipe.Droite, new Vector2D(1100, 380), Direction.LEFT, 200, 60, 20, 20);
    private final Murs[] murs = {
            mur(20, 60, 1160, 20),
            mur(20, 680, 1160, 20),
            mur(80, 80, 20, 180),
            mur(80, 500, 20, 180),
            mur(1100, 80, 20, 180),
            mur(1100, 500, 20, 180),
            mur(380, 220, 40, 40), mur(380, 500, 40, 40),
            mur(780, 220, 40, 40), mur(780, 500, 40, 40),
    };

    @Override public double getLargeur() { return largeur; }
    @Override public double getHauteur() { return hauteur; }
    @Override public Vector2D getPositionBalle() { return positionBalle; }
    @Override public Vector2D getPositionJoueur1() { return positionJoueur1; }
    @Override public Vector2D getPositionJoueur2() { return positionJoueur2; }
    @Override public Murs[] getMurs() { return murs; }
    @Override public Goal getButGauche() { return butGauche; }
    @Override public Goal getButDroit() { return butDroit; }
}
