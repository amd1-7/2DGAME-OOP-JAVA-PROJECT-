package com.game.front.view;

import com.game.models.physics.Hitbox;
import com.game.models.physics.Vector2D;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class BallView {
    private static final Color SHADOW = Color.rgb(0, 0, 0, 0.25);
    private static final Color BODY = Color.rgb(250, 250, 250);
    private static final Color PATCH = Color.rgb(40, 40, 40);
    private static final double SHADOW_OFFSET = 3;
    private static final double PATCH_RATIO = 0.4;

    public void render(GraphicsContext gc, Camera camera, Hitbox hitbox) {
        Vector2D center = hitbox.getCentre();
        double radius = hitbox.getWidth() / 2;
        double x = center.getX() - camera.getX();
        double y = center.getY() - camera.getY();
        drawShadow(gc, x, y, radius);
        drawBody(gc, x, y, radius);
        drawPatch(gc, x, y, radius);
    }

    private void drawShadow(GraphicsContext gc, double x, double y, double radius) {
        gc.setFill(SHADOW);
        fillCircle(gc, x + SHADOW_OFFSET, y + SHADOW_OFFSET, radius);
    }

    private void drawBody(GraphicsContext gc, double x, double y, double radius) {
        gc.setFill(BODY);
        fillCircle(gc, x, y, radius);
        gc.setStroke(PATCH);
        gc.setLineWidth(2);
        gc.strokeOval(x - radius, y - radius, 2 * radius, 2 * radius);
    }

    private void drawPatch(GraphicsContext gc, double x, double y, double radius) {
        gc.setFill(PATCH);
        fillCircle(gc, x, y, radius * PATCH_RATIO);
    }

    private void fillCircle(GraphicsContext gc, double x, double y, double radius) {
        gc.fillOval(x - radius, y - radius, 2 * radius, 2 * radius);
    }
}
