package com.example.restautomatique.controller;

import com.example.restautomatique.StockList;
import com.example.restautomatique.model.Plat;
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
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

public class PlatController implements Initializable {
    @FXML
    private TextField nameNew;
    @FXML
    private TextArea descrNew;
    @FXML
    private TextField sellNew;
    @FXML
    private TextField preparedNew;
    @FXML
    private TextField ingredientNew;
    @FXML
    private TextField pictureNew;
    @FXML
    private Button addPlatButton;
    @FXML
    private Button delPlatButton;
    @FXML
    private TableView<Plat> dishTab;
    @FXML
    private TableColumn<Plat, String> nameColumn;
    @FXML
    private TableColumn<Plat, String> descrColumn;
    @FXML
    private TableColumn<Plat, Integer> sellColumn;
    @FXML
    private TableColumn<Plat, Integer> preparedColumn;
    @FXML
    private TableColumn<Plat, String> ingredientColumn;
    @FXML
    private TableColumn<Plat, String> pictureColumn;
    @FXML
    private Button plus;
    @FXML
    private Button moins;
    @FXML
    private Label sum;
    @FXML
    private TextField search;
    private StockList stock = new StockList();
    private ObservableList<Plat> plats = stock.getPlats();
    private ObservableList<Plat> saveplats = stock.getPlats();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /* Lie la liste plats avec le tableau dishTab */
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        descrColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        sellColumn.setCellValueFactory(new PropertyValueFactory<>("sellPrice"));
        preparedColumn.setCellValueFactory(new PropertyValueFactory<>("preparationPrice"));
        ingredientColumn.setCellValueFactory(new PropertyValueFactory<>("ingredient"));
        pictureColumn.setCellValueFactory(new PropertyValueFactory<>("picture"));
        dishTab.setItems(plats);

        String path = "src/main/resources/com/example/restautomatique/Fichiers_JSON/";

        //On récupère le fichier JSON actuel et on le stocke (si il existe)
        String jsonPlats = "[]";
        try {
            jsonPlats = new String(Files.readAllBytes(Paths.get(path + "plat.json")));
        } catch (IOException e) {
            System.out.println("Fichier JSON n'existe pas encore. Création du fichier...");
        }
        //On ajoute les valeurs du JSON à l'observable list
        JSONArray arrayPlats = new JSONArray(jsonPlats);

        /* Ajoute une instance de Plat a la liste plats*/
        addPlatButton.setOnMousePressed( e -> {
            String name = nameNew.getText();
            String description = descrNew.getText();
            Integer sell = Integer.parseInt(sellNew.getText());
            Integer preparation = Integer.parseInt(preparedNew.getText());
            String picture = pictureNew.getText();
            String ingredient = ingredientNew.getText();
           Plat plat = new Plat(
                   name,
                   description,
                   sell,
                   preparation,
                   picture,
                   ingredient);
            plats.add(plat);
            //On crée un objet Json
            JSONObject platJson = new JSONObject();
            platJson.put("name",name);
            platJson.put("description",description);
            platJson.put("sellPrice", sell);
            platJson.put("preparationPrice",preparation);
            platJson.put("picture",picture);
            platJson.put("ingredient",ingredient);

            //On réutilise l'array Json à partir de ce qui existe déjà avec le nouvel objet, et on crée/update le fichier
            arrayPlats.put(platJson);
            try (PrintWriter out = new PrintWriter(new FileWriter(path+"plat.json"))) {
                out.write(arrayPlats.toString());
            } catch (Exception ex) {
                System.out.println("Echec: pas de mise à jour du JSON.");
            }
            clearFormAndSum();
            });

        /* Remplis les champs du formulaires avec les informations du Plat sélectionné dans le tableau dishTab*/
        dishTab.setOnMousePressed( e -> {
            TablePosition selectCell = dishTab.getSelectionModel().getSelectedCells().get(0);
            nameNew.setText(plats.get(selectCell.getRow()).getName());
            descrNew.setText(plats.get(selectCell.getRow()).getDescription());
            sellNew.setText(plats.get(selectCell.getRow()).getSellPrice()+"");
            preparedNew.setText(plats.get(selectCell.getRow()).getPreparationPrice()+"");
            ingredientNew.setText(plats.get(selectCell.getRow()).getIngredient());
            pictureNew.setText(plats.get(selectCell.getRow()).getPicture());
        });

        /* Tri la liste du moins cher au plus cher*/
        moins.setOnMousePressed( e -> {
            List<Plat> newplats = plats.stream()
                    .sorted(Comparator.comparingInt(Plat::getSellPrice)).toList();
            plats.clear();
            plats.addAll(newplats);
        });

        /* Tri la liste du plus cher au moins cher*/
        plus.setOnMousePressed( e -> {
            List<Plat> newplats = plats.stream()
                    .sorted((plat1,plat2)->plat2.getSellPrice()-plat1.getSellPrice())
                    .toList();
            plats.clear();
            plats.addAll(newplats);
        });

        search.setOnKeyReleased(e -> {
            if (!search.getText().equals("")){
                List<Plat> filterPlat = plats.stream()
                        .filter(Plat -> Plat.getIngredient().contains(search.getText()))
                        .toList();
                plats.clear();
                plats.addAll(filterPlat);
            } else {
                plats.clear();
                plats.addAll(saveplats);
            }
        });

        /* Supprime le Plat sélectionné dans le tableau dishTab de la liste plats */
        delPlatButton.setOnMousePressed( e -> {
            TablePosition selectCellSupr = dishTab.getSelectionModel().getSelectedCells().get(0);
            plats.remove(selectCellSupr.getRow());
            arrayPlats.remove(selectCellSupr.getRow());
            try (PrintWriter out = new PrintWriter(new FileWriter(path+"plat.json"))) {
                out.write(arrayPlats.toString());
            } catch (Exception ex) {
                System.out.println("Echec: ligne non supprimée.");
            }
            clearFormAndSum();
        });
    }

    /* Efface le contenu des champs du formulaire et recalcule la somme des SellPrices de tout les plats
     * Utiliser lors de l'ajout et la suppresion de Plat */
    public void clearFormAndSum() {
        sum.setText(plats.stream()
                .map(Plat::getSellPrice)
                .reduce(0, Integer::sum)+"€ 1xtous les plats");
        nameNew.clear();
        descrNew.clear();
        sellNew.clear();
        preparedNew.clear();
        ingredientNew.clear();
        pictureNew.clear();
        saveplats.clear();
        saveplats.addAll(plats);
        stock.refreshPlat();
    }
}
