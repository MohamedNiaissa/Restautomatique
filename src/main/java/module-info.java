module com.example.restautomatique {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.restautomatique to javafx.fxml;
    exports com.example.restautomatique;
}
