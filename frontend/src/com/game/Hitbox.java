package com.game;

public class Hitbox {
    private final float x, y, width, height;

    public Hitbox(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public float getX() { return x; }
    public float getY() { return y; }
    public float getWidth() { return width; }
    public float getHeight() { return height; }
    public float getRight() { return x + width; }
    public float getBottom() { return y + height; }

    public boolean intersects(Hitbox o) {
        return x < o.getRight() && getRight() > o.x && y < o.getBottom() && getBottom() > o.y;
    }

    public boolean contains(float px, float py) {
        return px >= x && px < getRight() && py >= y && py < getBottom();
    }

    public boolean containsFully(Hitbox o) {
        return o.x >= x && o.getRight() <= getRight() && o.y >= y && o.getBottom() <= getBottom();
    }
}
