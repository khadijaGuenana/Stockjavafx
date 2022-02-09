package com.example.test1.Model;

public class produit {
    int code ;
    String nom;
    int fourniseur;
    int quantite;
    int prix;

    public produit(int code,int fourniseur,String nom ,int prix ,int quantite) {
        this.code = code;
        this.nom =  nom;
        this.fourniseur = fourniseur;
        this.quantite = quantite;
        this.prix =prix ;

    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setFourniseur(int fourniseur) {
        this.fourniseur = fourniseur;
    }

    public void setQauntite(int qauntite) {
        this.quantite = quantite;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }


    public int getCode() {

        return this.code;
    }

    public int getFourniseur() {

        return this.fourniseur;
    }

    public String getNom()
    {
        return this.nom;
    }

    public int getQuantite()
    {
        return this.quantite;
    }

    public int getPrix() { return this.prix;}




}
