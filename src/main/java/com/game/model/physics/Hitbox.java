package com.game.model.physics;

public class Hitbox{
    private double width;
    private double height;
    private Vector2D position;

    public Hitbox(Vector2D position,double width,double height){
        this.position = position;
        this.width = width;
        this.height = height;
    }

    public Vector2D getPosition() {
        return position;
    }
    public void setPosition(Vector2D position) {
        this.position = position;
    }

    public double getWidth() {
        return width;
    }
    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }
    public void setHeight(double height) {
        this.height = height;
    }

    public  boolean intersects(Hitbox other){
        boolean b1 = (this.position.getX() < other.getPosition().getX() + other.getWidth());
        boolean b2 = (this.position.getX() + this.width > other.getPosition().getX());
        boolean b3 = (this.position.getY() < other.getPosition().getY() + height);
        boolean b4 = (this.position.getY() + this.height > other.getPosition().getY());

        return (b1 && b2 && b3 && b4);
    }
}