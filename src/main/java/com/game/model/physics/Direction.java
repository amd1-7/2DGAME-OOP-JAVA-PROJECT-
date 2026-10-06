package com.game.model.physics;

public enum Direction {
    HAUT(0, -1),
    BAS(0, 1),
    GAUCHE(-1, 0),
    DROITE(1, 0),
    HAUT_GAUCHE(-1, -1),
    HAUT_DROITE(1, -1),
    BAS_GAUCHE(-1, 1),
    BAS_DROITE(1, 1);

    private final Vector2D vecteur;

    Direction(double x, double y) {
        this.vecteur = new Vector2D(x, y).normalize();
    }

    public Vector2D getVecteur() {
        return vecteur;
    }
}
