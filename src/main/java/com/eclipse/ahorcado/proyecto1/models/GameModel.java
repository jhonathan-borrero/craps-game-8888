package com.eclipse.ahorcado.proyecto1.models;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

public class GameModel {
    private String secretWord;
    private String normalizedSecretWord;

    public GameModel() {
        // Inicializar vacío; la palabra se asignará desde el controlador de inicio
        this.secretWord = "";
        this.normalizedSecretWord = "";
    }

    public void setSecretWord(String secretWord) {
        this.secretWord = secretWord.toUpperCase();
        // Guardar una versión sin acentos para comparar entradas del usuario
        this.normalizedSecretWord = removeAccents(this.secretWord);
    }

    public String getSecretWord() {
        return this.secretWord;
    }

    public boolean isLetterValid(char letter) {
        return this.normalizedSecretWord.indexOf(letter) >= 0;
    }

    public List<Integer> getLetterPositions(char letter) {
        List<Integer> positions = new ArrayList<>();
        for (int i = 0; i < this.normalizedSecretWord.length(); i++) {
            if (this.normalizedSecretWord.charAt(i) == letter) {
                positions.add(i);
            }
        }
        return positions;
    }

    private String removeAccents(String text) {
        String normalized = Normalizer.normalize(text, Normalizer.Form.NFD);
        return normalized.replaceAll("\\p{M}", "");
    }
}