package com.game.front.map;

public enum Carte {
    CLASSIQUE("Classique", new MapClassique()),
    PILIERS("Piliers", new MapPiliers()),
    BARRES("Barres", new MapBarres()),
    JEUFOOT("Jeu Foot", new MapJeuFOOT());

    /** Taille de la fenêtre. */
    public static final double LARGEUR_ECRAN = 1200, HAUTEUR_ECRAN = 700;
    /** Hauteur du centre de l'écran */
    public static final double Y_CENTRE = 380;

    private final String nom;
    private final CarteStade map;

    Carte(String nom, CarteStade map) {
        this.nom = nom;
        this.map = map;
    }

    public String getNom() { return nom; }

    public CarteStade getMap() { return map; }
}
