package com.example.restautomatique.controller;

import com.example.restautomatique.model.Employe;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class EmployeController implements Initializable {


    @FXML
    private TableView<Employe> tableEmployee;

    @FXML
    private TableColumn<Employe, String> columnName;

    @FXML
    private TableColumn<Employe, String> columnAge;

    @FXML
    private TableColumn<Employe, String> columnJob;

    @FXML
    private TableColumn<Employe, String> columnWorkHours;

    @FXML
    private TextField filedName;

    @FXML
    private TextField fieldAge;

    @FXML
    private TextField fieldJob;

    @FXML
    private TextField fieldWorkHours;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnDelete;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

         ObservableList<Employe> employeesModels = FXCollections.observableArrayList(
                new Employe ("moi",12, "Chepchieng", 2),
                new Employe("mm",2, "Too", 3)
        );

        columnName.setCellValueFactory(new PropertyValueFactory<Employe, String>("name"));
        columnAge.setCellValueFactory(new PropertyValueFactory<Employe, String>("age"));
        columnJob.setCellValueFactory(new PropertyValueFactory<Employe, String>("role"));
        columnWorkHours.setCellValueFactory(new PropertyValueFactory<Employe, String>("hour"));
        tableEmployee.setItems(employeesModels);

        btnAdd.setOnMousePressed( actionEvent -> {
            String filedNameValue = filedName.getText();
            String fieldAgeValue = fieldAge.getText();
            String fieldJobValue = fieldJob.getText();
            String fieldWorkHoursValue = fieldWorkHours.getText();

            boolean conditionAdd = (!Objects.equals(filedNameValue, "") && !Objects.equals(fieldAgeValue, "") && !Objects.equals(fieldJobValue, "") && !Objects.equals(fieldWorkHoursValue, ""));

            if (conditionAdd) {
                Employe new_employe = new Employe(filedNameValue, Integer.parseInt(fieldAgeValue), fieldJobValue, Integer.parseInt(fieldWorkHoursValue));
                employeesModels.add(new_employe);
            } else if (Objects.equals(fieldWorkHoursValue, "")) {
                System.out.println(fieldAge.getText());
                Employe new_employe = new Employe(filedName.getText(), Integer.parseInt(fieldAgeValue), fieldJobValue, 0);
                employeesModels.add(new_employe);
            }
        });

        btnDelete.setOnMousePressed(actionEvent -> {
            TablePosition selectCellSupr = tableEmployee.getSelectionModel().getSelectedCells().get(0);
            employeesModels.remove(selectCellSupr.getRow());
        });

    }


}
