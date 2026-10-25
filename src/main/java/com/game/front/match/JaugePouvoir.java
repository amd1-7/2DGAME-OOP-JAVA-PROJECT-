package com.game.front.match;

import com.game.models.powers.Pouvoir;

/**
 * Suit l'état d'un pouvoir pour l'afficher : en charge (rouge), actif (orange) ou prêt (vert).
 * Le pouvoir reste maître : la jauge se cale sur son booléen « dispo ».
 */
public class JaugePouvoir {

    public enum Phase { CHARGE, ACTIF, PRET }

    /** Délai avant la toute première disponibilité (celui du constructeur de Pouvoir). */
    private static final double DELAI_INITIAL = 20;

    private final Pouvoir pouvoir;
    private Phase phase = Phase.CHARGE;
    private double duree = DELAI_INITIAL;
    private double ecoule;
    private boolean etaitDispo;

    public JaugePouvoir(Pouvoir pouvoir) {
        this.pouvoir = pouvoir;
    }

    public void mettreAJour(double dt) {
        boolean dispo = pouvoir.getDispo();
        if (dispo) changerPhase(Phase.PRET, 0);
        else if (etaitDispo) changerPhase(Phase.ACTIF, pouvoir.getDuréeActivate().toSeconds());
        else avancer(dt);
        etaitDispo = dispo;
    }

    private void avancer(double dt) {
        ecoule += dt;
        if (phase == Phase.ACTIF && ecoule >= duree) {
            changerPhase(Phase.CHARGE, pouvoir.getDuréeCharge().toSeconds());
        }
    }

    private void changerPhase(Phase nouvelle, double nouvelleDuree) {
        phase = nouvelle;
        duree = nouvelleDuree;
        ecoule = 0;
    }

    private double ratio() {
        return duree <= 0 ? 1 : Math.min(1, ecoule / duree);
    }

    /** @return la part de cercle à remplir, entre 0 et 1 */
    public double progression() {
        return switch (phase) {
            case PRET -> 1;
            case ACTIF -> 1 - ratio();
            case CHARGE -> ratio();
        };
    }

    public double secondesRestantes() {
        return Math.max(0, duree - ecoule);
    }

    public Phase getPhase() {
        return phase;
    }

    public boolean estPret() {
        return phase == Phase.PRET;
    }
}
