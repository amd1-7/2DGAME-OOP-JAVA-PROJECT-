package com.game.models.powers;

import static org.junit.jupiter.api.Assertions.*;

import com.game.models.Joueur;
import com.game.models.physics.Hitbox;
import com.game.models.physics.Vector2D;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.util.Duration;

class HulkTest {

    private static final double PUISSANCE_INITIALE = 10;
    private static final double PUISSANCE_HULK = 15;
    private static final double EPS = 1e-9;

    private Joueur joueur;
    private Hulk hulk;

    @BeforeAll
    static void initJavaFx() {
        JavaFxTestSupport.init();
    }

    @BeforeEach
    void setUp() {
        joueur = new Joueur(new Vector2D(0, 0), new Vector2D(0, 0), new Vector2D(0, 0),
                new Hitbox(new Vector2D(0, 0), 32, 32), null, null, PUISSANCE_INITIALE, null);
        hulk = new Hulk(joueur);
    }

    @Test
    void constructeur_definitNomEtType() {
        assertEquals("Hulk", hulk.getName());
        assertEquals(Pouvoir.Type.ATTAQUE, hulk.getType());
    }

    @Test
    void constructeur_definitLesDurees() {
        assertEquals(Duration.seconds(20), hulk.getDuréeActivate());
        assertEquals(Duration.minutes(33), hulk.getDuréeCharge());
    }

    @Test
    void activate_quandDispo_passeLaPuissanceDeTirA75() {
        hulk.setDispo(true);
        hulk.activate();
        assertEquals(PUISSANCE_HULK, joueur.getPuissanceTir(), EPS);
    }

    @Test
    void activate_quandDispo_rendLePouvoirIndisponible() {
        hulk.setDispo(true);
        hulk.activate();
        assertFalse(hulk.getDispo());
    }

    @Test
    void pouvoirNEstPasDisponibleDesLaCreation() {
        assertFalse(hulk.getDispo());
    }

    @Test
    void activate_quandPasDispo_neChangePasLaPuissance() {
        hulk.activate();
        assertEquals(PUISSANCE_INITIALE, joueur.getPuissanceTir(), EPS);
    }

    @Test
    void activate_deuxFoisDeSuite_secondAppelIgnore() {
        hulk.setDispo(true);
        hulk.activate();
        joueur.setPuissanceTir(42);
        hulk.activate();
        assertEquals(42, joueur.getPuissanceTir(), EPS);
    }

    @Test
    void activate_avecJoueurNull_leveNullPointerException() {
        Hulk sansJoueur = new Hulk(null);
        sansJoueur.setDispo(true);
        assertThrows(NullPointerException.class, sansJoueur::activate);
    }
}
