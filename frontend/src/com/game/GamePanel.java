package com.game;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GamePanel extends JPanel implements Runnable {
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    public static final int MAP_WIDTH = 1600;
    public static final int MAP_HEIGHT = 1200;

    private Thread gameThread;
    private boolean isRunning = false;

    private Player player;
    private Camera camera;

    private boolean up, down, left, right;

    public GamePanel() {
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);

        this.player = new Player(800, 600);
        this.camera = new Camera(WIDTH, HEIGHT, MAP_WIDTH, MAP_HEIGHT);

        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int code = e.getKeyCode();
                if (code == KeyEvent.VK_Z || code == KeyEvent.VK_UP) up = true;
                if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) down = true;
                if (code == KeyEvent.VK_Q || code == KeyEvent.VK_LEFT) left = true;
                if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) right = true;
            }

            @Override
            public void keyReleased(KeyEvent e) {
                int code = e.getKeyCode();
                if (code == KeyEvent.VK_Z || code == KeyEvent.VK_UP) up = false;
                if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) down = false;
                if (code == KeyEvent.VK_Q || code == KeyEvent.VK_LEFT) left = false;
                if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) right = false;
            }
        });
    }

    public void startGame() {
        isRunning = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double targetFPS = 60.0;
        double nsPerFrame = 1_000_000_000.0 / targetFPS;

        while (isRunning) {
            long now = System.nanoTime();
            double deltaTime = (now - lastTime) / 1_000_000_000.0;
            lastTime = now;

            update(deltaTime);
            repaint();

            try {
                Thread.sleep((long) (nsPerFrame / 1_000_000.0));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void update(double deltaTime) {
        player.update(deltaTime, up, down, left, right, MAP_WIDTH, MAP_HEIGHT);
        camera.update(player.getX(), player.getY());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(new Color(34, 139, 34));
        g2.fillRect(0, 0, WIDTH, HEIGHT);

        player.render(g2, camera);
        g2.dispose();
    }
}