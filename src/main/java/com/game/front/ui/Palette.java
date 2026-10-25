package com.game.front.ui;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/** Couleurs et polices communes aux pages de sélection et au HUD. */
public final class Palette {
    public static final Color FOND_CADRE = Color.rgb(12, 18, 36, 0.92);
    public static final Color BORDURE = Color.GOLD;
    public static final Color TEXTE = Color.WHITE;
    public static final Color TEXTE_DOUX = Color.rgb(200, 210, 230);
    public static final Color ENCRE = Color.rgb(12, 18, 36);
    public static final Color JOUEUR_1 = Color.DODGERBLUE;
    public static final Color JOUEUR_2 = Color.TOMATO;

    private Palette() { }

    public static Font gras(double taille) {
        return Font.font("Arial", FontWeight.BOLD, taille);
    }

    public static Font normal(double taille) {
        return Font.font("Arial", FontWeight.NORMAL, taille);
    }

    public static Color couleurJoueur(int numero) {
        return numero == 0 ? JOUEUR_1 : JOUEUR_2;
    }
}
