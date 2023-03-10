package com.example.restautomatique.model;

public class Table {
    private String size;
    private String emplacement;
    private String status;

    /**
     * Les tables du restaurant
     * @param size La taille de la table
     * @param emplacement L'emplacement de la table dans le restaurant
     * @param status Le status de la table
     */
    public Table(String size, String emplacement, String status) {
        this.size = size;
        this.emplacement = emplacement;
        if (status.equals("")) this.status = "Disponible";
        else this.status = status;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getEmplacement() {
        return emplacement;
    }

    public void setEmplacement(String emplacement) {
        this.emplacement = emplacement;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Table{" +
                "size='" + size + '\'' +
                ", emplacement='" + emplacement + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
