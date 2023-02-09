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
    private Button btnCommandes;
    
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

        int[] value = {25,0};
        new Thread(() -> {
            while (true) {
                Platform.runLater(() -> {
                 chrono.setText(Integer.toString(value[0]) + " : " + Integer.toString(value[1]));
                });

                if(value[0] == 15) {
                    Platform.runLater(() -> {
                        btnCommandes.setDisable(true);
                    });
                }
                if (value[0] == 0 && value[1] == 0) {
                    break;
                }
                else if (value[1] == 0) {
                    value[1] = 59;
                    value[0]--;
                }
                value[1]--;

                try {
                    Thread.sleep(40);
                } catch (InterruptedException e) {
                    System.out.println(e);
                    throw new RuntimeException(e);
                }
            }
        }).start();

    }


        public Label getChrono () {
            return chrono;
        }

        public void changeChrono (String chronoTime){
            chrono.setText(chronoTime);
        }
    }

