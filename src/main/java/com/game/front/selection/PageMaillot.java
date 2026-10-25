package com.game.front.selection;

import com.game.engine.init.Maillot;
import com.game.front.ui.Palette;
import com.game.front.ui.Ressources;
import com.game.front.ui.Texte;
import com.game.front.ui.Zone;
import com.game.view.spritesheet.Annimation;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

/** Page « Choisir Maillot » : le personnage porte le maillot et court dans le cadre. */
public class PageMaillot extends PageChoix<Maillot> {
    private static final double TAILLE_PERSO = 150;
    private static final double DUREE_IMAGE = 0.1;

    private final Map<Maillot, Annimation> animations = new EnumMap<>(Maillot.class);

    /** @param interdit le maillot déjà pris par l'autre joueur (null si aucun) */
    public PageMaillot(int joueur, Maillot interdit) {
        super(joueur, "CHOISIR MAILLOT", Maillot.disponibles(interdit));
        chargerAnimations();
    }

    private void chargerAnimations() {
        Image feuille = Ressources.image("/assets/players/joueurs_anim.png");
        Image glacons = Ressources.image("/assets/players/joueur_glacons.png");
        for (Maillot maillot : Maillot.values()) {
            animations.put(maillot, new Annimation(maillot.getCouleur(), feuille, glacons));
        }
    }

    @Override
    protected void dessinerContenu(GraphicsContext gc, Zone zone, Maillot option, double temps) {
        dessinerProjecteur(gc, zone);
        dessinerPersonnage(gc, zone, option, temps);
        dessinerNom(gc, zone, option);
    }

    private void dessinerProjecteur(GraphicsContext gc, Zone zone) {
        gc.setFill(Color.rgb(255, 255, 255, 0.08));
        gc.fillOval(zone.centreX() - 90, zone.y() + 105, 180, 50);
    }

    private void dessinerPersonnage(GraphicsContext gc, Zone zone, Maillot option, double temps) {
        List<Image> images = animations.get(option).getListImageCourse();
        Image image = images.get((int) (temps / DUREE_IMAGE) % images.size());
        gc.drawImage(image, zone.centreX() - TAILLE_PERSO / 2, zone.y() + 12, TAILLE_PERSO, TAILLE_PERSO);
    }

    private void dessinerNom(GraphicsContext gc, Zone zone, Maillot option) {
        Texte.centrer(gc, option.getNom().toUpperCase(), zone.centreX(), zone.y() + zone.h() - 38,
                Palette.gras(40), option.getCouleurUi());
    }
}
