package com.game;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Player {
    private float x, y;
    private float speed = 200.0f;
    private Direction currentDirection = Direction.DOWN;
    private PlayerState currentState = PlayerState.IDLE;
    
    private Animation walkAnim;
    private BufferedImage idleSprite;

    public Player(float startX, float startY) {
        this.x = startX;
        this.y = startY;

        try {
            BufferedImage sheet = ImageIO.read(new File("frontend/resources/assets/players/joueurs.png"));
            int tileWidth = sheet.getWidth() / 8;
            int tileHeight = sheet.getHeight() / 4;

            this.idleSprite = sheet.getSubimage(0, 0, tileWidth, tileHeight);

            BufferedImage[] walkFrames = new BufferedImage[4];
            for (int i = 0; i < 4; i++) {
                walkFrames[i] = sheet.getSubimage(i * tileWidth, 0, tileWidth, tileHeight);
            }

            this.walkAnim = new Animation(walkFrames, 0.15);

        } catch (IOException e) {
            System.err.println("Erreur de chargement du sprite : " + e.getMessage());
        }
    }

    public void update(double deltaTime, boolean up, boolean down, boolean left, boolean right, int mapWidth, int mapHeight) {
        boolean isMoving = false;

        if (up) { y -= speed * deltaTime; currentDirection = Direction.UP; isMoving = true; }
        if (down) { y += speed * deltaTime; currentDirection = Direction.DOWN; isMoving = true; }
        if (left) { x -= speed * deltaTime; currentDirection = Direction.LEFT; isMoving = true; }
        if (right) { x += speed * deltaTime; currentDirection = Direction.RIGHT; isMoving = true; }

        if (x < 0) x = 0;
        if (y < 0) y = 0;
        if (x > mapWidth - 48) x = mapWidth - 48;
        if (y > mapHeight - 48) y = mapHeight - 48;

        currentState = isMoving ? PlayerState.WALKING : PlayerState.IDLE;

        if (currentState == PlayerState.WALKING && walkAnim != null) {
            walkAnim.update(deltaTime);
        }
    }

    public void render(Graphics2D g, Camera camera) {
        int renderX = (int) (x - camera.getX());
        int renderY = (int) (y - camera.getY());

        BufferedImage currentImage = idleSprite;
        if (currentState == PlayerState.WALKING && walkAnim != null) {
            currentImage = walkAnim.getCurrentFrame();
        }

        if (currentImage != null) {
            g.drawImage(currentImage, renderX, renderY, 48, 48, null);
        }
    }

    public float getX() { return x; }
    public float getY() { return y; }
}