module com.eclipse.ahorcado.proyecto1 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.eclipse.ahorcado.proyecto1.controllers to javafx.fxml;
    exports com.eclipse.ahorcado.proyecto1;
}