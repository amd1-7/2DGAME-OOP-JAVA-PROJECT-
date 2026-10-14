package com.game.front.view;

import com.game.front.sprite.PlayerSprites;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class HudView {
    private static final double BAR_HEIGHT = 26;
    private static final Color BAR = Color.rgb(0, 0, 0, 0.6);
    private static final Font TITLE_FONT = Font.font("SansSerif", FontWeight.BOLD, 13);
    private static final Font HELP_FONT = Font.font("SansSerif", FontWeight.NORMAL, 12);
    private static final Font BANNER_FONT = Font.font("SansSerif", FontWeight.BOLD, 72);
    private static final String HELP = "ZQSD/flèches bouger | Espace tir | V passe | C tacle | B célébration"
            + " | H collisions | R relancer | 1-4 tenue";

    public void render(GraphicsContext gc, HudInfo info) {
        drawBars(gc);
        drawStatus(gc, info);
        drawHelp(gc);
        if (info.goalBanner()) drawBanner(gc);
    }

    private void drawBars(GraphicsContext gc) {
        gc.setFill(BAR);
        gc.fillRect(0, 0, ViewConfig.WIDTH, BAR_HEIGHT);
        gc.fillRect(0, ViewConfig.HEIGHT - BAR_HEIGHT, ViewConfig.WIDTH, BAR_HEIGHT);
    }

    private void drawStatus(GraphicsContext gc, HudInfo info) {
        gc.setFill(Color.WHITE);
        gc.setFont(TITLE_FONT);
        gc.fillText(statusLine(info), 10, 18);
    }

    private String statusLine(HudInfo info) {
        return "État : " + info.state()
                + "   |   Tenue : " + PlayerSprites.kitName(info.kit())
                + "   |   Balle : " + info.ballState()
                + "   |   Vitesse : " + Math.round(info.speed()) + " px/s";
    }

    private void drawHelp(GraphicsContext gc) {
        gc.setFill(Color.WHITE);
        gc.setFont(HELP_FONT);
        gc.fillText(HELP, 10, ViewConfig.HEIGHT - 9);
    }

    private void drawBanner(GraphicsContext gc) {
        gc.setFont(BANNER_FONT);
        gc.setFill(Color.rgb(255, 215, 0));
        gc.fillText("BUT !", ViewConfig.WIDTH / 2.0 - 110, ViewConfig.HEIGHT / 2.0 - 60);
    }
}
