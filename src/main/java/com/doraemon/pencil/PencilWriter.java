package com.doraemon.pencil;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.TextArea;
import javafx.util.Duration;

public class PencilWriter {

    /**
     * Simulates the pencil writing text into a TextArea.
     * @param text The string to be written.
     * @param textArea The target UI component.
     * @param speed Milliseconds between each character.
     */
    public void writeSlowly(String text, TextArea textArea, int speed) {
        textArea.clear(); // Start fresh
        
        final int[] index = {0};
        Timeline timeline = new Timeline();
        
        // KeyFrame runs every 'speed' milliseconds
        KeyFrame keyFrame = new KeyFrame(Duration.millis(speed), event -> {
            if (index[0] < text.length()) {
                textArea.appendText(String.valueOf(text.charAt(index[0])));
                index[0]++;
                
                // Optional: Play a "scribble" sound effect here in the future!
            }
        });

        timeline.getKeyFrames().add(keyFrame);
        timeline.setCycleCount(text.length());
        timeline.play();
    }
}