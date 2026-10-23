package com.game.models.powers;
import com.game.models.Joueur;

import javafx.animation.PauseTransition;
import javafx.util.Duration;

abstract public class Pouvoir {
    public enum Type {
        ATTAQUE,
        DEFENSE
    }
    private String name;
    private Type type;
    private boolean dispo;
    private Duration duréeActivate;
    private Duration duréeCharge;
    protected Joueur joueur;

    protected PauseTransition timerActivate;
    protected PauseTransition timerCharge;

    public String getName() {
        return name;
    }
    public Type getType() {
        return type;
    }
    public Duration getDuréeActivate() {
        return duréeActivate;
    }
    public Duration getDuréeCharge() {
        return duréeCharge;
    }

    public boolean getDispo(){
        return this.dispo;
    }
    public void setDispo(boolean dispo) {
        this.dispo = dispo;
    }

    public Pouvoir(String name,Type type,Duration timerCharge,Duration timerActivate,Joueur joueur){
        this.name = name;
        this.type = type;
        this.duréeCharge = timerCharge;
        this.duréeActivate = timerActivate;
        this.joueur = joueur;
    }

    public abstract void activate();
    protected abstract void charge();
}