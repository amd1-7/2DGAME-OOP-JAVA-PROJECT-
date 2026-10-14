package com.game.front.view;

public class Camera {
    private final int viewportWidth;
    private final int viewportHeight;
    private final int mapWidth;
    private final int mapHeight;
    private double x;
    private double y;

    public Camera(int viewportWidth, int viewportHeight, int mapWidth, int mapHeight) {
        this.viewportWidth = viewportWidth;
        this.viewportHeight = viewportHeight;
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
    }

    public void follow(double targetX, double targetY) {
        x = clamp(targetX - viewportWidth / 2.0, mapWidth - viewportWidth);
        y = clamp(targetY - viewportHeight / 2.0, mapHeight - viewportHeight);
    }

    private static double clamp(double value, double max) {
        return Math.max(0, Math.min(value, max));
    }

    public double getX() { return x; }
    public double getY() { return y; }
}
