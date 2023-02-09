package com.example.restautomatique.controller;

import com.example.restautomatique.model.Employe;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class RestauController implements Initializable {

    @FXML
    private HBox employeApp;

    @FXML
    private Button btnEmployes;
    
    @FXML
    private SplitPane splitpane;

    @FXML
    private VBox mainApp;

    @FXML
    private HBox h;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        splitpane.getItems().remove(employeApp);

        btnEmployes.setOnMousePressed( actionEvent -> {
            splitpane.getItems().add(employeApp);
        });
    }
}
