package com.example.restautomatique.controller;

import com.example.restautomatique.model.Commande;
import com.example.restautomatique.model.Plat;
import com.example.restautomatique.model.Table;
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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

import static java.lang.Integer.parseInt;
import org.json.*;

public class CommandeController implements Initializable {

    @FXML
    private TableView<Commande> tableCommande;
    @FXML
    private TableColumn<Commande, String> columnTable;
    @FXML
    private TableColumn<Commande, String> columnPlats;
    @FXML
    private TableColumn<Commande, String> columnDate;

    @FXML
    private TextField fieldTable;
    @FXML
    private TextField fieldPlats;

    @FXML
    private Button btnAdd;
    @FXML
    private Button btnDelete;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ObservableList<Commande> commandesModels = FXCollections.observableArrayList();
        String path = "src/main/resources/com/example/restautomatique/Fichiers_JSON/";

        //On récupère le fichier JSON actuel et on le stocke (si il existe)
        String jsonCommandes = "[]";
        try {
            jsonCommandes = new String(Files.readAllBytes(Paths.get(path + "commande.json")));
        } catch (IOException e) {
            System.out.println("Fichier JSON n'existe pas encore. Création du fichier...");
        }

        //DONC ingredients = liste d'ingredients, plats = liste de plats, plat = 1 plat de pates, table = obj table

        ArrayList<Plat> plats = new ArrayList<Plat>();

        ArrayList<String> ingredients = new ArrayList<String>();

        Plat plat1 = new Plat("pates", "jsp", 10, 8, "non", "ingredients");
        plats.add(plat1);

        Table table = new Table("grande","arriere","");

        //On ajoute les valeurs du JSON à l'observable list
        JSONArray arrayCommandes = new JSONArray(jsonCommandes);
        for (int i = 0; i < arrayCommandes.length(); i++) {
            JSONObject objetCommandes = arrayCommandes.getJSONObject(i);
            Commande commande = new Commande(
                    plats,
                    table,
                    objetCommandes.getString("date")
            );
            commandesModels.add(commande);
        }

        //On remplis les cellules du tableau principal
        columnPlats.setCellValueFactory(new PropertyValueFactory<Commande, String>("plat"));
        columnTable.setCellValueFactory(new PropertyValueFactory<Commande, String>("table"));
        columnDate.setCellValueFactory(new PropertyValueFactory<Commande, String>("creationDate"));
        tableCommande.setItems(commandesModels);

        //Si le formulaire est validé, on ajoute les valeurs dans le tableau et le JSON
        btnAdd.setOnMousePressed( actionEvent -> {

            Commande new_commande = new Commande(plats,table,"");
            commandesModels.add(new_commande);

            //On crée un objet Json
            JSONObject employeJson = new JSONObject();
            employeJson.put("plat",plats);
            employeJson.put("table",table);
            employeJson.put("date",LocalDateTime.now());

            //On réutilise l'array Json à partir de ce qui existe déjà avec le nouvel objet, et on crée/update le fichier
            arrayCommandes.put(employeJson);
            try (PrintWriter out = new PrintWriter(new FileWriter(path+"commande.json"))) {
                out.write(arrayCommandes.toString());
            } catch (Exception e) {
                System.out.println("Echec: pas de mise à jour du JSON.");
            }
        });

        //On supprime la ligne de l'employé dans le tableau, dans l'obsList et le JSON.
        btnDelete.setOnMousePressed(actionEvent -> {
            TablePosition selectCellSupr = tableCommande.getSelectionModel().getSelectedCells().get(0);
            commandesModels.remove(selectCellSupr.getRow());
            arrayCommandes.remove(selectCellSupr.getRow());
            try (PrintWriter out = new PrintWriter(new FileWriter(path+"commande.json"))) {
                out.write(arrayCommandes.toString());
            } catch (Exception e) {
                System.out.println("Echec: ligne non supprimée.");
            }
        });
    }
}
