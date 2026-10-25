package com.game.front.ui;

/** Rectangle à l'écran : sert à dessiner et à savoir si la souris est dessus. */
public record Zone(double x, double y, double l, double h) {

    public boolean contient(double px, double py) {
        return px >= x && px <= x + l && py >= y && py <= y + h;
    }

    public double centreX() {
        return x + l / 2;
    }

    public double centreY() {
        return y + h / 2;
    }
}
