package com.game.engine.init;

import com.game.models.Ball.Etat;
import com.game.models.Joueur.EtatJoueur;
import com.game.models.input.ControleurHumain;
import com.game.models.physics.Direction;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.input.KeyCode;

public final class FabriqueControleurs {

    private FabriqueControleurs() { }

    public static ControleurHumain[] creerDuo() {
        Touches[] touches = Touches.duo();
        ControleurHumain[] controleurs = new ControleurHumain[touches.length];
        for (int i = 0; i < touches.length; i++) controleurs[i] = creer(touches[i]);
        return controleurs;
    }

    public static ControleurHumain creer(Touches t) {
        return new ControleurHumain(deplacements(t), actionsBalle(t), actionsJoueur(t));
    }

    private static Map<KeyCode, Direction> deplacements(Touches t) {
        Map<KeyCode, Direction> map = new HashMap<>();
        map.put(t.haut(), Direction.UP);
        map.put(t.bas(), Direction.DOWN);
        map.put(t.gauche(), Direction.LEFT);
        map.put(t.droite(), Direction.RIGHT);
        return map;
    }

    private static Map<KeyCode, Etat> actionsBalle(Touches t) {
        Map<KeyCode, Etat> map = new HashMap<>();
        map.put(t.tir(), Etat.TIRE);
        map.put(t.passe(), Etat.PASSE);
        return map;
    }

    private static Map<KeyCode, EtatJoueur> actionsJoueur(Touches t) {
        Map<KeyCode, EtatJoueur> map = new HashMap<>();
        map.put(t.tir(), EtatJoueur.TIRE);
        map.put(t.passe(), EtatJoueur.PASSE);
        map.put(t.tacle(), EtatJoueur.TACLE);
        map.put(t.pouvoir(), EtatJoueur.Pouvoir);
        return map;
    }
}
