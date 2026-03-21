package com.eclipse.ahorcado.proyecto1.models;

import com.eclipse.ahorcado.proyecto1.models.GameModel;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

public class GameModel {
    private String secretWord;
    private String normalizedSecretWord;
    private int remainingAttempts;

    public GameModel() {
        this.secretWord = "ÁRBOL"; // Valor por defecto
        this.normalizedSecretWord = removeAccents(this.secretWord).toUpperCase();
        this.remainingAttempts = 5;
    }

    public void setSecretWord(String secretWord) {
        this.secretWord = secretWord;
        this.normalizedSecretWord = removeAccents(this.secretWord).toUpperCase();
    }

    // Método para leer la palabra
    public String getSecretWord() {
        return this.secretWord;
    }

    // Retorna una lista con los indices donde aparece la letra adivinada
    public List<Integer> getLetterPositions(char letter) {
        List<Integer> positions = new ArrayList<>();
        for (int i = 0; i < this.normalizedSecretWord.length(); i++) {
            if (this.normalizedSecretWord.charAt(i) == letter) {
                positions.add(i);
            }
        }
        return positions;
    }

    public int getRemainingAttempts() {
        return remainingAttempts;
    }

    public void decreaseAttempt() {
        if (this.remainingAttempts > 0) {
            this.remainingAttempts--;
        }
    }

    public boolean isGameOver() {
        return this.remainingAttempts == 0;
    }

    public boolean isLetterValid(char letter) {
        return this.normalizedSecretWord.indexOf(letter) >= 0;
    }

    private String removeAccents(String text) {
        String normalized = Normalizer.normalize(text, Normalizer.Form.NFD);
        return normalized.replaceAll("\\p{M}", "");
    }
}