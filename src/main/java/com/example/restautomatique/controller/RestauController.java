package com.example.restautomatique.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class RestauController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}
