package com.example.restautomatique.controller;

import com.example.restautomatique.model.Commande;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Header;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * Class qui correspond au rapport financier du restaurant
 */
public class FinancialReportController implements Initializable {


    @FXML
    private Button btnGenerate;

    @FXML
    private TextField pathForPdf;

    @FXML
    private Label recettes;

    @FXML
    private Label depenses;

    private int sell = 0;

    private int preparationPrice = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        String path = "src/main/resources/com/example/restautomatique/Fichiers_JSON/";

        String jsonCommandes = "[]";
        try {
            jsonCommandes = new String(Files.readAllBytes(Paths.get(path + "commande.json")));
        } catch (IOException exception) {
            System.out.println("Fichier JSON n'existe pas encore. Création du fichier...");
        }


        ArrayList<Object> plats = new ArrayList<Object>();
        JSONArray arrayCommandes = new JSONArray(jsonCommandes);
        for (int v = 0; v < arrayCommandes.length(); v++) {
            // Récupération des infos du plat sous forme de tableau
            var arrayPlat = arrayCommandes.getJSONObject(v).getJSONArray("plat").get(0).toString().replace("{", "").replace("}", "").split(", ");
            Arrays.stream(arrayPlat).forEach(
                    infoPlat -> {
                        // Récupération des valeur des prix à partir des index
                        int sellPriceEnd = infoPlat.indexOf(",\"preparationPrice\"");
                        int sellPriceBegin = infoPlat.indexOf("\"sellPrice\":") + 12;
                        int provisonarySellPrice = Integer.parseInt(infoPlat.substring(sellPriceBegin, sellPriceEnd));
                        this.sell +=provisonarySellPrice;
                        int prepPrice = infoPlat.indexOf(",\"picture\":");
                        int lastChar = infoPlat.indexOf(",\"preparationPrice\"") + 20;
                        this.preparationPrice += Integer.parseInt(infoPlat.substring(lastChar, prepPrice));
                    }
            );
        }

        recettes.setText(Integer.toString(this.sell - this.preparationPrice) + " €");
        depenses.setText(Integer.toString(preparationPrice)+ " €");

            btnGenerate.setOnMousePressed(action -> {
            generatePdfForReport(pathForPdf.getText(), this.sell - this.preparationPrice, this.preparationPrice);
        });
    }

    /**
     *
     * @param path : chemin de stockage du pdf
     * @param recette : valeur de la recette total
     * @param depense : valeur de la depense total
     * Methode générant un PDF à l'endroit définit par l'utilisateur avec les données de recettes et de dépense inséré dynamiquement
     */
    private void generatePdfForReport(String path, int recette, int depense) {

        Document document = new Document();

        try
        {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(path));
            document.open();
            document.add(new Paragraph("Les chiffres de Restautomatique " ));
            document.add(new Paragraph(" " ));
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Les recettes : " + recette + "€"));
            document.add(new Paragraph("Les dépenses : " + depense + "€"));
            document.close();
            writer.close();
        } catch (DocumentException exception)
        {
            exception.printStackTrace();
        } catch (FileNotFoundException exception)
        {
            exception.printStackTrace();
        }

    }
}
