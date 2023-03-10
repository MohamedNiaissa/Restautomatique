package com.example.restautomatique.model;

public class Employe {
    String name;
    int age;
    String role;
    int hour;

    /**
     * Les employés du restaurant.
     * @param name Le nom de l'employé
     * @param age L'age de l'employé
     * @param role Le poste de l'employé
     * @param hour Le temps de travail de l'employé
     */
    public Employe(String name, int age, String role, int hour) {
        this.name = name;
        this.age = age;
        this.role = role;
        this.hour = hour;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    @Override
    public String toString() {
        return "Employe{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", role='" + role + '\'' +
                ", hour=" + hour +
                '}';
    }
}
