package com.game;

import java.awt.image.BufferedImage;

public class Animation {
    private final BufferedImage[] frames;
    private final double frameDuration;
    private final boolean loop;
    private double timer;
    private int currentFrameIndex;
    private boolean finished;

    public Animation(BufferedImage[] frames, double frameDuration) {
        this(frames, frameDuration, true);
    }

    public Animation(BufferedImage[] frames, double frameDuration, boolean loop) {
        this.frames = frames;
        this.frameDuration = frameDuration;
        this.loop = loop;
        this.timer = 0;
        this.currentFrameIndex = 0;
        this.finished = false;
    }

    public void update(double deltaTime) {
        if (frames == null || frames.length == 0 || finished || frameDuration <= 0) return;

        timer += deltaTime;

        while (timer >= frameDuration) {
            timer -= frameDuration;
            if (currentFrameIndex < frames.length - 1) {
                currentFrameIndex++;
            } else if (loop) {
                currentFrameIndex = 0;
            } else {
                finished = true;
                timer = 0;
                break;
            }
        }
    }

    public void reset() {
        timer = 0;
        currentFrameIndex = 0;
        finished = false;
    }

    public BufferedImage getCurrentFrame() {
        if (frames == null || frames.length == 0) return null;
        return frames[currentFrameIndex];
    }

    public boolean isFinished() { return finished; }
    public boolean isLoop() { return loop; }
    public int getFrameIndex() { return currentFrameIndex; }
    public int getFrameCount() { return frames == null ? 0 : frames.length; }
}
