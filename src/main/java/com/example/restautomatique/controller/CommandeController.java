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
import java.lang.reflect.Array;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

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
    private ListView listPlats;
    @FXML
    private ChoiceBox boxTable;

    @FXML
    private Button btnAdd;
    @FXML
    private Button btnDelete;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //Pour chaque ligne dans JSON, on crée un objet
        ObservableList<Commande> commandesModels = FXCollections.observableArrayList();
        ArrayList<Plat> platsModels = new ArrayList<Plat>();
        ArrayList<Table> tablesModels = new ArrayList<Table>();
        String path = "src/main/resources/com/example/restautomatique/Fichiers_JSON/";

        String jsonTables = "[]";
        try {
            jsonTables = new String(Files.readAllBytes(Paths.get(path + "table.json")));
        } catch (IOException e) {
            System.out.println("Fichier JSON n'existe pas encore. Création du fichier...");
        }
        JSONArray arrayTables = new JSONArray(jsonTables);
        for (int i = 0; i < arrayTables.length(); i++) {
            JSONObject objetTables = arrayTables.getJSONObject(i);
            Table table = new Table(
                    objetTables.getString("emplacement"),
                    objetTables.getString("size"),
                    objetTables.getString("status")
            );
            tablesModels.add(table);
            boxTable.getItems().add(table.getEmplacement());
        }

        listPlats.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        String jsonPlats = "[]";
        try {
            jsonPlats = new String(Files.readAllBytes(Paths.get(path + "plat.json")));
        } catch (IOException e) {
            System.out.println("Fichier JSON n'existe pas encore. Création du fichier...");
        }
        JSONArray arrayPlats = new JSONArray(jsonPlats);
        for (int i = 0; i < arrayPlats.length(); i++) {
            JSONObject objetPlats = arrayPlats.getJSONObject(i);
            Plat plat = new Plat(
                    objetPlats.getString("name"),
                    objetPlats.getString("description"),
                    objetPlats.getInt("sellPrice"),
                    objetPlats.getInt("preparationPrice"),
                    objetPlats.getString("picture"),
                    objetPlats.getString("ingredient")
            );
            platsModels.add(plat);
            listPlats.getItems().add(plat.getName());
        }

        String jsonCommandes = "[]";
        try {
            jsonCommandes = new String(Files.readAllBytes(Paths.get(path + "commande.json")));
        } catch (IOException e) {
            System.out.println("Fichier JSON n'existe pas encore. Création du fichier...");
        }

        //On ajoute les valeurs du JSON à l'observable list
        JSONArray arrayCommandes = new JSONArray(jsonCommandes);
        for (int i = 0; i < arrayCommandes.length(); i++) {
            //JSONObject objetCommandes = arrayCommandes.getJSONObject(i);
            //JSONArray testtt = objetCommandes.getJSONArray("plat");
            //ArrayList<Plat> test2 = new ArrayList<Plat>();
            //for (Object plat : testtt) {
            //    Plat platObjet = new Plat(plat);
            //    System.out.println(plat);
            //}
            //Commande commande = new Commande(
                    //objetCommandes.getJSONArray("plat"),
                    //objetCommandes.getJSONArray("table"),
                    //objetCommandes.getString("date")
            //);
            //commandesModels.add(commande);
        }

        //On remplis les cellules du tableau principal
        columnPlats.setCellValueFactory(new PropertyValueFactory<Commande, String>("plat"));
        columnTable.setCellValueFactory(new PropertyValueFactory<Commande, String>("table"));
        columnDate.setCellValueFactory(new PropertyValueFactory<Commande, String>("creationDate"));
        tableCommande.setItems(commandesModels);


        //Si le formulaire est validé, on ajoute les valeurs dans le tableau et le JSON
        btnAdd.setOnMousePressed( actionEvent -> {

            ObservableList<String> selectedPlatsObs =  listPlats.getSelectionModel().getSelectedItems();
            //System.out.println(selectedPlatsObs);
            ArrayList<Plat> selectedPlats = new ArrayList<Plat>();

            for(String plat : selectedPlatsObs){
                List<Plat> test = platsModels.stream().filter(plat1 -> plat1.getName() == plat).collect(Collectors.toList());
                System.out.println("selected item: " + test.get(0));
                selectedPlats.add(test.get(0));
            }

            Table selectedTable = tablesModels.get(boxTable.getSelectionModel().getSelectedIndex());

            Commande new_commande = new Commande(selectedPlats,selectedTable,"");
            commandesModels.add(new_commande);

            //On crée un objet Json
            JSONObject commandeJson = new JSONObject();
            commandeJson.put("plat",selectedPlats);
            commandeJson.put("table",selectedTable);
            commandeJson.put("date",LocalDateTime.now());

            //On réutilise l'array Json à partir de ce qui existe déjà avec le nouvel objet, et on crée/update le fichier
            arrayCommandes.put(commandeJson);
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
