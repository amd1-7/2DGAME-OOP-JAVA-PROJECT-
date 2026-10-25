package com.game.front.selection;

import javafx.scene.canvas.GraphicsContext;

/** Une page du parcours de sélection (pouvoir, maillot...). */
public interface PageSelection {

    void dessiner(GraphicsContext gc, double temps);

    void survoler(double x, double y);

    void cliquer(double x, double y);

    /** Fait défiler le contenu du cadre : -1 vers la gauche, +1 vers la droite. */
    void naviguer(int sens);

    void valider();

    boolean estTerminee();

    boolean estAnnulee();
}
