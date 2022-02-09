package com.example.test1;

import com.example.test1.Database.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class HelloController implements Initializable {

    @FXML
    private Button exitButton;

    @FXML
    private Button logibButton;

    @FXML
    private PasswordField passwordText;

    @FXML
    private TextField userNameText;

    @FXML
    void LoginButtonAction(ActionEvent event) throws IOException {
        //messageLabel.setText("");
        Window owner= logibButton.getScene().getWindow();
        String username = userNameText.getText();
        String password = passwordText.getText();
        if (username.isEmpty() ||password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "ERROR!",
                    "Complétez les infomation s'il vous plait");
            return;
        }
        String sql ="SELECT  COUNT(1) FROM users where username ='"+username+"' and password ='"+password+"'";
        try{
            DatabaseConnection DBC = new DatabaseConnection();
            DBC.Connect();
            Connection con = DBC.con;
            Statement stmt = DBC.stmt;
            ResultSet rs =stmt.executeQuery(sql);
            while (rs.next()){
                if(rs.getInt(1)==1){
                    Stage stage = (Stage) logibButton.getScene().getWindow();
                    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("AcceilFxml.fxml"));
                    Scene scene = new Scene(fxmlLoader.load(), 1100, 600);
                    stage.setTitle("SHYFK STOCK");
                    stage.setScene(scene);
                    stage.show();


                }

            }

        } catch (SQLException  e) {

             // vide les input
            showAlert(Alert.AlertType.ERROR, owner, "ERROR!",
                    "Les informations ne sont pas valides");

            e.printStackTrace();
            clearFields();
            //return;
        }

    }

    @FXML
    void exitAction() {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    // appele une alert avec message ;
    static void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }

    public void clearFields(){
        userNameText.clear();
        passwordText.clear();

    }



}
