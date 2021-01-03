package com.example.incidencia;

import java.util.Date;

public class Incidencia {
    private String nom, prioritat, description, date;

    public Incidencia() {
    }

    public Incidencia(String nom, String prioritat, String description, String date) {
        this.nom = nom;
        this.prioritat = prioritat;
        this.description=description;
        this.date=date;
    }

    public String getNom() {
        return nom;
    }

    public String getPrioritat() {
        return prioritat;
    }

    public String getDescription(){
        return description;
    }

    public String getDate(){ return date;}

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrioritat(String prioritat) {
        this.prioritat = prioritat;
    }

    public void setDescription(String description) {
        this.description=description;
    }

    public void setDate(String date){
        this.date=date;
    }


}
