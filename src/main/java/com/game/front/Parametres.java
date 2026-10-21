package com.game.front;

import com.game.front.map.Carte;

/**
 * Réglages d'une partie, choisis dans le menu.
 *
 * @param dureeSecondes durée du match
 * @param scoreMax      buts pour gagner (0 = pas de limite)
 * @param carte         la carte jouée
 * @param mortSubite    si égalité à la fin du temps : le prochain but gagne
 */
public record Parametres(int dureeSecondes, int scoreMax, Carte carte, boolean mortSubite) {
    public Parametres {
        if (dureeSecondes <= 0) throw new IllegalArgumentException("durée <= 0");
        if (scoreMax < 0) throw new IllegalArgumentException("scoreMax < 0");
        if (carte == null) throw new IllegalArgumentException("carte null");
    }
}
