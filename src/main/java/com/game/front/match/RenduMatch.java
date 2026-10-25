package com.game.front.match;

import com.game.engine.MatchManager;
import com.game.front.Fond;
import com.game.front.map.Carte;
import com.game.front.map.CarteStade;
import com.game.front.ui.Ressources;
import com.game.models.Ball;
import com.game.models.Equipe;
import com.game.models.Joueur;
import com.game.models.physics.Vector2D;
import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/** Dessine un match : terrain, joueurs (via Joueur.render), balle, HUD, score, messages et confettis. */
public class RenduMatch {
    /** Taille sur laquelle Joueur.render dessine son sprite. */
    private static final double TAILLE_SPRITE = 32;

    private final Fond fond;
    private final MatchManager match;
    private final HudMatch hud;
    private final TableauScore score = new TableauScore();
    private final MessageMatch message = new MessageMatch();
    private MatchManager.Phase phase = MatchManager.Phase.COMPTE_A_REBOURS;

    public RenduMatch(Fond fond, MatchManager match) {
        this.fond = fond;
        this.match = match;
        this.hud = new HudMatch(match);
    }

    public void mettreAJour(double dt) {
        hud.mettreAJour(dt);
        declencherConfettis();
    }

    public void dessiner(GraphicsContext gc, double dt) {
        gc.setTextBaseline(VPos.CENTER);
        dessinerFondEcran(gc);
        dessinerTerrain(gc, dt);
        hud.dessiner(gc);
        score.dessiner(gc, match);
        message.dessiner(gc, match);
        fond.dessinerConfettis(gc);
    }

    private void dessinerFondEcran(GraphicsContext gc) {
        gc.setFill(Color.rgb(17, 24, 39));
        gc.fillRect(0, 0, Carte.LARGEUR_ECRAN, Carte.HAUTEUR_ECRAN);
    }

    private void dessinerTerrain(GraphicsContext gc, double dt) {
        gc.save();
        placerTerrain(gc);
        String carte = match.getParametres().carte().name().toLowerCase();
        gc.drawImage(Ressources.image("/assets/cartes/" + carte + ".png"), 0, 0);
        for (Joueur joueur : match.getJoueurs()) dessinerJoueur(gc, joueur, dt);
        dessinerBalle(gc, match.getBalle());
        gc.restore();
    }

    private void placerTerrain(GraphicsContext gc) {
        CarteStade map = match.getParametres().carte().getMap();
        double echelle = Math.min(Carte.LARGEUR_ECRAN / map.getLargeur(), Carte.HAUTEUR_ECRAN / map.getHauteur());
        gc.translate((Carte.LARGEUR_ECRAN - map.getLargeur() * echelle) / 2,
                (Carte.HAUTEUR_ECRAN - map.getHauteur() * echelle) / 2);
        gc.scale(echelle, echelle);
    }

    /** Le sprite est centré dans la hitbox, puis dessiné par le render du joueur. */
    private void dessinerJoueur(GraphicsContext gc, Joueur joueur, double dt) {
        double decalage = (joueur.getHitbox().getWidth() - TAILLE_SPRITE) / 2;
        gc.save();
        gc.translate(decalage, decalage);
        joueur.render(gc, dt);
        gc.restore();
    }

    private void dessinerBalle(GraphicsContext gc, Ball balle) {
        Vector2D centre = balle.getHitbox().getCentre();
        gc.save();
        gc.translate(centre.getX(), centre.getY());
        gc.rotate((centre.getX() + centre.getY()) * 5);
        gc.drawImage(Ressources.image("/assets/stade/ballon.png"),
                -MatchManager.TAILLE_BALLE / 2, -MatchManager.TAILLE_BALLE / 2);
        gc.restore();
    }

    /** Lance des confettis aux couleurs du maillot du buteur (ou du vainqueur) à chaque changement de phase. */
    private void declencherConfettis() {
        if (match.getPhase() == phase) return;
        phase = match.getPhase();
        Equipe gagnant = switch (phase) {
            case BUT -> match.getDernierButeur();
            case FINI -> match.getVainqueur();
            default -> null;
        };
        if (gagnant != null) fond.lancerConfettis(match.getChoix()[gagnant.ordinal()].maillot().ordinal());
    }
}
