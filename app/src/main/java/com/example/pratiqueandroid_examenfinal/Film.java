package com.example.pratiqueandroid_examenfinal;

import java.io.Serializable;

public class Film implements Serializable {
    private int num;
    private String titre;
    private int codeCateg;
    private String langue;
    private int cote;
    private String pochette;

    public Film(Film film){
        this.num = film.num;
        this.titre = film.titre;
        this.codeCateg = film.codeCateg;
        this.langue = film.langue;
        this.cote = film.cote;
    }

    public Film(int num, String titre, int codeCateg, String langue, int cote, String pochette) {
        this.num = num;
        this.titre = titre;
        this.codeCateg = codeCateg;
        this.langue = langue;
        this.cote = cote;
        this.pochette = pochette;
    }


    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public int getCodeCateg() {
        return codeCateg;
    }

    public void setCodeCateg(int codeCateg) {
        this.codeCateg = codeCateg;
    }

    public String getLangue() {
        return langue;
    }

    public void setLangue(String langue) {
        this.langue = langue;
    }

    public int getCote() {
        return cote;
    }

    public void setCote(int cote) {
        this.cote = cote;
    }

    public String getPochette() {
        return pochette;
    }

    public void setPochette(String pochette) {
        this.pochette = pochette;
    }
}
