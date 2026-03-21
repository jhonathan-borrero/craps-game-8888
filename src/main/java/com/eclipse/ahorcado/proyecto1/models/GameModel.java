package com.eclipse.ahorcado.proyecto1.models;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

/**
 * Modelo de datos del juego Ahorcado Lunar.
 * Almacena la palabra secreta, intentos restantes y lógica de validación.
 */
public class GameModel {
    private String secretWord;
    private String normalizedSecretWord;
    private int remainingAttempts;

    /**
     * Constructor. Inicializa el modelo con valores por defecto.
     */
    public GameModel() {
        // Inicializar vacío; la palabra se asignará desde el controlador de inicio
        this.secretWord = "";
        this.normalizedSecretWord = "";
        this.remainingAttempts = 5;
    }

    /**
     * Asigna la palabra secreta y la normaliza para comparación.
     * @param secretWord Palabra secreta a adivinar.
     */
    public void setSecretWord(String secretWord) {
        this.secretWord = secretWord.toUpperCase();
        // Guardar una versión sin acentos para comparar entradas del usuario
        this.normalizedSecretWord = removeAccents(this.secretWord);
    }

    /**
     * Obtiene la palabra secreta.
     * @return Palabra secreta.
     */
    public String getSecretWord() {
        return this.secretWord;
    }

    /**
     * Verifica si la letra está en la palabra secreta.
     * @param letter Letra a verificar.
     * @return true si la letra está presente, false en caso contrario.
     */
    public boolean isLetterValid(char letter) {
        return this.normalizedSecretWord.indexOf(letter) >= 0;
    }

    /**
     * Devuelve las posiciones de la letra en la palabra secreta.
     * @param letter Letra a buscar.
     * @return Lista de posiciones donde aparece la letra.
     */
    public List<Integer> getLetterPositions(char letter) {
        List<Integer> positions = new ArrayList<>();
        for (int i = 0; i < this.normalizedSecretWord.length(); i++) {
            if (this.normalizedSecretWord.charAt(i) == letter) {
                positions.add(i);
            }
        }
        return positions;
    }

    /**
     * Obtiene la cantidad de intentos restantes.
     * @return Intentos restantes.
     */
    public int getRemainingAttempts() {
        return remainingAttempts;
    }

    /**
     * Disminuye el contador de intentos.
     */
    public void decreaseAttempt() {
        if (this.remainingAttempts > 0) {
            this.remainingAttempts--;
        }
    }

    /**
     * Indica si el juego ha terminado.
     * @return true si no quedan intentos, false en caso contrario.
     */
    public boolean isGameOver() {
        return this.remainingAttempts == 0;
    }

    private String removeAccents(String text) {
        String normalized = Normalizer.normalize(text, Normalizer.Form.NFD);
        return normalized.replaceAll("\\p{M}", "");
    }
}