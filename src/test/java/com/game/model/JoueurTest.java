package com.game.model;

import com.game.model.physics.Direction;
import com.game.model.physics.Hitbox;
import com.game.model.physics.Vector2D;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JoueurTest {

    private static final double EPSILON = 0.0001;

    private Joueur creerJoueurParDefaut() {
        Vector2D pos = new Vector2D(0, 0);
        Vector2D vel = new Vector2D(0, 0);
        Vector2D acc = new Vector2D(0, 0);
        Hitbox h = new Hitbox(pos, 10, 10);
        return new Joueur(pos, vel, acc, h, Equipe.Gauche, 5.0, 10.0, Direction.DROITE);
    }

    // --- TESTS NOMINAUX ---

    @Test
    void testConstructeurEtGetters() {
        Joueur j = creerJoueurParDefaut();

        assertEquals(Equipe.Gauche, j.getEquipe());
        assertEquals(5.0, j.getVitesse(), EPSILON);
        assertEquals(10.0, j.getPuissanceTir(), EPSILON);
        assertEquals(Direction.DROITE, j.getDirectionRegard());
    }

    @Test
    void testSetRegardChangementDirection() {
        Joueur j = creerJoueurParDefaut();

        // Regard vers le haut
        j.setRegard(new Vector2D(0, -1));
        assertEquals(Direction.HAUT, j.getDirectionRegard());

        // Regard en bas-droite
        j.setRegard(new Vector2D(1, 1));
        assertEquals(Direction.BAS_DROITE, j.getDirectionRegard());
    }

    // --- TESTS D'ERREUR ET CAS LIMITES ---

    @Test
    void testSetRegardVectorNulNeChangePasLeRegard() {
        Joueur j = creerJoueurParDefaut();

        j.setRegard(new Vector2D(0, 0));

        // Conserve la direction par défaut (DROITE)
        assertEquals(Direction.DROITE, j.getDirectionRegard());
    }

    @Test
    void testSetRegardNullNeChangePasLeRegard() {
        Joueur j = creerJoueurParDefaut();

        j.setRegard(null);

        assertEquals(Direction.DROITE, j.getDirectionRegard());
    }
}