package com.game.front.view;

import com.game.models.Goal;
import com.game.models.Murs;
import com.game.models.physics.Hitbox;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class GoalView {
    private static final Color NET_FILL = Color.rgb(235, 235, 235, 0.27);
    private static final Color NET_LINE = Color.rgb(255, 255, 255, 0.43);
    private static final Color POST = Color.rgb(245, 245, 245);
    private static final double NET_STEP = 14;

    public void render(GraphicsContext gc, Camera camera, Goal goal) {
        gc.save();
        gc.translate(-camera.getX(), -camera.getY());
        drawNet(gc, boundsOf(goal.getMurs()));
        drawPosts(gc, goal.getMurs());
        gc.restore();
    }

    private Rectangle2D boundsOf(Murs[] walls) {
        double minX = Double.MAX_VALUE, minY = Double.MAX_VALUE;
        double maxX = -Double.MAX_VALUE, maxY = -Double.MAX_VALUE;
        for (Murs wall : walls) {
            Hitbox h = wall.getHitbox();
            minX = Math.min(minX, h.getPosition().getX());
            minY = Math.min(minY, h.getPosition().getY());
            maxX = Math.max(maxX, h.getPosition().getX() + h.getWidth());
            maxY = Math.max(maxY, h.getPosition().getY() + h.getHeight());
        }
        return new Rectangle2D(minX, minY, maxX - minX, maxY - minY);
    }

    private void drawNet(GraphicsContext gc, Rectangle2D area) {
        gc.setFill(NET_FILL);
        gc.fillRect(area.getMinX(), area.getMinY(), area.getWidth(), area.getHeight());
        gc.setStroke(NET_LINE);
        gc.setLineWidth(1);
        drawVerticalLines(gc, area);
        drawHorizontalLines(gc, area);
    }

    private void drawVerticalLines(GraphicsContext gc, Rectangle2D area) {
        for (double x = area.getMinX(); x <= area.getMaxX(); x += NET_STEP) {
            gc.strokeLine(x, area.getMinY(), x, area.getMaxY());
        }
    }

    private void drawHorizontalLines(GraphicsContext gc, Rectangle2D area) {
        for (double y = area.getMinY(); y <= area.getMaxY(); y += NET_STEP) {
            gc.strokeLine(area.getMinX(), y, area.getMaxX(), y);
        }
    }

    private void drawPosts(GraphicsContext gc, Murs[] walls) {
        gc.setFill(POST);
        for (Murs wall : walls) {
            Hitbox h = wall.getHitbox();
            gc.fillRect(h.getPosition().getX(), h.getPosition().getY(), h.getWidth(), h.getHeight());
        }
    }
}
