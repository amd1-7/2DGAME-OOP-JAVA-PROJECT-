package com.game.model;

import com.game.model.physics.Hitbox;
import com.game.model.physics.Vector2D;

public class Mur {
    private Vector2D position;
    private Hitbox hitbox;

    public Mur(Vector2D position,Hitbox hitbox){
        this.position = position;

        this.hitbox = hitbox;
    }

    public Hitbox getHitbox() {
        return hitbox;
    }

    public Vector2D getTangente(){
        double calculX = this.position.getX() /this.position.norm();
        double calculY = this.position.getY() /this.position.norm();

        return new Vector2D(calculX, calculY);
    }

    public Vector2D getNormaleUnitaire(){
        return new Vector2D(this.getTangente().getX(), - this.getTangente().getY());
    }
}
