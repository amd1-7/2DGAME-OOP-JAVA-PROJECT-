package com.game.front.selection;

import com.game.engine.init.ChoixPouvoir;
import com.game.front.ui.Palette;
import com.game.front.ui.Texte;
import com.game.front.ui.Zone;
import com.game.models.powers.Pouvoir;
import java.util.List;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.FontWeight;

/** Page « Choisir Pouvoir » : le nom du pouvoir en gros et sa phrase d'accroche en bas du cadre. */
public class PagePouvoir extends PageChoix<ChoixPouvoir> {

    public PagePouvoir(int joueur) {
        super(joueur, "CHOISIR POUVOIR", List.of(ChoixPouvoir.values()));
    }

    @Override
    protected void dessinerContenu(GraphicsContext gc, Zone zone, ChoixPouvoir option, double temps) {
        dessinerSousTitre(gc, zone, option);
        dessinerNom(gc, zone, option);
        dessinerAccroche(gc, zone, option);
    }

    private void dessinerSousTitre(GraphicsContext gc, Zone zone, ChoixPouvoir option) {
        String texte = "Pouvoir de " + option.getPersonnage() + "  -  " + libelleType(option.getType());
        Texte.centrer(gc, texte, zone.centreX(), zone.y() + 45, Palette.normal(20), Palette.TEXTE_DOUX);
    }

    private void dessinerNom(GraphicsContext gc, Zone zone, ChoixPouvoir option) {
        String nom = option.getNom().toUpperCase();
        var police = Texte.ajuster(FontWeight.BOLD, 62, nom, zone.l() - 60);
        Texte.centrer(gc, nom, zone.centreX(), zone.centreY() - 5, police, Palette.BORDURE);
    }

    private void dessinerAccroche(GraphicsContext gc, Zone zone, ChoixPouvoir option) {
        String accroche = option.getAccroche();
        var police = Texte.ajuster(FontWeight.NORMAL, 26, accroche, zone.l() - 60);
        Texte.centrer(gc, accroche, zone.centreX(), zone.y() + zone.h() - 50, police, Palette.TEXTE);
    }

    private String libelleType(Pouvoir.Type type) {
        return type == Pouvoir.Type.ATTAQUE ? "ATTAQUE" : "DÉFENSE";
    }
}
