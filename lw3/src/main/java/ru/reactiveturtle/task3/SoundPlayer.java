package ru.reactiveturtle.task3;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class SoundPlayer {
    public void playNewGame() {
        playSound("success.wav");
    }

    public void playNewLevel() {
        playSound("clear.wav");
    }

    public void playFall() {
        playSound("fall.wav");
    }

    public void playLineDestroy() {
        playSound("line.wav");
    }

    public void playGameOver() {
        playSound("gameover.wav");
    }

    private static void playSound(String file) {
        new Thread(() -> {
            try {
                Clip clip = AudioSystem.getClip();
                AudioInputStream inputStream = AudioSystem.getAudioInputStream(
                        SoundPlayer.class.getResourceAsStream("/sound/" + file));
                clip.open(inputStream);
                clip.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
