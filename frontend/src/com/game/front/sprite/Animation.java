package com.game.front.sprite;

import javafx.geometry.Rectangle2D;

public class Animation {
    private final Rectangle2D[] frames;
    private final double frameDuration;
    private final boolean loop;
    private double timer;
    private int currentFrameIndex;
    private boolean finished;

    public Animation(Rectangle2D[] frames, double frameDuration, boolean loop) {
        this.frames = frames;
        this.frameDuration = frameDuration;
        this.loop = loop;
    }

    public void update(double deltaTime) {
        if (!canAdvance()) return;
        timer += deltaTime;
        while (timer >= frameDuration && !finished) advance();
    }

    private boolean canAdvance() {
        return frames.length > 0 && !finished && frameDuration > 0;
    }

    private void advance() {
        timer -= frameDuration;
        if (currentFrameIndex < frames.length - 1) currentFrameIndex++;
        else if (loop) currentFrameIndex = 0;
        else finish();
    }

    private void finish() {
        finished = true;
        timer = 0;
    }

    public void reset() {
        timer = 0;
        currentFrameIndex = 0;
        finished = false;
    }

    public Rectangle2D getCurrentFrame() {
        return frames[currentFrameIndex];
    }

    public boolean isFinished() {
        return finished;
    }
}
