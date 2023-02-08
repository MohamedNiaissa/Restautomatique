package com.example.restautomatique.model;

public class Table {
    private String size;
    private String emplacement;
    private String status;

    public Table(String size, String emplacement) {
        this.size = size;
        this.emplacement = emplacement;
        this.status = "Disponible";
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
