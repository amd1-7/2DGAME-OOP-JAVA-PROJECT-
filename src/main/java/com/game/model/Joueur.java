package com.game.model;
import com.game.model.physics.Vector2D;
import com.game.model.physics.Direction;
import com.game.model.physics.Hitbox;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
public class Joueur extends Entite{
    private Equipe equipe;
    private double vitesse;
    private double puissanceTir;
    protected Direction directionBut;
    protected Direction directionRegard = directionBut;

    public Joueur(Vector2D position,Vector2D velocite,Vector2D acceleration,Hitbox hitbox,Equipe equipe,double vitesse,double puissanceTir,Direction directionBut){
        super(position, velocite, acceleration, hitbox);
        this.equipe = equipe;
        this.vitesse = vitesse;
        this.puissanceTir = puissanceTir;
        this.directionBut = directionBut;
    }

    public Equipe getEquipe() {
        return equipe;
    }

    public double getVitesse() {
        return vitesse;
    }
    public void setVitesse(double vitesse) {
        this.vitesse = vitesse;
    }

    public double getPuissanceTir() {
        return puissanceTir;
    }
    public void setPuissanceTir(double puissanceTir) {
        this.puissanceTir = puissanceTir;
    }

    public void setRegard(Vector2D directionJoueur) {
        if (directionJoueur == null || (directionJoueur.getX() == 0 && directionJoueur.getY() == 0)) {
            return;
        }

        double x = directionJoueur.getX();
        double y = directionJoueur.getY();

        if (x > 0 && y < 0) directionRegard = Direction.HAUT_DROITE;
        else if (x < 0 && y < 0) directionRegard = Direction.HAUT_GAUCHE; 
        else if (x > 0 && y > 0) directionRegard = Direction.BAS_DROITE;
        else if (x < 0 && y > 0) directionRegard = Direction.BAS_GAUCHE;
        else if (y < 0) directionRegard = Direction.HAUT;
        else if (y > 0) directionRegard = Direction.BAS;
        else if (x < 0) directionRegard = Direction.GAUCHE;
        else if (x > 0) directionRegard = Direction.DROITE;
    }
    public Direction getDirectionRegard() {
        return directionRegard;
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.setFill(Color.BLACK);
        gc.fillRect(this.position.getX(),this.position.getY(),this.getHitbox().getWidth() ,this.getHitbox().getHeight());
    }
}