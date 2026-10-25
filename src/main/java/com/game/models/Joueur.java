package com.game.models;
import com.game.models.input.ControleurHumain;
import com.game.models.physics.Direction;
import com.game.models.physics.Hitbox;
import com.game.models.physics.Vector2D;
import com.game.view.spritesheet.Annimation;

import javafx.animation.PauseTransition;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.util.Duration;

public class Joueur extends Entite {
    public enum EtatJoueur {
    IDLE,
    TACLE,
    TIRE,
    PASSE,
    COURS,
    Glace,
    Pouvoir,
    Celebration
    }
    
    protected ControleurHumain controleurs;
    private double puissanceTir;
    protected Vector2D dernierOrientation;
    protected  EtatJoueur etatJoueur = EtatJoueur.IDLE;
    protected  boolean isProprietaire = false;
    protected Annimation annimation;

    protected int indexCourse = 1;
    protected int indexTire = 1;
    protected int indexPasse = 1;
    protected int indexTacle = 1;
    protected int indexGlace = 1;
    protected int indexCelebration = 1;
    protected double timerAnimation = 0;

   public Joueur(Vector2D position,Vector2D vitesse,Vector2D acceleration,Hitbox hitbox,ControleurHumain controleurs,Image image,double puissanceTir,Annimation annimation){
        super(position, vitesse, acceleration, hitbox);

        this.controleurs = controleurs;
        this.puissanceTir = puissanceTir;
        this.annimation = annimation;
    }


    public boolean getIsProprietaire(){
        return this.isProprietaire;
    }
    public void setIsProprietaire(boolean isProprietaire) {
        this.isProprietaire = isProprietaire;
    }


    public Vector2D getDernierOrientation() {
        return dernierOrientation;
    }
    public void setDernierOrientation(Vector2D dernierOrientation) {
        this.dernierOrientation = dernierOrientation;
    }

    public double getPuissanceTir() {
        return puissanceTir;
    }
    public void setPuissanceTir(double puissanceTir) {
        this.puissanceTir = puissanceTir;
    }

    public ControleurHumain getControleurs() {
        return controleurs;
    }

    public EtatJoueur getEtatJoueur() {
        return etatJoueur;
    }
    public void setEtatJoueur(EtatJoueur etatJoueur) {
        this.etatJoueur = etatJoueur;
    }

    public void reset(Vector2D coin){
    this.position = coin;
    this.vitesse = new Vector2D(0, 0);
    this.acceleration = new Vector2D(0, 0);
    this.getHitbox().setPosition(coin);
    this.etatJoueur = EtatJoueur.IDLE;
    this.controleurs.setActif(true);
    }

    @Override
    /** 
     * @param mettre à jour le joueur  */
    public void update(double dt,Murs[] murs){
        double f = 1200;
        double k = 5;
        Vector2D force = this.getControleurs().getDirection().multiply(f);
        if (this.getControleurs().getDirection().norm() > 0) this.dernierOrientation = this.getControleurs().getDirection().normalize();
        
        this.acceleration = force.sub(this.vitesse.multiply(k));
        this.vitesse = this.vitesse.add(this.acceleration.multiply(dt));
        this.position = this.position.add(this.vitesse.multiply(dt));

        switch (this.controleurs.getActionJoueurSansballon()) {
            case IDLE ->{
                if(this.vitesse.norm() < 27.5) this.etatJoueur = EtatJoueur.IDLE;
                else this.etatJoueur = EtatJoueur.COURS;
            }

            case PASSE ->{
                this.passer();
            }
            case TACLE ->{
                this.tacler();
            }
            case TIRE ->{
                this.tirer();
            }
            default -> {;}
        }

        this.Collision(murs);
        this.getHitbox().setPosition(this.position);
    }

     /** 
     * @param en cas de collision réagir en fonction de la direction inverse de la vitesse */
    protected void Collision(Murs[] murs){
        for (Murs mur : murs) {
        Hitbox m = mur.getHitbox();
        Direction d = this.getHitbox().intersect(m);
        if (d == null) continue;

        double mx = m.getPosition().getX();
        double my = m.getPosition().getY();

        this.getHitbox().setPosition(this.position);
        switch (d) {
            case RIGHT -> {   
                position = new Vector2D(mx - getHitbox().getWidth(), position.getY());
                vitesse  = new Vector2D(Math.min(0, vitesse.getX()), vitesse.getY());
            }
            case LEFT -> {    
                position = new Vector2D(mx + m.getWidth(), position.getY());
                vitesse  = new Vector2D(Math.max(0, vitesse.getX()), vitesse.getY());
            }
            case DOWN -> {    
                position = new Vector2D(position.getX(), my - getHitbox().getHeight());
                vitesse  = new Vector2D(vitesse.getX(), Math.min(0, vitesse.getY()));
            }
            case UP -> {     
                position = new Vector2D(position.getX(), my + m.getHeight());
                vitesse  = new Vector2D(vitesse.getX(), Math.max(0, vitesse.getY()));
            }
        }
    }
    }
     /** 
     * @param mettre à jour état joueur */
    public void tacler(){
        if ((this.etatJoueur != EtatJoueur.IDLE || this.etatJoueur != EtatJoueur.COURS) && this.isProprietaire == true)return;
        this.etatJoueur = EtatJoueur.TACLE;
        PauseTransition pause = new PauseTransition(Duration.seconds(7));
        pause.setOnFinished(e->{
            this.etatJoueur = EtatJoueur.IDLE;
        });
        pause.play();
    }
    public void tirer(){
        if ((this.etatJoueur != EtatJoueur.IDLE || this.etatJoueur != EtatJoueur.COURS) && this.isProprietaire == false)return;
        this.etatJoueur = EtatJoueur.TIRE;
        PauseTransition pause = new PauseTransition(Duration.seconds(8));
        pause.setOnFinished(e->{
            this.etatJoueur = EtatJoueur.IDLE;
        });
        pause.play();
    }
    public void passer(){
        if ((this.etatJoueur != EtatJoueur.IDLE || this.etatJoueur != EtatJoueur.COURS) && this.isProprietaire == false)return;
        this.etatJoueur = EtatJoueur.PASSE;
        PauseTransition pause = new PauseTransition(Duration.seconds(7));
        pause.setOnFinished(e->{
            this.etatJoueur = EtatJoueur.IDLE;
        });
        pause.play();
    }

     /** 
     * @param dessiner joueur  */
    public void render(GraphicsContext gc,double dt){
        Image img = this.chooseImage(dt);
        Vector2D dir = this.getDernierOrientation();

        if (dir.getX() != 0 || dir.getY() != 0) {
            double angle = Math.toDegrees(Math.atan2(dir.getY(), dir.getX()));

            gc.save();
            gc.translate(this.position.getX() + 32 / 2.0, this.position.getY() + 32 / 2.0);
            gc.rotate(angle);
            gc.drawImage(img, -32 / 2.0, -32 / 2.0, 32, 32);
            gc.restore();
        } 
        else {
            gc.drawImage(img, this.position.getX(), this.position.getY(), 32, 32);
        }
    }

    protected Image chooseImage(double dt){
        Image img = this.annimation.getIdle();
        switch (this.etatJoueur) {
            case IDLE ->{
                this.indexTire = 0;
                this.indexCourse = 0;
                this.indexPasse = 0;
                this.indexTacle = 0;
                this.indexGlace = 0;
                this.indexCelebration = 0;

                img = this.annimation.getIdle();
            }

            case COURS ->{
                this.indexTire = 0;
                this.indexPasse = 0;
                this.indexTacle = 0;
                this.indexGlace = 0;
                this.indexCelebration = 0;
                this.timerAnimation += dt;
                
                if(0.1 <= this.timerAnimation){
                    if (this.indexCourse +1 < this.annimation.getListImageCourse().size())this.indexCourse++;
                    else this.indexCourse = 0;
                    this.timerAnimation -= 0.1;
                }
                
                img = this.annimation.getListImageCourse().get(this.indexCourse);
            }
            
            case PASSE ->{
                this.indexTire = 0;
                this.indexCourse = 0;
                this.indexTacle = 0;
                this.indexGlace = 0;
                this.indexCelebration = 0;
                this.timerAnimation += dt;

                if(0.1 <= this.timerAnimation){
                    if (this.indexPasse +1 < this.annimation.getListImagePasse().size())this.indexPasse++;
                    else this.indexPasse = 0;
                    this.timerAnimation -= 0.1;
                }
                
                img = this.annimation.getListImagePasse().get(this.indexPasse);
            }

            case TIRE ->{
                this.indexCourse = 0;
                this.indexPasse = 0;
                this.indexTacle = 0;
                this.indexGlace = 0;
                this.indexCelebration = 0;
                this.timerAnimation += dt;

                if(0.1 <= this.timerAnimation){
                    if (this.indexTire +1 < this.annimation.getListImageTire().size())this.indexTire++;
                    else this.indexTire = 0;
                    this.timerAnimation -= 0.1;
                    }
                img = this.annimation.getListImageTire().get(this.indexTire);
            }
            case TACLE ->{
                this.indexCourse = 0;
                this.indexPasse = 0;
                this.indexTire = 0;
                this.indexGlace = 0;
                this.indexCelebration = 0;
                this.timerAnimation += dt;

                if(0.1 <= this.timerAnimation){
                    if (this.indexTacle +1 < this.annimation.getListImageTacle().size())this.indexTacle++;
                    else this.indexTacle = 0;
                    this.timerAnimation -= 0.1;
                    }
                img = this.annimation.getListImageTacle().get(this.indexTacle);
            }
            case Glace->{
                this.indexCourse = 0;
                this.indexPasse = 0;
                this.indexTire = 0;
                this.indexTacle = 0;
                this.indexCelebration = 0;
                this.timerAnimation += dt;

                if(0.2 <= this.timerAnimation){
                    if(this.indexGlace +1 < this.annimation.getListeImageGlacon().size())this.indexGlace++;
                    else this.indexGlace = 0;
                    this.timerAnimation -= 0.2;
                }
                img = this.annimation.getListeImageGlacon().get(indexGlace);
            }
            case Celebration ->{
                this.indexCourse = 0;
                this.indexPasse = 0;
                this.indexTire = 0;
                this.indexTacle = 0;
                this.indexGlace = 0;
                this.timerAnimation += dt;

                if(0.1 <= this.timerAnimation){
                    if(this.indexCelebration +1 < this.annimation.getListImageCelebration().size())this.indexCelebration++;
                    else this.indexCelebration = 0;
                    this.timerAnimation -= 0.1;
                }
                img = this.annimation.getListImageCelebration().get(indexCelebration);
            }
            default ->{;}
        }
        return img;
    }

    @Override
    void render(GraphicsContext gc) {return ;}
}