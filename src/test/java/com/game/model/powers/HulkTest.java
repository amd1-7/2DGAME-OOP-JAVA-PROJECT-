package com.game.model.powers;

import com.game.model.powers.Hulk;
import com.game.model.Joueur;
import com.game.model.Equipe;
import com.game.model.physics.Direction;
import com.game.model.physics.Hitbox;
import com.game.model.physics.Vector2D;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class HulkTest {

    private static final double EPSILON = 0.0001;

    private Joueur creerJoueur() {
        Vector2D pos = new Vector2D(0, 0);
        Hitbox h = new Hitbox(pos, 10, 10);
        return new Joueur(pos, new Vector2D(0, 0), new Vector2D(0, 0), h, Equipe.Gauche, 5.0, 10.0, Direction.DROITE);
    }

    // --- TESTS NOMINAUX ---

    @Test
    void testConstructeurEtGetters() {
        Hulk hulk = new Hulk();

        assertEquals("Hulk", hulk.getName());
        assertEquals(Pouvoir.Type.ATTAQUE, hulk.getType());
        assertEquals(30.0, hulk.getDuréeActivate().toSeconds(), EPSILON);
        assertEquals(60.0, hulk.getDuréeCharge().toSeconds(), EPSILON);
    }

    @Test
    void testSetEtGetDispo() {
        Hulk hulk = new Hulk();

        hulk.setDispo(true);
        assertTrue(hulk.getDispo());

        hulk.setDispo(false);
        assertFalse(hulk.getDispo());
    }

    // --- TESTS D'ERREUR ET CAS LIMITES ---

    @Test
    void testActivateQuandNonDisponibleNeModifiePasLeJoueur() {
        Hulk hulk = new Hulk();
        hulk.setDispo(false);

        Joueur j = creerJoueur();
        double puissanceInitiale = j.getPuissanceTir();

        hulk.activate(j);

        // La puissance du joueur ne doit pas changer si le pouvoir n'est pas dispo
        assertEquals(puissanceInitiale, j.getPuissanceTir(), EPSILON);
    }

    @Test
    void testActivateNull_devraitLancerNullPointerException() {
        Hulk hulk = new Hulk();
        hulk.setDispo(true);

        assertThrows(NullPointerException.class, () -> hulk.activate(null));
    }
}