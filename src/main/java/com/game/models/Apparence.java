package com.game.models;

import java.util.EnumMap;
import java.util.Map;

import javafx.scene.image.Image;


public class Apparence {
    private final Map<Joueur.EtatJoueur, Image> images = new EnumMap<>(Joueur.EtatJoueur.class);

    public Apparence(Image parDefaut) {
        images.put(Joueur.EtatJoueur.IDLE, parDefaut);
    }

    public void definir(Joueur.EtatJoueur etat, Image image) {
        images.put(etat, image);
    }

    public Image pour(Joueur.EtatJoueur etat) {
        return images.getOrDefault(etat, images.get(Joueur.EtatJoueur.IDLE));
    }
}