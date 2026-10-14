package com.game.models.physics;

public enum Direction {
    RIGHT(1,0),
    LEFT(-1,0),
    UP(0,-1),
    DOWN(0,1);

    private Vector2D v;

    Direction(double x,double y){
        this.v = new Vector2D(x, y);
    }
    public Vector2D getVector() {
        return v;
    }
}