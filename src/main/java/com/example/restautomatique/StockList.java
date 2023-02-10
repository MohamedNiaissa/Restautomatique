package com.example.restautomatique;

import com.example.restautomatique.model.Plat;
import com.example.restautomatique.model.Table;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class StockList{
    static ObservableList<Plat> plats = FXCollections.observableArrayList();
    static ObservableList<Table> tables = FXCollections.observableArrayList();

    public void refreshPlat(){
        plats.clear();
        String path = "src/main/resources/com/example/restautomatique/Fichiers_JSON/";

        //On récupère le fichier JSON actuel et on le stocke (si il existe)
        String jsonPlats = "[]";
        try {
            jsonPlats = new String(Files.readAllBytes(Paths.get(path + "plat.json")));
        } catch (
                IOException e) {
            System.out.println("Fichier JSON n'existe pas encore. Création du fichier...");
        }
        //On ajoute les valeurs du JSON à l'observable list
        JSONArray arrayPlats = new JSONArray(jsonPlats);
        for (int i = 0; i < arrayPlats.length(); i++) {
            JSONObject objetPlats = arrayPlats.getJSONObject(i);
            Plat plat = new Plat(
                    objetPlats.getString("name"),
                    objetPlats.getString("description"),
                    objetPlats.getInt("sellPrice"),
                    objetPlats.getInt("preparationPrice"),
                    objetPlats.getString("ingredient"),
                    objetPlats.getString("picture")
            );
            plats.add(plat);
        }
    }
    public void refreshTable(){
        tables.clear();
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
    }

    public ObservableList<Plat> getPlats() {
        return plats;
    }

    public ObservableList<Table> getTables() {
        return tables;
    }
}
