package com.game.front.match;

import com.game.engine.MatchManager;
import com.game.front.map.Carte;
import com.game.front.ui.Palette;
import com.game.front.ui.Ressources;
import com.game.front.ui.Texte;
import com.game.models.Equipe;
import javafx.scene.canvas.GraphicsContext;

/** Le panneau au centre de l'écran : compte à rebours, BUT, fin de match. */
public class MessageMatch {

    public void dessiner(GraphicsContext gc, MatchManager match) {
        String titre = titre(match);
        if (titre == null) return;
        String sousTitre = sousTitre(match);
        dessinerPanneau(gc, sousTitre != null);
        Texte.centrer(gc, titre, Carte.LARGEUR_ECRAN / 2, Carte.Y_CENTRE, Palette.gras(72), Palette.TEXTE);
        if (sousTitre != null) {
            Texte.centrer(gc, sousTitre, Carte.LARGEUR_ECRAN / 2, Carte.Y_CENTRE + 70, Palette.normal(24), Palette.TEXTE);
        }
    }

    private void dessinerPanneau(GraphicsContext gc, boolean avecSousTitre) {
        gc.drawImage(Ressources.image("/assets/stade/panneau.png"), Carte.LARGEUR_ECRAN / 2 - 260,
                Carte.Y_CENTRE - 70, 520, avecSousTitre ? 180 : 140);
    }

    private String titre(MatchManager match) {
        return switch (match.getPhase()) {
            case COMPTE_A_REBOURS -> String.valueOf((int) Math.ceil(match.getChrono()));
            case BUT -> "BUT !  " + nom(match.getDernierButeur());
            case FINI -> match.getVainqueur() == null ? "Égalité" : nom(match.getVainqueur()) + " gagne !";
            case EN_JEU -> null;
        };
    }

    private String sousTitre(MatchManager match) {
        boolean retour = match.getPhase() == MatchManager.Phase.FINI && match.getChrono() > 1;
        return retour ? "Entrée : retour au menu" : null;
    }

    private String nom(Equipe equipe) {
        return equipe == Equipe.Gauche ? "J1" : "J2";
    }
}
