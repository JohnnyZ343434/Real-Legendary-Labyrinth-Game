package model;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class BackgroundMusic {
    private Clip clip;

    public void playMusic(String filepath) {
        try {
            // open the file
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File(filepath));
            // 
            clip = AudioSystem.getClip();
            // 
            clip.open(audioStream);
            // start to play the music
            clip.start();
            // loop playing
//            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    // method to stop the music
    public void stopMusic() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }
}