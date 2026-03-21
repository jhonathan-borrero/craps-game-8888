package com.eclipse.ahorcado.proyecto1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // Cambia esto por el nombre exacto de tu FXML del sol eclipsado
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("init.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400); // Ajusta el tamaño de la ventana
        stage.setTitle("Ahorcado Lunar");
        stage.setScene(scene);
        stage.show();
    }
}
