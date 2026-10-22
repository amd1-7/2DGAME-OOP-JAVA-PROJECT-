package com.game.models.personnage;
import com.game.models.Joueur;
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
}
