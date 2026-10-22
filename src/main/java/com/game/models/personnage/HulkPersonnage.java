package com.game.models.personnage;

import com.game.models.input.ControleurHumain;
import com.game.models.physics.Hitbox;
import com.game.models.physics.Vector2D;
import com.game.models.powers.Hulk;
import com.game.view.spritesheet.Annimation;

public class HulkPersonnage extends Personnage {
    public HulkPersonnage(Vector2D position,Vector2D vitesse,Vector2D acceleration,Hitbox hitbox,ControleurHumain controleurs,Annimation annimation){
        super(position, hitbox, annimation, controleurs, 15);
        this.setPouvoir(new Hulk(this));
    }

    @Override
    public void ActivatePower(){
            ((Hulk)this.pouvoir).activate();
    }
}
