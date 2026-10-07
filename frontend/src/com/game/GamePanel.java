package com.game;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

public class GamePanel extends JPanel implements Runnable {
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    public static final int MAP_WIDTH = 1600;
    public static final int MAP_HEIGHT = 1200;

    private static final boolean SHOW_TEST_HUD = true;

    private Thread gameThread;
    private boolean isRunning = false;

    private Player player;
    private Camera camera;
    private Field field;
    // touche H : affiche collisions et zones
    private boolean debugView = false;

    // volatile : écrit par le thread clavier, lu par le thread du jeu
    private volatile boolean up, down, left, right;
    private volatile boolean sprint;

    // touches enfoncées : évite que la répétition clavier relance une action
    private final Set<Integer> heldKeys = new HashSet<>();

    public GamePanel() {
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);

        // pour une autre tenue au départ : new Player(800, 600, PlayerSprites.KIT_RED)
        this.player = new Player(800, 600);
        this.camera = new Camera(WIDTH, HEIGHT, MAP_WIDTH, MAP_HEIGHT);
        this.field = new Field();
        this.player.setWorld(field);

        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int code = e.getKeyCode();

                if (code == KeyEvent.VK_Z || code == KeyEvent.VK_UP) up = true;
                if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) down = true;
                if (code == KeyEvent.VK_Q || code == KeyEvent.VK_LEFT) left = true;
                if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) right = true;
                if (code == KeyEvent.VK_SHIFT) sprint = true;

                if (heldKeys.add(code)) testActionKey(code);

                int kitKey = kitFromKey(e);
                if (kitKey >= 0 && heldKeys.add(e.getExtendedKeyCode() + 100000)) player.setKit(kitKey);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                int code = e.getKeyCode();
                if (code == KeyEvent.VK_Z || code == KeyEvent.VK_UP) up = false;
                if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) down = false;
                if (code == KeyEvent.VK_Q || code == KeyEvent.VK_LEFT) left = false;
                if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) right = false;
                if (code == KeyEvent.VK_SHIFT) sprint = false;
                heldKeys.remove(code);
                heldKeys.remove(e.getExtendedKeyCode() + 100000);
            }
        });
    }

    // renvoie la tenue (0 à 3) demandée par la touche, ou -1
    // touches acceptées : 1-4 (QWERTY), & é " ' (AZERTY), pavé numérique, F1-F4
    private static int kitFromKey(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_NUMPAD1, KeyEvent.VK_F1 -> { return PlayerSprites.KIT_BLUE; }
            case KeyEvent.VK_NUMPAD2, KeyEvent.VK_F2 -> { return PlayerSprites.KIT_RED; }
            case KeyEvent.VK_NUMPAD3, KeyEvent.VK_F3 -> { return PlayerSprites.KIT_GOALKEEPER_YELLOW; }
            case KeyEvent.VK_NUMPAD4, KeyEvent.VK_F4 -> { return PlayerSprites.KIT_GOALKEEPER_TURQUOISE; }
            default -> { }
        }
        switch (e.getKeyChar()) {
            case '1', '&' -> { return PlayerSprites.KIT_BLUE; }
            case '2', '\u00e9' -> { return PlayerSprites.KIT_RED; }
            case '3', '"' -> { return PlayerSprites.KIT_GOALKEEPER_YELLOW; }
            case '4', '\'' -> { return PlayerSprites.KIT_GOALKEEPER_TURQUOISE; }
            default -> { return -1; }
        }
    }

    // touches de TEST des animations : à supprimer quand le back appellera player.play(...)
    private void testActionKey(int code) {
        switch (code) {
            case KeyEvent.VK_SPACE -> player.play(PlayerState.SHOOTING);
            case KeyEvent.VK_V -> player.play(PlayerState.PASSING);
            case KeyEvent.VK_X -> player.play(PlayerState.SUPER_SHOOTING);
            case KeyEvent.VK_C -> player.play(PlayerState.TACKLING);
            case KeyEvent.VK_H -> debugView = !debugView;
            case KeyEvent.VK_B -> {
                if (player.getState() == PlayerState.CELEBRATING) player.stopAction();
                else player.play(PlayerState.CELEBRATING);
            }

            default -> { }
        }
    }

    public void startGame() {
        isRunning = true;
        gameThread = new Thread(this);
        gameThread.start();
        this.requestFocusInWindow();
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
        player.setSprinting(sprint);
        player.update(deltaTime, up, down, left, right, MAP_WIDTH, MAP_HEIGHT);
        camera.update(player.getX(), player.getY());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        field.render(g2, camera);
        if (debugView) field.renderDebug(g2, camera);

        player.render(g2, camera);
        if (SHOW_TEST_HUD) drawTestHud(g2);
        g2.dispose();
    }

    private void drawTestHud(Graphics2D g2) {
        g2.setFont(new Font("SansSerif", Font.BOLD, 13));
        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRect(0, 0, WIDTH, 26);
        g2.fillRect(0, HEIGHT - 26, WIDTH, 26);
        g2.setColor(Color.WHITE);
        Hitbox hb = player.getHitbox();
        Zone zone = field.getZoneAt(hb.getX() + hb.getWidth() / 2, hb.getY() + hb.getHeight() / 2);
        g2.drawString("État : " + player.getState() + "   |   Tenue : " + PlayerSprites.kitName(player.getKit())
                + "   |   Zone : " + zone, 10, 18);
        g2.setFont(new Font("SansSerif", Font.PLAIN, 12));
        g2.drawString("ZQSD/flèches bouger | Shift accélérer | Espace tir | V passe | X super-tir | C tacle | B célébration | H collisions | 1-4 (&é\"') tenue",
                10, HEIGHT - 9);
    }
}
