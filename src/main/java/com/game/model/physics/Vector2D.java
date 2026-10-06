package com.game.model.physics;
public class Vector2D{
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
        this.x += v.getX();
        this.y += v.getY();
        return this;
    }
    public Vector2D sub(Vector2D v){
        this.x -= v.getX();
        this.y -= v.getY();
        return this;
    }

    public Vector2D multiply(double k){
        this.x *= k;
        this.y *= k;
        return this;
    }

    public double norm(){
        double calcul = this.x * this.x + this.y * this.y;
        return Math.sqrt(calcul);
    }
    public Vector2D normalize(){
        double norm = this.norm();
        if(norm <= 0) return new Vector2D(0, 0);
        return new Vector2D(this.x / norm, this.y / norm);
    }

    public double distanceTo(Vector2D v){
        double calcul = (v.getX() - this.x) * (v.getX() - this.x) +  (v.getY() - this.y) * (v.getY() - this.y);
        return Math.sqrt(calcul);
    }
}