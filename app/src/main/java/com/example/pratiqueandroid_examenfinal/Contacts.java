package com.example.pratiqueandroid_examenfinal;

import java.io.Serializable;

public class Contacts implements Serializable {
    private String image;
    private int numero;
    private String nom;
    private String prenom;
    private int numeroTel;
    public Contacts(Contacts contacts){
        this.image = contacts.image;
        this.numero = contacts.numero;
        this.nom = contacts.nom;
        this.prenom = contacts.prenom;
        this.numeroTel = contacts.numeroTel;
    }

    public Contacts(String image, int numero, String nom, String prenom, int numeroTel) {
        this.image = image;
        this.numero = numero;
        this.nom = nom;
        this.prenom = prenom;
        this.numeroTel = numeroTel;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public int getNumeroTel() {
        return numeroTel;
    }

    public void setNumeroTel(int numeroTel) {
        this.numeroTel = numeroTel;
    }
}
