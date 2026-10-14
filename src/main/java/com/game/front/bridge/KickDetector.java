package com.game.front.bridge;

import com.game.models.Ball;

public class KickDetector {
    private Ball.Etat previous = Ball.Etat.LIBRE;

    public boolean kickHappened(Ball ball) {
        Ball.Etat current = ball.getEtat();
        boolean kick = previous != Ball.Etat.TIRE && current == Ball.Etat.TIRE;
        previous = current;
        return kick;
    }
}
