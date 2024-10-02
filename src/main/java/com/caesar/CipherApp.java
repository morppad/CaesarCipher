package com.caesar;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CipherApp extends Application {

    private TextArea inputArea;
    private TextArea outputArea;
    private ComboBox<String> languageComboBox;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Caesar Cipher");

        inputArea = new TextArea();
        outputArea = new TextArea();
        outputArea.setEditable(false);

        TextField keyField = new TextField();
        keyField.setPromptText("Введите ключ");

        Button encryptButton = new Button("Зашифровать");
        Button decryptButton = new Button("Расшифровать");
        Button loadButton = new Button("Загрузить файл");
        Button saveButton = new Button("Сохранить файл");

        languageComboBox = new ComboBox<>();
        languageComboBox.getItems().addAll("Русский", "Английский");
        languageComboBox.setValue("Английский");

        encryptButton.setOnAction(e -> encryptText(keyField.getText()));
        decryptButton.setOnAction(e -> decryptText(keyField.getText()));
        loadButton.setOnAction(e -> loadFile());
        saveButton.setOnAction(e -> saveFile());

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setVgap(8);
        grid.setHgap(10);

        grid.add(new Label("Язык:"), 0, 0);
        grid.add(languageComboBox, 1, 0);
        grid.add(new Label("Введите текст:"), 0, 1);
        grid.add(inputArea, 0, 2, 2, 1);
        grid.add(new Label("Ключ:"), 0, 3);
        grid.add(keyField, 1, 3);
        grid.add(encryptButton, 0, 4);
        grid.add(decryptButton, 1, 4);
        grid.add(loadButton, 0, 5);
        grid.add(saveButton, 1, 5);
        grid.add(new Label("Результат:"), 0, 6);
        grid.add(outputArea, 0, 7, 2, 1);

        Scene scene = new Scene(grid, 600, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void encryptText(String keyText) {
        try {
            int key = Integer.parseInt(keyText);
            String language = languageComboBox.getValue().equals("Русский") ? "russian" : "english";
            CaesarCipher cipher = new CaesarCipher(key, language);
            String encryptedText = cipher.encrypt(inputArea.getText());
            outputArea.setText(encryptedText);
        } catch (NumberFormatException e) {
            showAlert("Ошибка", "Введите корректный числовой ключ.");
        }
    }

    private void decryptText(String keyText) {
        try {
            int key = Integer.parseInt(keyText);
            String language = languageComboBox.getValue().equals("Русский") ? "russian" : "english";
            CaesarCipher cipher = new CaesarCipher(key, language);
            String decryptedText = cipher.decrypt(inputArea.getText());
            outputArea.setText(decryptedText);
        } catch (NumberFormatException e) {
            showAlert("Ошибка", "Введите корректный числовой ключ.");
        }
    }

    private void loadFile() {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            try {
                String content = new String(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
                inputArea.setText(content);
            } catch (IOException e) {
                showAlert("Ошибка", "Не удалось загрузить файл.");
            }
        }
    }

    private void saveFile() {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            try {
                Files.write(Paths.get(file.getAbsolutePath()), outputArea.getText().getBytes());
            } catch (IOException e) {
                showAlert("Ошибка", "Не удалось сохранить файл.");
            }
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
