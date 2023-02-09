package com.example.restautomatique.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class RestauController implements Initializable {

    @FXML
    private HBox employeApp;

    @FXML
    private HBox dishsApp;

    @FXML
    private HBox tablesApp;

    @FXML
    private HBox financeApp;
    @FXML
    private SplitPane splitpane;

    @FXML
    private Button btnEmployes;

    @FXML
    private Button btnPlats;

    @FXML
    private Button btnTables;

    @FXML
    private VBox mainApp;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        splitpane.getItems().remove(employeApp);
        splitpane.getItems().remove(dishsApp);
        splitpane.getItems().remove(tablesApp);

        btnEmployes.setOnMousePressed( actionEvent -> {
            splitpane.getItems().add(employeApp);
            splitpane.getItems().remove(dishsApp);
            splitpane.getItems().remove(tablesApp);
            splitpane.getItems().remove(financeApp);
        });

        btnPlats.setOnMousePressed(actionEvent -> {
            splitpane.getItems().add(dishsApp);
            splitpane.getItems().remove(employeApp);
            splitpane.getItems().remove(tablesApp);
            splitpane.getItems().remove(financeApp);
        });

        btnTables.setOnMousePressed(actionEvent -> {
            splitpane.getItems().add(tablesApp);
            splitpane.getItems().remove(employeApp);
            splitpane.getItems().remove(dishsApp);
            splitpane.getItems().remove(financeApp);
        });

    }
}
