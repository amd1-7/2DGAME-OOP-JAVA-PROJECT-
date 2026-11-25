package com.game.models.powers;

import static org.junit.jupiter.api.Assertions.*;

import com.game.models.Joueur;
import com.game.models.Joueur.EtatJoueur;
import com.game.models.physics.Hitbox;
import com.game.models.physics.Vector2D;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.util.Duration;

class PepeTest {

    private Joueur lanceur;
    private Joueur adversaire;
    private Pepe pepe;

    @BeforeAll
    static void initJavaFx() {
        JavaFxTestSupport.init();
    }

    private static Joueur nouveauJoueur() {
        return new Joueur(new Vector2D(0, 0), new Vector2D(0, 0), new Vector2D(0, 0),
                new Hitbox(new Vector2D(0, 0), 32, 32), null, null, 15, null);
    }

    @BeforeEach
    void setUp() {
        lanceur = nouveauJoueur();
        adversaire = nouveauJoueur();
        pepe = new Pepe(lanceur, adversaire);
    }

    @Test
    void constructeur_definitNomEtType() {
        assertEquals("Pepe", pepe.getName());
        assertEquals(Pouvoir.Type.DEFENSE, pepe.getType());
    }

    @Test
    void constructeur_definitLesDurees() {
        assertEquals(Duration.seconds(16), pepe.getDuréeActivate());
        assertEquals(Duration.seconds(33), pepe.getDuréeCharge());
    }

    @Test
    void activate_quandDispo_glaceLAdversaire() {
        pepe.setDispo(true);
        pepe.activate();
        assertEquals(EtatJoueur.Glace, adversaire.getEtatJoueur());
    }

    @Test
    void activate_quandDispo_rendLePouvoirIndisponible() {
        pepe.setDispo(true);
        pepe.activate();
        assertFalse(pepe.getDispo());
    }

    @Test
    void activate_neGlacePasLeLanceur() {
        pepe.setDispo(true);
        pepe.activate();
        assertEquals(EtatJoueur.IDLE, lanceur.getEtatJoueur());
    }

    @Test
    void pouvoirNEstPasDisponibleDesLaCreation() {
        assertFalse(pepe.getDispo());
    }

    @Test
    void activate_quandPasDispo_neGlacePasLAdversaire() {
        pepe.activate();
        assertEquals(EtatJoueur.IDLE, adversaire.getEtatJoueur());
    }

    @Test
    void activate_deuxFoisDeSuite_secondAppelIgnore() {
        pepe.setDispo(true);
        pepe.activate();
        adversaire.setEtatJoueur(EtatJoueur.IDLE);
        pepe.activate();
        assertEquals(EtatJoueur.IDLE, adversaire.getEtatJoueur());
    }

    @Test
    void activate_avecAdversaireNull_leveNullPointerException() {
        Pepe sansAdversaire = new Pepe(lanceur, null);
        sansAdversaire.setDispo(true);
        assertThrows(NullPointerException.class, sansAdversaire::activate);
    }
}
