package com.game.models.personnage;

import com.game.models.powers.Pouvoir;

public final class AccesPouvoir {

    private AccesPouvoir() { }

    public static Pouvoir de(Personnage personnage) {
        return personnage.pouvoir;
    }
}
