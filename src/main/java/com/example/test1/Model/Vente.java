package com.example.test1.Model;

import java.util.Date;

public class Vente {



        Integer id_vente  ,quantite ,remise ,prix_total , prix ;
         Date date;
        String  produit ;

        public Integer getId_vente() {
            return id_vente;
        }

        public Integer getQuantite() {
            return quantite;
        }

        public Integer getRemise() {
            return remise;
        }

        public Integer getPrix_total() {
            return prix_total;
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

        public void setId_vente(Integer id_vente) {
            this.id_vente = id_vente;
        }

        public void setQuantite(Integer quantite) {
            this.quantite = quantite;
        }

        public void setRemise(Integer remise) {
            this.remise = remise;
        }

        public void setPrix_total(Integer prix_total) {
            this.prix_total = prix_total;
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

        public Vente(Integer id_vente  , String  produit, Date  date , Integer prix ,Integer quantite , Integer remise, Integer prix_total ){


            this.id_vente=id_vente;
            this.produit=produit;
            this.date=date;
            this.quantite=quantite;
            this.remise=remise;
            this.prix_total=prix_total;
            this.prix=prix;
        }




    }

