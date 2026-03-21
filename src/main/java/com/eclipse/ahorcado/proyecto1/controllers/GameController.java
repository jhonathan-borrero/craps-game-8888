package com.eclipse.ahorcado.proyecto1.controllers;

import com.eclipse.ahorcado.proyecto1.models.GameModel;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import java.text.Normalizer;
import java.util.ArrayList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import java.util.List;
import java.util.Random;

public class GameController {

    private int helpsUsed = 0;

    @FXML
    public void help() {
        // Limitar a 3 ayudas por partida
        if (helpsUsed >= 3) {
            mostrarAlerta(AlertType.WARNING, "Sin Pistas", "Has agotado tus 3 ayudas lunares en esta partida.");
            return;
        }

        // Buscar posiciones de letras aún ocultas
        List<Integer> hiddenIndices = new ArrayList<>();
        for (int i = 0; i < letterFields.size(); i++) {
            if (letterFields.get(i).getText().isEmpty()) {
                hiddenIndices.add(i);
            }
        }

        if (hiddenIndices.isEmpty()) {
            mostrarAlerta(AlertType.INFORMATION, "¡Casi listo!", "¡Ya no hay más letras que revelar!");
            return;
        }

        // Elegir una letra aleatoria entre las ocultas y mostrarla
        Random random = new Random();
        int randomIndex = hiddenIndices.get(random.nextInt(hiddenIndices.size()));
        char letterToReveal = model.getSecretWord().charAt(randomIndex);

        // Mostrar la letra en la interfaz
        updateVisibleLetters(formatInputChar(String.valueOf(letterToReveal)));

        helpsUsed++;
        mostrarAlerta(AlertType.INFORMATION, "Pista Lunar",
                "Se ha revelado la letra: " + letterToReveal + "\nTe quedan " + (3 - helpsUsed) + " ayudas.");
    }

    private void mostrarAlerta(AlertType tipo, String titulo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    @FXML
    private TextField letterInput;

    @FXML
    private Label feedbackLabel;

    @FXML
    private HBox wordContainer; // Contenedor para los campos de las letras

    private GameModel model;
    private List<TextField> letterFields; // Referencias a los campos generados

    @FXML
    public void initialize() {
        this.letterFields = new ArrayList<>();
        // El modelo se inicializa cuando se recibe la palabra desde la vista inicial
    }

    // Inicializa el juego con la palabra recibida desde InitController
    public void iniciarJuegoConPalabra(String palabraSecreta) {
        this.model = new GameModel();
        this.model.setSecretWord(palabraSecreta);

        generateWordFields();
    }

    // Crea un TextField no editable por cada letra de la palabra secreta
    private void generateWordFields() {
        int wordLength = model.getSecretWord().length();

        for (int i = 0; i < wordLength; i++) {
            TextField field = new TextField();
            field.setPrefWidth(40);
            field.setEditable(false);
            field.setStyle("-fx-alignment: center; -fx-font-size: 16px;");

            letterFields.add(field);
            wordContainer.getChildren().add(field);
        }
    }

    @FXML
    public void handleVerifyLetter() {
        String input = letterInput.getText();

        if (input == null || input.trim().length() != 1) {
            feedbackLabel.setText("Entrada inválida");
            return;
        }

        char cleanLetter = formatInputChar(input);
        boolean isValid = model.isLetterValid(cleanLetter);

        if (isValid) {
            feedbackLabel.setText("Es correcta");
            updateVisibleLetters(cleanLetter);
        } else {
            feedbackLabel.setText("Es incorrecta");
        }

        letterInput.clear();
    }

    // Muestra la letra en las posiciones correspondientes
    private void updateVisibleLetters(char cleanLetter) {
        List<Integer> positions = model.getLetterPositions(cleanLetter);
        String originalWord = model.getSecretWord();

        for (int pos : positions) {
            // Tomar la letra original de la palabra (incluye acentos si existen)
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