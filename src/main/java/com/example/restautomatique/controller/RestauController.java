package com.example.restautomatique.controller;

import com.example.restautomatique.model.Employe;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.json.*;

import static java.lang.Integer.parseInt;

public class RestauController {
    @FXML
    private TextField iptName;
    @FXML
    private TextField iptRole;
    @FXML
    private TextField iptHours;
    @FXML
    private TextField iptAge;

    @FXML
    protected void newEmployee() {

        //On récup le texte des cases
        String nom = iptName.getText();
        String role = iptRole.getText();
        int heures = parseInt(iptHours.getText());
        int age = parseInt(iptAge.getText());

        //On crée un nouvel objet employé
        Employe test = new Employe(nom,age,role,heures);
        System.out.println(test.toString());

        //Si le fichier n'existe pas, on le crée
        String path = "src/main/resources/com/example/restautomatique/";
        JSONArray employeJsonArray = new JSONArray();
        try {
            String employeJson = new String(Files.readAllBytes(Paths.get(path+"employe.json")));
            employeJsonArray = new JSONArray(employeJson);
        } catch(IOException e) {
            System.out.println("Fichier n'existe pas encore.");
        }

        //On crée un objet Json
        JSONObject employeJson = new JSONObject();
        employeJson.put("name",nom);
        employeJson.put("role",role);
        employeJson.put("hours",heures);
        employeJson.put("age",age);

        //On crée un array Json à partir de ce qui existe déjà avec le nouvel objet, et on crée/update le fichier
        employeJsonArray.put(employeJson);
        try (PrintWriter out = new PrintWriter(new FileWriter(path+"employe.json"))) {
            out.write(employeJsonArray.toString());
        } catch (Exception e) {
            System.out.println("Fail");;
        }

    }
}
