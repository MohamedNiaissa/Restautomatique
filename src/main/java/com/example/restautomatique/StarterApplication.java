package com.example.restautomatique;

import com.example.restautomatique.model.Employe;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static java.lang.Integer.parseInt;

public class StarterApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StarterApplication.class.getResource("MainView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Restautomatique");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {

        String path = "src/main/resources/com/example/restautomatique/";
        String jsonEmployes = "[]";

        try{
            jsonEmployes = new String(Files.readAllBytes(Paths.get(path+"employe.json")));
        } catch(IOException e){
            System.out.println("Fichier n'existe pas :(");
        }

        JSONArray arrayEmployes = new JSONArray(jsonEmployes);
        ObservableList<Employe> obsListEmploye = FXCollections.observableArrayList();
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

        launch();
    }
}
