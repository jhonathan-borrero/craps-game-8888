package com.eclipse.ahorcado.proyecto1;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * Controlador de la vista de bienvenida.
 */
public class HelloController {
    @FXML
    private Label welcomeText;

    /**
     * Maneja el evento de clic en el botón de saludo.
     */
    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}
