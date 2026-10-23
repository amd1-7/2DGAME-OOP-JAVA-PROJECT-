package com.game.models.personnage;
import com.game.models.Joueur;
import com.game.models.Murs;
import com.game.models.input.ControleurHumain;
import com.game.models.physics.Hitbox;
import com.game.models.physics.Vector2D;
import com.game.models.powers.Pouvoir;
import com.game.view.spritesheet.Annimation;

abstract public class Personnage extends Joueur{
    protected  Pouvoir pouvoir;

    public Personnage(Vector2D position,Hitbox hitbox,Annimation annimation,ControleurHumain controleurs,double puissanceTir){
        super(position, new Vector2D(0, 0), new Vector2D(0, 0), hitbox, controleurs, null,puissanceTir , annimation);
    }

    protected void setPouvoir(Pouvoir pouvoir) {
        this.pouvoir = pouvoir;
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
}
