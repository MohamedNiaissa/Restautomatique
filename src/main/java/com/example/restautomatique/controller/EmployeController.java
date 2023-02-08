package com.example.restautomatique.controller;

import com.example.restautomatique.model.Employe;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {




        columnName.setCellValueFactory(new PropertyValueFactory<Employe, String>("name"));
        columnAge.setCellValueFactory(new PropertyValueFactory<Employe, String>("age"));
        columnJob.setCellValueFactory(new PropertyValueFactory<Employe, String>("role"));
        columnWorkHours.setCellValueFactory(new PropertyValueFactory<Employe, String>("hour"));
        tableEmployee.setItems(employeesModels);

//        ObservableList<Book> books = FXCollections.observableArrayList();

        btnAdd.setOnMousePressed( actionEvent -> {
            String filedNameValue = filedName.getText();
            String fieldAgeValue = fieldAge.getText();
            String fieldJobValue = fieldJob.getText();
            String fieldWorkHoursValue = fieldWorkHours.getText();

            boolean conditionAdd = (!Objects.equals(filedNameValue, "") && !Objects.equals(fieldAgeValue, "") && !Objects.equals(fieldJobValue, "") && !Objects.equals(fieldWorkHoursValue, ""));


            if (conditionAdd) {
                Employe new_employe = new Employe(filedNameValue, Integer.parseInt(fieldAgeValue), fieldJobValue, Integer.parseInt(fieldWorkHoursValue));
                // Employe new_employe = new Employe("filedNameValue", 2, "fieldJobValue", 3);
                employeesModels.add(new_employe);
            } else if (Objects.equals(fieldWorkHoursValue, "")) {
                System.out.println(fieldAge.getText());
                 Employe new_employe = new Employe(filedName.getText(), Integer.parseInt(fieldAgeValue), fieldJobValue, 0);
               // Employe new_employe = new Employe("defaut 0", 2, "fieldJobValue", 3);
                System.out.println(new_employe);

                employeesModels.add(new_employe);
            }
        });
    }


    private ObservableList<Employe> employeesModels = FXCollections.observableArrayList(
            new Employe ("moi",12, "Chepchieng", 2),
            new Employe("mm",2, "Too", 3)
    );

}
