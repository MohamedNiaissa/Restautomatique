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
