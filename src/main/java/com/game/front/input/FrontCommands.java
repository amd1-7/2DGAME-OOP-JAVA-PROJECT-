package com.game.front.input;

import com.game.front.bridge.MatchPresenter;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class FrontCommands {
    private final MatchPresenter presenter;

    public FrontCommands(MatchPresenter presenter) {
        this.presenter = presenter;
    }

    public void onKeyPressed(KeyEvent event) {
        runCommand(event.getCode());
        runKitCommand(event);
    }

    private void runCommand(KeyCode code) {
        switch (code) {
            case H -> presenter.toggleDebug();
            case B -> presenter.toggleCelebration();
            case C -> presenter.playTackle();
            case R -> presenter.reset();
            default -> { }
        }
    }

    private void runKitCommand(KeyEvent event) {
        int kit = KitKeys.kitOf(event);
        if (kit >= 0) presenter.setKit(kit);
    }
}
