package com.example.restautomatique.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.HBox;
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

    private HBox dishsApp;
    @FXML
    private SplitPane splitpane;

    @FXML
    private Button btnPlats;

    @FXML
    private SplitPane splitpane;

    @FXML
    private VBox mainApp;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        splitpane.getItems().remove(dishsApp);
        btnPlats.setOnMousePressed(actionEvent -> {
            splitpane.getItems().add(dishsApp);
        });
        splitpane.getItems().remove(employeApp);

        btnEmployes.setOnMousePressed( actionEvent -> {
            splitpane.getItems().add(employeApp);
        });
    }
}
