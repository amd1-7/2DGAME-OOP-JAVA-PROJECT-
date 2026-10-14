package com.game.front.input;

import java.util.HashSet;
import java.util.Set;

import com.game.models.input.ControleurHumain;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class InputRouter {
    private final ControleurHumain controller;
    private final FrontCommands commands;
    private final Set<KeyCode> held = new HashSet<>();

    public InputRouter(ControleurHumain controller, FrontCommands commands) {
        this.controller = controller;
        this.commands = commands;
    }

    public void attach(Scene scene) {
        scene.setOnKeyPressed(this::onPressed);
        scene.setOnKeyReleased(this::onReleased);
    }

    private void onPressed(KeyEvent event) {
        controller.toucheAppuyee(event.getCode());
        if (held.add(event.getCode())) commands.onKeyPressed(event);
    }

    private void onReleased(KeyEvent event) {
        controller.toucheRelachee(event.getCode());
        held.remove(event.getCode());
    }
}
