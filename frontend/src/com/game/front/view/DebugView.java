package com.game.front.view;

import com.game.front.world.MatchWorld;
import com.game.models.Murs;
import com.game.models.physics.Hitbox;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class DebugView {
    private static final Color WALL = Color.rgb(255, 0, 0, 0.6);
    private static final Color PLAYER = Color.rgb(0, 220, 255);
    private static final Color BALL = Color.rgb(255, 230, 0);

    public void render(GraphicsContext gc, Camera camera, MatchWorld world) {
        gc.save();
        gc.translate(-camera.getX(), -camera.getY());
        drawWalls(gc, world.getWalls());
        drawOutline(gc, world.getPlayer().getHitbox(), PLAYER);
        drawOutline(gc, world.getBall().getHitbox(), BALL);
        gc.restore();
    }

    private void drawWalls(GraphicsContext gc, Murs[] walls) {
        gc.setFill(WALL);
        for (Murs wall : walls) {
            Hitbox h = wall.getHitbox();
            gc.fillRect(h.getPosition().getX(), h.getPosition().getY(), h.getWidth(), h.getHeight());
        }
    }

    private void drawOutline(GraphicsContext gc, Hitbox h, Color color) {
        gc.setStroke(color);
        gc.setLineWidth(2);
        gc.strokeRect(h.getPosition().getX(), h.getPosition().getY(), h.getWidth(), h.getHeight());
    }
}
