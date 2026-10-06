package com.game;

import java.awt.image.BufferedImage;

public class Animation {
    private final BufferedImage[] frames;
    private final double frameDuration;
    private double timer;
    private int currentFrameIndex;

    public Animation(BufferedImage[] frames, double frameDuration) {
        this.frames = frames;
        this.frameDuration = frameDuration;
        this.timer = 0;
        this.currentFrameIndex = 0;
    }

    public void update(double deltaTime) {
        if (frames == null || frames.length == 0) return;

        timer += deltaTime;
        if (timer >= frameDuration) {
            timer -= frameDuration;
            currentFrameIndex = (currentFrameIndex + 1) % frames.length;
        }
    }

    public BufferedImage getCurrentFrame() {
        if (frames == null || frames.length == 0) return null;
        return frames[currentFrameIndex];
    }
}