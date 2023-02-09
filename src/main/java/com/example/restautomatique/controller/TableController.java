package com.example.restautomatique.controller;

import com.example.restautomatique.model.Table;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ResourceBundle;

public class TableController implements Initializable {
    @FXML
    private TextField sizeNew;
    @FXML
    private TextField placeNew;
    @FXML
    private Button addTableButton;
    @FXML
    private Button delTableButton;
    @FXML
    private Button libreTableButton;
    @FXML
    private TableView<Table> tableTab;
    @FXML
    private TableColumn<Table, String> sizeColumn;
    @FXML
    private TableColumn<Table, String> placeColumn;
    @FXML
    private TableColumn<Table, String> dispoColumn;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        /* Lie la liste tables avec le tableau tableTab */
        ObservableList<Table> tables = FXCollections.observableArrayList();
        sizeColumn.setCellValueFactory(new PropertyValueFactory<>("size"));
        placeColumn.setCellValueFactory(new PropertyValueFactory<>("emplacement"));
        dispoColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        tableTab.setItems(tables);

        String path = "src/main/resources/com/example/restautomatique/Fichiers_JSON/";

        //On récupère le fichier JSON actuel et on le stocke (si il existe)
        String jsonTables = "[]";
        try {
            jsonTables = new String(Files.readAllBytes(Paths.get(path + "table.json")));
        } catch (IOException e) {
            System.out.println("Fichier JSON n'existe pas encore. Création du fichier...");
        }
        //On ajoute les valeurs du JSON à l'observable list
        JSONArray arrayTables = new JSONArray(jsonTables);
        for (int i = 0; i < arrayTables.length(); i++) {
            JSONObject objetTables = arrayTables.getJSONObject(i);
            Table table = new Table(
                    objetTables.getString("size"),
                    objetTables.getString("emplacement"),
                    objetTables.getString("status")
            );
            tables.add(table);
        }

        /* Ajoute une instance de Table a la liste tables*/
        addTableButton.setOnMousePressed( e -> {
            String size = sizeNew.getText();
            String place = placeNew.getText();
           Table table = new Table(
                   size,
                   place,
                   "");
            clearForm();
            tables.add(table);
            //On crée un objet Json
            JSONObject tableJson = new JSONObject();
            tableJson.put("size",size);
            tableJson.put("emplacement",place);
            tableJson.put("status", "Disponible");

            //On réutilise l'array Json à partir de ce qui existe déjà avec le nouvel objet, et on crée/update le fichier
            arrayTables.put(tableJson);
            try (PrintWriter out = new PrintWriter(new FileWriter(path+"table.json"))) {
                out.write(arrayTables.toString());
            } catch (Exception ex) {
                System.out.println("Echec: pas de mise à jour du JSON.");
            }
            });

        /* Remplis les champs du formulaires avec les informations du Table sélectionné dans le tableau tableTab*/
        tableTab.setOnMousePressed( e -> {
            TablePosition selectCell = tableTab.getSelectionModel().getSelectedCells().get(0);
            sizeNew.setText(tables.get(selectCell.getRow()).getSize());
            placeNew.setText(tables.get(selectCell.getRow()).getEmplacement());
        });

        /* Supprime la Table sélectionné dans le tableau tableTab de la liste tables */
        delTableButton.setOnMousePressed( e -> {
            TablePosition selectCellSupr = tableTab.getSelectionModel().getSelectedCells().get(0);
            tables.remove(selectCellSupr.getRow());
            arrayTables.remove(selectCellSupr.getRow());
            try (PrintWriter out = new PrintWriter(new FileWriter(path+"table.json"))) {
                out.write(arrayTables.toString());
            } catch (Exception ex) {
                System.out.println("Echec: ligne non supprimée.");
            }
            clearForm();
        });
        /* Change le status de la table sélectionné en Disponible au cas où le changement n'est pas été effectué ailleurs*/
        libreTableButton.setOnMousePressed( e -> {
            TablePosition selectCellLibre = tableTab.getSelectionModel().getSelectedCells().get(0);
            Table table = new Table(
                    tables.get(selectCellLibre.getRow()).getSize(),
                    tables.get(selectCellLibre.getRow()).getEmplacement(),
                    "Disponible");
            tables.remove(selectCellLibre.getRow());
            tables.add(table);
            arrayTables.getJSONObject(selectCellLibre.getRow()).put("status", "Disponible");
            try (PrintWriter out = new PrintWriter(new FileWriter(path+"table.json"))) {
                out.write(arrayTables.toString());
            } catch (Exception ex) {
                System.out.println("Echec: ligne non supprimée.");
            }
        });
    }

    /* Efface le contenu des champs du formulaire
     * Utiliser lors de l'ajout et la suppresion de Table */
    public void clearForm() {
        sizeNew.clear();
        placeNew.clear();
    }
}
