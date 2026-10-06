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
    public void update(double dt){
        Vector2D accelerationCpy = new Vector2D(this.acceleration.getX(), this.acceleration.getY());
        accelerationCpy.multiply(dt);
        this.velocite.add(accelerationCpy);


        Vector2D velociteCpy = new Vector2D(this.velocite.getX(), this.velocite.getY());
        velociteCpy.multiply(dt);
        position.add(velociteCpy);

        this.velocite.multiply(0.95);
        this.acceleration.multiply(0);
        if(this.hitbox != null) this.hitbox.setPosition(this.position);
    }

    abstract public void render(GraphicsContext gc);

}