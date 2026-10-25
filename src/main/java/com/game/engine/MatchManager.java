package com.game.engine;

import com.game.engine.init.ChoixJoueur;
import com.game.engine.init.FabriqueJoueurs;
import com.game.front.Fond;
import com.game.front.Parametres;
import com.game.front.map.CarteStade;
import com.game.front.match.RenduMatch;
import com.game.models.Ball;
import com.game.models.Equipe;
import com.game.models.Goal;
import com.game.models.Joueur;
import com.game.models.Joueur.EtatJoueur;
import com.game.models.Murs;
import com.game.models.input.ControleurHumain;
import com.game.models.personnage.Personnage;
import com.game.models.physics.Hitbox;
import com.game.models.physics.Vector2D;
import java.util.ArrayList;
import java.util.List;
import javafx.animation.AnimationTimer;
import javafx.animation.PauseTransition;
import javafx.scene.canvas.GraphicsContext;
import javafx.util.Duration;

public class MatchManager extends AnimationTimer {

    public enum Phase { COMPTE_A_REBOURS, EN_JEU, BUT, FINI }

    public static final double TAILLE_JOUEUR = FabriqueJoueurs.TAILLE_HITBOX;
    public static final double TAILLE_BALLE = 22;

    private static final double PAS = 1.0 / 240;
    private static final double DUREE_COMPTE = 3;
    private static final double DUREE_BUT = 5;

    private final GraphicsContext gc;
    private final Parametres parametres;
    private final CarteStade map;
    private final ChoixJoueur[] choix;
    private final Fond fond;
    private final Goal[] buts;
    private final Murs[] murs;
    private final Vector2D[] departs;
    private final Personnage[] joueurs;
    private final int[] scores = new int[2];
    private final RenduMatch rendu;
    private Ball balle;

    private Phase phase = Phase.COMPTE_A_REBOURS;
    private double chrono = DUREE_COMPTE;
    private double tempsRestant;
    private boolean mortSubite;
    private Equipe dernierButeur;
    private PauseTransition minuteurBut;
    private long dernier;

    public MatchManager(GraphicsContext gc, Parametres parametres, ChoixJoueur[] choix,
                        ControleurHumain[] controleurs, Fond fond) {
        this.gc = gc;
        this.parametres = parametres;
        this.map = parametres.carte().getMap();
        this.choix = choix;
        this.fond = fond;
        this.tempsRestant = parametres.dureeSecondes();
        this.buts = new Goal[]{map.getButGauche(), map.getButDroit()};
        this.murs = rassemblerMurs();
        this.departs = calculerDeparts();
        this.joueurs = new FabriqueJoueurs().creerDuo(choix, controleurs, departs);
        this.rendu = new RenduMatch(fond, this);
        remiseEnPlace();
    }


     /** 
     * @param creer but
     * @return Murs[] pour creer Goal  */
    private Murs[] rassemblerMurs() {
        List<Murs> liste = new ArrayList<>(List.of(map.getMurs()));
        for (Goal but : buts) liste.addAll(List.of(but.getMurs()));
        return liste.toArray(new Murs[0]);
    }

    private Vector2D[] calculerDeparts() {
        return new Vector2D[]{
                FabriqueJoueurs.coinDepart(map.getPositionJoueur1()),
                FabriqueJoueurs.coinDepart(map.getPositionJoueur2())
        };
    }

    private void placerBalle() {
        Vector2D coin = map.getPositionBalle().sub(new Vector2D(TAILLE_BALLE / 2, TAILLE_BALLE / 2));
        balle = new Ball(coin, new Hitbox(coin, TAILLE_BALLE, TAILLE_BALLE));
    }

    private void remiseEnPlace() {
        reinitialiserJoueurs();
        placerBalle();
        phase = Phase.COMPTE_A_REBOURS;
        chrono = DUREE_COMPTE;
    }

    private void reinitialiserJoueurs() {
        for (int i = 0; i < joueurs.length; i++) reinitialiserJoueur(i);
    }

    private void reinitialiserJoueur(int numero) {
        Personnage joueur = joueurs[numero];
        joueur.reset(departs[numero]);
        joueur.setIsProprietaire(false);
        joueur.setDernierOrientation(FabriqueJoueurs.regardDepart(numero));
    }


    @Override
    /** @param boucle lancer jeu  */
    public void handle(long now) {
        double dt = dernier == 0 ? 0 : (now - dernier) / 1_000_000_000.0;
        dernier = now;
        fond.update(Math.min(dt, 0.05));
        update(dt);
        rendu.mettreAJour(dt);
        rendu.dessiner(gc, dt);
    }

    @Override
    public void stop() {
        annulerMinuteurBut();
        super.stop();
    }

    public void update(double dt) {
        dt = Math.min(dt, 0.05);
        switch (phase) {
            case COMPTE_A_REBOURS -> compterARebours(dt);
            case EN_JEU -> jouer(dt);
            case BUT -> chrono -= dt;
            case FINI -> chrono += dt;
        }
    }

    private void compterARebours(double dt) {
        chrono -= dt;
        if (chrono <= 0) phase = Phase.EN_JEU;
    }

    private void jouer(double dt) {
        double reste = dt;
        while (reste > 1e-9 && phase == Phase.EN_JEU) {
            double h = Math.min(PAS, reste);
            avancerPhysique(h);
            verifierBut();
            reste -= h;
        }
        if (phase == Phase.EN_JEU) decompterTemps(dt);
    }

    private void avancerPhysique(double h) {
        for (Joueur joueur : joueurs) joueur.update(h, murs);
        balle.update(h, murs, joueurs);
    }

    private void decompterTemps(double dt) {
        if (mortSubite) return;
        tempsRestant -= dt;
        if (tempsRestant <= 0) {
            tempsRestant = 0;
            finDuTemps();
        }
    }

    private void finDuTemps() {
        if (scores[0] == scores[1] && parametres.mortSubite()) mortSubite = true;
        else terminer();
    }

    private void verifierBut() {
        for (Goal but : buts) {
            if (!but.estBut(balle)) continue;
            buts(adverse(but.getEquipe()));
            return;
        }
    }

    private void buts(Equipe buteur) {
        marquer(buteur);
        if (matchGagne(buteur)) {
            terminer();
            return;
        }
        phase = Phase.BUT;
        chrono = DUREE_BUT;
        celebrer(joueurDe(buteur));
        couperControleur(joueurDe(adverse(buteur)));
        armerMinuteurBut();
    }

    private void marquer(Equipe buteur) {
        scores[buteur.ordinal()]++;
        dernierButeur = buteur;
    }

    private boolean matchGagne(Equipe buteur) {
        boolean limite = parametres.scoreMax() > 0 && scores[buteur.ordinal()] >= parametres.scoreMax();
        return limite || mortSubite;
    }

    private void celebrer(Personnage joueur) {
        joueur.setEtatJoueur(EtatJoueur.Celebration);
    }

    private void couperControleur(Personnage joueur) {
        joueur.getControleurs().setActif(false);
        joueur.setEtatJoueur(EtatJoueur.IDLE);
    }

    private void armerMinuteurBut() {
        annulerMinuteurBut();
        minuteurBut = new PauseTransition(Duration.seconds(DUREE_BUT));
        minuteurBut.setOnFinished(e -> remiseEnPlace());
        minuteurBut.play();
    }

    private void annulerMinuteurBut() {
        if (minuteurBut != null) minuteurBut.stop();
        minuteurBut = null;
    }

    private void terminer() {
        phase = Phase.FINI;
        chrono = 0;
        Equipe vainqueur = getVainqueur();
        if (vainqueur == null) return;
        celebrer(joueurDe(vainqueur));
        couperControleur(joueurDe(adverse(vainqueur)));
    }

    private Personnage joueurDe(Equipe equipe) {
        return joueurs[equipe.ordinal()];
    }

    private Equipe adverse(Equipe equipe) {
        return equipe == Equipe.Gauche ? Equipe.Droite : Equipe.Gauche;
    }

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
    public Personnage[] getPersonnages() { return joueurs; }
    public ChoixJoueur[] getChoix() { return choix; }
    public Ball getBalle() { return balle; }
    public Parametres getParametres() { return parametres; }
}
