package com.example.test1.Model;

public class client {
    String nom  , ville, phone, id ;


    public client(String id,String nom,String phone,String ville) {
        this.nom=nom;
        this.phone=phone;
        this.id=id;
        this.ville=ville;
    }


    //setter

    public void setNom(String nom){
        this.nom=nom;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }


    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setId(String id) {
        this.id = id;
    }

    //getter

    public String getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }



    public String getVille() {
        return ville;
    }

    public String getPhone() {
        return phone;
    }





}
