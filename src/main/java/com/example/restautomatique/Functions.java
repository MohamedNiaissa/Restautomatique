/*package com.example.restautomatique;

import com.example.restautomatique.model.Employe;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Functions {
    static ObservableList<Employe> obsListEmploye = FXCollections.observableArrayList();

    public static void fetchJson(String json) {
        String path = "src/main/resources/com/example/restautomatique/";
        String jsonEmployes = "[]";

        try {
            jsonEmployes = new String(Files.readAllBytes(Paths.get(path + json + ".json")));
        } catch (
                IOException e) {
            System.out.println("Fichier n'existe pas :(");
        }

        JSONArray arrayEmployes = new JSONArray(jsonEmployes);

        for (int i = 0; i < arrayEmployes.length(); i++) {
            JSONObject objetEmployes = arrayEmployes.getJSONObject(i);
            Employe employe = new Employe(
                    objetEmployes.getString("name"),
                    objetEmployes.getInt("age"),
                    objetEmployes.getString("role"),
                    objetEmployes.getInt("hours")
            );
            obsListEmploye.add(employe);

            System.out.println(employe);
        }
    }
}*/
