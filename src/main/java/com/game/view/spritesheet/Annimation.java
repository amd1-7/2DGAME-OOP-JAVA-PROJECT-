package com.game.view.spritesheet;

import java.util.ArrayList;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;

public class Annimation {
    private DefAnimation.CouleuraAnnimation couleurAnimation;
    private Image idle;
    private ArrayList<Image> listImageCourse = new ArrayList<>();
    private ArrayList<Image> listImageTire = new ArrayList<>();
    private ArrayList<Image> listImagePasse = new ArrayList<>();
    private ArrayList<Image> listImageTacle = new ArrayList<>();
    private ArrayList<Image> listImageCelebration = new ArrayList<>();

    private ArrayList<Image> listImageSuperVitesse = new ArrayList<>();
    private ArrayList<Image> listImageSuperTire = new ArrayList<>();
    private ArrayList<Image> listeImageGlacon = new ArrayList<>();


    public Annimation(DefAnimation.CouleuraAnnimation c,Image spritesheet){
        this.couleurAnimation = c;
        this.loadAnimation(spritesheet);
    }

    public Image getIdle() {
        return idle;
    }
    public ArrayList<Image> getListImageCourse() {
        return listImageCourse;
    }
    public ArrayList<Image> getListImageTire() {
        return listImageTire;
    }
    public ArrayList<Image> getListImagePasse() {
        return listImagePasse;
    }
    public ArrayList<Image> getListImageCelebration() {
        return listImageCelebration;
    }
    public ArrayList<Image> getListImageSuperVitesse() {
        return listImageSuperVitesse;
    }
    public ArrayList<Image> getListImageSuperTire() {
        return listImageSuperTire;
    }

    private void loadAnimation(Image img){
        PixelReader reader = img.getPixelReader();
        for(DefAnimation.AnimationPosition i:DefAnimation.AnimationPosition.values()){
            switch (i) {
                case IDLE -> {
                    int ligne = (this.couleurAnimation.getCouleurindex() + i.getLigne() - 1) * 32;
                    int colonne = (i.MaxColone() -1) * 32;
                    this.idle = new WritableImage(reader,colonne,ligne,32,32);
                }
                case COURSE ->{
                    int ligne = (this.couleurAnimation.getCouleurindex() + i.getLigne() - 1) * 32;
                    for (int n = 1;n<= i.MaxColone();n++){
                        int colonne = (n - 1) * 32;
                        this.listImageCourse.add(new WritableImage(reader,colonne,ligne,32,32));   
                    }
                }
                case TIRE ->{
                    int ligne = (this.couleurAnimation.getCouleurindex() + i.getLigne() - 1) * 32;
                    for (int n = 1;n<= i.MaxColone();n++){
                        int colonne = (n - 1) * 32;
                        this.listImageTire.add(new WritableImage(reader,colonne,ligne,32,32));   
                    }
                }
                case PASSE ->{
                    int ligne = (this.couleurAnimation.getCouleurindex() + i.getLigne() - 1) * 32;
                    for (int n = 1;n<= i.MaxColone();n++){
                        int colonne = (n - 1) * 32;
                        this.listImagePasse.add(new WritableImage(reader,colonne,ligne,32,32));   
                    }
                }
                case SUPER_TIRE ->{
                    int ligne = (this.couleurAnimation.getCouleurindex() + i.getLigne() - 1) * 32;
                    for (int n = 1;n<= i.MaxColone();n++){
                        int colonne = (n - 1) * 32;
                        this.listImageSuperTire.add(new WritableImage(reader,colonne,ligne,32,32));   
                    }
                }
                case SUPER_VITESSE ->{
                    int ligne = (this.couleurAnimation.getCouleurindex() + i.getLigne() - 1) * 32;
                    for (int n = 1;n<= i.MaxColone();n++){
                        int colonne = (n - 1) * 32;
                        this.listImageSuperVitesse.add(new WritableImage(reader,colonne,ligne,32,32));   
                    }
                }
                case CElEBRATION ->{
                    int ligne = (this.couleurAnimation.getCouleurindex() + i.getLigne() - 1) * 32;
                    for (int n = 1;n<= i.MaxColone();n++){
                        int colonne = (n - 1) * 32;
                        this.listImageCelebration.add(new WritableImage(reader,colonne,ligne,32,32));   
                    }
                }
                case TACLE->{
                    int ligne = (this.couleurAnimation.getCouleurindex() + i.getLigne() - 1) * 32;
                    for (int n = 1;n<= i.MaxColone();n++){
                        int colonne = (n - 1) * 32;
                        this.listImageTacle.add(new WritableImage(reader,colonne,ligne,32,32));   
                    }
                }
                case Glacon->{
                    int index = 0;
                    switch (this.couleurAnimation) {
                        case BLEU->{index = 0;}
                        case ROUGE->{index = 1;}
                        case JAUNE->{index = 2;}
                        case CYAN->{index = 3;}
                        default -> {;}
                    };
                    int ligne = (index + i.getLigne() -1) * 32;
                    for (int n = 1;n <= i.MaxColone();n++){
                        int colonne = (n - 1) * 32;
                        this.listeImageGlacon.add(new WritableImage(reader,colonne,ligne,32,32)); 
                    }
                }
                default ->{;}
            }
        }
    }
}
