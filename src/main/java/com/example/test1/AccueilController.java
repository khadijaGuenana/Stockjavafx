package com.example.test1;

        import com.example.test1.FxmlLoader;
        import javafx.event.ActionEvent;
        import javafx.fxml.FXML;
        import javafx.fxml.FXMLLoader;
        import javafx.fxml.Initializable;
        import javafx.scene.Scene;
        import javafx.scene.control.Button;
        import javafx.scene.layout.AnchorPane;
        import javafx.scene.layout.BorderPane;
        import javafx.scene.layout.Pane;
        import javafx.scene.layout.VBox;
        import javafx.stage.Stage;
       // import  com.example.test1.Model.Vente;

        import java.io.IOException;
        import java.net.URL;
        import java.util.Date;
        import java.util.ResourceBundle;

public class AccueilController implements Initializable {
    @FXML
    private VBox vbox;
    @FXML
    private Button buttonAccueil;

    @FXML
    private Button buttonAchat;

    @FXML
    private Button buttonClient;

    @FXML
    private Button buttonFournisseur;

    @FXML
    private Button buttonLogOut;

    @FXML
    private Button buttonProduct;

    @FXML
    private Button buttonVente;

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private BorderPane mainPane;


    @FXML
    void LogOut(ActionEvent event) throws IOException {
        Stage stage = (Stage) buttonLogOut.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700, 500);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    void ShowAccueil() {
        FxmlLoader object =new FxmlLoader();
        Pane view =object.getPage("Accueil1");
        mainPane.setCenter(view);

    }

    @FXML
    void ShowAchat() {
        FxmlLoader object =new FxmlLoader();
        Pane view =object.getPage("Achat");
        mainPane.setCenter(view);

    }

    @FXML
    void ShowClient() {
        FxmlLoader object =new FxmlLoader();
        Pane view =object.getPage("Client");
        mainPane.setCenter(view);
    }

    @FXML
    void ShowFournisseur(ActionEvent event) {
        FxmlLoader object =new FxmlLoader();
        Pane view =object.getPage("Fournisseur");
        mainPane.setCenter(view);
    }

    @FXML
    void ShowProduct(ActionEvent event) {
        FxmlLoader object =new FxmlLoader();
        Pane view =object.getPage("Product");
        mainPane.setCenter(view);

    }

    @FXML
    void ShowVente(ActionEvent event) {
        FxmlLoader object =new FxmlLoader();
        Pane view =object.getPage("Vente");
        mainPane.setCenter(view);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ShowAccueil();


    }


}
