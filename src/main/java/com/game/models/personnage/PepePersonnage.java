package com.game.models.personnage;

import com.game.models.Joueur;
import com.game.models.input.ControleurHumain;
import com.game.models.physics.Hitbox;
import com.game.models.physics.Vector2D;
import com.game.models.powers.Pepe;
import com.game.view.spritesheet.Annimation;

public class PepePersonnage extends Personnage {
    public PepePersonnage(Vector2D position,Vector2D vitesse,Vector2D acceleration,Hitbox hitbox,Annimation annimation,ControleurHumain controleurs){
        super(position, hitbox, annimation, controleurs, 15);
    }
    
    public void setPouvoirJoueur(Joueur joueurAdverse) {
        this.setPouvoir(new Pepe(this, joueurAdverse));
    }

    @Override
    public void ActivatePower(){
        this.pouvoir.activate();
    }
}
