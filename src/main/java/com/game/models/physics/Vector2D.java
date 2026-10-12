package com.game.models.physics;

public class Vector2D {
    private double x;
    private double y;

    public Vector2D(double x,double y){
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }

    public Vector2D add(Vector2D v){
        return new Vector2D(this.x + v.getX(), this.y + v.getY());
    }
    public Vector2D sub(Vector2D v){
        return new Vector2D(this.x - v.getX(), this.y - v.getY());
    }

    public Vector2D multiply(double k){
        return new Vector2D(this.x *k, this.y *k);
    }
    public Vector2D scaler(Vector2D v){
        return new Vector2D(this.x * v.getX(), this.y *v.getY());
    }

    public double norm(){
        return Math.sqrt(this.x * this.x + this.y * this.y);
    }
    public Vector2D normalize(){
        double norm = this.norm();
        return new Vector2D(this.x / norm,this.y / norm);
    }
}