package com.game.front.app;

import com.game.front.bridge.MatchPresenter;
import com.game.front.input.FrontCommands;
import com.game.front.input.InputRouter;
import com.game.front.input.KeyBindings;
import com.game.front.view.ViewConfig;
import com.game.models.input.ControleurHumain;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;

public class FrontApp extends Application {
    private static final String TITLE = "Jeu 2D";

    @Override
    public void start(Stage stage) {
        Canvas canvas = new Canvas(ViewConfig.WIDTH, ViewConfig.HEIGHT);
        ControleurHumain controller = KeyBindings.createController();
        MatchPresenter presenter = new MatchPresenter(controller);
        Scene scene = new Scene(new Group(canvas));
        attachInput(scene, controller, presenter);
        showStage(stage, scene);
        new GameLoop(presenter, canvas.getGraphicsContext2D()).start();
    }

    private void attachInput(Scene scene, ControleurHumain controller, MatchPresenter presenter) {
        new InputRouter(controller, new FrontCommands(presenter)).attach(scene);
    }

    private void showStage(Stage stage, Scene scene) {
        stage.setTitle(TITLE);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
        stage.requestFocus();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
