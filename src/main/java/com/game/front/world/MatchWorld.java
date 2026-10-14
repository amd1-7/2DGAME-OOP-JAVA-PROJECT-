package com.game.front.world;

import com.game.models.Ball;
import com.game.models.Goal;
import com.game.models.Joueur;
import com.game.models.Murs;
import com.game.models.input.ControleurHumain;

public class MatchWorld {
    private final ControleurHumain controller;
    private final Joueur player;
    private final Ball ball;
    private final Goal leftGoal;
    private final Goal rightGoal;
    private final Murs[] walls;
    private final Joueur[] players;

    public MatchWorld(ControleurHumain controller, Joueur player, Ball ball,
                      Goal leftGoal, Goal rightGoal, Murs[] walls) {
        this.controller = controller;
        this.player = player;
        this.ball = ball;
        this.leftGoal = leftGoal;
        this.rightGoal = rightGoal;
        this.walls = walls;
        this.players = new Joueur[]{player};
    }

    public void update(double dt) {
        player.update(dt, walls);
        ball.update(dt, walls, players);
    }

    public Goal scoringGoal() {
        if (leftGoal.estBut(ball)) return leftGoal;
        if (rightGoal.estBut(ball)) return rightGoal;
        return null;
    }

    public ControleurHumain getController() { return controller; }
    public Joueur getPlayer() { return player; }
    public Ball getBall() { return ball; }
    public Goal getLeftGoal() { return leftGoal; }
    public Goal getRightGoal() { return rightGoal; }
    public Murs[] getWalls() { return walls; }
}
