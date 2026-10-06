package com.game;

public class Camera {
    private float x, y;
    private int viewportWidth, viewportHeight;
    private int mapWidth, mapHeight;

    public Camera(int viewportWidth, int viewportHeight, int mapWidth, int mapHeight) {
        this.viewportWidth = viewportWidth;
        this.viewportHeight = viewportHeight;
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
    }

    public void update(float targetX, float targetY) {
        this.x = targetX - (viewportWidth / 2.0f);
        this.y = targetY - (viewportHeight / 2.0f);

        if (x < 0) x = 0;
        if (y < 0) y = 0;
        if (x > mapWidth - viewportWidth) x = mapWidth - viewportWidth;
        if (y > mapHeight - viewportHeight) y = mapHeight - viewportHeight;
    }

    public float getX() { return x; }
    public float getY() { return y; }
}