package com.game.front.ui;

import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/** Petits utilitaires pour écrire du texte aligné et mesurer sa largeur. */
public final class Texte {

    private Texte() { }

    public static void centrer(GraphicsContext gc, String texte, double x, double y, Font police, Color couleur) {
        ecrire(gc, texte, x, y, police, couleur, TextAlignment.CENTER);
    }

    public static void gauche(GraphicsContext gc, String texte, double x, double y, Font police, Color couleur) {
        ecrire(gc, texte, x, y, police, couleur, TextAlignment.LEFT);
    }

    public static void droite(GraphicsContext gc, String texte, double x, double y, Font police, Color couleur) {
        ecrire(gc, texte, x, y, police, couleur, TextAlignment.RIGHT);
    }

    private static void ecrire(GraphicsContext gc, String texte, double x, double y, Font police,
                               Color couleur, TextAlignment alignement) {
        gc.setTextBaseline(VPos.CENTER);
        gc.setTextAlign(alignement);
        gc.setFont(police);
        gc.setFill(couleur);
        gc.fillText(texte, x, y);
    }

    public static double largeur(Font police, String texte) {
        Text mesure = new Text(texte);
        mesure.setFont(police);
        return mesure.getLayoutBounds().getWidth();
    }

    /** Réduit la taille de la police jusqu'à ce que le texte tienne dans largeurMax. */
    public static Font ajuster(FontWeight poids, double taille, String texte, double largeurMax) {
        double courante = taille;
        Font police = Font.font("Arial", poids, courante);
        while (courante > 8 && largeur(police, texte) > largeurMax) {
            courante -= 1;
            police = Font.font("Arial", poids, courante);
        }
        return police;
    }
}
