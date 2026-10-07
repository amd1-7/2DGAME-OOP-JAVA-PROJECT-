package com.game;

import java.awt.image.BufferedImage;

public class Animation {
    private final BufferedImage[] frames;   // les images de l'animation, dans l'ordre
    private final double frameDuration;     // durée d'UNE image, en secondes (0.1 = 10 images/seconde)
    private final boolean loop;             // vrai = recommence au début à la fin
    private double timer;                   // temps écoulé sur l'image courante
    private int currentFrameIndex;          // numéro de l'image affichée (0 = la première)
    private boolean finished;               // vrai quand une animation « une fois » est terminée

    /** Animation en boucle : c'est le constructeur d'origine, il marche toujours. */
    public Animation(BufferedImage[] frames, double frameDuration) {
        this(frames, frameDuration, true);
    }

    /**
     * @param frames        images de l'animation
     * @param frameDuration durée d'une image en secondes
     * @param loop          vrai = en boucle, faux = une seule fois
     */
    public Animation(BufferedImage[] frames, double frameDuration, boolean loop) {
        this.frames = frames;
        this.frameDuration = frameDuration;
        this.loop = loop;
        this.timer = 0;
        this.currentFrameIndex = 0;
        this.finished = false;
    }

   
    public void update(double deltaTime) {
        
        if (frames == null || frames.length == 0 || finished || frameDuration <= 0) return;

        timer += deltaTime;
        
        while (timer >= frameDuration) {
            timer -= frameDuration;
            if (currentFrameIndex < frames.length - 1) {
                currentFrameIndex++;               // image suivante
            } else if (loop) {
                currentFrameIndex = 0;             // fin atteinte, en boucle alors on recommence
            } else {
                finished = true;                   // fin atteinte, une seule fois  on reste sur la dernière image
                timer = 0;
                break;
            }
        }
    }

    public void reset() {
        timer = 0;
        currentFrameIndex = 0;
        finished = false;
    }

    /** @return l'image à dessiner maintenant */
    public BufferedImage getCurrentFrame() {
        if (frames == null || frames.length == 0) return null;
        return frames[currentFrameIndex];
    }

    /** @return vrai quand une animation une seule fois est terminée */
    public boolean isFinished() { return finished; }
    public boolean isLoop() { return loop; }
    public int getFrameIndex() { return currentFrameIndex; }
    public int getFrameCount() { return frames == null ? 0 : frames.length; }
}
