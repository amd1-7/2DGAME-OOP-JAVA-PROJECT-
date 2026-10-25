package com.game.models.physics;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HitboxTest {

    private static final double EPS = 1e-9;
    private Hitbox base;

    @BeforeEach
    void setUp() {
        base = new Hitbox(new Vector2D(0, 0), 10, 10);
    }

    private Hitbox box(double x, double y) {
        return new Hitbox(new Vector2D(x, y), 10, 10);
    }

    @Test
    void getters_renvoientLesValeursDuConstructeur() {
        Hitbox h = new Hitbox(new Vector2D(2, 3), 20, 30);
        assertEquals(2, h.getPosition().getX(), EPS);
        assertEquals(3, h.getPosition().getY(), EPS);
        assertEquals(20, h.getWidth(), EPS);
        assertEquals(30, h.getHeight(), EPS);
    }

    @Test
    void setPosition_changeLaPosition() {
        base.setPosition(new Vector2D(7, 8));
        assertEquals(7, base.getPosition().getX(), EPS);
        assertEquals(8, base.getPosition().getY(), EPS);
    }

    @Test
    void getCentre_estAuMilieuDeLaHitbox() {
        Hitbox h = new Hitbox(new Vector2D(10, 20), 40, 60);
        assertEquals(30, h.getCentre().getX(), EPS);
        assertEquals(50, h.getCentre().getY(), EPS);
    }

    @Test
    void contient_pointInterieur() {
        assertTrue(base.contient(new Vector2D(5, 5)));
    }

    @Test
    void contient_pointSurLeBordEstContenu() {
        assertTrue(base.contient(new Vector2D(0, 0)));
        assertTrue(base.contient(new Vector2D(10, 10)));
    }

    @Test
    void intersect_autreADroite_donneRIGHT() {
        assertEquals(Direction.RIGHT, base.intersect(box(8, 0)));
    }

    @Test
    void intersect_autreAGauche_donneLEFT() {
        assertEquals(Direction.LEFT, base.intersect(box(-8, 0)));
    }

    @Test
    void intersect_autreEnBas_donneDOWN() {
        assertEquals(Direction.DOWN, base.intersect(box(0, 8)));
    }

    @Test
    void intersect_autreEnHaut_donneUP() {
        assertEquals(Direction.UP, base.intersect(box(0, -8)));
    }

    @Test
    void intersect_memePosition_renvoieUneDirection() {
        assertNotNull(base.intersect(box(0, 0)));
    }

    @Test
    void intersect_hitboxesLoinDeLAutre_donneNull() {
        assertNull(base.intersect(box(100, 100)));
    }

    @Test
    void intersect_bordsSeTouchentSeulement_donneNull() {
        assertNull(base.intersect(box(10, 0)));
        assertNull(base.intersect(box(-10, 0)));
        assertNull(base.intersect(box(0, 10)));
        assertNull(base.intersect(box(0, -10)));
    }

    @Test
    void intersect_alignesEnXmaisSepareesEnY_donneNull() {
        assertNull(base.intersect(box(5, 50)));
    }

    @Test
    void intersect_avecNull_leveNullPointerException() {
        assertThrows(NullPointerException.class, () -> base.intersect(null));
    }

    @Test
    void contient_pointExterieur_estFaux() {
        assertFalse(base.contient(new Vector2D(11, 5)));
        assertFalse(base.contient(new Vector2D(5, -1)));
    }

    @Test
    void contient_avecNull_leveNullPointerException() {
        assertThrows(NullPointerException.class, () -> base.contient(null));
    }

    @Test
    void getCentre_sansPosition_leveNullPointerException() {
        Hitbox h = new Hitbox(null, 10, 10);
        assertThrows(NullPointerException.class, h::getCentre);
    }
}
