package com.game.engine;

import com.game.front.Fond;
import com.game.front.Parametres;
import com.game.front.VueMatch;
import com.game.front.map.CarteStade;
import com.game.models.Ball;
import com.game.models.Equipe;
import com.game.models.Goal;
import com.game.models.Joueur;
import com.game.models.Murs;
import com.game.models.input.ControleurHumain;
import com.game.models.physics.Hitbox;
import com.game.models.physics.Vector2D;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;

/** Le match : construit la map, fait tourner les objets du back, compte les buts et le temps, puis demande l'affichage. */
public class MatchManager extends AnimationTimer {

    /** Les grandes étapes d'un match. */
    public enum Phase { COMPTE_A_REBOURS, EN_JEU, BUT, FINI }

    public static final double TAILLE_JOUEUR = 36;
    public static final double TAILLE_BALLE = 22;

    private static final double PAS = 1.0 / 240;
    private static final double DUREE_COMPTE = 3;
    private static final double DUREE_BUT = 2;
    private static final double PUISSANCE_TIR = 10;

    private final GraphicsContext gc;
    private final Parametres parametres;
    private final CarteStade map;
    private final ControleurHumain[] controleurs;
    private final KeyCode[] touchesDepart;
    private final Predicate<KeyCode> estEnfoncee;
    private final Fond fond;
    private final VueMatch vue;
    private final Goal[] buts;
    private final Murs[] murs;
    private final Joueur[] joueurs = new Joueur[2];
    private final int[] scores = new int[2];
    private Ball balle;

    private Phase phase = Phase.COMPTE_A_REBOURS;
    private double chrono = DUREE_COMPTE;
    private double tempsRestant;
    private boolean mortSubite;
    private Equipe dernierButeur;
    private long dernier;

    /**
     * @param touchesDepart une touche de déplacement de chaque joueur, pour lui donner son orientation de départ
     * @param estEnfoncee   dit si une touche est tenue en ce moment (pour ne pas la perdre à la remise en jeu)
     */
    public MatchManager(GraphicsContext gc, Parametres parametres, ControleurHumain[] controleurs,
                        KeyCode[] touchesDepart, Predicate<KeyCode> estEnfoncee, Fond fond) {
        this.gc = gc;
        this.parametres = parametres;
        this.map = parametres.carte().getMap();
        this.controleurs = controleurs;
        this.touchesDepart = touchesDepart;
        this.estEnfoncee = estEnfoncee;
        this.fond = fond;
        this.tempsRestant = parametres.dureeSecondes();
        this.buts = new Goal[]{map.getButGauche(), map.getButDroit()};
        this.murs = rassemblerMurs();
        this.vue = new VueMatch(fond);
        engagement();
    }

    private Murs[] rassemblerMurs() {
        List<Murs> liste = new ArrayList<>(List.of(map.getMurs()));
        for (Goal but : buts) liste.addAll(List.of(but.getMurs()));
        return liste.toArray(new Murs[0]);
    }

    /** Remet joueurs et balle à leur place de départ (le back n'a pas de setter de position : on recrée les objets). */
    private void engagement() {
        joueurs[0] = creerJoueur(0, map.getPositionJoueur1());
        joueurs[1] = creerJoueur(1, map.getPositionJoueur2());
        Vector2D coin = map.getPositionBalle().sub(new Vector2D(TAILLE_BALLE / 2, TAILLE_BALLE / 2));
        balle = new Ball(coin, new Hitbox(coin, TAILLE_BALLE, TAILLE_BALLE));
    }

    private Joueur creerJoueur(int numero, Vector2D centre) {
        Vector2D coin = centre.sub(new Vector2D(TAILLE_JOUEUR / 2, TAILLE_JOUEUR / 2));
        Hitbox hitbox = new Hitbox(coin, TAILLE_JOUEUR, TAILLE_JOUEUR);
        Joueur joueur = new Joueur(coin, new Vector2D(0, 0), new Vector2D(0, 0), hitbox,
                controleurs[numero], null, PUISSANCE_TIR, null);
        donnerUneOrientation(numero, joueur);
        return joueur;
    }

    /**
     * Le back laisse l'orientation à null tant que le joueur n'a pas bougé : on simule une seule touche
     * (les autres sont relâchées un instant), puis on remet les touches réellement tenues.
     */
    private void donnerUneOrientation(int numero, Joueur joueur) {
        ControleurHumain controleur = controleurs[numero];
        for (KeyCode touche : KeyCode.values()) controleur.toucheRelachee(touche);
        controleur.toucheAppuyee(touchesDepart[numero]);
        joueur.update(0, murs);
        controleur.toucheRelachee(touchesDepart[numero]);
        for (KeyCode touche : KeyCode.values()) {
            if (estEnfoncee.test(touche)) controleur.toucheAppuyee(touche);
        }
    }

    @Override
    public void handle(long now) {
        double dt = dernier == 0 ? 0 : (now - dernier) / 1_000_000_000.0;
        dernier = now;
        fond.update(Math.min(dt, 0.05));
        update(dt);
        vue.dessiner(gc, this);
    }

    /** Fait avancer le match de dt secondes. */
    public void update(double dt) {
        dt = Math.min(dt, 0.05);
        switch (phase) {
            case COMPTE_A_REBOURS -> {
                chrono -= dt;
                if (chrono <= 0) phase = Phase.EN_JEU;
            }
            case EN_JEU -> jouer(dt);
            case BUT -> {
                chrono -= dt;
                if (chrono <= 0) {
                    engagement();
                    phase = Phase.COMPTE_A_REBOURS;
                    chrono = DUREE_COMPTE;
                }
            }
            case FINI -> chrono += dt;
        }
    }

    private void jouer(double dt) {
        double reste = dt;
        while (reste > 1e-9 && phase == Phase.EN_JEU) {
            double h = Math.min(PAS, reste);
            for (Joueur j : joueurs) j.update(h, murs);
            balle.update(h, murs, joueurs);
            verifierBut();
            reste -= h;
        }
        if (phase == Phase.EN_JEU && !mortSubite) {
            tempsRestant -= dt;
            if (tempsRestant <= 0) {
                tempsRestant = 0;
                finDuTemps();
            }
        }
    }

    private void verifierBut() {
        for (Goal but : buts) {
            if (!but.estBut(balle)) continue;
            Equipe buteur = but.getEquipe() == Equipe.Gauche ? Equipe.Droite : Equipe.Gauche;
            scores[buteur.ordinal()]++;
            dernierButeur = buteur;
            boolean limite = parametres.scoreMax() > 0 && scores[buteur.ordinal()] >= parametres.scoreMax();
            if (limite || mortSubite) terminer();
            else {
                phase = Phase.BUT;
                chrono = DUREE_BUT;
            }
            return;
        }
    }

    private void finDuTemps() {
        if (scores[0] == scores[1] && parametres.mortSubite()) mortSubite = true;
        else terminer();
    }

    private void terminer() {
        phase = Phase.FINI;
        chrono = 0;
    }

    /** @return l'équipe gagnante, ou null s'il y a égalité */
    public Equipe getVainqueur() {
        if (scores[0] == scores[1]) return null;
        return scores[0] > scores[1] ? Equipe.Gauche : Equipe.Droite;
    }

    public Phase getPhase() { return phase; }
    public double getChrono() { return chrono; }
    public double getTempsRestant() { return tempsRestant; }
    public boolean isMortSubite() { return mortSubite; }
    public Equipe getDernierButeur() { return dernierButeur; }
    public int getScore(Equipe e) { return scores[e.ordinal()]; }
    public Joueur[] getJoueurs() { return joueurs; }
    public Ball getBalle() { return balle; }
    public Parametres getParametres() { return parametres; }
}