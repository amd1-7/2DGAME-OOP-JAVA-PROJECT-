package com.game.model;
import com.game.model.physics.Vector2D;
import com.game.model.physics.Hitbox;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


public class Ball extends Entite{
    private Joueur proprietaireActuel = null;

    public Ball(Vector2D position,Vector2D velocite,Vector2D acceleration,Hitbox hitbox,double friction){
        super(position, velocite, acceleration, hitbox);
    }

    public Joueur getProprietaireActuel() {
        return proprietaireActuel;
    }
    public void setProprietaireActuel(Joueur proprietaireActuel) {
        this.proprietaireActuel = proprietaireActuel;
    }

    @Override
    public void update(double dt) {
        if(this.proprietaireActuel == null){
            super.update(dt);
            return;
        }
        Vector2D directionJoueur = this.proprietaireActuel.getDirectionRegard().getVecteur();
        double posX = this.proprietaireActuel.position.getX() + directionJoueur.getX() * 30;
        double posY = this.proprietaireActuel.position.getY() + directionJoueur.getY() * 30;
        Vector2D position = new Vector2D(posX,posY);

        this.setPosition(position);
        if(this.hitbox != null) this.hitbox.setPosition(position);
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.setFill(Color.BROWN);
        gc.fillOval(this.position.getX(), this.position.getY(),this.getHitbox().getWidth(),this.getHitbox().getHeight());
    }

    public void etreTiree(){
        if (this.proprietaireActuel == null) return;
        double puissance = this.proprietaireActuel.getPuissanceTir();
        Vector2D direction = this.proprietaireActuel.getDirectionRegard().getVecteur();
        Vector2D RealDirection = new Vector2D(direction.getX(), direction.getY());

        this.proprietaireActuel = null;
        this.getAcceleration().add(RealDirection.multiply(275 * puissance));
    }
}