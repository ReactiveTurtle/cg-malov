package ru.reactiveturtle.task1;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class SoundPlayer {
    public void playNewElement() {
        playSound("newElement.wav");
    }

    public void playElementCreated() {
        playSound("elementCreated.wav");
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
