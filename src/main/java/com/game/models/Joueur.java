package com.game.models;

import com.game.models.input.ControleurHumain;
import com.game.models.physics.Direction;
import com.game.models.physics.Hitbox;
import com.game.models.physics.Vector2D;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Joueur extends Entite {
    private ControleurHumain controleurs;
    private Image image;
    private double puissanceTir;
    private Vector2D dernierOrientation;

   public Joueur(Vector2D position,Vector2D vitesse,Vector2D acceleration,Hitbox hitbox,ControleurHumain controleurs,Image image,double puissanceTir){
        super(position, vitesse, acceleration, hitbox);

        this.controleurs = controleurs;
        this.image = image;
        this.puissanceTir = puissanceTir;
    }

    public Vector2D getDernierOrientation() {
        return dernierOrientation;
    }

    public double getPuissanceTir() {
        return puissanceTir;
    }
    public void setPuissanceTir(double puissanceTir) {
        this.puissanceTir = puissanceTir;
    }

    public ControleurHumain getControleurs() {
        return controleurs;
    }

    @Override
    public void update(double dt,Murs[] murs){
        double f = 1250;
        double k = 2;
        Vector2D force = this.getControleurs().getDirection().multiply(f);
        if (this.getControleurs().getDirection().norm() > 0) this.dernierOrientation = this.getControleurs().getDirection().normalize();
        
        this.acceleration = force.sub(this.vitesse.multiply(k));
        this.vitesse = this.vitesse.add(this.acceleration.multiply(dt));
        this.position = this.position.add(this.vitesse.multiply(dt));

        this.Collision(murs);

        this.getHitbox().setPosition(this.position);
    }

    void Collision(Murs[] murs){
        for (Murs mur : murs) {
        Hitbox m = mur.getHitbox();
        Direction d = this.getHitbox().intersect(m);
        if (d == null) continue;

        double mx = m.getPosition().getX();
        double my = m.getPosition().getY();

        this.getHitbox().setPosition(this.position);
        switch (d) {
            case RIGHT -> {   
                position = new Vector2D(mx - getHitbox().getWidth(), position.getY());
                vitesse  = new Vector2D(Math.min(0, vitesse.getX()), vitesse.getY());
            }
            case LEFT -> {    
                position = new Vector2D(mx + m.getWidth(), position.getY());
                vitesse  = new Vector2D(Math.max(0, vitesse.getX()), vitesse.getY());
            }
            case DOWN -> {    
                position = new Vector2D(position.getX(), my - getHitbox().getHeight());
                vitesse  = new Vector2D(vitesse.getX(), Math.min(0, vitesse.getY()));
            }
            case UP -> {     
                position = new Vector2D(position.getX(), my + m.getHeight());
                vitesse  = new Vector2D(vitesse.getX(), Math.max(0, vitesse.getY()));
            }
        }
    }
    }

    public void render(GraphicsContext gc){
        gc.drawImage(this.image, this.position.getX(), this.position.getY());
    }
}
