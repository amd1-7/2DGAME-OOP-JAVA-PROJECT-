package com.game.models.physics;
public class Hitbox {
    private Vector2D position;
    private double width;
    private double height;

    public Hitbox(Vector2D position,double width,double height){
        this.position = position;
        this.width = width;
        this.height = height;
    }

    public double getHeight() {
        return height;
    }
    public double getWidth() {
        return width;
    }

    public Vector2D getPosition() {
        return position;
    }
    public void setPosition(Vector2D position) {
        this.position = position;
    }

    public Direction intersect(Hitbox other) {
    double x  = this.position.getX(),  y  = this.position.getY();
    double ox = other.position.getX(), oy = other.position.getY();

    double droite = x + width - ox;
    double gauche = ox + other.getWidth() - x;
    double bas    = y + height - oy;
    double haut   = oy + other.getHeight() - y;

    if (droite <= 0 || gauche <= 0 || bas <= 0 || haut <= 0) return null;

    double min = Math.min(Math.min(droite, gauche), Math.min(bas, haut));

    if (min == droite) return Direction.RIGHT;
    if (min == gauche) return Direction.LEFT;
    if (min == bas)    return Direction.DOWN;
    return Direction.UP;
}
    public Vector2D getCentre() {
        return position.add(new Vector2D(width / 2, height / 2));
    }

    public boolean contient(Vector2D point) {
        return point.getX() >= position.getX() && point.getX() <= position.getX() + width
            && point.getY() >= position.getY() && point.getY() <= position.getY() + height;
    }
}