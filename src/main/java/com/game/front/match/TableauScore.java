package com.game.front.match;

import com.game.engine.MatchManager;
import com.game.front.map.Carte;
import com.game.front.ui.Palette;
import com.game.front.ui.Texte;
import com.game.models.Equipe;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/** Score des deux joueurs (aux couleurs de leur maillot) et chrono au centre. */
public class TableauScore {
    private static final double Y = 30;

    public void dessiner(GraphicsContext gc, MatchManager match) {
        Font police = Palette.gras(34);
        double centre = Carte.LARGEUR_ECRAN / 2;
        Color c1 = match.getChoix()[0].maillot().getCouleurUi();
        Color c2 = match.getChoix()[1].maillot().getCouleurUi();
        Texte.droite(gc, "J1   " + match.getScore(Equipe.Gauche), centre - 160, Y, police, c1);
        Texte.gauche(gc, match.getScore(Equipe.Droite) + "   J2", centre + 160, Y, police, c2);
        dessinerChrono(gc, match, centre);
    }

    private void dessinerChrono(GraphicsContext gc, MatchManager match, double centre) {
        int secondes = (int) Math.ceil(match.getTempsRestant());
        String texte = match.isMortSubite() ? "MORT SUBITE" : String.format("%d:%02d", secondes / 60, secondes % 60);
        Font police = match.isMortSubite() ? Palette.gras(26) : Palette.gras(34);
        Texte.centrer(gc, texte, centre, Y, police, match.isMortSubite() ? Color.GOLD : Color.WHITE);
    }
}
