package com.game.front.view;

import java.util.Map;

import com.game.front.sprite.Animation;
import com.game.front.sprite.PlayerSprites;
import com.game.front.sprite.PlayerState;
import com.game.models.physics.Hitbox;
import com.game.models.physics.Vector2D;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;

public class PlayerView {
    private static final double BODY_SIZE = 48;
    private static final double DRAW_SIZE = BODY_SIZE * PlayerSprites.CELL / PlayerSprites.PLAYER_PIXELS;

    private Map<PlayerState, Animation> animations;
    private PlayerState state = PlayerState.IDLE;
    private PlayerState action;
    private int kit;
    private double angle = Math.PI / 2;

    public PlayerView(int kit) {
        loadKit(kit);
    }

    public void setKit(int kit) {
        loadKit(kit);
    }

    private void loadKit(int kit) {
        this.kit = PlayerSprites.normalizeKit(kit);
        this.animations = PlayerSprites.load(this.kit);
        this.action = null;
    }

    public void play(PlayerState actionState) {
        if (!actionState.isAction()) return;
        action = actionState;
        animations.get(actionState).reset();
    }

    public void stopAction() {
        action = null;
    }

    public void face(Vector2D orientation) {
        if (orientation == null) return;
        angle = Math.atan2(orientation.getY(), orientation.getX());
    }

    public void update(double dt, PlayerState movement) {
        dropFinishedAction();
        switchTo(action != null ? action : movement);
        animations.get(state).update(dt);
    }

    private void dropFinishedAction() {
        if (action != null && animations.get(action).isFinished()) action = null;
    }

    private void switchTo(PlayerState next) {
        if (next == state) return;
        animations.get(next).reset();
        state = next;
    }

    public void render(GraphicsContext gc, Camera camera, Hitbox hitbox) {
        Vector2D center = hitbox.getCentre();
        gc.save();
        gc.setImageSmoothing(false);
        gc.translate(center.getX() - camera.getX(), center.getY() - camera.getY());
        gc.rotate(Math.toDegrees(angle));
        drawFrame(gc);
        gc.restore();
    }

    private void drawFrame(GraphicsContext gc) {
        Rectangle2D frame = animations.get(state).getCurrentFrame();
        gc.drawImage(PlayerSprites.sheet(),
                frame.getMinX(), frame.getMinY(), frame.getWidth(), frame.getHeight(),
                -DRAW_SIZE / 2, -DRAW_SIZE / 2, DRAW_SIZE, DRAW_SIZE);
    }

    public boolean isCelebrating() { return action == PlayerState.CELEBRATING; }
    public PlayerState getState() { return state; }
    public int getKit() { return kit; }
}
