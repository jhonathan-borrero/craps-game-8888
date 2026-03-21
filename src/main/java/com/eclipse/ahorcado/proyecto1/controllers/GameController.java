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

/**
 * Controlador principal del juego Ahorcado Lunar.
 * Gestiona la lógica de interacción, intentos, ayudas y efectos visuales.
 */
public class GameController {

    private int helpsUsed = 0;

    /**
     * Proporciona una ayuda al usuario, revelando una letra oculta.
     * Solo se permiten hasta 3 ayudas por partida.
     */
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

        // Verificar si ganó tras la ayuda
        if (checkWin()) {
            feedbackLabel.setText("¡GANASTE!");
            letterInput.setDisable(true);
        }

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

    @FXML
    private Label attemptsLabel; // Texto de intentos

    private GameModel model;
    private List<TextField> letterFields; // Referencias a los campos generados

    /**
     * Inicializa el controlador y prepara la interfaz.
     * El modelo se inicializa cuando se recibe la palabra desde la vista inicial.
     */
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

        // Forzamos el color del texto a blanco para que resalte y no se vea gris
        if (attemptsLabel != null) {
            attemptsLabel.setStyle("-fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold;");
        }

        // Llamamos al efecto desde el inicio para que marque "Intentos: 5"
        // y aplique el fondo inicial al wordContainer
        updateSunEffect();
    }

    // Crea un TextField no editable por cada letra de la palabra secreta
    private void generateWordFields() {
        // Limpia los campos anteriores si existen
        letterFields.clear();
        wordContainer.getChildren().clear();
        
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
            feedbackLabel.setText("Letra correcta");
            updateVisibleLetters(cleanLetter);
            
            // Verificar si ganó
            if (checkWin()) {
                feedbackLabel.setText("¡GANASTE!");
                letterInput.setDisable(true);
            }

        } else {
            feedbackLabel.setText("Letra incorrecta.");

            // Restamos un intento en el modelo
            model.decreaseAttempt();

            // Actualizamos el color del contenedor y el texto de intentos
            updateSunEffect();

            // Verificamos si se quedó sin intentos
            if (model.isGameOver()) {
                feedbackLabel.setText("¡GAME OVER!");
                letterInput.setDisable(true); // Bloquea el campo de texto para que no siga jugando
            }
        }

        letterInput.clear();
    }

    // Verifica si el jugador ganó (todos los campos llenosEspecialmente después de descubrir todas las letras)
    private boolean checkWin() {
        for (TextField field : letterFields) {
            if (field.getText().isEmpty()) {
                return false;
            }
        }
        return true;
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