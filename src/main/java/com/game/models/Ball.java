package com.game.models;

import com.game.models.Joueur.EtatJoueur;
import com.game.models.physics.Direction;
import com.game.models.physics.Hitbox;
import com.game.models.physics.Vector2D;

import javafx.scene.canvas.GraphicsContext;

public class Ball extends Entite {
    public enum Etat {
    TIRE,
    PASSE,
    EN_POSSESSION,
    LIBRE
    }

    private Joueur proprietaire;
    private Joueur exProprietaire;
    private Etat etat;
    private double delaiRecup;


    public Ball(Vector2D position,Hitbox hitbox){
        super(position,new Vector2D(0,0), new Vector2D(0,0),hitbox);
        this.proprietaire = null;
        this.exProprietaire = null;
        this.etat = Etat.LIBRE;
    }

    public Etat getEtat() {
        return etat;
    }
    public void setEtat(Etat etat) {
        this.etat = etat;
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.fillOval(this.position.getX(), this.position.getY(), 45, 45);
    }
    @Override
    void update(double dt, Murs[] murs){return;};

    public void update(double dt, Murs[] murs,Joueur[] joueurs) {
        if(this.proprietaire != null){
            Etat actionJouer = this.proprietaire.getControleurs().getActionJoueur();
            switch (actionJouer) {
                case EN_POSSESSION -> {
                    this.touche2Balle();
                    this.etreTacle(joueurs);
                }
                case PASSE -> {this.passeBalle();}
                case TIRE -> {this.tireBalle();}
                case LIBRE -> {;}
            }
            return;
        }
        double k = 1.5;              
        this.Collision(murs);                      
        this.vitesse   = this.vitesse.add(this.vitesse.multiply(-k * dt));
        this.position  = this.position.add(this.vitesse.multiply(dt));
        this.getHitbox().setPosition(this.position);
        this.delaiRecup -= dt;
        this.Recup(joueurs);
    }

    private void Recup(Joueur[] joueurs){
        if(this.proprietaire != null) return;

        for (Joueur i:joueurs){
            if(i == this.exProprietaire && delaiRecup > 0)continue;

            Hitbox hitboxJoueur = i.getHitbox();
            if(this.getHitbox().intersect(hitboxJoueur) != null){
                this.proprietaire = i;
                i.setIsProprietaire(true);
                break;
            }
        }
    }

     private void touche2Balle(){
        if(this.proprietaire == null) return;
        this.etat = Etat.EN_POSSESSION;
 
        Hitbox hJoueur = this.proprietaire.getHitbox();
        Hitbox hBalle  = this.getHitbox();
 
        Vector2D centreJoueur = hJoueur.getCentre();

        double distance = 0.5 * (hJoueur.getWidth() + hBalle.getWidth()) * 0.9;
 
        Vector2D orientation = this.proprietaire.getDernierOrientation();
        if (orientation == null || orientation.norm() == 0) orientation = new Vector2D(1, 0);
 
        Vector2D centreBalle = centreJoueur.add(orientation.normalize().multiply(distance));
 
        this.position = centreBalle.sub(new Vector2D(hBalle.getWidth() / 2, hBalle.getHeight() / 2));
        hBalle.setPosition(this.position);
    }

    private void passeBalle(){
        if(this.proprietaire == null) return;

        this.exProprietaire = this.proprietaire;
        Joueur lanceur = this.exProprietaire;
        this.proprietaire = null;
        this.exProprietaire.setIsProprietaire(false);

        this.vitesse = lanceur.getDernierOrientation().multiply(600);
        this.acceleration = new Vector2D(0, 0);
        this.etat = Etat.TIRE;
        this.delaiRecup = 0.4;
    }

    private void tireBalle(){
        if(this.proprietaire == null) return;

        this.exProprietaire = this.proprietaire;
        Joueur lanceur = this.exProprietaire;
        this.proprietaire = null;
        this.exProprietaire.setIsProprietaire(false);

        this.vitesse = lanceur.getDernierOrientation().multiply(lanceur.getPuissanceTir() * 120);
        this.acceleration = new Vector2D(0, 0);
        this.etat = Etat.TIRE;
        this.delaiRecup = 0.5;
    }

    private void etreTacle(Joueur[] joueurs){
        if(this.proprietaire == null) return;
        for(Joueur i:joueurs){
            Hitbox hitboxJoueur = i.getHitbox();
            if(this.getHitbox().intersect(hitboxJoueur) != null && i.getEtatJoueur() == EtatJoueur.TACLE){
                this.exProprietaire = this.proprietaire;
                this.exProprietaire.setIsProprietaire(false);
                this.proprietaire = i;
                i.setIsProprietaire(true);
                break;
            }

        }
    }

    void Collision(Murs[] murs){
        if(this.etat == Etat.EN_POSSESSION) return;
        for(Murs i:murs){
            Hitbox hitboxMur = i.getHitbox();
            Direction d = this.getHitbox().intersect(hitboxMur);
            if(d == null) continue;
            Vector2D normal = new Vector2D(0, 0).sub(d.getVector());
            Vector2D v_n = this.vitesse.scaler(normal).scaler(normal);
            Vector2D v_t = this.vitesse.sub(v_n);

            this.vitesse = v_t.sub(v_n);
        }
    }


}