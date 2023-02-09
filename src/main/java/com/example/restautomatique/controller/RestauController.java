package com.example.restautomatique.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class RestauController extends Thread  implements Initializable {

    @FXML
    private HBox employeApp;

    @FXML
    private Button btnEmployes;
    
    @FXML
    private SplitPane splitpane;

    @FXML
    private VBox mainApp;

    @FXML
    private Label chrono;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        splitpane.getItems().remove(employeApp);

        btnEmployes.setOnMousePressed( actionEvent -> {
            splitpane.getItems().add(employeApp);
        });

        new Thread(() -> {
            int value;
            for (int i = 30; i >=  0 ; i--) {
                System.out.println(i);
                chrono.getText();
                chrono.setText(Integer.toString(i));
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println(e);
                    throw new RuntimeException(e);
                }
            }
         /*   Platform.runLater(() -> {

            });*/

        }).start();
    }


        public Label getChrono () {
            return chrono;
        }

        public void changeChrono (String chronoTime){
            chrono.setText(chronoTime);
        }
    }

