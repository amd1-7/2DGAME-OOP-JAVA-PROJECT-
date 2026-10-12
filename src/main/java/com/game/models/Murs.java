package com.game.models;

import com.game.models.physics.Hitbox;
import com.game.models.physics.Vector2D;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Murs extends Entite {
     public Murs(Vector2D position,Hitbox hitbox){
        super(position, new Vector2D(0,0), new Vector2D(0,0), hitbox);
     }
     @Override
     void update(double dt,Murs[] murs) {
         return;
     }

     @Override
     public void render(GraphicsContext gc) {
         gc.setFill(Color.BLUE);
         gc.fillRect(this.position.getX(), this.position.getY(), this.getHitbox().getWidth(), this.getHitbox().getHeight());
     }
     @Override
     void Collision(Murs[] murs) {return ;}
}
