package com.game.front.view;

import static com.game.front.world.WorldLayout.CENTER_X;
import static com.game.front.world.WorldLayout.CENTER_Y;
import static com.game.front.world.WorldLayout.MAP_HEIGHT;
import static com.game.front.world.WorldLayout.MAP_WIDTH;
import static com.game.front.world.WorldLayout.PITCH_BOTTOM;
import static com.game.front.world.WorldLayout.PITCH_LEFT;
import static com.game.front.world.WorldLayout.PITCH_RIGHT;
import static com.game.front.world.WorldLayout.PITCH_TOP;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;

public class FieldView {
    private static final Color GRASS = Color.rgb(30, 112, 40);
    private static final Color STRIPE_DARK = Color.rgb(46, 139, 52);
    private static final Color STRIPE_LIGHT = Color.rgb(56, 156, 62);
    private static final double STRIPE_WIDTH = 100;
    private static final double LINE_WIDTH = 4;
    private static final double SPOT_RADIUS = 5;
    private static final double CIRCLE_RADIUS = 90;
    private static final double GOAL_AREA_DEPTH = 90;
    private static final double GOAL_AREA_HALF = 120;
    private static final double PENALTY_DEPTH = 260;
    private static final double PENALTY_HALF = 250;
    private static final double SPOT_OFFSET = 180;
    private static final double ARC_EXTENT = 54;

    public void render(GraphicsContext gc, Camera camera) {
        gc.save();
        gc.translate(-camera.getX(), -camera.getY());
        drawGrass(gc);
        drawStripes(gc);
        drawMarkings(gc);
        gc.restore();
    }

    private void drawGrass(GraphicsContext gc) {
        gc.setFill(GRASS);
        gc.fillRect(0, 0, MAP_WIDTH, MAP_HEIGHT);
    }

    private void drawStripes(GraphicsContext gc) {
        double height = PITCH_BOTTOM - PITCH_TOP;
        int index = 0;
        for (double x = PITCH_LEFT; x < PITCH_RIGHT; x += STRIPE_WIDTH) {
            gc.setFill(index++ % 2 == 0 ? STRIPE_DARK : STRIPE_LIGHT);
            gc.fillRect(x, PITCH_TOP, STRIPE_WIDTH, height);
        }
    }

    private void drawMarkings(GraphicsContext gc) {
        gc.setStroke(Color.WHITE);
        gc.setFill(Color.WHITE);
        gc.setLineWidth(LINE_WIDTH);
        drawOutline(gc);
        drawCenter(gc);
        drawSide(gc, true);
        drawSide(gc, false);
    }

    private void drawOutline(GraphicsContext gc) {
        gc.strokeRect(PITCH_LEFT, PITCH_TOP, PITCH_RIGHT - PITCH_LEFT, PITCH_BOTTOM - PITCH_TOP);
        gc.strokeLine(CENTER_X, PITCH_TOP, CENTER_X, PITCH_BOTTOM);
    }

    private void drawCenter(GraphicsContext gc) {
        gc.strokeOval(CENTER_X - CIRCLE_RADIUS, CENTER_Y - CIRCLE_RADIUS, 2 * CIRCLE_RADIUS, 2 * CIRCLE_RADIUS);
        drawSpot(gc, CENTER_X);
    }

    private void drawSide(GraphicsContext gc, boolean left) {
        double lineX = left ? PITCH_LEFT : PITCH_RIGHT;
        double direction = left ? 1 : -1;
        double spotX = lineX + direction * SPOT_OFFSET;
        drawBox(gc, lineX, direction, PENALTY_DEPTH, PENALTY_HALF);
        drawBox(gc, lineX, direction, GOAL_AREA_DEPTH, GOAL_AREA_HALF);
        drawSpot(gc, spotX);
        drawPenaltyArc(gc, spotX, left);
    }

    private void drawBox(GraphicsContext gc, double lineX, double direction, double depth, double half) {
        double x = direction > 0 ? lineX : lineX - depth;
        gc.strokeRect(x, CENTER_Y - half, depth, 2 * half);
    }

    private void drawSpot(GraphicsContext gc, double x) {
        gc.fillOval(x - SPOT_RADIUS, CENTER_Y - SPOT_RADIUS, 2 * SPOT_RADIUS, 2 * SPOT_RADIUS);
    }

    private void drawPenaltyArc(GraphicsContext gc, double spotX, boolean left) {
        double start = left ? -ARC_EXTENT / 2 : 180 - ARC_EXTENT / 2;
        gc.strokeArc(spotX - CIRCLE_RADIUS, CENTER_Y - CIRCLE_RADIUS, 2 * CIRCLE_RADIUS, 2 * CIRCLE_RADIUS,
                start, ARC_EXTENT, ArcType.OPEN);
    }
}
