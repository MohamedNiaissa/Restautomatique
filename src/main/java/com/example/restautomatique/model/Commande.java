package com.example.restautomatique.model;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Commande {
    private ArrayList<Plat> meal;
    private Table table;
    private LocalDateTime creationDate;

    public Commande(ArrayList<Plat> meal, Table table) {
        this.meal = meal;
        this.table = table;
        this.creationDate = LocalDateTime.now();
    }

    public ArrayList<Plat> getMeal() {
        return meal;
    }

    public void setMeal(ArrayList<Plat> meal) {
        this.meal = meal;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public String toString() {
        return "Commande{" +
                "meal=" + meal +
                ", table=" + table +
                ", creationDate=" + creationDate +
                '}';
    }
}
