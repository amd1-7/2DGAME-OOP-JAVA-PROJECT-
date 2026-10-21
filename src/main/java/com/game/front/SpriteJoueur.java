package com.game.front;

import com.game.models.Joueur;
import com.game.models.Joueur.EtatJoueur;
import com.game.models.physics.Vector2D;
import javafx.scene.canvas.GraphicsContext;

public class SpriteJoueur {
    private enum Anim {
        REPOS(4, 4, true), MARCHE(8, 10, true), COURSE(6, 14, true), SPRINT(6, 18, true),
        PASSE(6, 14, false), TIR(7, 14, false), SUPER_TIR(12, 12, false), TACLE(7, 12, false), FETE(12, 12, true);

        final int images;
        final double fps;
        final boolean boucle;

        Anim(int images, double fps, boolean boucle) {
            this.images = images;
            this.fps = fps;
            this.boucle = boucle;
        }
    }

    private final int couleur;
    private EtatJoueur dernierEtat = EtatJoueur.IDLE;
    private Anim action;
    private double debutAction;

    public SpriteJoueur(int couleur) {
        this.couleur = couleur;
    }

    public void dessiner(GraphicsContext gc, Joueur joueur, boolean fete, double temps) {
        Anim anim = choisir(joueur, fete, temps);
        Vector2D c = joueur.getHitbox().getCentre();
        double depuis = anim.boucle ? temps : temps - debutAction;
        dessiner(gc, c.getX(), c.getY(), joueur.getDernierOrientation(), anim, depuis, 56);
    }

    public void dessinerCourse(GraphicsContext gc, double x, double y, double taille, double temps) {
        dessiner(gc, x, y, new Vector2D(1, 0), Anim.COURSE, temps, taille);
    }

    private Anim choisir(Joueur joueur, boolean fete, double temps) {
        EtatJoueur etat = joueur.getEtatJoueur();
        if (etat != dernierEtat) {
            Anim nouvelle = switch (etat) {
                case TACLE -> Anim.TACLE;
                case TIRE -> Anim.TIR;
                case PASSE -> Anim.PASSE;
                default -> null;
            };
            if (nouvelle != null) {
                action = nouvelle;
                debutAction = temps;
            }
            dernierEtat = etat;
        }
        if (action != null && temps - debutAction >= action.images / action.fps) action = null;
        if (fete) return Anim.FETE;
        if (action != null) return action;
        return switch (etat) {
            case COURS -> joueur.getVitesse().norm() < 130 ? Anim.MARCHE : Anim.COURSE;
            default -> Anim.REPOS;
        };
    }

    private void dessiner(GraphicsContext gc, double x, double y, Vector2D orientation,
                          Anim anim, double depuis, double taille) {
        int colonne = (int) (depuis * anim.fps);
        colonne = anim.boucle ? colonne % anim.images : Math.min(colonne, anim.images - 1);
        int ligne = couleur * 9 + anim.ordinal();
        gc.save();
        gc.translate(x, y);
        gc.rotate(Math.toDegrees(Math.atan2(orientation.getY(), orientation.getX())));
        gc.drawImage(Images.charger("/assets/players/joueurs_anim.png"), colonne * 32, ligne * 32, 32, 32,
                -taille / 2, -taille / 2, taille, taille);
        gc.restore();
    }
}
