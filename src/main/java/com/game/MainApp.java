package com.game;

import com.game.engine.MatchManager;
import com.game.front.Clavier;
import com.game.front.Fond;
import com.game.front.Menu;
import com.game.front.map.Carte;
import com.game.models.Ball.Etat;
import com.game.models.Joueur.EtatJoueur;
import com.game.models.input.ControleurHumain;
import com.game.models.physics.Direction;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

/** Fenêtre, menu et touches : crée les deux ControleurHumain puis lance un MatchManager. */
public class MainApp extends Application {
    private final Clavier clavier = new Clavier();
    private final Fond fond = new Fond();
    private final Menu menu = new Menu(fond);
    private final ControleurHumain[] controleurs = new ControleurHumain[2];
    private final KeyCode[] touchesDepart = {KeyCode.D, KeyCode.LEFT};
    private GraphicsContext gc;
    private MatchManager match;

    @Override
    public void start(Stage stage) {
        Canvas canvas = new Canvas(Carte.LARGEUR_ECRAN, Carte.HAUTEUR_ECRAN);
        gc = canvas.getGraphicsContext2D();
        gc.setImageSmoothing(false);
        Scene scene = new Scene(new Group(canvas));
        scene.setOnKeyPressed(e -> toucheAppuyee(e.getCode()));
        scene.setOnKeyReleased(e -> toucheRelachee(e.getCode()));
        stage.focusedProperty().addListener((obs, avant, apres) -> {
            if (!apres) toutRelacher();
        });

        new AnimationTimer() {
            private long dernier;

            @Override
            public void handle(long now) {
                double dt = dernier == 0 ? 0 : (now - dernier) / 1_000_000_000.0;
                dernier = now;
                if (match == null) {
                    fond.update(Math.min(dt, 0.05));
                    boucleMenu();
                }
                clavier.finDeTour();
            }
        }.start();

        stage.setTitle("Foot 1 vs 1");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    private void boucleMenu() {
        if (clavier.aEtePressee(KeyCode.ESCAPE)) {
            Platform.exit();
            return;
        }
        List<Menu.Action> actions = new ArrayList<>();
        if (clavier.aEtePressee(KeyCode.UP)) actions.add(Menu.Action.HAUT);
        if (clavier.aEtePressee(KeyCode.DOWN)) actions.add(Menu.Action.BAS);
        if (clavier.aEtePressee(KeyCode.LEFT)) actions.add(Menu.Action.GAUCHE);
        if (clavier.aEtePressee(KeyCode.RIGHT)) actions.add(Menu.Action.DROITE);
        if (clavier.aEtePressee(KeyCode.ENTER) || clavier.aEtePressee(KeyCode.SPACE)) actions.add(Menu.Action.VALIDER);
        if (menu.traiter(actions)) lancerMatch();
        else menu.dessiner(gc);
    }

    private void lancerMatch() {
        controleurs[0] = creerControleur(KeyCode.Z, KeyCode.S, KeyCode.Q, KeyCode.D, KeyCode.E, KeyCode.R, KeyCode.SPACE);
        controleurs[1] = creerControleur(KeyCode.UP, KeyCode.DOWN, KeyCode.LEFT, KeyCode.RIGHT, KeyCode.M, KeyCode.L, KeyCode.K);
        match = new MatchManager(gc, menu.parametres(), controleurs, touchesDepart, clavier::estEnfoncee, fond);
        match.start();
    }

    private ControleurHumain creerControleur(KeyCode haut, KeyCode bas, KeyCode gauche, KeyCode droite,
                                             KeyCode tir, KeyCode passe, KeyCode tacle) {
        Map<KeyCode, Direction> deplacement = new HashMap<>();
        deplacement.put(haut, Direction.UP);
        deplacement.put(bas, Direction.DOWN);
        deplacement.put(gauche, Direction.LEFT);
        deplacement.put(droite, Direction.RIGHT);

        Map<KeyCode, Etat> actionBalle = new HashMap<>();
        actionBalle.put(tir, Etat.TIRE);
        actionBalle.put(passe, Etat.PASSE);

        Map<KeyCode, EtatJoueur> actionJoueur = new HashMap<>();
        actionJoueur.put(tir, EtatJoueur.TIRE);
        actionJoueur.put(passe, EtatJoueur.PASSE);
        actionJoueur.put(tacle, EtatJoueur.TACLE);
        return new ControleurHumain(deplacement, actionBalle, actionJoueur);
    }

    private void toucheAppuyee(KeyCode touche) {
        clavier.appuyer(touche);
        if (match == null) return;
        if (touche == KeyCode.ESCAPE || (touche == KeyCode.ENTER && matchFini())) {
            quitterMatch();
            return;
        }
        for (ControleurHumain c : controleurs) c.toucheAppuyee(touche);
    }

    private void toucheRelachee(KeyCode touche) {
        clavier.relacher(touche);
        if (match == null) return;
        for (ControleurHumain c : controleurs) c.toucheRelachee(touche);
    }

    private void toutRelacher() {
        clavier.toutRelacher();
        if (match == null) return;
        for (KeyCode touche : KeyCode.values()) {
            for (ControleurHumain c : controleurs) c.toucheRelachee(touche);
        }
    }

    private boolean matchFini() {
        return match.getPhase() == MatchManager.Phase.FINI && match.getChrono() > 1;
    }

    private void quitterMatch() {
        match.stop();
        match = null;
        clavier.finDeTour();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
