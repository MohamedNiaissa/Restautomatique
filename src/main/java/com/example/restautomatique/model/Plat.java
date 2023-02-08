package com.example.restautomatique.model;

import java.util.ArrayList;

public class Plat {
    private String name;
    private String description;
    private ArrayList<String> ingredient;
    private int sellPrice;
    private int preparationPrice;
    private String picture;

    public Plat(String name, String description, int sellPrice, int preparationPrice, String picture, ArrayList<String> ingredient) {
        this.name = name;
        this.description = description;
        this.sellPrice = sellPrice;
        this.preparationPrice = preparationPrice;
        this.picture = picture;
        this.ingredient = ingredient;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<String> getIngredient() {
        return ingredient;
    }

    public void setIngredient(ArrayList<String> ingredient) {
        this.ingredient = ingredient;
    }

    public int getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(int sellPrice) {
        this.sellPrice = sellPrice;
    }

    public int getPreparationPrice() {
        return preparationPrice;
    }

    public void setPreparationPrice(int preparationPrice) {
        this.preparationPrice = preparationPrice;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String toString() {
        return "Plat{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", ingredient=" + ingredient +
                ", sellPrice=" + sellPrice +
                ", preparationPrice=" + preparationPrice +
                ", picture='" + picture + '\'' +
                '}';
    }
}
