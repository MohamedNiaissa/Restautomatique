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
    private HBox commandeApp;
    @FXML
    private HBox employeApp;
    @FXML
    private HBox financeApp;

    @FXML
    private Button btnCommandes;
    @FXML
    private Button btnEmployes;
    @FXML
    private Button btnFinance;
    
    @FXML
    private SplitPane splitpane;

    @FXML
    private VBox mainApp;

    public void clear(){
        splitpane.getItems().remove(employeApp);
        splitpane.getItems().remove(financeApp);
        splitpane.getItems().remove(commandeApp);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        clear();

        btnCommandes.setOnMousePressed( actionEvent -> {
            clear();
            splitpane.getItems().add(commandeApp);
        });
        btnEmployes.setOnMousePressed( actionEvent -> {
            clear();
            splitpane.getItems().add(employeApp);
        });
        btnFinance.setOnMousePressed( actionEvent -> {
            clear();
            splitpane.getItems().add(financeApp);
        });
    }
}
