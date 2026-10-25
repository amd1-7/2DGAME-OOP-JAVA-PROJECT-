package com.game.models.powers;

import com.game.models.Joueur;

import javafx.animation.PauseTransition;
import javafx.util.Duration;

public class Hulk extends Pouvoir{
    private double power = 75;
    public Hulk(Joueur joueur){
        super("Hulk",Type.ATTAQUE,Duration.minutes(33),Duration.seconds(20),joueur);
    }

    public void activate() {
        if(!this.getDispo()) return;
        double puissanceinitial = this.joueur.getPuissanceTir();
        this.joueur.setPuissanceTir(this.power);
        this.setDispo(false);

        this.timerActivate = new PauseTransition(this.getDuréeActivate());
        this.timerActivate.setOnFinished(e->{

            this.joueur.setPuissanceTir(puissanceinitial);
            this.charge();
        });
        this.timerActivate.play();
    }

    protected void charge() {
        if(this.getDispo()) return;
        this.timerCharge = new PauseTransition(this.getDuréeCharge());
        this.timerCharge.setOnFinished(e->{
            this.setDispo(true);
        });
        this.timerCharge.play();
    }
}
