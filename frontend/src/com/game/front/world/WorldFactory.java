package com.game.front.world;

import com.game.models.Ball;
import com.game.models.Equipe;
import com.game.models.Goal;
import com.game.models.Joueur;
import com.game.models.Murs;
import com.game.models.input.ControleurHumain;
import com.game.models.physics.Direction;
import com.game.models.physics.Hitbox;
import com.game.models.physics.Vector2D;

import javafx.scene.image.Image;

public final class WorldFactory {
    private WorldFactory() {}

    public static MatchWorld create(ControleurHumain controller, Image playerImage) {
        Goal left = createGoal(Equipe.Gauche, WorldLayout.PITCH_LEFT, Direction.RIGHT);
        Goal right = createGoal(Equipe.Droite, WorldLayout.PITCH_RIGHT, Direction.LEFT);
        Murs[] walls = mergeWalls(left, right, createBorders());
        return new MatchWorld(controller, createPlayer(controller, playerImage), createBall(), left, right, walls);
    }

    private static Joueur createPlayer(ControleurHumain controller, Image image) {
        double x = WorldLayout.CENTER_X - WorldLayout.PLAYER_START_OFFSET;
        double y = WorldLayout.CENTER_Y - WorldLayout.PLAYER_HITBOX / 2;
        Vector2D position = new Vector2D(x, y);
        Hitbox hitbox = new Hitbox(position, WorldLayout.PLAYER_HITBOX, WorldLayout.PLAYER_HITBOX);
        return new Joueur(position, new Vector2D(0, 0), new Vector2D(0, 0), hitbox,
                controller, image, WorldLayout.SHOT_POWER);
    }

    private static Ball createBall() {
        double x = WorldLayout.CENTER_X - WorldLayout.BALL_SIZE / 2;
        double y = WorldLayout.CENTER_Y - WorldLayout.BALL_SIZE / 2;
        Vector2D position = new Vector2D(x, y);
        return new Ball(position, new Hitbox(position, WorldLayout.BALL_SIZE, WorldLayout.BALL_SIZE));
    }

    private static Goal createGoal(Equipe team, double lineX, Direction orientation) {
        return new Goal(team, new Vector2D(lineX, WorldLayout.CENTER_Y), orientation,
                WorldLayout.GOAL_MOUTH, WorldLayout.GOAL_DEPTH, WorldLayout.GOAL_POST, WorldLayout.GOAL_BACK);
    }

    private static Murs[] createBorders() {
        double t = WorldLayout.WALL_THICKNESS;
        double w = WorldLayout.MAP_WIDTH;
        double h = WorldLayout.MAP_HEIGHT;
        return new Murs[]{
                wall(-t, -t, w + 2 * t, t),
                wall(-t, h, w + 2 * t, t),
                wall(-t, -t, t, h + 2 * t),
                wall(w, -t, t, h + 2 * t)
        };
    }

    private static Murs wall(double x, double y, double width, double height) {
        Hitbox hitbox = new Hitbox(new Vector2D(x, y), width, height);
        return new Murs(hitbox.getPosition(), hitbox);
    }

    private static Murs[] mergeWalls(Goal left, Goal right, Murs[] borders) {
        Murs[] all = new Murs[left.getMurs().length + right.getMurs().length + borders.length];
        int next = copy(left.getMurs(), all, 0);
        next = copy(right.getMurs(), all, next);
        copy(borders, all, next);
        return all;
    }

    private static int copy(Murs[] source, Murs[] target, int from) {
        System.arraycopy(source, 0, target, from, source.length);
        return from + source.length;
    }
}
