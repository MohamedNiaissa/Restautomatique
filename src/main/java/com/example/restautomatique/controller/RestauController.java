package com.example.restautomatique.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

public class RestauController implements Initializable {
    @FXML
    private HBox Dishs;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Dishs.setOpacity(0);
    }

    @FXML
    protected void onPlatsButtonClick() {
        if (Dishs.getOpacity() == 0) Dishs.setOpacity(1);
        else Dishs.setOpacity(0);
    }
}
