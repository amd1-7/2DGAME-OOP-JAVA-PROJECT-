package com.game.model.input;

import com.game.model.physics.Vector2D;


public interface Controleur {
    public Vector2D getDirectionVoulue();
    public boolean isTireDemande();
    public boolean isPasseDemande();
    public boolean isPouvoirDemande();
}