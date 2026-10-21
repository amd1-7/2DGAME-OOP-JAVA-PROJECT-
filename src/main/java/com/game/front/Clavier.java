package com.game.front;

import java.util.HashSet;
import java.util.Set;
import javafx.scene.input.KeyCode;

public class Clavier {
    private final Set<KeyCode> enfoncees = new HashSet<>();
    private final Set<KeyCode> pressees = new HashSet<>();

    public void appuyer(KeyCode touche) {
        if (enfoncees.add(touche)) pressees.add(touche);
    }

    public void relacher(KeyCode touche) {
        enfoncees.remove(touche);
    }

    public void toutRelacher() {
        enfoncees.clear();
    }

    public boolean aEtePressee(KeyCode touche) {
        return pressees.contains(touche);
    }

    public void finDeTour() {
        pressees.clear();
    }

    public boolean estEnfoncee(KeyCode touche) {
        return enfoncees.contains(touche);
    }
}
