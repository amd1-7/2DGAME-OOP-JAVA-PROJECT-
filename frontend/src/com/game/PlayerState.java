package com.game;

public enum PlayerState {
    IDLE,            // repos : le joueur ne bouge pas
    WALKING,         // marche
    SHOOTING,        // tir
    TACKLING,        // tacle
    CELEBRATING,     // célébration (tourne en boucle jusqu'à player.stopAction())
    RUNNING,         // course (animation prête, mais personne ne la déclenche pour l'instant)
    SPRINTING,       // accélération (course rapide, avec lignes de vitesse)
    PASSING,         // passe
    SUPER_SHOOTING;  // super-tir (aura + flammes)
    public boolean isAction() {
        return this == SHOOTING || this == TACKLING || this == CELEBRATING
                || this == PASSING || this == SUPER_SHOOTING;
    }
}
