package com.game;

import com.game.engine.MatchManager;
import com.game.engine.init.ChoixJoueur;
import com.game.engine.init.FabriqueControleurs;
import com.game.front.Clavier;
import com.game.front.Fond;
import com.game.front.Menu;
import com.game.front.map.Carte;
import com.game.front.selection.ParcoursSelection;
import com.game.models.input.ControleurHumain;
import java.util.ArrayList;
import java.util.List;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

/** Fenêtre et enchaînement des écrans : menu des réglages, choix du pouvoir et du maillot, match. */
public class MainApp extends Application {

    private enum Ecran { MENU, SELECTION, MATCH }

    private final Clavier clavier = new Clavier();
    private final Fond fond = new Fond();
    private final Menu menu = new Menu(fond);
    private ControleurHumain[] controleurs = FabriqueControleurs.creerDuo();
    private GraphicsContext gc;
    private Ecran ecran = Ecran.MENU;
    private ParcoursSelection selection;
    private MatchManager match;

    @Override
    public void start(Stage stage) {
        Canvas canvas = new Canvas(Carte.LARGEUR_ECRAN, Carte.HAUTEUR_ECRAN);
        gc = canvas.getGraphicsContext2D();
        gc.setImageSmoothing(false);
        Scene scene = new Scene(new Group(canvas));
        brancherEvenements(scene, stage);
        demarrerBoucle();

        stage.setTitle("Foot 1 vs 1");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    // ---------------------------------------------------------------- évènements

    private void brancherEvenements(Scene scene, Stage stage) {
        scene.setOnKeyPressed(e -> toucheAppuyee(e.getCode()));
        scene.setOnKeyReleased(e -> toucheRelachee(e.getCode()));
        scene.setOnMouseMoved(e -> sourisBougee(e.getX(), e.getY()));
        scene.setOnMousePressed(e -> sourisCliquee(e.getX(), e.getY()));
        stage.focusedProperty().addListener((obs, avant, apres) -> {
            if (!apres) toutRelacher();
        });
    }

    private void sourisBougee(double x, double y) {
        if (ecran == Ecran.SELECTION && selection != null) selection.survoler(x, y);
    }

    private void sourisCliquee(double x, double y) {
        if (ecran == Ecran.SELECTION && selection != null) selection.cliquer(x, y);
    }

    private void toucheAppuyee(KeyCode touche) {
        clavier.appuyer(touche);
        if (ecran != Ecran.MATCH) return;
        if (touche == KeyCode.ESCAPE || (touche == KeyCode.ENTER && matchFini())) {
            quitterMatch();
            return;
        }
        for (ControleurHumain c : controleurs) c.toucheAppuyee(touche);
    }

    private void toucheRelachee(KeyCode touche) {
        clavier.relacher(touche);
        if (ecran != Ecran.MATCH) return;
        for (ControleurHumain c : controleurs) c.toucheRelachee(touche);
    }

    private void toutRelacher() {
        clavier.toutRelacher();
        if (ecran != Ecran.MATCH) return;
        for (KeyCode touche : KeyCode.values()) {
            for (ControleurHumain c : controleurs) c.toucheRelachee(touche);
        }
    }

    // ---------------------------------------------------------------- boucle

    private void demarrerBoucle() {
        new AnimationTimer() {
            private long dernier;

            @Override
            public void handle(long now) {
                double dt = dernier == 0 ? 0 : (now - dernier) / 1_000_000_000.0;
                dernier = now;
                if (ecran != Ecran.MATCH) fond.update(Math.min(dt, 0.05));
                if (ecran == Ecran.MENU) boucleMenu();
                else if (ecran == Ecran.SELECTION) boucleSelection();
                clavier.finDeTour();
            }
        }.start();
    }

    private void boucleMenu() {
        if (clavier.aEtePressee(KeyCode.ESCAPE)) {
            Platform.exit();
            return;
        }
        if (menu.traiter(actionsMenu())) ouvrirSelection();
        else menu.dessiner(gc);
    }

    private List<Menu.Action> actionsMenu() {
        List<Menu.Action> actions = new ArrayList<>();
        if (clavier.aEtePressee(KeyCode.UP)) actions.add(Menu.Action.HAUT);
        if (clavier.aEtePressee(KeyCode.DOWN)) actions.add(Menu.Action.BAS);
        if (clavier.aEtePressee(KeyCode.LEFT)) actions.add(Menu.Action.GAUCHE);
        if (clavier.aEtePressee(KeyCode.RIGHT)) actions.add(Menu.Action.DROITE);
        if (clavier.aEtePressee(KeyCode.ENTER) || clavier.aEtePressee(KeyCode.SPACE)) actions.add(Menu.Action.VALIDER);
        return actions;
    }

    private void boucleSelection() {
        if (clavier.aEtePressee(KeyCode.ESCAPE)) {
            retourMenu();
            return;
        }
        appliquerToucheSelection();
        selection.mettreAJour();
        if (selection.estAnnule()) retourMenu();
        else if (selection.estTermine()) lancerMatch(selection.resultat());
        else selection.dessiner(gc, fond.getTemps());
    }

    private void appliquerToucheSelection() {
        if (clavier.aEtePressee(KeyCode.LEFT)) selection.naviguer(-1);
        if (clavier.aEtePressee(KeyCode.RIGHT)) selection.naviguer(1);
        if (clavier.aEtePressee(KeyCode.ENTER)) selection.valider();
    }


    private void ouvrirSelection() {
        selection = new ParcoursSelection(fond);
        ecran = Ecran.SELECTION;
    }

    private void retourMenu() {
        selection = null;
        ecran = Ecran.MENU;
    }

    private void lancerMatch(ChoixJoueur[] choix) {
        controleurs = FabriqueControleurs.creerDuo();
        match = new MatchManager(gc, menu.parametres(), choix, controleurs, fond);
        selection = null;
        ecran = Ecran.MATCH;
        match.start();
    }

    private boolean matchFini() {
        return match.getPhase() == MatchManager.Phase.FINI && match.getChrono() > 1;
    }

    private void quitterMatch() {
        match.stop();
        match = null;
        ecran = Ecran.MENU;
        clavier.finDeTour();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
