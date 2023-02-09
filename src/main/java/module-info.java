module com.example.restautomatique {
    requires javafx.controls;
    requires javafx.fxml;
    requires itextpdf;


    opens com.example.restautomatique to javafx.fxml;
    exports com.example.restautomatique;
    exports com.example.restautomatique.controller;
    opens com.example.restautomatique.controller to javafx.fxml;
}
