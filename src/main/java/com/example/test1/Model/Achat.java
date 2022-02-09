package com.example.test1.Model;

import java.util.Date;

public class Achat{



    Integer id_Achat  ,quantite ,remise , prix ;
    Date date;
    String  produit ;

    public Integer getId_Achat() {
        return id_Achat;
    }

    public Integer getQuantite() {
        return quantite;
    }

    public Integer getRemise() {
        return remise;
    }


    public Integer getPrix() {
        return prix;
    }

    public  Date getDate() {
        return date;
    }

    public String getProduit() {
        return produit;
    }

    public void setId_Achat(Integer id_vente) {
        this.id_Achat = id_Achat;
    }

    public void setQuantite(Integer quantite) {
        this.quantite = quantite;
    }

    public void setRemise(Integer remise) {
        this.remise = remise;
    }


    public void setPrix(Integer prix) {
        this.prix = prix;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setProduit(String produit) {
        this.produit = produit;
    }

    public Achat(Integer id_Achat, String produit, Date date, Integer prix, Integer quantite, Integer remise){


        this.id_Achat=id_Achat;
        this.produit=produit;
        this.date=date;
        this.quantite=quantite;
        this.remise=remise;
        this.prix=prix;
    }




}

