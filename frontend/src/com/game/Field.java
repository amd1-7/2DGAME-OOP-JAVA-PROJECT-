package com.game;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;

public class Field {
    public static final float PITCH_LEFT = 150, PITCH_TOP = 100, PITCH_RIGHT = 1450, PITCH_BOTTOM = 1100;
    public static final float CENTER_X = (PITCH_LEFT + PITCH_RIGHT) / 2;
    public static final float CENTER_Y = (PITCH_TOP + PITCH_BOTTOM) / 2;

    private final Hitbox pitch = new Hitbox(PITCH_LEFT, PITCH_TOP, PITCH_RIGHT - PITCH_LEFT, PITCH_BOTTOM - PITCH_TOP);
    private final Goal leftGoal = new Goal(Goal.Side.LEFT, PITCH_LEFT, CENTER_Y);
    private final Goal rightGoal = new Goal(Goal.Side.RIGHT, PITCH_RIGHT, CENTER_Y);
    private final List<Hitbox> colliders = new ArrayList<>();

    public Field() {
        colliders.addAll(leftGoal.getSolids());
        colliders.addAll(rightGoal.getSolids());
    }

    public Goal getLeftGoal() { return leftGoal; }
    public Goal getRightGoal() { return rightGoal; }
    public Hitbox getPitch() { return pitch; }

    public List<Hitbox> getColliders() { return colliders; }

    public Hitbox firstCollision(Hitbox box) {
        for (Hitbox c : colliders) if (c.intersects(box)) return c;
        return null;
    }

    // les cages et surfaces sont testées avant la pelouse
    public Zone getZoneAt(float px, float py) {
        if (leftGoal.getScoringZone().contains(px, py)) return Zone.LEFT_GOAL;
        if (rightGoal.getScoringZone().contains(px, py)) return Zone.RIGHT_GOAL;
        if (leftGoal.getGoalArea().contains(px, py)) return Zone.LEFT_GOAL_AREA;
        if (rightGoal.getGoalArea().contains(px, py)) return Zone.RIGHT_GOAL_AREA;
        if (leftGoal.getPenaltyArea().contains(px, py)) return Zone.LEFT_PENALTY_AREA;
        if (rightGoal.getPenaltyArea().contains(px, py)) return Zone.RIGHT_PENALTY_AREA;
        return pitch.contains(px, py) ? Zone.FIELD : Zone.OUTSIDE;
    }

    // but = balle ENTIÈREMENT dans la cage (le score est compté par le back)
    public Goal goalScoredBy(Hitbox ball) {
        if (leftGoal.isBallInside(ball)) return leftGoal;
        if (rightGoal.isBallInside(ball)) return rightGoal;
        return null;
    }

    public void render(Graphics2D g, Camera camera) {
        AffineTransform old = g.getTransform();
        Stroke oldStroke = g.getStroke();
        Object oldAA = g.getRenderingHint(RenderingHints.KEY_ANTIALIASING);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.translate(-camera.getX(), -camera.getY());

        drawGrass(g);
        drawLines(g);
        drawGoal(g, leftGoal);
        drawGoal(g, rightGoal);

        g.setTransform(old);
        g.setStroke(oldStroke);
        if (oldAA != null) g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, oldAA);
    }

    private void drawGrass(Graphics2D g) {
        g.setColor(new Color(30, 112, 40));
        g.fillRect(0, 0, GamePanel.MAP_WIDTH, GamePanel.MAP_HEIGHT);

        int stripe = 100;
        for (int i = 0; PITCH_LEFT + i * stripe < PITCH_RIGHT; i++) {
            g.setColor(i % 2 == 0 ? new Color(46, 139, 52) : new Color(56, 156, 62));
            g.fillRect((int) (PITCH_LEFT + i * stripe), (int) PITCH_TOP, stripe, (int) (PITCH_BOTTOM - PITCH_TOP));
        }
    }

    private void drawLines(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.setStroke(new BasicStroke(4f));
        int l = (int) PITCH_LEFT, t = (int) PITCH_TOP, r = (int) PITCH_RIGHT, b = (int) PITCH_BOTTOM;
        int cx = (int) CENTER_X, cy = (int) CENTER_Y;

        g.drawRect(l, t, r - l, b - t);
        g.drawLine(cx, t, cx, b);
        g.drawOval(cx - 90, cy - 90, 180, 180);
        g.fillOval(cx - 5, cy - 5, 10, 10);

        for (Goal goal : new Goal[]{leftGoal, rightGoal}) {
            boolean left = goal.getSide() == Goal.Side.LEFT;
            drawBox(g, goal.getPenaltyArea());
            drawBox(g, goal.getGoalArea());
            int spotX = left ? l + 180 : r - 180;
            g.fillOval(spotX - 5, cy - 5, 10, 10);

            g.drawArc(spotX - 90, cy - 90, 180, 180, left ? -27 : 153, 54);
        }
    }

    private void drawBox(Graphics2D g, Hitbox h) {
        g.drawRect((int) h.getX(), (int) h.getY(), (int) h.getWidth(), (int) h.getHeight());
    }

    private void drawGoal(Graphics2D g, Goal goal) {
        Hitbox in = goal.getScoringZone();
        g.setColor(new Color(235, 235, 235, 70));
        g.fillRect((int) in.getX(), (int) in.getY(), (int) in.getWidth(), (int) in.getHeight());
        g.setColor(new Color(255, 255, 255, 110));
        g.setStroke(new BasicStroke(1f));
        for (float xx = in.getX(); xx <= in.getRight(); xx += 14) g.drawLine((int) xx, (int) in.getY(), (int) xx, (int) in.getBottom());
        for (float yy = in.getY(); yy <= in.getBottom(); yy += 14) g.drawLine((int) in.getX(), (int) yy, (int) in.getRight(), (int) yy);
        g.setColor(new Color(245, 245, 245));
        for (Hitbox s : goal.getSolids()) {
            g.fillRect((int) s.getX(), (int) s.getY(), (int) s.getWidth(), (int) s.getHeight());
        }
    }

    public void renderDebug(Graphics2D g, Camera camera) {
        AffineTransform old = g.getTransform();
        Stroke oldStroke = g.getStroke();
        g.translate(-camera.getX(), -camera.getY());

        g.setColor(new Color(255, 0, 0, 150));
        for (Hitbox c : colliders) g.fillRect((int) c.getX(), (int) c.getY(), (int) c.getWidth(), (int) c.getHeight());
        for (Goal goal : new Goal[]{leftGoal, rightGoal}) {
            Hitbox z = goal.getScoringZone();
            g.setColor(new Color(255, 230, 0, 110));
            g.fillRect((int) z.getX(), (int) z.getY(), (int) z.getWidth(), (int) z.getHeight());
            g.setColor(new Color(255, 230, 0));
            g.setStroke(new BasicStroke(2f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10f, new float[]{6f, 6f}, 0f));
            drawBox(g, goal.getGoalArea());
            g.setColor(new Color(0, 220, 255));
            drawBox(g, goal.getPenaltyArea());
        }
        g.setTransform(old);
        g.setStroke(oldStroke);
    }
}
