package com.example.restautomatique.controller;

import com.example.restautomatique.model.Plat;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
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
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ObservableList<Plat> plats = FXCollections.observableArrayList();
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        descrColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        sellColumn.setCellValueFactory(new PropertyValueFactory<>("sellPrice"));
        preparedColumn.setCellValueFactory(new PropertyValueFactory<>("preparationPrice"));
        ingredientColumn.setCellValueFactory(new PropertyValueFactory<>("ingredient"));
        pictureColumn.setCellValueFactory(new PropertyValueFactory<>("picture"));
        dishTab.setItems(plats);

        addPlatButton.setOnMousePressed( e -> {
           Plat plat = new Plat(nameNew.getText(), descrNew.getText(), Integer.parseInt(sellNew.getText()), Integer.parseInt(preparedNew.getText()), pictureNew.getText(), ingredientNew.getText());
            clearForm();
            plats.add(plat);
            });

        dishTab.setOnMousePressed( e -> {
            TablePosition selectCell = dishTab.getSelectionModel().getSelectedCells().get(0);
            nameNew.setText(plats.get(selectCell.getRow()).getName());
            descrNew.setText(plats.get(selectCell.getRow()).getDescription());
            sellNew.setText(plats.get(selectCell.getRow()).getSellPrice()+"");
            preparedNew.setText(plats.get(selectCell.getRow()).getPreparationPrice()+"");
            ingredientNew.setText(plats.get(selectCell.getRow()).getIngredient());
            pictureNew.setText(plats.get(selectCell.getRow()).getPicture());
        });

        delPlatButton.setOnMousePressed( e -> {
            TablePosition selectCellSupr = dishTab.getSelectionModel().getSelectedCells().get(0);
            plats.remove(selectCellSupr.getRow());
            clearForm();
        });
    }
    public void clearForm() {
        nameNew.clear();
        descrNew.clear();
        sellNew.clear();
        preparedNew.clear();
        ingredientNew.clear();
        pictureNew.clear();
    }
}
