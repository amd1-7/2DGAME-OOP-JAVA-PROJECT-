package com.game.models.physics;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class Vector2DTest {

    private static final double EPS = 1e-9;

    @Test
    void constructeur_stockeXetY() {
        Vector2D v = new Vector2D(3, -4);
        assertEquals(3, v.getX(), EPS);
        assertEquals(-4, v.getY(), EPS);
    }

    @Test
    void add_additionneLesComposantes() {
        Vector2D r = new Vector2D(1, 2).add(new Vector2D(3, 4));
        assertEquals(4, r.getX(), EPS);
        assertEquals(6, r.getY(), EPS);
    }

    @Test
    void add_neModifiePasLesOperandes() {
        Vector2D a = new Vector2D(1, 2);
        a.add(new Vector2D(10, 10));
        assertEquals(1, a.getX(), EPS);
        assertEquals(2, a.getY(), EPS);
    }

    @Test
    void sub_soustraitLesComposantes() {
        Vector2D r = new Vector2D(5, 7).sub(new Vector2D(2, 3));
        assertEquals(3, r.getX(), EPS);
        assertEquals(4, r.getY(), EPS);
    }

    @Test
    void multiply_multiplieParUnScalaire() {
        Vector2D r = new Vector2D(2, -3).multiply(2.5);
        assertEquals(5, r.getX(), EPS);
        assertEquals(-7.5, r.getY(), EPS);
    }

    @Test
    void scaler_multiplieComposanteParComposante() {
        Vector2D r = new Vector2D(2, 3).scaler(new Vector2D(4, 5));
        assertEquals(8, r.getX(), EPS);
        assertEquals(15, r.getY(), EPS);
    }

    @Test
    void norm_triangle3_4_5() {
        assertEquals(5, new Vector2D(3, 4).norm(), EPS);
    }

    @Test
    void norm_vecteurNulEstZero() {
        assertEquals(0, new Vector2D(0, 0).norm(), EPS);
    }

    @Test
    void normalize_donneUnVecteurDeNormeUn() {
        Vector2D n = new Vector2D(3, 4).normalize();
        assertEquals(0.6, n.getX(), EPS);
        assertEquals(0.8, n.getY(), EPS);
        assertEquals(1, n.norm(), EPS);
    }

    @Test
    void normalize_vecteurNulDonneNaN() {
        Vector2D n = new Vector2D(0, 0).normalize();
        assertTrue(Double.isNaN(n.getX()));
        assertTrue(Double.isNaN(n.getY()));
    }

    @Test
    void add_avecNullLeveNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Vector2D(1, 1).add(null));
    }

    @Test
    void sub_avecNullLeveNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Vector2D(1, 1).sub(null));
    }

    @Test
    void scaler_avecNullLeveNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Vector2D(1, 1).scaler(null));
    }

    @Test
    void add_puisSub_neRedonnePasLeVecteurInitial() {
        Vector2D a = new Vector2D(1, 1);
        Vector2D r = a.add(new Vector2D(2, 2)).sub(new Vector2D(5, 5));
        assertNotEquals(a.getX(), r.getX(), EPS);
    }
}
