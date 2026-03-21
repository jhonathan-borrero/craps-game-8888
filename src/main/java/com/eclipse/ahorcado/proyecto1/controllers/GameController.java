package com.eclipse.ahorcado.proyecto1.controllers;

import com.eclipse.ahorcado.proyecto1.models.GameModel;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

public class GameController {

    @FXML
    private TextField letterInput;

    @FXML
    private Label feedbackLabel;

    @FXML
    private HBox wordContainer; // Contenedor para los campos dinamicos

    @FXML
    private Label attemptsLabel; // Texto de intentos

    private GameModel model;
    private List<TextField> letterFields; // Guarda las referencias a los campos generados

    @FXML
    public void initialize() {
        this.letterFields = new ArrayList<>();
    }


    public void iniciarJuegoConPalabra(String palabraSecreta) {
        this.model = new GameModel();
        this.model.setSecretWord(palabraSecreta);

        generateWordFields();

        // Forzamos el color del texto a blanco para que resalte y no se vea gris
        if (attemptsLabel != null) {
            attemptsLabel.setStyle("-fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold;");
        }

        // Llamamos al efecto desde el inicio para que marque "Intentos: 5"
        // y aplique el fondo inicial al wordContainer
        updateSunEffect();
    }

    // Genera un TextField bloqueado por cada letra de la palabra secreta
    private void generateWordFields() {
        // Limpia los campos anteriores si existen
        letterFields.clear();
        wordContainer.getChildren().clear();
        
        int wordLength = model.getSecretWord().length();

        for (int i = 0; i < wordLength; i++) {
            TextField field = new TextField();
            field.setPrefWidth(40);
            field.setEditable(false); // El usuario no escribe directamente aqui
            field.setStyle("-fx-alignment: center; -fx-font-size: 16px;");

            letterFields.add(field);
            wordContainer.getChildren().add(field);
        }
    }

    @FXML
    public void handleVerifyLetter() {
        String input = letterInput.getText();

        if (input == null || input.trim().length() != 1) {
            feedbackLabel.setText("Entrada invalida. Ingrese solo una letra.");
            return;
        }

        char cleanLetter = formatInputChar(input);
        boolean isValid = model.isLetterValid(cleanLetter);

        if (isValid) {
            feedbackLabel.setText("La letra '" + cleanLetter + "' es valida.");
            updateVisibleLetters(cleanLetter);

        } else {
            feedbackLabel.setText("La letra '" + cleanLetter + "' es incorrecta.");

            // Restamos un intento en el modelo
            model.decreaseAttempt();

            // Actualizamos el color del contenedor y el texto de intentos
            updateSunEffect();

            // Verificamos si se quedó sin intentos
            if (model.isGameOver()) {
                feedbackLabel.setText("¡Juego terminado! Te quedaste sin intentos.");
                letterInput.setDisable(true); // Bloquea el campo de texto para que no siga jugando
            }
        }

        letterInput.clear();
    }

    private void updateSunEffect() {
        int attempts = model.getRemainingAttempts();

        // Actualizamos el texto si existe
        if (attemptsLabel != null) {
            attemptsLabel.setText("Intentos restantes: " + attempts);
        }

        // Calculamos el porcentaje de error
        int errors = 5 - attempts;
        int orangePercentage = errors * 20;

        // Le aplicamos el estilo directamente al HBox que ya tenias (wordContainer)
        if (wordContainer != null) {
            String cssStyle = String.format(
                    "-fx-background-color: linear-gradient(to top, #FFA500 %d%%, #cccccc %d%%); " +
                            "-fx-background-radius: 15;",
                    orangePercentage, orangePercentage
            );
            wordContainer.setStyle(cssStyle);
        }
    }

    // Revela la letra en los campos correspondientes usando su posicion
    private void updateVisibleLetters(char cleanLetter) {
        List<Integer> positions = model.getLetterPositions(cleanLetter);
        String originalWord = model.getSecretWord();

        for (int pos : positions) {
            // Obtenemos la letra original (con acento si lo tenia) basandonos en la posicion
            char originalChar = originalWord.charAt(pos);
            letterFields.get(pos).setText(String.valueOf(originalChar));
        }
    }

    private char formatInputChar(String input) {
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
        String withoutAccents = normalized.replaceAll("\\p{M}", "");
        return withoutAccents.toUpperCase().charAt(0);
    }
}