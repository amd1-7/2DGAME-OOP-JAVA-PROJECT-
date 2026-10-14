package com.game.models.input;

import com.game.models.Ball.Etat;
import com.game.models.physics.Vector2D;


public interface Controleur {
    public Vector2D getDirection();
    public Etat getActionJoueur();
}