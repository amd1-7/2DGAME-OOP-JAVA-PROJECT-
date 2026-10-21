package com.game.front;

import com.game.front.map.Carte;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javafx.scene.canvas.GraphicsContext;

public class Fond {
    private final Random hasard = new Random();
    private final List<double[]> ballons = new ArrayList<>();
    private final List<double[]> confettis = new ArrayList<>();
    private double temps;

    public Fond() {
        for (int i = 0; i < 14; i++) {
            ballons.add(new double[]{hasard.nextDouble() * Carte.LARGEUR_ECRAN, hasard.nextDouble() * Carte.HAUTEUR_ECRAN,
                    hasard.nextDouble() * 40 - 20, -15 - hasard.nextDouble() * 35, 14 + hasard.nextDouble() * 22});
        }
    }

    public double getTemps() { return temps; }

    public void update(double dt) {
        temps += dt;
        for (double[] b : ballons) {
            b[0] += b[2] * dt;
            b[1] += b[3] * dt;
            if (b[1] < -50) {
                b[0] = hasard.nextDouble() * Carte.LARGEUR_ECRAN;
                b[1] = Carte.HAUTEUR_ECRAN + 50;
            }
        }
        for (double[] c : confettis) {
            c[0] += c[2] * dt;
            c[1] += c[3] * dt;
            c[3] += 300 * dt;
        }
        confettis.removeIf(c -> c[1] > Carte.HAUTEUR_ECRAN);
    }

    public void dessinerBallons(GraphicsContext gc) {
        gc.setGlobalAlpha(0.3);
        for (double[] b : ballons) gc.drawImage(Images.charger("/assets/stade/ballon.png"), b[0] - b[4], b[1] - b[4], 2 * b[4], 2 * b[4]);
        gc.setGlobalAlpha(1);
    }

    public void lancerConfettis(int couleur) {
        int[] choix = {couleur, 2, 3};
        for (int i = 0; i < 160; i++) {
            confettis.add(new double[]{hasard.nextDouble() * Carte.LARGEUR_ECRAN, -hasard.nextDouble() * 150,
                    hasard.nextDouble() * 120 - 60, 60 + hasard.nextDouble() * 120, choix[hasard.nextInt(3)]});
        }
    }

    public void dessinerConfettis(GraphicsContext gc) {
        for (double[] c : confettis) gc.drawImage(Images.charger("/assets/stade/confettis.png"), c[4] * 10, 0, 10, 6, c[0], c[1], 10, 6);
    }
}
