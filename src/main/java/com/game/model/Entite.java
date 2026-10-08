package com.game.model;
import com.game.model.physics.Vector2D;
import com.game.model.physics.Hitbox;
import javafx.scene.canvas.GraphicsContext;

public abstract class Entite {
    protected Vector2D position;
    protected Vector2D velocite;
    protected Vector2D acceleration;
    protected Hitbox hitbox;

    public Entite(Vector2D position,Vector2D velocite,Vector2D acceleration,Hitbox hitbox){
        this.position = position;
        this.velocite = velocite;
        this.acceleration = acceleration;
        this.hitbox = hitbox;
    }

    public Vector2D getPosition() {
        return position;
    }
    public void setPosition(Vector2D position) {
        this.position = position;
    }

    public Vector2D getVelocite() {
        return velocite;
    }
    public void setVelocite(Vector2D velocite) {
        this.velocite = velocite;
    }

    public Vector2D getAcceleration() {
        return acceleration;
    }
    public void setAcceleration(Vector2D acceleration) {
        this.acceleration = acceleration;
    }

    public Hitbox getHitbox() {
        return hitbox;
    }
    public void setHitbox(Hitbox hitbox) {
        this.hitbox = hitbox;
    }

    abstract public void update(double dt);
    abstract public void render(GraphicsContext gc);
}