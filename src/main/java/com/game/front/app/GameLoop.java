package com.game.front.app;

import com.game.front.bridge.MatchPresenter;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;

public class GameLoop extends AnimationTimer {
    private static final double MAX_DELTA = 0.05;
    private static final double NANOS_PER_SECOND = 1_000_000_000.0;

    private final MatchPresenter presenter;
    private final GraphicsContext gc;
    private long lastTime;

    public GameLoop(MatchPresenter presenter, GraphicsContext gc) {
        this.presenter = presenter;
        this.gc = gc;
    }

    @Override
    public void handle(long now) {
        double dt = nextDelta(now);
        presenter.update(dt);
        presenter.render(gc);
    }

    private double nextDelta(long now) {
        if (lastTime == 0) lastTime = now;
        double dt = (now - lastTime) / NANOS_PER_SECOND;
        lastTime = now;
        return Math.min(dt, MAX_DELTA);
    }
}
