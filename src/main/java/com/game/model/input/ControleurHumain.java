package com.game.model.input;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.game.model.physics.Vector2D;

import javafx.scene.input.KeyCode;

public class ControleurHumain implements Controleur {
    public enum Action {
        HAUT, 
        BAS,
        GAUCHE,
        DROITE, 
        TIR, 
        PASSE, 
        POUVOIR
    }
    private Map<KeyCode,Action> touches;
    private Set<KeyCode> touchesPressees = new HashSet<>();
    private boolean tirDemande;
    private boolean passeDemandee;
    private boolean pouvoirDemandee;

    public ControleurHumain(Map<KeyCode,Action> touches){
        this.touches = touches;
    }

    public void surToucheAppuyee(KeyCode e){
        if(!this.touches.containsKey(e))return;
        if(!touchesPressees.add(e)) return;

        Action key = this.touches.get(e);
    
        if(key == Action.TIR) this.tirDemande = true;
        if(key == Action.PASSE) this.passeDemandee = true;
        if(key == Action.POUVOIR) this.pouvoirDemandee = true;
    }

    public void surToucheRelachee(KeyCode e){
        this.touchesPressees.remove(e);
    }

    
    public void reinitialiser(){
        this.passeDemandee = false;
        this.pouvoirDemandee = false;
        this.tirDemande = false;
        
        this.touchesPressees.clear();
    }

    @Override
    public Vector2D getDirectionVoulue(){
        Vector2D vecteur = new Vector2D(0, 0);
        for(KeyCode i:this.touchesPressees){
            if(touches.get(i).equals(Action.DROITE)) vecteur.add(new Vector2D(1, 0));
            if(touches.get(i).equals(Action.GAUCHE)) vecteur.add(new Vector2D(-1, 0));
            if(touches.get(i).equals(Action.HAUT)) vecteur.add(new Vector2D(0, 1));
            if(touches.get(i).equals(Action.BAS)) vecteur.add(new Vector2D(0, -1));
        }
        if(vecteur.getX() == 0 && vecteur.getY() == 0) vecteur = vecteur.normalize();
        return vecteur;
    }
    
    @Override
    public boolean isPasseDemande() {
        if(this.passeDemandee) 
        {
            this.passeDemandee = false;
            return true;
        }
        return false;
    }

    @Override
    public boolean isPouvoirDemande() {
        if(this.pouvoirDemandee) 
        {
            this.pouvoirDemandee = false;
            return true;
        }
        return false;
    }
    @Override
    public boolean isTireDemande() {
        if(this.tirDemande) 
        {
            this.tirDemande = false;
            return true;
        }
        return false;
    }
}