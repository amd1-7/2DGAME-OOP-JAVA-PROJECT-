package com.game.models.personnage;
import javafx.util.Duration;

import com.game.models.Joueur;
import com.game.models.Murs;
import com.game.models.input.ControleurHumain;
import com.game.models.physics.Hitbox;
import com.game.models.physics.Vector2D;
import com.game.models.powers.Hulk;
import com.game.models.powers.Pouvoir;
import com.game.view.spritesheet.Annimation;

import javafx.scene.image.Image;

abstract public class Personnage extends Joueur{
    protected  Pouvoir pouvoir;
    private int indexPouvoir;

    public Personnage(Vector2D position,Hitbox hitbox,Annimation annimation,ControleurHumain controleurs,double puissanceTir){
        super(position, new Vector2D(0, 0), new Vector2D(0, 0), hitbox, controleurs, null,puissanceTir , annimation);
    }

    protected void setPouvoir(Pouvoir pouvoir) {
        this.pouvoir = pouvoir;
    }

    public Duration getCharge(){
        return this.pouvoir.getDuréeCharge();
    }

    public abstract void ActivatePower();

    @Override
    public void update(double dt,Murs[] murs){
        this.EtreGlace();
        double f = 1200;
        double k = 5;
        Vector2D force = this.getControleurs().getDirection().multiply(f);
        if (this.getControleurs().getDirection().norm() > 0) this.dernierOrientation = this.getControleurs().getDirection().normalize();
        
        this.acceleration = force.sub(this.vitesse.multiply(k));
        this.vitesse = this.vitesse.add(this.acceleration.multiply(dt));
        this.position = this.position.add(this.vitesse.multiply(dt));

        switch (this.getControleurs().getActionJoueurSansballon()) {
            case IDLE ->{
                if(this.vitesse.norm() < 27.5) this.etatJoueur = EtatJoueur.IDLE;
                else this.etatJoueur = EtatJoueur.COURS;
            }

            case PASSE ->{
                this.passer();
            }
            case TACLE ->{
                this.tacler();
            }
            case TIRE ->{
                this.tirer();
            }
            case Pouvoir ->{
                this.ActivatePower();
            }
            default -> {;}
        }

        this.Collision(murs);
        this.getHitbox().setPosition(this.position);
    }

     private void EtreGlace(){
        if(this.etatJoueur != EtatJoueur.Glace && this.controleurs.getActif()) return;
        if(this.etatJoueur != EtatJoueur.Glace && !this.controleurs.getActif()){
            this.controleurs.setActif(true);
            return;
        }
        if(this.etatJoueur == EtatJoueur.Glace && this.controleurs.getActif())this.controleurs.setActif(false);;
    }

    @Override
    protected Image chooseImage(double dt){
        Image img = this.annimation.getIdle();
        switch (this.etatJoueur) {
            case IDLE ->{
                this.indexTire = 0;
                this.indexCourse = 0;
                this.indexPasse = 0;
                this.indexTacle = 0;
                this.indexGlace = 0;
                this.indexPouvoir = 0;
                this.indexCelebration = 0;

                img = this.annimation.getIdle();
            }

            case COURS ->{
                this.indexTire = 0;
                this.indexPasse = 0;
                this.indexTacle = 0;
                this.indexGlace = 0;
                this.indexCelebration = 0;
                this.indexPouvoir = 0;
                this.timerAnimation += dt;
                
                if(0.1 <= this.timerAnimation){
                    if (this.indexCourse +1 < this.annimation.getListImageCourse().size())this.indexCourse++;
                    else this.indexCourse = 0;
                    this.timerAnimation -= 0.1;
                }
                
                img = this.annimation.getListImageCourse().get(this.indexCourse);
            }
            
            case PASSE ->{
                this.indexTire = 0;
                this.indexCourse = 0;
                this.indexTacle = 0;
                this.indexGlace = 0;
                this.indexCelebration = 0;
                this.indexPouvoir = 0;
                this.timerAnimation += dt;

                if(0.1 <= this.timerAnimation){
                    if (this.indexPasse +1 < this.annimation.getListImagePasse().size())this.indexPasse++;
                    else this.indexPasse = 0;
                    this.timerAnimation -= 0.1;
                }
                
                img = this.annimation.getListImagePasse().get(this.indexPasse);
            }

            case TIRE ->{
                this.indexCourse = 0;
                this.indexPasse = 0;
                this.indexTacle = 0;
                this.indexGlace = 0;
                this.indexCelebration = 0;
                this.indexPouvoir = 0;
                this.timerAnimation += dt;

                if(0.1 <= this.timerAnimation){
                    if (this.indexTire +1 < this.annimation.getListImageTire().size())this.indexTire++;
                    else this.indexTire = 0;
                    this.timerAnimation -= 0.1;
                    }
                img = this.annimation.getListImageTire().get(this.indexTire);
            }
            case TACLE ->{
                this.indexCourse = 0;
                this.indexPasse = 0;
                this.indexTire = 0;
                this.indexGlace = 0;
                this.indexCelebration = 0;
                this.indexPouvoir = 0;
                this.timerAnimation += dt;

                if(0.1 <= this.timerAnimation){
                    if (this.indexTacle +1 < this.annimation.getListImageTacle().size())this.indexTacle++;
                    else this.indexTacle = 0;
                    this.timerAnimation -= 0.1;
                    }
                img = this.annimation.getListImageTacle().get(this.indexTacle);
            }
            case Glace->{
                this.indexCourse = 0;
                this.indexPasse = 0;
                this.indexTire = 0;
                this.indexTacle = 0;
                this.indexCelebration = 0;
                this.indexPouvoir = 0;
                this.timerAnimation += dt;

                if(0.625 <= this.timerAnimation){
                    if(this.indexGlace +1 < this.annimation.getListeImageGlacon().size())this.indexGlace++;
                    else this.indexGlace = 0;
                    this.timerAnimation -= 0.625;
                }
                img = this.annimation.getListeImageGlacon().get(indexGlace);
            }
            case Celebration ->{
                this.indexCourse = 0;
                this.indexPasse = 0;
                this.indexTire = 0;
                this.indexTacle = 0;
                this.indexGlace = 0;
                this.indexPouvoir = 0;
                this.timerAnimation += dt;

                if(0.1 <= this.timerAnimation){
                    if(this.indexCelebration +1 < this.annimation.getListImageCelebration().size())this.indexCelebration++;
                    else this.indexCelebration = 0;
                    this.timerAnimation -= 0.1;
                }
                img = this.annimation.getListImageCelebration().get(indexCelebration);
            }
            case Pouvoir ->{
                this.indexTire = 0;
                this.indexCourse = 0;
                this.indexPasse = 0;
                this.indexTacle = 0;
                this.indexGlace = 0;
                this.indexCelebration = 0;

                this.timerAnimation += dt;

                switch (this.pouvoir) {
                    case Hulk h->{
                       this.indexCourse = 0;
                        this.indexPasse = 0;
                        this.indexTire = 0;
                        this.indexTacle = 0;
                        this.indexGlace = 0;
                        this.indexCelebration = 0;
                        this.timerAnimation += dt;

                        if(0.1 <= this.timerAnimation){
                            if(this.indexPouvoir +1 < this.annimation.getListImageSuperTire().size())this.indexPouvoir++;
                            else this.indexPouvoir = 0;
                            this.timerAnimation -= 0.1;
                        }
                        img = this.annimation.getListImageSuperTire().get(indexPouvoir);
                    }
                    default ->{;}
                }

            }
            default ->{;}
        }
        return img;
    }
}
