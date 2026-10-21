package com.game.front;

import com.game.engine.MatchManager;
import com.game.front.map.Carte;
import com.game.models.Ball;
import com.game.models.Equipe;
import com.game.models.Joueur;
import com.game.models.physics.Vector2D;
import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

public class VueMatch {
    private static final Color BLEU = Color.DODGERBLUE;
    private static final Color ROUGE = Color.TOMATO;

    private final Fond fond;
    private final SpriteJoueur[] sprites = {new SpriteJoueur(0), new SpriteJoueur(1)};
    private MatchManager.Phase phase = MatchManager.Phase.COMPTE_A_REBOURS;

    public VueMatch(Fond fond) {
        this.fond = fond;
    }

    public void dessiner(GraphicsContext gc, MatchManager match) {
        gc.setTextBaseline(VPos.CENTER);
        gc.setFill(Color.rgb(17, 24, 39));
        gc.fillRect(0, 0, Carte.LARGEUR_ECRAN, Carte.HAUTEUR_ECRAN);
        confettis(match);

        gc.save();
        placerTerrain(gc, match);
        gc.drawImage(Images.charger("/assets/cartes/" + match.getParametres().carte().name().toLowerCase() + ".png"), 0, 0);
        for (Joueur j : match.getJoueurs()) dessinerJoueur(gc, j, match);
        dessinerBalle(gc, match.getBalle());
        gc.restore();
        dessinerScore(gc, match);
        dessinerMessage(gc, match);
        fond.dessinerConfettis(gc);
    }

    private void placerTerrain(GraphicsContext gc, MatchManager match) {
        double largeur = match.getParametres().carte().getMap().getLargeur();
        double hauteur = match.getParametres().carte().getMap().getHauteur();
        double echelle = Math.min(Carte.LARGEUR_ECRAN / largeur, Carte.HAUTEUR_ECRAN / hauteur);
        gc.translate((Carte.LARGEUR_ECRAN - largeur * echelle) / 2, (Carte.HAUTEUR_ECRAN - hauteur * echelle) / 2);
        gc.scale(echelle, echelle);
    }

    private void confettis(MatchManager match) {
        if (match.getPhase() == phase) return;
        phase = match.getPhase();
        Equipe gagnant = phase == MatchManager.Phase.BUT ? match.getDernierButeur() : phase == MatchManager.Phase.FINI ? match.getVainqueur() : null;
        if (gagnant != null) fond.lancerConfettis(gagnant.ordinal());
    }

    private void dessinerJoueur(GraphicsContext gc, Joueur j, MatchManager match) {
        boolean fete = (match.getPhase() == MatchManager.Phase.BUT && match.getDernierButeur() == equipeDe(j, match))
                || (match.getPhase() == MatchManager.Phase.FINI && match.getVainqueur() == equipeDe(j, match));
        sprites[equipeDe(j, match).ordinal()].dessiner(gc, j, fete, fond.getTemps());
    }

    private Equipe equipeDe(Joueur j, MatchManager match) {
        return j == match.getJoueurs()[0] ? Equipe.Gauche : Equipe.Droite;
    }

    private void dessinerBalle(GraphicsContext gc, Ball b) {
        Vector2D c = b.getHitbox().getCentre();
        gc.save();
        gc.translate(c.getX(), c.getY());
        gc.rotate((c.getX() + c.getY()) * 5);
        gc.drawImage(Images.charger("/assets/stade/ballon.png"), -MatchManager.TAILLE_BALLE / 2, -MatchManager.TAILLE_BALLE / 2);
        gc.restore();
    }

    private void dessinerScore(GraphicsContext gc, MatchManager match) {
        double w = Carte.LARGEUR_ECRAN;
        gc.setFont(Font.font("Arial", FontWeight.BOLD, 34));
        gc.setTextAlign(TextAlignment.LEFT);
        gc.setFill(BLEU);
        gc.fillText("J1   " + match.getScore(Equipe.Gauche), 40, 30);
        gc.setTextAlign(TextAlignment.RIGHT);
        gc.setFill(ROUGE);
        gc.fillText(match.getScore(Equipe.Droite) + "   J2", w - 40, 30);
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setFill(match.isMortSubite() ? Color.GOLD : Color.WHITE);
        int s = (int) Math.ceil(match.getTempsRestant());
        gc.fillText(match.isMortSubite() ? "MORT SUBITE" : String.format("%d:%02d", s / 60, s % 60), w / 2, 30);
    }

    private void dessinerMessage(GraphicsContext gc, MatchManager match) {
        String titre = null;
        String sousTitre = null;
        switch (match.getPhase()) {
            case COMPTE_A_REBOURS -> titre = String.valueOf((int) Math.ceil(match.getChrono()));
            case BUT -> titre = "BUT !  " + (match.getDernierButeur() == Equipe.Gauche ? "J1" : "J2");
            case FINI -> {
                Equipe v = match.getVainqueur();
                titre = v == null ? "Égalité" : (v == Equipe.Gauche ? "J1" : "J2") + " gagne !";
                sousTitre = "Entrée : retour au menu";
            }
            case EN_JEU -> { }
        }
        if (titre == null) return;

        double w = Carte.LARGEUR_ECRAN, y = Carte.Y_CENTRE;
        gc.drawImage(Images.charger("/assets/stade/panneau.png"), w / 2 - 260, y - 70, 520, sousTitre == null ? 140 : 180);
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font("Arial", FontWeight.BOLD, 72));
        gc.fillText(titre, w / 2, y);
        if (sousTitre != null && match.getChrono() > 1) {
            gc.setFont(Font.font("Arial", FontWeight.NORMAL, 24));
            gc.fillText(sousTitre, w / 2, y + 70);
        }
    }
}
