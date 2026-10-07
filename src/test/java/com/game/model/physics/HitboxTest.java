package com.game.model.physics;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class HitboxTest {

    private static final double EPSILON = 0.0001;


    @Test
    void testConstructeurEtGetters() {
        Vector2D pos = new Vector2D(10, 20);
        Hitbox h = new Hitbox(pos, 50, 100);

        assertEquals(10, h.getPosition().getX(), EPSILON);
        assertEquals(20, h.getPosition().getY(), EPSILON);
        assertEquals(50, h.getWidth(), EPSILON);
        assertEquals(100, h.getHeight(), EPSILON);
    }

    @Test
    void testIntersectsAvecIntersection() {
        Hitbox h1 = new Hitbox(new Vector2D(0, 0), 10, 10);
        Hitbox h2 = new Hitbox(new Vector2D(5, 5), 10, 10);

        assertTrue(h1.intersects(h2));
        assertTrue(h2.intersects(h1));
    }

    @Test
    void testIntersectsSansIntersection() {
        Hitbox h1 = new Hitbox(new Vector2D(0, 0), 10, 10);
        Hitbox h2 = new Hitbox(new Vector2D(20, 20), 10, 10);

        assertFalse(h1.intersects(h2));
        assertFalse(h2.intersects(h1));
    }


    @Test
    void testIntersectsPointeursAvisinageEdgeCase() {
        Hitbox h1 = new Hitbox(new Vector2D(0, 0), 10, 10);
        Hitbox h2 = new Hitbox(new Vector2D(10, 0), 10, 10);

        assertFalse(h1.intersects(h2));
    }

    @Test
    void testIntersectsNull_devraitLancerNullPointerException() {
        Hitbox h = new Hitbox(new Vector2D(0, 0), 10, 10);
        assertThrows(NullPointerException.class, () -> h.intersects(null));
    }

    @Test
    void testIntersectsHitboxAvecPositionNull_devraitLancerNullPointerException() {
        Hitbox h1 = new Hitbox(null, 10, 10);
        Hitbox h2 = new Hitbox(new Vector2D(0, 0), 10, 10);

        assertThrows(NullPointerException.class, () -> h1.intersects(h2));
    }
}