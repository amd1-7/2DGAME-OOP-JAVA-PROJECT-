package com.game;

public enum PlayerState {
    IDLE,
    WALKING,
    SHOOTING,
    TACKLING,
    CELEBRATING,
    RUNNING,
    SPRINTING,
    PASSING,
    SUPER_SHOOTING;

    public boolean isAction() {
        return this == SHOOTING || this == TACKLING || this == CELEBRATING
                || this == PASSING || this == SUPER_SHOOTING;
    }
}
