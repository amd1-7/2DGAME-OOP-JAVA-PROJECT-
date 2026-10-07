package com.game.model;

import com.game.model.physics.Direction;
import com.game.model.physics.Hitbox;
import com.game.model.physics.Vector2D;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BallTest {

    private static final double EPSILON = 0.0001;

    private Ball creerBalle() {
        Vector2D pos = new Vector2D(0, 0);
        Vector2D vel = new Vector2D(0, 0);
        Vector2D acc = new Vector2D(0, 0);
        Hitbox h = new Hitbox(pos, 5, 5);
        return new Ball(pos, vel, acc, h, 0.95);
    }

    private Joueur creerJoueur() {
        Vector2D pos = new Vector2D(100, 100);
        Hitbox h = new Hitbox(pos, 10, 10);
        return new Joueur(pos, new Vector2D(0, 0), new Vector2D(0, 0), h, Equipe.Gauche, 5.0, 2.0, Direction.DROITE);
    }

    // --- TESTS NOMINAUX ---

    @Test
    void testUpdateBalleLibre() {
        Ball b = creerBalle();
        b.getVelocite().add(new Vector2D(10, 0));

        b.update(1.0);

        // Se comporte comme une Entite normale
        assertEquals(10.0, b.getPosition().getX(), EPSILON);
        assertNull(b.getProprietaireActuel());
    }

    @Test
    void testUpdateBalleAvecProprietaire() {
        Ball b = creerBalle();
        Joueur j = creerJoueur(); // Position (100,100), Regard vers DROITE (1, 0)

        b.setProprietaireActuel(j);
        b.update(1.0);

        // Position calculée : joueur.x + direction.x * 30 = 100 + 1 * 30 = 130
        assertEquals(130.0, b.getPosition().getX(), EPSILON);
        assertEquals(100.0, b.getPosition().getY(), EPSILON);
    }

    @Test
    void testEtreTiree() {
        Ball b = creerBalle();
        Joueur j = creerJoueur(); // Puissance 2.0, Direction DROITE (1, 0)
        b.setProprietaireActuel(j);

        b.etreTiree();

        // Le joueur n'est plus propriétaire
        assertNull(b.getProprietaireActuel());

        // Accélération calculée : direction.x * 275 * puissance = 1 * 275 * 2.0 = 550
        assertEquals(550.0, b.getAcceleration().getX(), EPSILON);
        assertEquals(0.0, b.getAcceleration().getY(), EPSILON);
    }

    // --- TESTS D'ERREUR ET CAS LIMITES ---

    @Test
    void testEtreTireeSansProprietaireNeFaitRien() {
        Ball b = creerBalle();

        assertDoesNotThrow(b::etreTiree);
        assertEquals(0.0, b.getAcceleration().getX(), EPSILON);
        assertEquals(0.0, b.getAcceleration().getY(), EPSILON);
    }

    @Test
    void testUpdateAvecProprietairePositionNull_devraitLancerNullPointerException() {
        Ball b = creerBalle();
        Joueur j = new Joueur(null, new Vector2D(0, 0), new Vector2D(0, 0), null, Equipe.Gauche, 1.0, 1.0, Direction.DROITE);

        b.setProprietaireActuel(j);

        assertThrows(NullPointerException.class, () -> b.update(1.0));
    }
}