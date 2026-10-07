package com.game.model.physics;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Vector2DTest {

    private static final double EPSILON = 0.0001;

    @Test
    void testConstructeurEtGetters() {
        Vector2D v = new Vector2D(4.5, -2.0);
        assertEquals(4.5, v.getX(), EPSILON);
        assertEquals(-2.0, v.getY(), EPSILON);
    }

    @Test
    void testAdd() {
        Vector2D v = new Vector2D(10, 10);
        Vector2D w = new Vector2D(20, 20);

        v.add(w);

        assertEquals(30, v.getX(), EPSILON);
        assertEquals(30, v.getY(), EPSILON);
    }

    @Test
    void testAddNull_devraitLancerNullPointerException() {
        Vector2D v = new Vector2D(10, 10);
        assertThrows(NullPointerException.class, () -> v.add(null));
    }

    @Test
    void testSub() {
        Vector2D v = new Vector2D(30, 20);
        Vector2D w = new Vector2D(10, 5);

        v.sub(w);

        assertEquals(20, v.getX(), EPSILON);
        assertEquals(15, v.getY(), EPSILON);
    }

    @Test
    void testSubNull_devraitLancerNullPointerException() {
        Vector2D v = new Vector2D(10, 10);
        assertThrows(NullPointerException.class, () -> v.sub(null));
    }

    @Test
    void testMultiply() {
        Vector2D v = new Vector2D(3, -4);

        v.multiply(2.5);

        assertEquals(7.5, v.getX(), EPSILON);
        assertEquals(-10.0, v.getY(), EPSILON);
    }

    @Test
    void testMultiplyParZero() {
        Vector2D v = new Vector2D(5, 5);

        v.multiply(0);

        assertEquals(0, v.getX(), EPSILON);
        assertEquals(0, v.getY(), EPSILON);
    }

    @Test
    void testNorm() {
        Vector2D v = new Vector2D(3, 4);
        assertEquals(5.0, v.norm(), EPSILON);
    }

    @Test
    void testNormVecteurNul() {
        Vector2D v = new Vector2D(0, 0);
        assertEquals(0.0, v.norm(), EPSILON);
    }

    @Test
    void testNormalize() {
        Vector2D v = new Vector2D(3, 4);
        Vector2D normalized = v.normalize();

        assertEquals(0.6, normalized.getX(), EPSILON);
        assertEquals(0.8, normalized.getY(), EPSILON);
        assertEquals(1.0, normalized.norm(), EPSILON);
    }

    @Test
    void testNormalizeVecteurNul_devraitRenvoyerVecteurNul() {
        Vector2D v = new Vector2D(0, 0);
        Vector2D normalized = v.normalize();

        assertEquals(0, normalized.getX(), EPSILON);
        assertEquals(0, normalized.getY(), EPSILON);
    }

    @Test
    void testDistanceTo() {
        Vector2D v1 = new Vector2D(1, 1);
        Vector2D v2 = new Vector2D(4, 5);

        assertEquals(5.0, v1.distanceTo(v2), EPSILON);
    }

    @Test
    void testDistanceToMemePoint() {
        Vector2D v1 = new Vector2D(2, 3);
        Vector2D v2 = new Vector2D(2, 3);

        assertEquals(0.0, v1.distanceTo(v2), EPSILON);
    }

    @Test
    void testDistanceToNull_devraitLancerNullPointerException() {
        Vector2D v = new Vector2D(1, 1);
        assertThrows(NullPointerException.class, () -> v.distanceTo(null));
    }
}