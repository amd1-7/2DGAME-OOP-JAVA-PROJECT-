package com.game.model.powers;

import javafx.animation.PauseTransition;
import com.game.model.Joueur;
import javafx.util.Duration;

public class Hulk extends Pouvoir{
    private double power = 800;
    public Hulk(){
        super("Hulk",Type.ATTAQUE,Duration.minutes(1),Duration.seconds(30)); /* 30 SECONDES */
    }

    public void activate(Joueur j) {
        if(!this.getDispo()) return;
        double puissanceinitial = j.getPuissanceTir();
        j.setPuissanceTir(this.power);

        this.timerActivate = new PauseTransition(this.getDuréeActivate());

        j.setPuissanceTir(puissanceinitial);
        this.setDispo(false);
        this.charge();
    }

    public void charge() {
        if(this.getDispo()) return;
        this.timerCharge = new PauseTransition(this.getDuréeCharge());
        this.setDispo(true);
    }
}
