package com.game.model.input;

import com.game.model.input.ControleurHumain.Action;
import com.game.model.physics.Vector2D;
import javafx.scene.input.KeyCode;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class ControleurHumainTest {

    public ControleurHumain creerControleurHumain() {
        Map<KeyCode, Action> map = new HashMap<>();
        map.put(KeyCode.RIGHT, Action.DROITE);
        map.put(KeyCode.LEFT, Action.GAUCHE);
        map.put(KeyCode.DOWN, Action.BAS);
        map.put(KeyCode.UP, Action.HAUT);
        map.put(KeyCode.A, Action.TIR);
        map.put(KeyCode.Z, Action.PASSE);
        map.put(KeyCode.Q, Action.POUVOIR);
        return new ControleurHumain(map);
    }

    @Test
    void surToucheAppuyeeNullTest() {
        ControleurHumain c = this.creerControleurHumain();
        assertDoesNotThrow(() -> c.surToucheAppuyee(null));
    }

    @Test
    void surToucheAppuyeeNonMappeeTest() {
        ControleurHumain c = this.creerControleurHumain();
        c.surToucheAppuyee(KeyCode.SPACE);
        assertFalse(c.isTireDemande());
        assertFalse(c.isPasseDemande());
        assertFalse(c.isPouvoirDemande());
    }

    @Test
    void isTireDemandeTest() {
        ControleurHumain c = this.creerControleurHumain();
        assertFalse(c.isTireDemande());
        c.surToucheAppuyee(KeyCode.A);
        assertTrue(c.isTireDemande());
        assertFalse(c.isTireDemande());
    }

    @Test
    void isPasseDemandeTest() {
        ControleurHumain c = this.creerControleurHumain();
        assertFalse(c.isPasseDemande());
        c.surToucheAppuyee(KeyCode.Z);
        assertTrue(c.isPasseDemande());
        assertFalse(c.isPasseDemande());
    }

    @Test
    void isPouvoirDemandeTest() {
        ControleurHumain c = this.creerControleurHumain();
        assertFalse(c.isPouvoirDemande());
        c.surToucheAppuyee(KeyCode.Q);
        assertTrue(c.isPouvoirDemande());
        assertFalse(c.isPouvoirDemande());
    }

    @Test
    void getDirectionVoulueDroiteTest() {
        ControleurHumain c = this.creerControleurHumain();
        c.surToucheAppuyee(KeyCode.RIGHT);
        Vector2D dir = c.getDirectionVoulue();
        assertEquals(1.0, dir.getX(), 0.0001);
        assertEquals(0.0, dir.getY(), 0.0001);
    }

    @Test
    void getDirectionVoulueDiagonaleTest() {
        ControleurHumain c = this.creerControleurHumain();
        c.surToucheAppuyee(KeyCode.LEFT);
        c.surToucheAppuyee(KeyCode.DOWN);
        Vector2D dir = c.getDirectionVoulue();
        assertEquals(-1.0, dir.getX(), 0.0001);
        assertEquals(-1.0, dir.getY(), 0.0001);
    }

    @Test
    void getDirectionVoulueOpposeeTest() {
        ControleurHumain c = this.creerControleurHumain();
        c.surToucheAppuyee(KeyCode.LEFT);
        c.surToucheAppuyee(KeyCode.RIGHT);
        Vector2D dir = c.getDirectionVoulue();
        assertEquals(0.0, dir.getX(), 0.0001);
        assertEquals(0.0, dir.getY(), 0.0001);
    }

    @Test
    void surToucheRelacheeTest() {
        ControleurHumain c = this.creerControleurHumain();
        c.surToucheAppuyee(KeyCode.UP);
        c.surToucheRelachee(KeyCode.UP);
        Vector2D dir = c.getDirectionVoulue();
        assertEquals(0.0, dir.getX(), 0.0001);
        assertEquals(0.0, dir.getY(), 0.0001);
    }

    @Test
    void reinitialiserTest() {
        ControleurHumain c = this.creerControleurHumain();
        c.surToucheAppuyee(KeyCode.RIGHT);
        c.surToucheAppuyee(KeyCode.A);
        c.reinitialiser();
        assertFalse(c.isTireDemande());
        Vector2D dir = c.getDirectionVoulue();
        assertEquals(0.0, dir.getX(), 0.0001);
        assertEquals(0.0, dir.getY(), 0.0001);
    }
}