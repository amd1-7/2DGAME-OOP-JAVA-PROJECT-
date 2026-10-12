package com.game.models;

import com.game.models.physics.Hitbox;
import com.game.models.physics.Vector2D;

import javafx.scene.canvas.GraphicsContext;

abstract public class Entite {
    protected Vector2D position;
    protected Vector2D vitesse;
    protected Vector2D acceleration;

    private Hitbox hitbox;

    public Entite(Vector2D position,Vector2D vitesse,Vector2D acceleration,Hitbox hitbox){
        this.position = position;
        this.vitesse = vitesse;
        this.acceleration = acceleration;
        this.hitbox = hitbox;
    }

    public Hitbox getHitbox() {
        return hitbox;
    }
    public Vector2D getPosition() {
        return position;
    }
    public Vector2D getVitesse() {
        return vitesse;
    }
    public Vector2D getAcceleration() {
        return acceleration;
    }

    abstract void update(double dt,Murs[] murs);
    abstract void render(GraphicsContext gc);
    abstract void Collision(Murs[] murs);
}
