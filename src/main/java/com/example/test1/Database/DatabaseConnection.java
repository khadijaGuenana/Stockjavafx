package com.example.test1.Database;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
public class DatabaseConnection {

    public Connection con;
    public Statement stmt;
    public  String DatabaseName="stock";
    public void Connect() {
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+DatabaseName+"?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
                    "root", "gnana");
            stmt = con.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




}



