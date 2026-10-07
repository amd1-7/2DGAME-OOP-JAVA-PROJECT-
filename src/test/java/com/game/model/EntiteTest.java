package com.game.model;

import com.game.model.physics.Hitbox;
import com.game.model.physics.Vector2D;
import javafx.scene.canvas.GraphicsContext;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EntiteTest {

    private static final double EPSILON = 0.0001;

    private static class DummyEntite extends Entite {
        public DummyEntite(Vector2D pos, Vector2D vel, Vector2D acc, Hitbox h) {
            super(pos, vel, acc, h);
        }

        @Override
        public void render(GraphicsContext gc) {
        }
    }


    @Test
    void testConstructeurEtGetters() {
        Vector2D pos = new Vector2D(0, 0);
        Vector2D vel = new Vector2D(1, 1);
        Vector2D acc = new Vector2D(2, 2);
        Hitbox h = new Hitbox(pos, 10, 10);

        DummyEntite entite = new DummyEntite(pos, vel, acc, h);

        assertEquals(0, entite.getPosition().getX(), EPSILON);
        assertEquals(1, entite.getVelocite().getX(), EPSILON);
        assertEquals(2, entite.getAcceleration().getX(), EPSILON);
        assertNotNull(entite.getHitbox());
    }

    @Test
    void testUpdateMetAJourPositionEtVelocite() {
        Vector2D pos = new Vector2D(0, 0);
        Vector2D vel = new Vector2D(10, 0);
        Vector2D acc = new Vector2D(2, 0);
        Hitbox h = new Hitbox(pos, 10, 10);

        DummyEntite entite = new DummyEntite(pos, vel, acc, h);

        entite.update(1.0);

        assertEquals(12.0, entite.getPosition().getX(), EPSILON);
        assertEquals(11.4, entite.getVelocite().getX(), EPSILON);
        assertEquals(0.0, entite.getAcceleration().getX(), EPSILON);
    }

    @Test
    void testUpdateSansHitboxNeLancePasDException() {
        Vector2D pos = new Vector2D(0, 0);
        Vector2D vel = new Vector2D(0, 0);
        Vector2D acc = new Vector2D(0, 0);

        DummyEntite entite = new DummyEntite(pos, vel, acc, null);

        assertDoesNotThrow(() -> entite.update(1.0));
    }

    @Test
    void testUpdateAvecPositionNull_devraitLancerNullPointerException() {
        Vector2D vel = new Vector2D(0, 0);
        Vector2D acc = new Vector2D(0, 0);

        DummyEntite entite = new DummyEntite(null, vel, acc, null);

        assertThrows(NullPointerException.class, () -> entite.update(1.0));
    }
}