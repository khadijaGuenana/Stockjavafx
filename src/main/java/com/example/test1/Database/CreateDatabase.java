package com.example.test1.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class CreateDatabase {
    public static void main(String[] args) {
        Connection con=null;
        Statement stmt=null;
        String DatabaseName="stock";
        try {
            con= DriverManager.getConnection("jdbc:mysql://localhost:3306?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
                    "root","");
            stmt = con.createStatement();
            stmt.executeUpdate("DROP DATABASE "+DatabaseName);
            int status = stmt.executeUpdate("CREATE DATABASE "+DatabaseName);
            stmt.executeUpdate("USE "+DatabaseName+";");
            stmt.executeUpdate("create table users ( accountId int(10) primary key ,\r\n"+"username varchar(100),\r\n"+"password varchar(100))");
            stmt.executeUpdate("create table Fournisseur(CodeFrnsr int(100) primary key,\r\n"+"NomFrnsr varchar(100),\r\n"+"AdresseFrnsr varchar(100),\r\n"+"VilleFrnsr varchar(100),\r\n"+"TelFrnsr varchar(100))");
            stmt.executeUpdate("create table product(prodId int(10) primary key ,\r\n"+"fornID int(100),\r\n"+"nomProd varchar(100),\r\n"+"prix int(10),\r\n"+"quantite int(10))");
            stmt.executeUpdate("create table Vente(id_vente int(10) primary key,\r\n"+"produit int(10),\r\n"+"dateV date,\r\n"+"prixProd int(10),\r\n"+"quantite int(10),\r\n"+"remise int(10),\r\n"+"prixTotal int(10))");
            stmt.executeUpdate("create table achat(id_achat int(10) primary key,\r\n"+"produit int(10),\r\n"+"dateV date,\r\n"+"prixProd int(10),\r\n"+"quantite int(10),\r\n"+"remise int(10),\r\n"+"prixTotal int(10))");
            stmt.executeUpdate("create table client(idClient int(100) primary key,\r\n"+"NomClient varchar(100),\r\n"+"TelClient varchar(100),\r\n"+"VilleClient varchar(100))");

            stmt.executeUpdate("INSERT INTO users (accountId, username, password) values ('1','khadija','java') ");
            stmt.executeUpdate("INSERT INTO users ( accountId,username, password) values ('2','stock','java') ");
            stmt.executeUpdate("INSERT INTO Vente (id_vente,produit, dateV,prixProd,quantite ,remise ,prixTotal) values ('1','10','2020-7-04','150','10','0','1500') ");
            stmt.executeUpdate("INSERT INTO Fournisseur(CodeFrnsr ,NomFrnsr ,AdresseFrnsr ,VilleFrnsr ,TelFrnsr )values ('1','frnsname','AdresseFR','villefrn','0612789543')");

            stmt.executeUpdate("INSERT INTO product(prodId  ,fornID ,nomProd  ,prix ,quantite ) values ('10','1','produitTest','10',20)");
            stmt.executeUpdate("INSERT INTO product(prodId  ,fornID ,nomProd  ,prix ,quantite ) values ('5','1','produitTest3','5',10)");
            stmt.executeUpdate("INSERT INTO achat (id_achat,produit, dateV,prixProd,quantite ,remise ,prixTotal) values ('1','10','2020-7-04','150','10','0','1500') ");

            if(status > 0) {
                System.out.println("Database is created successfully !!!");
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

}
