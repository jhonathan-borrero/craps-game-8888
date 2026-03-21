package com.eclipse.ahorcado.proyecto1;

import javafx.application.Application;

/**
 * Clase de lanzamiento para ejecutar la aplicación JavaFX desde la línea de comandos.
 */
public class Launcher {
    /**
     * Método principal. Lanza la aplicación JavaFX.
     * @param args Argumentos de línea de comandos.
     */
    public static void main(String[] args) {
        Application.launch(HelloApplication.class, args);
    }
}
