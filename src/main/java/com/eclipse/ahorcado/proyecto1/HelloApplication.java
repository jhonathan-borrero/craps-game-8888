package com.eclipse.ahorcado.proyecto1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
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
