package com.example.restautomatique.model;
public class Plat {
    private String name;
    private String description;
    private String ingredient;
    private int sellPrice;
    private int preparationPrice;
    private String picture;

    /**
     * Les plats de la carte du restaurant.
     * @param name Le nom du plat
     * @param description La description du plat
     * @param sellPrice Le prix de revente du plat
     * @param preparationPrice Le coût de préparation du plat
     * @param picture Le lien de la photo du plat
     * @param ingredient Les ingrédients qui composent le plat
     */
    public Plat(String name, String description, int sellPrice, int preparationPrice, String picture, String ingredient) {
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

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
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
