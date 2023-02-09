package com.example.restautomatique.controller;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Class qui correspond au rapport financier du restaurant
 */
public class FinancialReportController implements Initializable {


    @FXML
    private Button btnGenerate;

    @FXML
    private TextField pathForPdf;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        Integer i = 3;
        int e = 7;

        btnGenerate.setOnMousePressed(action -> {
            generatePdfForReport(pathForPdf.getText(), i, e);
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
            document.add(new Paragraph("Les recettes : " + recette));
            document.add(new Paragraph("Les dépenses : " + depense));
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
