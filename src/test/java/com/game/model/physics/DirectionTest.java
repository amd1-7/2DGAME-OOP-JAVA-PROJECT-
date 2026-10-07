package com.game.model.physics;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DirectionTest {

    private static final double EPSILON = 0.0001;

    @Test
    void testGetVecteurBasique() {
        Vector2D vDroite = Direction.DROITE.getVecteur();
        assertEquals(1.0, vDroite.getX(), EPSILON);
        assertEquals(0.0, vDroite.getY(), EPSILON);
    }

    @Test
    void testGetVecteurDiagonaleNormalise() {
        Vector2D vHautGauche = Direction.HAUT_GAUCHE.getVecteur();
        
        assertEquals(1.0, vHautGauche.norm(), EPSILON);
        assertEquals(-0.7071, vHautGauche.getX(), EPSILON);
        assertEquals(-0.7071, vHautGauche.getY(), EPSILON);
    }


    @Test
    void testValueOfDirectionInexistante_devraitLancerIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> Direction.valueOf("HAUT_HAUT"));
    }

    @Test
    void testValueOfNull_devraitLancerNullPointerException() {
        assertThrows(NullPointerException.class, () -> Direction.valueOf(null));
    }
}