package com.example.incidencia;

public class Incidencia {
    private String nom, prioritat;
    public enum prioritat{
        BAIXA,
        MITJA,
        ALTA
    }

    public Incidencia() {
    }

    public Incidencia(String nom, String prioritat) {
        this.nom = nom;
        this.prioritat = prioritat;
    }

    public String getNom() {
        return nom;
    }

    public String getPrioritat() {
        return prioritat;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrioritat(String prioritat) {
        this.prioritat = prioritat;
    }
}
