package com.game.front;

import com.game.front.map.Carte;
import java.util.List;
import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

public class Menu {
    public enum Action { HAUT, BAS, GAUCHE, DROITE, VALIDER }

    private static final int[] DUREES = {60, 120, 180, 300};
    private static final int[] SCORES = {0, 3, 5, 10};
    private static final Carte[] CARTES = Carte.values();
    private static final String[] TITRES = {"Durée du match", "Buts pour gagner", "Carte", "Mort subite si égalité"};
    private static final int[] TAILLES = {DUREES.length, SCORES.length, CARTES.length, 2};

    private final Fond fond;
    private final SpriteJoueur bleu = new SpriteJoueur(0);
    private final SpriteJoueur rouge = new SpriteJoueur(1);
    private final int[] choix = {1, 0, 0, 1};
    private int ligne = 0;

    public Menu(Fond fond) {
        this.fond = fond;
    }

    public boolean traiter(List<Action> actions) {
        for (Action a : actions) {
            switch (a) {
                case HAUT -> ligne = Math.floorMod(ligne - 1, TITRES.length + 1);
                case BAS -> ligne = Math.floorMod(ligne + 1, TITRES.length + 1);
                case GAUCHE -> changer(-1);
                case DROITE -> changer(1);
                case VALIDER -> { return true; }
            }
        }
        return false;
    }

    private void changer(int delta) {
        if (ligne < TITRES.length) choix[ligne] = Math.floorMod(choix[ligne] + delta, TAILLES[ligne]);
    }

    public Parametres parametres() {
        return new Parametres(DUREES[choix[0]], SCORES[choix[1]], CARTES[choix[2]], choix[3] == 1);
    }

    private String valeur(int l) {
        return switch (l) {
            case 0 -> String.format("%d:%02d", DUREES[choix[0]] / 60, DUREES[choix[0]] % 60);
            case 1 -> SCORES[choix[1]] == 0 ? "Illimité" : String.valueOf(SCORES[choix[1]]);
            case 2 -> CARTES[choix[2]].getNom();
            default -> choix[3] == 1 ? "Oui" : "Non";
        };
    }

    public void dessiner(GraphicsContext gc) {
        double w = Carte.LARGEUR_ECRAN, t = fond.getTemps();
        gc.drawImage(Images.charger("/assets/stade/menu.png"), 0, 0);
        fond.dessinerBallons(gc);
        bleu.dessinerCourse(gc, 250, 100, 110, t);
        rouge.dessinerCourse(gc, 950, 100, 110, t);

        gc.setTextBaseline(VPos.CENTER);
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font("Arial", FontWeight.BOLD, 64));
        gc.fillText("QADAR", w / 2, 100 + Math.sin(t * 3) * 6);

        gc.setFont(Font.font("Arial", FontWeight.NORMAL, 26));
        for (int l = 0; l < TITRES.length; l++) {
            double y = 205 + l * 55;
            if (l == ligne) {
                gc.setGlobalAlpha(0.7 + 0.3 * Math.sin(t * 5));
                gc.drawImage(Images.charger("/assets/stade/selection.png"), 260, y - 25);
                gc.setGlobalAlpha(1);
            }
            gc.setFill(l == ligne ? Color.GOLD : Color.WHITE);
            gc.setTextAlign(TextAlignment.LEFT);
            gc.fillText(TITRES[l], 290, y);
            gc.setTextAlign(TextAlignment.CENTER);
            gc.fillText("<   " + valeur(l) + "   >", 850, y);
        }

        gc.setFont(Font.font("Arial", FontWeight.BOLD, 40));
        gc.setFill(ligne == TITRES.length ? Color.GOLD : Color.WHITE);
        gc.fillText("JOUER !", w / 2, 440);

        gc.setFont(Font.font("Arial", FontWeight.NORMAL, 20));
        gc.setFill(Color.WHITE);
        gc.fillText("J1 (bleu) : ZQSD bouger | E tir | R passe | ESPACE tacle", w / 2, 540);
        gc.fillText("J2 (rouge) : Flèches bouger | M tir | L passe | K tacle", w / 2, 575);
        gc.setFill(Color.LIGHTGRAY);
        gc.fillText("Haut/Bas : choisir   Gauche/Droite : modifier   Entrée : jouer   Echap : quitter", w / 2, 640);
    }
}
