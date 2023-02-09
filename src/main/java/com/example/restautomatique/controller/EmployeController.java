package com.example.restautomatique.controller;

import com.example.restautomatique.model.Employe;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.ResourceBundle;

import static java.lang.Integer.parseInt;
import org.json.*;

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

        ObservableList<Employe> employeesModels = FXCollections.observableArrayList();
        String path = "src/main/resources/com/example/restautomatique/Fichiers_JSON/";

        //On récupère le fichier JSON actuel et on le stocke (si il existe)
        String jsonEmployes = "[]";
        try {
            jsonEmployes = new String(Files.readAllBytes(Paths.get(path + "employe.json")));
        } catch (IOException e) {
            System.out.println("Fichier JSON n'existe pas encore. Création du fichier...");
        }

        //On ajoute les valeurs du JSON à l'observable list
        JSONArray arrayEmployes = new JSONArray(jsonEmployes);
        for (int i = 0; i < arrayEmployes.length(); i++) {
            JSONObject objetEmployes = arrayEmployes.getJSONObject(i);
            Employe employe = new Employe(
                    objetEmployes.getString("name"),
                    objetEmployes.getInt("age"),
                    objetEmployes.getString("role"),
                    objetEmployes.getInt("hours")
            );
            employeesModels.add(employe);
        }

        //On remplis les cellules du tableau principal
        columnName.setCellValueFactory(new PropertyValueFactory<Employe, String>("name"));
        columnAge.setCellValueFactory(new PropertyValueFactory<Employe, String>("age"));
        columnJob.setCellValueFactory(new PropertyValueFactory<Employe, String>("role"));
        columnWorkHours.setCellValueFactory(new PropertyValueFactory<Employe, String>("hour"));
        tableEmployee.setItems(employeesModels);

        //Si le formulaire est validé, on ajoute les valeurs dans le tableau et le JSON
        btnAdd.setOnMousePressed( actionEvent -> {
            String filedNameValue = filedName.getText();
            String fieldAgeValue = fieldAge.getText();
            String fieldJobValue = fieldJob.getText();
            String fieldWorkHoursValue = fieldWorkHours.getText();

            boolean conditionAdd = (!Objects.equals(filedNameValue, "") && !Objects.equals(fieldAgeValue, "") && !Objects.equals(fieldJobValue, "") && !Objects.equals(fieldWorkHoursValue, ""));

            //Si champs remplis, on rajoute l'employé
            if (conditionAdd) {
                Employe new_employe = new Employe(filedNameValue, Integer.parseInt(fieldAgeValue), fieldJobValue, Integer.parseInt(fieldWorkHoursValue));
                employeesModels.add(new_employe);
            } else if (Objects.equals(fieldWorkHoursValue, "")) {
                Employe new_employe = new Employe(filedNameValue, Integer.parseInt(fieldAgeValue), fieldJobValue, 0);
                System.out.println("condition 0: " + new_employe);
                employeesModels.add(new_employe);
            }

            //On crée un objet Json
            JSONObject employeJson = new JSONObject();
            employeJson.put("name",filedNameValue);
            employeJson.put("role",fieldJobValue);
            if (Objects.equals(fieldWorkHoursValue, "")) {
                employeJson.put("hours", "0");
            } else {
                employeJson.put("hours", fieldWorkHoursValue);
            }
            employeJson.put("age",fieldAgeValue);

            //On réutilise l'array Json à partir de ce qui existe déjà avec le nouvel objet, et on crée/update le fichier
            arrayEmployes.put(employeJson);
            try (PrintWriter out = new PrintWriter(new FileWriter(path+"employe.json"))) {
                System.out.println(fieldWorkHoursValue);
                out.write(arrayEmployes.toString());
            } catch (Exception e) {
                System.out.println("Echec: pas de mise à jour du JSON.");
            }
        });

        //On supprime la ligne de l'employé dans le tableau, dans l'obsList et le JSON.
        btnDelete.setOnMousePressed(actionEvent -> {
            TablePosition selectCellSupr = tableEmployee.getSelectionModel().getSelectedCells().get(0);
            employeesModels.remove(selectCellSupr.getRow());
            arrayEmployes.remove(selectCellSupr.getRow());
            try (PrintWriter out = new PrintWriter(new FileWriter(path+"employe.json"))) {
                out.write(arrayEmployes.toString());
            } catch (Exception e) {
                System.out.println("Echec: ligne non supprimée.");
            }
        });
    }
}