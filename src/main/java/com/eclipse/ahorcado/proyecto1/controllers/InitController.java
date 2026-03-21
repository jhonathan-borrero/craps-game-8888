package com.eclipse.ahorcado.proyecto1.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Controlador de la pantalla de inicio.
 * Permite ingresar la palabra secreta y pasar al juego.
 */
public class InitController {

    @FXML
    private TextField txtPalabra;

    /**
     * Maneja el evento de clic en el botón Jugar.
     * Valida la palabra y carga la vista del juego si es válida.
     * @param event Evento de acción generado por el botón.
     */
    @FXML
    public void onJugarClick(ActionEvent event) {
        // Obtenemos el texto y quitamos espacios al inicio y final
        String palabra = txtPalabra.getText().trim();

        // Validación: Solo letras de la A a la Z (mayúsculas o minúsculas), entre 6 y 12 caracteres
        if (palabra.matches("^[a-zA-Z]{6,12}$")) {
            // Si es válida, cargamos la siguiente vista y le pasamos la palabra
            cargarVistaJuego(event, palabra.toUpperCase());
        } else {
            mostrarError("La palabra debe tener entre 6 y 12 letras y no contener caracteres especiales o números.");
        }
    }

    private void cargarVistaJuego(ActionEvent event, String palabraSecreta) {
        try {
            // Cargar el FXML del juego (Asegúrate de que el nombre del archivo sea correcto)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/eclipse/ahorcado/proyecto1/game.fxml"));
            Parent root = loader.load();

            // Obtener el GameController para pasarle la palabra secreta ANTES de mostrar la escena
            GameController gameController = loader.getController();
            gameController.iniciarJuegoConPalabra(palabraSecreta);

            // Cambiar la escena
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            mostrarError("Error al cargar la vista del juego.");
        }
    }

    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error en la palabra");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}