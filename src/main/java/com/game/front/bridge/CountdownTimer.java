package com.game.front.bridge;

public class CountdownTimer {
    private double remaining;

    public void start(double seconds) {
        remaining = seconds;
    }

    public void cancel() {
        remaining = 0;
    }

    public boolean tick(double dt) {
        if (remaining <= 0) return false;
        remaining -= dt;
        return remaining <= 0;
    }

    public boolean isRunning() {
        return remaining > 0;
    }
}
