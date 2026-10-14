package com.game.front.sprite;

import com.game.models.Apparence;
import com.game.models.Joueur.EtatJoueur;

import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

public final class ApparenceFactory {
    private ApparenceFactory() {}

    public static Apparence create(int kit) {
        Image sheet = PlayerSprites.sheet();
        Apparence apparence = new Apparence(frame(sheet, kit, PlayerState.IDLE));
        apparence.definir(EtatJoueur.COURS, frame(sheet, kit, PlayerState.RUNNING));
        apparence.definir(EtatJoueur.TIRE, frame(sheet, kit, PlayerState.SHOOTING));
        apparence.definir(EtatJoueur.PASSE, frame(sheet, kit, PlayerState.PASSING));
        apparence.definir(EtatJoueur.TACLE, frame(sheet, kit, PlayerState.TACKLING));
        return apparence;
    }

    private static Image frame(Image sheet, int kit, PlayerState state) {
        int row = kit * PlayerState.values().length + state.ordinal();
        int size = PlayerSprites.CELL;
        return new WritableImage(sheet.getPixelReader(), 0, row * size, size, size);
    }
}