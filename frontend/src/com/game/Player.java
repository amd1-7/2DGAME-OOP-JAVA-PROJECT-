package com.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.EnumMap;
import java.util.Map;

public class Player {
    public static final int SIZE = 48;

    private float x, y;
    private float speed = 200.0f;
    private Direction currentDirection = Direction.DOWN;
    private PlayerState currentState = PlayerState.IDLE;

    private int kit = PlayerSprites.KIT_BLUE;
    private Map<PlayerState, Animation> animations = new EnumMap<>(PlayerState.class);
    private PlayerState action = null;
    private boolean sprinting = false;
    private double angle = Math.PI / 2;

    public Player(float startX, float startY) {
        this(startX, startY, PlayerSprites.KIT_BLUE);
    }

    public Player(float startX, float startY, int kit) {
        this.x = startX;
        this.y = startY;
        setKit(kit);
    }

    public synchronized void update(double deltaTime, boolean up, boolean down, boolean left, boolean right, int mapWidth, int mapHeight) {
        boolean isMoving = false;

        if (up) { y -= speed * deltaTime; currentDirection = Direction.UP; isMoving = true; }
        if (down) { y += speed * deltaTime; currentDirection = Direction.DOWN; isMoving = true; }
        if (left) { x -= speed * deltaTime; currentDirection = Direction.LEFT; isMoving = true; }
        if (right) { x += speed * deltaTime; currentDirection = Direction.RIGHT; isMoving = true; }

        if (x < 0) x = 0;
        if (y < 0) y = 0;
        if (x > mapWidth - 48) x = mapWidth - 48;
        if (y > mapHeight - 48) y = mapHeight - 48;

        int dx = (right ? 1 : 0) - (left ? 1 : 0);
        int dy = (down ? 1 : 0) - (up ? 1 : 0);
        if (dx != 0 || dy != 0) angle = Math.atan2(dy, dx);

        if (action != null) {
            Animation a = animations.get(action);
            if (a == null || a.isFinished()) action = null;
        }

        PlayerState next;
        if (action != null) next = action;
        else if (isMoving) next = sprinting ? PlayerState.SPRINTING : PlayerState.WALKING;
        else next = PlayerState.IDLE;

        if (next != currentState) {
            Animation a = animations.get(next);
            if (a != null) a.reset();
            currentState = next;
        }

        Animation current = animations.get(currentState);
        if (current != null) current.update(deltaTime);
    }

    public synchronized void render(Graphics2D g, Camera camera) {
        int renderX = (int) (x - camera.getX());
        int renderY = (int) (y - camera.getY());
        double cx = renderX + SIZE / 2.0;
        double cy = renderY + SIZE / 2.0;

        Animation anim = animations.get(currentState);
        BufferedImage currentImage = anim != null ? anim.getCurrentFrame() : null;

        if (currentImage == null) {
            g.setColor(Color.ORANGE);
            g.fillOval(renderX, renderY, SIZE, SIZE);
            return;
        }

        AffineTransform oldTransform = g.getTransform();
        Object oldHint = g.getRenderingHint(RenderingHints.KEY_INTERPOLATION);
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);

        g.rotate(angle, cx, cy);
        int drawSize = SIZE * PlayerSprites.CELL / PlayerSprites.PLAYER_PIXELS;
        g.drawImage(currentImage, (int) Math.round(cx - drawSize / 2.0), (int) Math.round(cy - drawSize / 2.0),
                drawSize, drawSize, null);

        g.setTransform(oldTransform);
        if (oldHint != null) g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, oldHint);
    }

    public synchronized void play(PlayerState state) {
        if (state == null || !state.isAction()) {
            action = null;
            return;
        }
        action = state;
        Animation a = animations.get(state);
        if (a != null) a.reset();
    }

    public synchronized void stopAction() { action = null; }

    public void setSprinting(boolean sprinting) { this.sprinting = sprinting; }

    public synchronized void setKit(int kit) {
        this.kit = Math.floorMod(kit, PlayerSprites.KIT_COUNT);
        this.animations = PlayerSprites.load(this.kit);
        this.action = null;
    }

    public synchronized boolean isActionPlaying() { return action != null; }
    public PlayerState getState() { return currentState; }
    public Direction getDirection() { return currentDirection; }
    public int getKit() { return kit; }

    public float getX() { return x; }
    public float getY() { return y; }
}