package com.game.engine;

import com.game.models.Ball;
import com.game.models.Joueur;
import com.game.models.Murs;
import com.game.models.physics.Hitbox;
import com.game.models.physics.Vector2D;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;

public class MatchManager extends AnimationTimer {
    private long lastUpdate;
    private GraphicsContext gc;
    private Joueur joueur;
    private Joueur[] joueurs;
    private Murs murs = new Murs(new Vector2D(200, 200), new Hitbox(new Vector2D(200, 200), 50, 200000));
    private Murs[] m = new Murs[]{
        murs
    };
    private Ball balle;

    public MatchManager(GraphicsContext gc,Joueur joueur,Scene scene){
        this.joueur = joueur;
        this.joueurs = new Joueur[]{this.joueur};

        Hitbox hitboxballe = new Hitbox(this.joueur.getPosition().multiply(2), 45, 45);
        this.balle = new Ball(this.joueur.getPosition().multiply(2),hitboxballe);

        this.gc = gc;
        scene.setOnKeyPressed(e->{
            this.joueur.getControleurs().toucheAppuyee(e.getCode());
        });

        scene.setOnKeyReleased(e->{
            this.joueur.getControleurs().toucheRelachee(e.getCode());
        });
    }

    @Override
    public void handle(long now) {
        if(this.lastUpdate == 0){
            this.lastUpdate = now;
            return;
        }

        long delta = now - this.lastUpdate;
        double dt = delta / 1_000_000_000.0;
        this.lastUpdate = now;

        this.joueur.update(dt,m);
        this.balle.update(dt, m, joueurs);
        gc.clearRect(0, 0, 800, 800);
        this.joueur.render(gc);
        this.murs.render(gc);
        this.balle.render(gc);
    }
}
