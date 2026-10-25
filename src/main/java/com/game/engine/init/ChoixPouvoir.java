package com.game.engine.init;

import com.game.models.input.ControleurHumain;
import com.game.models.personnage.HulkPersonnage;
import com.game.models.personnage.PepePersonnage;
import com.game.models.personnage.Personnage;
import com.game.models.physics.Hitbox;
import com.game.models.physics.Vector2D;
import com.game.models.powers.Pouvoir;
import com.game.view.spritesheet.Annimation;

public enum ChoixPouvoir {
    HULK("Tire Puissant", "Hulk", Pouvoir.Type.ATTAQUE, "Prêt à envoyer des bastos !") {
        @Override
        public Personnage creer(Vector2D coin, Hitbox hitbox, ControleurHumain controleur, Annimation animation) {
            return new HulkPersonnage(coin, new Vector2D(0, 0), new Vector2D(0, 0), hitbox, controleur, animation);
        }
    },
    PEPE("Gel Glacial", "Pepe", Pouvoir.Type.DEFENSE, "Un coup de froid : l'adversaire reste figé !") {
        @Override
        public Personnage creer(Vector2D coin, Hitbox hitbox, ControleurHumain controleur, Annimation animation) {
            return new PepePersonnage(coin, new Vector2D(0, 0), new Vector2D(0, 0), hitbox, animation, controleur);
        }

        @Override
        public void lier(Personnage soi, Personnage adversaire) {
            ((PepePersonnage) soi).setPouvoirJoueur(adversaire);
        }
    };

    private final String nom;
    private final String personnage;
    private final Pouvoir.Type type;
    private final String accroche;

    ChoixPouvoir(String nom, String personnage, Pouvoir.Type type, String accroche) {
        this.nom = nom;
        this.personnage = personnage;
        this.type = type;
        this.accroche = accroche;
    }

    public abstract Personnage creer(Vector2D coin, Hitbox hitbox, ControleurHumain controleur, Annimation animation);

    public void lier(Personnage soi, Personnage adversaire) {
    }

    public String getNom() { return nom; }
    public String getPersonnage() { return personnage; }
    public Pouvoir.Type getType() { return type; }
    public String getAccroche() { return accroche; }
}
