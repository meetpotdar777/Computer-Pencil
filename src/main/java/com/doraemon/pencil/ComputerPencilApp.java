package com.doraemon.pencil;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ComputerPencilApp extends Application {
    private LogicEngine engine = new LogicEngine();

    @Override
    public void start(Stage stage) {
        stage.setTitle("Doraemon's Computer Pencil");

        Label titleLabel = new Label("✎ COMPUTER PENCIL");

        TextField inputField = new TextField();
        inputField.setPromptText("Enter a math problem, coding question, or report topic...");

        TextArea outputArea = new TextArea();
        outputArea.setEditable(false);
        outputArea.setWrapText(true);
        outputArea.setPrefHeight(300);

        Button solveButton = new Button("SOLVE");
        Button clearButton = new Button("CLEAR");
        Button saveButton = new Button("SAVE TO DESKTOP");
        
        // Add styling classes
        clearButton.getStyleClass().add("clear-button");

        // SOLVE LOGIC (Using a Thread so the window doesn't freeze)
        solveButton.setOnAction(e -> {
            String question = inputField.getText();
            if (!question.isEmpty()) {
                outputArea.setText("> SYSTEM: ANALYZING DATA...\n\n");
                new Thread(() -> {
                    String answer = engine.solveProblem(question);
                    Platform.runLater(() -> outputArea.appendText(answer));
                }).start();
            }
        });

        // CLEAR LOGIC
        clearButton.setOnAction(e -> {
            inputField.clear();
            outputArea.clear();
        });

        // SAVE LOGIC
        saveButton.setOnAction(e -> {
            String content = outputArea.getText();
            if (!content.isEmpty()) {
                try {
                    String userHome = System.getProperty("user.home");
                    Path path = Paths.get(userHome, "Desktop", "Pencil_Output.txt");
                    Files.writeString(path, content);
                    outputArea.appendText("\n\n>>> [SYSTEM: Saved to Desktop as Pencil_Output.txt]");
                } catch (Exception ex) {
                    outputArea.appendText("\n\n>>> [ERROR: Could not save file]");
                }
            }
        });

        HBox buttonBox = new HBox(10, solveButton, clearButton, saveButton);
        buttonBox.setAlignment(Pos.CENTER);

        VBox layout = new VBox(20, titleLabel, inputField, buttonBox, outputArea);
        layout.setPadding(new Insets(25));
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, 550, 600);
        
        // Load CSS - Ensure style.css is in src/main/resources
        try {
            String cssPath = getClass().getResource("/style.css").toExternalForm();
            scene.getStylesheets().add(cssPath);
        } catch (Exception e) {
            System.out.println("CSS check: Ensure style.css is in src/main/resources/");
        }

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}