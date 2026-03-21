package com.eclipse.ahorcado.proyecto1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Clase principal que inicia la aplicación JavaFX para el juego Ahorcado Lunar.
 */
public class HelloApplication extends Application {
    /**
     * Inicia la aplicación y muestra la ventana principal.
     * @param stage Escenario principal de JavaFX.
     * @throws IOException Si ocurre un error al cargar la vista.
     */
    @Override
    public void start(Stage stage) throws IOException {
        var resource = HelloApplication.class.getResource("init.fxml");
        if (resource == null) {
            throw new RuntimeException("Cannot find init.fxml resource");
        }
        FXMLLoader fxmlLoader = new FXMLLoader(resource);
        Scene scene = new Scene(fxmlLoader.load(), 600, 500);
        stage.setTitle("Ahorcado Lunar");
        stage.setScene(scene);
        stage.setResizable(true);
        stage.show();
    }
}
