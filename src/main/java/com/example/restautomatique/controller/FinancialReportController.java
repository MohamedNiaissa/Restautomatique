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

        String guess = "sellPrice";
        System.out.println(jsonCommandes.indexOf("name"));
        System.out.println(jsonCommandes.charAt(85));

        int index = jsonCommandes.indexOf(guess);
        while (index >= 0) {
            System.out.println(index);
            index = jsonCommandes.indexOf(guess, index + 1);
        }

        ArrayList<Object> plats = new ArrayList<Object>();
        JSONArray arrayCommandes = new JSONArray(jsonCommandes);
        int sellPriceValue = 0;
        for (int v = 0; v < arrayCommandes.length(); v++) {
            var arrayPlat = arrayCommandes.getJSONObject(v).getJSONArray("plat").get(0).toString().replace("{", "").replace("}", "").split(", ");
            System.out.println(arrayCommandes.getJSONObject(v).getJSONArray("plat"));

            for (int i = 0; i < arrayPlat.length; i++) {
               //  System.out.println(arrayPlat[i]);
                int sellPrice = arrayPlat[i].indexOf(",\"preparationPrice\"");
                int p = arrayPlat[i].indexOf("\"sellPrice\":") + 12;
               // System.out.println(sellPrice);

                //System.out.println(arrayPlat[i].substring(p, sellPrice));
                int provisonarySellPrice = Integer.parseInt(arrayPlat[i].substring(p, sellPrice));

                this.sell +=provisonarySellPrice;

                int prepPrice = arrayPlat[i].indexOf(",\"picture\":");
                int lastChar = arrayPlat[i].indexOf(",\"preparationPrice\"") + 20;

               //  System.out.println(arrayPlat[i].substring(lastChar, prepPrice));

                this.preparationPrice += Integer.parseInt(arrayPlat[i].substring(lastChar, prepPrice));

            }

        //    System.out.println(sellPriceValue);

           // JSONObject objetCommandes = arrayCommandes.getJSONObject(v);

        /*    for (int j = 0; j < objetCommandes.getJSONArray("plat").length(); j++) {
                plats.add(objetCommandes.getJSONArray("plat").get(j));
            }*/
        }
       // List<String> list = new ArrayList<String>();

  /*      for (int i = 0; i < plats.size(); i++) {
            System.out.println(plats.get(i));
            JSONObject obj = new JSONObject(plats);
            System.out.println(obj);

            JSONArray array = obj.getJSONArray("name");
            for(int k = 0 ; k < array.length() ; k++){
                plats.add(array.getJSONObject(k).getString("name"));
            }
        }

        System.out.println(plats);

        String jsonPlats = "[]";
        try {
            jsonPlats = new String(Files.readAllBytes(Paths.get(path + "plat.json")));
        } catch (IOException exception) {
            System.out.println("Fichier JSON n'existe pas encore. Création du fichier...");
        }

        JSONArray arrayPlats = new JSONArray(jsonPlats);
        for (int j = 0; j < plats.size(); j++) {
            for (int o = 0; o < arrayPlats.length(); o++) {
                JSONObject objetPlats = arrayPlats.getJSONObject(o);
                if (plats.get(j).equals(objetPlats.getString("name"))) {
                    this.sell += objetPlats.getInt("sellPrice");
                    this.preparationPrice += objetPlats.getInt("preparationPrice");
                }
            }
        }*/
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
