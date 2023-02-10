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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.lang.Integer.parseInt;
import org.json.*;

public class CommandeController implements Initializable {

    @FXML
    private TableView<Commande> tableauCommande;
    @FXML
    private TableColumn<Commande, String> columnTable;
    @FXML
    private TableColumn<Commande, String> columnPlats;
    @FXML
    private TableColumn<Commande, String> columnDate;
    @FXML
    private TableColumn<Commande, String> columnStatus;

    @FXML
    private ListView listPlats;
    @FXML
    private ChoiceBox boxTable;

    @FXML
    private Button btnAdd;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnStatusCancel;
    @FXML
    private Button btnStatusAtt;
    @FXML
    private Button btnStatusPrep;
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
            JSONObject objetCommandes = arrayCommandes.getJSONObject(i);

            //On récup les plats du Json
            JSONArray platsCommande = objetCommandes.getJSONArray("plat");
            ArrayList<Plat> listeDePlats = new ArrayList<Plat>();
            for (int j = 0; j < platsCommande.length(); j++) {
                Plat platCommande = new Plat(
                        platsCommande.getJSONObject(j).getString("name"),
                        platsCommande.getJSONObject(j).getString("description"),
                        platsCommande.getJSONObject(j).getInt("sellPrice"),
                        platsCommande.getJSONObject(j).getInt("preparationPrice"),
                        platsCommande.getJSONObject(j).getString("picture"),
                        platsCommande.getJSONObject(j).getString("ingredient")
                );
                listeDePlats.add(platCommande);
            }
            //On récup la table du Json
            String tableCommandeJson = objetCommandes.get("table").toString();

            Pattern pattern = Pattern.compile("size='(.*?)', emplacement='(.*?)', status='(.*?)'");
            Matcher matcher = pattern.matcher(tableCommandeJson);

            Table tableDeLaCommande = new Table("","","");

            if (matcher.find()) {
                String size = matcher.group(1);
                String emplacement = matcher.group(2);
                String status = matcher.group(3);

                tableDeLaCommande = new Table(size,emplacement,status);
            }

            //On ajoute le tout dans le model
            Commande commande = new Commande(
                    listeDePlats,
                    tableDeLaCommande,
                    objetCommandes.getString("date"),
                    objetCommandes.getString("status")
            );
            commandesModels.add(commande);
        }

        //On remplis les cellules du tableau principal
        columnPlats.setCellValueFactory(new PropertyValueFactory<Commande, String>("plat"));
        columnTable.setCellValueFactory(new PropertyValueFactory<Commande, String>("table"));
        columnDate.setCellValueFactory(new PropertyValueFactory<Commande, String>("creationDate"));
        columnStatus.setCellValueFactory(new PropertyValueFactory<Commande, String>("status"));
        tableauCommande.setItems(commandesModels);
        System.out.println(commandesModels);


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

            Table selectedTable1 = tablesModels.get(boxTable.getSelectionModel().getSelectedIndex());
            Table selectedTable2 = new Table(selectedTable1.getSize(),selectedTable1.getEmplacement(),selectedTable1.getStatus());
            System.out.println(selectedTable2);

            Commande new_commande = new Commande(selectedPlats,selectedTable2,"","En attente");
            commandesModels.add(new_commande);

            //On crée un objet Json
            JSONObject commandeJson = new JSONObject();
            commandeJson.put("plat",selectedPlats);
            commandeJson.put("table",selectedTable2);
            commandeJson.put("date",LocalDateTime.now());
            commandeJson.put("status","En attente");

            //On réutilise l'array Json à partir de ce qui existe déjà avec le nouvel objet, et on crée/update le fichier
            arrayCommandes.put(commandeJson);
            try (PrintWriter out = new PrintWriter(new FileWriter(path+"commande.json"))) {
                out.write(arrayCommandes.toString());
            } catch (Exception e) {
                System.out.println("Echec: pas de mise à jour du JSON.");
            }
        });

        //On supprime le status de la commande dans le tableau, dans l'obsList et le JSON.
        btnDelete.setOnMousePressed(actionEvent -> {
            TablePosition selectCellSupr = tableauCommande.getSelectionModel().getSelectedCells().get(0);
            commandesModels.remove(selectCellSupr.getRow());
            arrayCommandes.remove(selectCellSupr.getRow());
            try (PrintWriter out = new PrintWriter(new FileWriter(path+"commande.json"))) {
                out.write(arrayCommandes.toString());
            } catch (Exception e) {
                System.out.println("Echec: ligne non supprimée.");
            }
        });

        //On change le status de la commande dans le tableau, dans l'obsList et le JSON, en "En attente".
        btnStatusAtt.setOnMousePressed(actionEvent -> {
            TablePosition selectCellStatAtt = tableauCommande.getSelectionModel().getSelectedCells().get(0);
            Commande commandeSelect = new Commande(
                    commandesModels.get(selectCellStatAtt.getRow()).getPlat(),
                    commandesModels.get(selectCellStatAtt.getRow()).getTable(),
                    commandesModels.get(selectCellStatAtt.getRow()).getCreationDate(),
                    "En attente"
            );
            commandesModels.set(selectCellStatAtt.getRow(), commandeSelect);
            arrayCommandes.put(selectCellStatAtt.getRow(), commandeSelect);
            try (PrintWriter out = new PrintWriter(new FileWriter(path+"commande.json"))) {
                out.write(arrayCommandes.toString());
            } catch (Exception e) {
                System.out.println("Echec: status non changé.");
            }
        });

        //On change le status de la commande dans le tableau, dans l'obsList et le JSON, en "Préparée".
        btnStatusPrep.setOnMousePressed(actionEvent -> {
            TablePosition selectCellStatAtt = tableauCommande.getSelectionModel().getSelectedCells().get(0);
            Commande commandeSelect = new Commande(
                    commandesModels.get(selectCellStatAtt.getRow()).getPlat(),
                    commandesModels.get(selectCellStatAtt.getRow()).getTable(),
                    commandesModels.get(selectCellStatAtt.getRow()).getCreationDate(),
                    "Préparée"
            );
            commandesModels.set(selectCellStatAtt.getRow(), commandeSelect);
            arrayCommandes.put(selectCellStatAtt.getRow(), commandeSelect);
            try (PrintWriter out = new PrintWriter(new FileWriter(path+"commande.json"))) {
                out.write(arrayCommandes.toString());
            } catch (Exception e) {
                System.out.println("Echec: status non changé.");
            }
        });

        //On change le status de la commande dans le tableau, dans l'obsList et le JSON, en "Annulée".
        btnStatusCancel.setOnMousePressed(actionEvent -> {
            TablePosition selectCellStatAtt = tableauCommande.getSelectionModel().getSelectedCells().get(0);
            Commande commandeSelect = new Commande(
                    commandesModels.get(selectCellStatAtt.getRow()).getPlat(),
                    commandesModels.get(selectCellStatAtt.getRow()).getTable(),
                    commandesModels.get(selectCellStatAtt.getRow()).getCreationDate(),
                    "Annulée"
            );
            commandesModels.set(selectCellStatAtt.getRow(), commandeSelect);
            arrayCommandes.put(selectCellStatAtt.getRow(), commandeSelect);
            try (PrintWriter out = new PrintWriter(new FileWriter(path+"commande.json"))) {
                out.write(arrayCommandes.toString());
            } catch (Exception e) {
                System.out.println("Echec: status non changé.");
            }
        });

    }
}
