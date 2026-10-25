package com.game.engine.init;

import com.game.view.spritesheet.DefAnimation.CouleuraAnnimation;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.paint.Color;

public enum Maillot {
    BLEU("Bleu", Color.DODGERBLUE, CouleuraAnnimation.BLEU),
    ROUGE("Rouge", Color.TOMATO, CouleuraAnnimation.ROUGE),
    JAUNE("Jaune", Color.GOLD, CouleuraAnnimation.JAUNE),
    CYAN("Cyan", Color.CYAN, CouleuraAnnimation.CYAN);

    private final String nom;
    private final Color couleurUi;
    private final CouleuraAnnimation couleur;

    Maillot(String nom, Color couleurUi, CouleuraAnnimation couleur) {
        this.nom = nom;
        this.couleurUi = couleurUi;
        this.couleur = couleur;
    }

    public String getNom() { return nom; }
    public Color getCouleurUi() { return couleurUi; }
    public CouleuraAnnimation getCouleur() { return couleur; }

    /** Tous les maillots sauf celui déjà pris (interdit peut être null). */
    public static List<Maillot> disponibles(Maillot interdit) {
        List<Maillot> liste = new ArrayList<>();
        for (Maillot maillot : values()) {
            if (maillot != interdit) liste.add(maillot);
        }
        return liste;
    }
}
