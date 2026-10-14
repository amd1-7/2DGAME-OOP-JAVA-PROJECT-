package com.game.front.sprite;

public enum PlayerState {
    IDLE,
    WALKING,
    RUNNING,
    SPRINTING,
    PASSING,
    SHOOTING,
    SUPER_SHOOTING,
    TACKLING,
    CELEBRATING;

    public boolean isAction() {
        return this == PASSING || this == SHOOTING || this == SUPER_SHOOTING
                || this == TACKLING || this == CELEBRATING;
    }
}
