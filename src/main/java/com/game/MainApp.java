package com.game;

import com.game.models.Joueur;
import com.game.models.Ball.Etat;
import com.game.models.Joueur.EtatJoueur;
import com.game.engine.MatchManager;
import com.game.models.input.ControleurHumain;
import com.game.models.physics.Hitbox;
import com.game.models.physics.Vector2D;
import com.game.view.spritesheet.Annimation;
import com.game.view.spritesheet.DefAnimation.CouleuraAnnimation;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.stage.Stage;


import java.util.Map;

import com.game.models.physics.Direction;
import javafx.scene.input.KeyCode;

import java.util.HashMap;


public class MainApp extends Application{
    @Override
    public void start(Stage stage) throws Exception {
        Canvas canvas = new Canvas(800,800);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        Group root = new Group(canvas);
        Scene scene = new Scene(root,800,800);

        Vector2D position = new Vector2D(50, 100);
        Hitbox hitbox = new Hitbox(position, 20, 20);

        Map<KeyCode,Direction> controle = new HashMap<>();
        controle.put(KeyCode.RIGHT, Direction.RIGHT);
        controle.put(KeyCode.LEFT, Direction.LEFT);
        controle.put(KeyCode.UP, Direction.UP);
        controle.put(KeyCode.DOWN, Direction.DOWN);

         Map<KeyCode,Etat> controleSpecial = new HashMap<>();
        controleSpecial.put(KeyCode.F, Etat.TIRE);
        controleSpecial.put(KeyCode.D, Etat.PASSE);

         Map<KeyCode,EtatJoueur> controleAction = new HashMap<>();
         controleAction.put(KeyCode.D, EtatJoueur.PASSE);
         controleAction.put(KeyCode.F, EtatJoueur.TIRE);
         controleAction.put(KeyCode.K, EtatJoueur.TACLE);

        ControleurHumain controleur = new ControleurHumain(controle,controleSpecial,controleAction);

        Image image = new Image(getClass().getResourceAsStream("/assets/players/joueurs_anim.png"));
        Annimation anim = new Annimation(CouleuraAnnimation.ROUGE, image);
        Joueur joueur = new Joueur(position, position.multiply(0), position.multiply(0),hitbox,controleur,image,10,anim);

        MatchManager mm = new MatchManager(gc, joueur, scene);

        stage.setTitle("JEUX");
        stage.setScene(scene);
        stage.show();
        stage.requestFocus();

        mm.start();
    }
    public static void main(String[] args) {
        launch(args);
    }
}