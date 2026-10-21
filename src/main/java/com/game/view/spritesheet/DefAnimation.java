package com.game.view.spritesheet;

public class DefAnimation {
    public enum CouleuraAnnimation {
        BLEU(0),
        ROUGE(9),
        JAUNE(18),
        CYAN(27);

        private int x;

        CouleuraAnnimation(int x){
            this.x = x;
        }
        public int getCouleurindex(){
            return this.x;
        }
    }

    public enum AnimationPosition {
        IDLE(1,1),
        COURSE(6,4),
        TIRE(7,6),
        PASSE(6,5),
        CElEBRATION(12,9),

        SUPER_VITESSE(7,8),
        SUPER_TIRE(12,7),
        TACLE(4,8);

        private int col;
        private int ligne;

        AnimationPosition(int col,int ligne){
            this.col =col;
            this.ligne = ligne;
        }
        public int MaxColone(){
            return this.col;
        }
        public int getLigne(){
            return this.ligne;
        }
    }
}