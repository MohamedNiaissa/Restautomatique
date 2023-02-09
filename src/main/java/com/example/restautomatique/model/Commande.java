package com.example.restautomatique.model;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

public class Commande {
    private ArrayList<Plat> plat;
    private Table table;
    private String creationDate;

    public Commande(ArrayList<Plat> plat, Table table, String creationDate) {
        this.plat = plat;
        this.table = table;
        if(creationDate == ""){
            this.creationDate = LocalDateTime.now().toString();
        }else{
            this.creationDate = creationDate;
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

    @Override
    public String toString() {
        return "Commande{" +
                "plats=" + plat +
                ", table=" + table +
                ", creationDate=" + creationDate +
                '}';
    }
}
