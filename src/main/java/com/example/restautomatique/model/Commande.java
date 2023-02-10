package com.example.restautomatique.model;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

public class Commande {
    private ArrayList<Plat> plat;
    private Table table;
    private String creationDate;
    private String status;

    /**
     * La commande d'un client
     * @param plat La liste d'objets Plats de la commande
     * @param table La table qui a passé la commande
     * @param creationDate La date de création de la commande
     * @param status Le status de la commande
     */
    public Commande(ArrayList<Plat> plat, Table table, String creationDate, String status) {
        this.plat = plat;
        this.table = table;
        if(creationDate == ""){
            this.creationDate = LocalDateTime.now().toString();
        }else{
            this.creationDate = creationDate;
        }
        if(status == ""){
            this.status = "En attente";
        }else{
            this.status = status;
        }
    }

    public ArrayList<Plat> getPlat() {
        return plat;
    }

    public void setPlat(ArrayList<Plat> plat) {
        this.plat = plat;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getStatus() {return status;}

    public void setStatus(String status) {this.status = status;}

    @Override
    public String toString() {
        return "Commande{" +
                "plat=" + plat +
                ", table=" + table +
                ", creationDate='" + creationDate + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
