package com.game.models.powers;
import com.game.models.Joueur;
import com.game.models.Joueur.EtatJoueur;

import javafx.animation.PauseTransition;
import javafx.util.Duration;
public class Pepe extends Pouvoir {

    private Joueur joueurAdversaire;
    public Pepe(Joueur joueur,Joueur joueurAdversaire){
        super("Pepe", Type.DEFENSE, Duration.minutes(1.5),Duration.seconds(5),joueur);
        this.joueurAdversaire = joueurAdversaire;
    }
    @Override
    public void activate() {
        if(!this.getDispo())return;
        this.joueurAdversaire.setEtatJoueur(EtatJoueur.Glace);
        this.setDispo(false);
        this.timerActivate = new PauseTransition(this.getDuréeActivate());

        this.timerActivate.setOnFinished(e->{
            this.charge();
            this.joueurAdversaire.setEtatJoueur(EtatJoueur.IDLE);
        });
        this.timerActivate.play();
    }

    @Override
    protected void charge() {
        if(this.getDispo())return;
        this.timerCharge = new PauseTransition(this.getDuréeCharge());
        this.timerCharge.setOnFinished(e->{
            this.setDispo(true);
        });
        this.timerCharge.play();
    }
}
