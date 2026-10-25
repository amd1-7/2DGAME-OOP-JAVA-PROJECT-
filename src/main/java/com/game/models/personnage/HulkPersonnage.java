package com.game.models.personnage;

import com.game.models.input.ControleurHumain;
import com.game.models.physics.Hitbox;
import com.game.models.physics.Vector2D;
import com.game.models.powers.Hulk;
import com.game.view.spritesheet.Annimation;

import javafx.animation.PauseTransition;
import javafx.util.Duration;

public class HulkPersonnage extends Personnage {
    public HulkPersonnage(Vector2D position,Vector2D vitesse,Vector2D acceleration,Hitbox hitbox,ControleurHumain controleurs,Annimation annimation){
        super(position, hitbox, annimation, controleurs, 10);
        this.setPouvoir(new Hulk(this));
    }

    @Override
    public void ActivatePower(){
            this.setEtatJoueur(EtatJoueur.Pouvoir);
            this.pouvoir.activate();
            this.setEtatJoueur(EtatJoueur.IDLE);
    }

    @Override 
    public void tirer(){
        if ((this.etatJoueur != EtatJoueur.IDLE || this.etatJoueur != EtatJoueur.COURS || this.etatJoueur != EtatJoueur.Pouvoir) && this.isProprietaire == false)return;
        this.etatJoueur = EtatJoueur.TIRE;
        PauseTransition pause = new PauseTransition(Duration.seconds(2.5));
        pause.setOnFinished(e->{
            this.etatJoueur = EtatJoueur.IDLE;
        });
        pause.play();
    }
}
