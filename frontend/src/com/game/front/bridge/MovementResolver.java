package com.game.front.bridge;

import com.game.front.sprite.PlayerState;
import com.game.models.Joueur;

public final class MovementResolver {
    private static final double RUN_SPEED = 200;
    private static final double SPRINT_SPEED = 400;

    private MovementResolver() {}

    public static PlayerState resolve(Joueur player) {
        if (player.getEtatJoueur() == Joueur.EtatJoueur.IDLE) return PlayerState.IDLE;
        return fromSpeed(player.getVitesse().norm());
    }

    private static PlayerState fromSpeed(double speed) {
        if (speed >= SPRINT_SPEED) return PlayerState.SPRINTING;
        if (speed >= RUN_SPEED) return PlayerState.RUNNING;
        return PlayerState.WALKING;
    }
}
