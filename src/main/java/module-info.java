module com.example.restautomatique {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.restautomatique to javafx.fxml;
    exports com.example.restautomatique;
    opens com.example.restautomatique.controller to javafx.fxml;
    exports com.example.restautomatique.controller;
    opens com.example.restautomatique.model;
    exports com.example.restautomatique.model;
}
