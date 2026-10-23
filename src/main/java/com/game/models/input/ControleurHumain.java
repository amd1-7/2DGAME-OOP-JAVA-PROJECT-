package com.game.models.input;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.game.models.Ball.Etat;
import com.game.models.Joueur.EtatJoueur;
import com.game.models.physics.Direction;
import com.game.models.physics.Vector2D;

import javafx.scene.input.KeyCode;

public class ControleurHumain implements Controleur{

    private Map<KeyCode,Direction> touches;
    private Map<KeyCode,Etat> touchesSpecial;
    private Map<KeyCode,EtatJoueur> touchesAction;
    private Set<KeyCode> toucheAppuyee = new HashSet<>();
    private boolean actif = true;

    public ControleurHumain(Map<KeyCode,Direction> touches, Map<KeyCode,Etat> touchesSpecial,Map<KeyCode,EtatJoueur> touchesAction){
        this.touches = touches;
        this.touchesSpecial = touchesSpecial;
        this.touchesAction = touchesAction;
    }
    public boolean getActif(){
        return this.actif;
    }
    public void setActif(boolean actif) {
        this.actif = actif;
    }

    public void toucheAppuyee(KeyCode k){
        if(!touches.containsKey(k) && !touchesSpecial.containsKey(k) && !touchesAction.containsKey(k)) return;
        if(toucheAppuyee.add(k)) return;
    }
    public void toucheRelachee(KeyCode k){
        toucheAppuyee.remove(k);
    }

    public Vector2D getDirection(){
        Vector2D v = new Vector2D(0, 0);
        if(!this.actif)return v;
        for (KeyCode i :toucheAppuyee){
            if(touches.containsKey(i)){
                v = v.add(this.touches.get(i).getVector());
            }
        }
        return v;
    }

    public Etat getActionJoueur(){
        Etat etat = Etat.EN_POSSESSION;
        if(!this.actif)return etat;

        for (KeyCode i : toucheAppuyee){
            if(touchesSpecial.containsKey(i)){
                etat = touchesSpecial.get(i);
                break;
            }
        }
        return etat;
    }
    public EtatJoueur getActionJoueurSansballon(){
        EtatJoueur etatJoueur = EtatJoueur.IDLE;
        if(!this.actif)return etatJoueur;
        
        for (KeyCode i : toucheAppuyee){
            if(touchesAction.containsKey(i)){
                etatJoueur = touchesAction.get(i);
                break;
            }
        }
        return etatJoueur;
    }
}