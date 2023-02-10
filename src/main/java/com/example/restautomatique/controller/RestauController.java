package com.example.restautomatique.controller;

import com.example.restautomatique.StockList;
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
    private HBox commandeApp;
    @FXML
    private HBox employeApp;
    @FXML
    private HBox financeApp;
    @FXML
    private HBox dishsApp;
    @FXML
    private HBox tablesApp;

    @FXML
    private Button btnCommandes;
    @FXML
    private Button btnEmployes;
    @FXML
    private Button btnFinance;

    @FXML
    private SplitPane splitpane;

    @FXML
    private Button btnPlats;

    @FXML
    private Button btnTables;

    @FXML
    private VBox mainApp;

    public int[] time = {25,0};

    private StockList stock = new StockList();

    public void clear(){
        splitpane.getItems().remove(dishsApp);
        splitpane.getItems().remove(employeApp);
        splitpane.getItems().remove(financeApp);
        splitpane.getItems().remove(commandeApp);
        splitpane.getItems().remove(tablesApp);
    }
    @FXML
    private Label chrono;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        clear();
        stock.refreshPlat();
        stock.refreshTable();

        btnPlats.setOnMousePressed(actionEvent -> {
            clear();
            splitpane.getItems().add(dishsApp);
        });
        btnCommandes.setOnMousePressed( actionEvent -> {
            clear();
            splitpane.getItems().add(commandeApp);
            stock.refreshPlat();
            stock.refreshTable();
        });
        btnEmployes.setOnMousePressed( actionEvent -> {
            clear();
            splitpane.getItems().add(employeApp);
        });
        btnFinance.setOnMousePressed( actionEvent -> {
            clear();
            splitpane.getItems().add(financeApp);
        });
        btnTables.setOnMousePressed(actionEvent -> {
            clear();
            splitpane.getItems().add(tablesApp);
        });

        new Thread(() -> {
            while (true) {
                Platform.runLater(() -> chrono.setText(time[0] + " : " + time[1]));

                if(time[0] == 15 && time[1] == 0) {
                    Platform.runLater(() -> {
                        splitpane.getItems().remove(commandeApp);
                        btnCommandes.setDisable(true);
                    });
                }
                if (time[0] == 0 && time[1] == 0) {
                    break;
                }
                else if (time[1] == 0) {
                    time[1] = 59;
                    time[0]--;
                }
                time[1]--;

                try {
                    Thread.sleep(1000);
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

