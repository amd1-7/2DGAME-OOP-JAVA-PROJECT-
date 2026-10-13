package com.game.models;

import com.game.models.physics.Direction;
import com.game.models.physics.Hitbox;
import com.game.models.physics.Vector2D;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Goal {
    private final Equipe equipe;
    private final Vector2D position;
    private final Vector2D avant;
    private final Vector2D cote;
    private final double largeurOuverture;
    private final double profondeur;
    private final double epaisseurPoteau;
    private final double epaisseurFond;

    private Murs poteauA;
    private Murs poteauB;
    private Murs fond;
    private Hitbox zoneBut;

    public Goal(Equipe equipe, Vector2D position, Direction orientation,
                double largeurOuverture, double profondeur,
                double epaisseurPoteau, double epaisseurFond) {
        this.equipe = equipe;
        this.position = position;
        this.avant = orientation.getVector();
        this.cote = new Vector2D(-avant.getY(), avant.getX());
        this.largeurOuverture = largeurOuverture;
        this.profondeur = profondeur;
        this.epaisseurPoteau = epaisseurPoteau;
        this.epaisseurFond = epaisseurFond;
        this.construireGeometrie();
    }

    private Vector2D point(double a, double l) {
        return position.sub(avant.multiply(a)).add(cote.multiply(l));
    }

    private Hitbox rectangle(double a0, double a1, double l0, double l1) {
        Vector2D p0 = point(a0, l0);
        Vector2D p1 = point(a1, l1);
        Vector2D coin = new Vector2D(Math.min(p0.getX(), p1.getX()), Math.min(p0.getY(), p1.getY()));
        return new Hitbox(coin, Math.abs(p1.getX() - p0.getX()), Math.abs(p1.getY() - p0.getY()));
    }

    private Murs mur(Hitbox h) {
        return new Murs(h.getPosition(), h);
    }

    private void construireGeometrie() {
        double demi = largeurOuverture / 2;
        double exterieur = demi + epaisseurPoteau;

        this.zoneBut = rectangle(0, profondeur, -demi, demi);
        this.poteauA = mur(rectangle(0, profondeur, -exterieur, -demi));
        this.poteauB = mur(rectangle(0, profondeur, demi, exterieur));
        this.fond    = mur(rectangle(profondeur, profondeur + epaisseurFond, -exterieur, exterieur));
    }

    public Murs[] getMurs() {
        return new Murs[]{poteauA, poteauB, fond};
    }

    public boolean estBut(Ball balle) {
        return zoneBut.contient(balle.getHitbox().getCentre());
    }

    public Equipe getEquipe() {
        return equipe;
    }

    public void render(GraphicsContext gc) {
        poteauA.render(gc);
        poteauB.render(gc);
        fond.render(gc);
    }
}