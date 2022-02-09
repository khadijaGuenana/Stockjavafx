package com.example.test1;
import com.example.test1.Database.DatabaseConnection;
import com.example.test1.Model.produit;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import java.sql.PreparedStatement;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Window;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;

public class ProductController implements Initializable {

    @FXML
    private TableColumn<produit, Integer> id_col;
    @FXML
    private TableColumn<produit, String> nom_col ;
    @FXML
    private TableColumn<produit, Integer> fourniseur_col ;
    @FXML
    private TableColumn<produit, Integer> quantite_col;
    @FXML
    private TableColumn<produit, Integer> prix_col;
    @FXML
    private Button clearbutton;
    @FXML
    private TextField idtextfield;
    @FXML
    private TextField nomtextfield;
    @FXML
    private TextField fourniseurtextfield;
    @FXML
    private TextField quantitetextfield;
    @FXML
    private TextField prixtextfield;
    @FXML
    private AnchorPane pane_product;
    @FXML
    private TextField filterField;
    @FXML
    private Button modifierbutton;
    @FXML
    private Button ajouterbutton;
    @FXML
    private Button supprimerbutton;
    @FXML
    private TableView<produit> tableproduits;

    private Connection con = null;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.showsProduits();
        this.RechercheFilter();
    }

    @FXML
    public void onTableItemSelect() {
        produit pr = (produit) this.tableproduits.getSelectionModel().getSelectedItem();
        if (pr != null) {
            this.idtextfield.setText(String.valueOf(pr.getCode()));
            this.fourniseurtextfield.setText(String.valueOf(pr.getFourniseur()));
            this.nomtextfield.setText(pr.getNom());
            this.prixtextfield.setText(String.valueOf(pr.getPrix()));
            this.quantitetextfield.setText(String.valueOf(pr.getQuantite()));


        }

    }

    @FXML
    void AjouterButtonAction(ActionEvent event) {
        Window owner = this.ajouterbutton.getScene().getWindow();
        String code = this.idtextfield.getText();
        String nom=this.nomtextfield.getText();
        String fourniseur=this.fourniseurtextfield.getText();
        String quantite= this.quantitetextfield.getText();
        String prix= this.prixtextfield.getText();

        if (!code.isEmpty() && !nom.isEmpty() && !fourniseur.isEmpty()  && !quantite.isEmpty() && !prix.isEmpty()  ) {
            this.Connect();

            try {
                PreparedStatement preStmt = this.con.prepareStatement("insert into product (prodId , fornID,nomProd ,prix,quantite ) value (?, ?,?,?,?)");
                try {
                    preStmt.executeUpdate("USE stock;");
                    preStmt.setInt(1, Integer.parseInt(code));
                    preStmt.setInt(2, Integer.parseInt(fourniseur));
                    preStmt.setString(3, nom);
                    preStmt.setInt(4, Integer.parseInt(prix));
                    preStmt.setInt(5, Integer.parseInt(quantite));

                    preStmt.executeUpdate();
                    this.con.close();
                } catch (Throwable var11) {
                    if (preStmt != null) {
                        try {
                            preStmt.close();
                        } catch (Throwable var10) {
                            var11.addSuppressed(var10);
                        }
                    }
                    throw var11;
                }
                if (preStmt != null) {
                    preStmt.close();
                }
            } catch (SQLException var12) {
                var12.printStackTrace();
                showAlert(Alert.AlertType.ERROR, owner, "ERROR!", "Les informations ne sont pas valides");
                return;
            }
            showAlert(Alert.AlertType.INFORMATION, owner, "Réussi", "Le produit a été ajouté avec succès ");
            this.clearFields();
            this.showsProduits();
            this.RechercheFilter();

        } else {
            showAlert(Alert.AlertType.ERROR, owner, "ERROR!", "Complétez les infomation s'il vous plait");
        }
    }

    public ObservableList<produit> getProduitsList() {
        ObservableList<produit> ListView = FXCollections.observableArrayList();
        this.Connect();
        String sql = "select * from product";

        try {
            Statement stm = this.con.createStatement();
            ResultSet rs = stm.executeQuery(sql);

            while (rs.next()) {
                ListView.add(new produit(rs.getInt("prodId"), rs.getInt("fornID"), rs.getString("nomProd"), rs.getInt("prix"), rs.getInt("quantite")));

                //   ListView.add(new produit(rs.getInt("prodId"), rs.getInt("fourniseur"), rs.getString("nom"), rs.getInt("prix"), rs.getInt("quantite")));
            }

            this.con.close();
        } catch (Exception var6) {
            var6.printStackTrace();
        }

        return ListView;
    }

    @FXML
    public void showsProduits() {
        ObservableList<produit> list = this.getProduitsList() ;
        this.id_col.setCellValueFactory(new PropertyValueFactory<>("code"));//
        this.fourniseur_col.setCellValueFactory(new PropertyValueFactory<>("fourniseur"));
        this.nom_col.setCellValueFactory(new PropertyValueFactory<>("nom"));
        this.prix_col.setCellValueFactory(new PropertyValueFactory<>("prix"));
        this.quantite_col.setCellValueFactory(new PropertyValueFactory<>("quantite"));

        this.tableproduits.setItems(list);
    }

    @FXML
    void ModifierButtonAction(ActionEvent event) {
        Window owner = this.filterField.getScene().getWindow();

        String code = this.idtextfield.getText();
        String nom = this.nomtextfield.getText();
        String fournisseur = this.fourniseurtextfield.getText();
        String quantite = this.quantitetextfield.getText();
        String prix = this.prixtextfield.getText();

        if (!code.isEmpty() && !nom.isEmpty() && !quantite.isEmpty() && !fournisseur.isEmpty() &&  !prix.isEmpty() ) {
            this.Connect();

            try {
                PreparedStatement preStmt = this.con.prepareStatement("update product set fornID=?, nomProd=? ,prix =?,quantite=? where prodId=? ");

                try {
                    preStmt.executeUpdate("USE stock;");
                    preStmt.setString(1, code);
                    preStmt.setString(3, fournisseur);
                    preStmt.setString(2, nom);
                    preStmt.setString(4, prix);
                    preStmt.setString(5, quantite);
                    preStmt.executeUpdate();
                    this.con.close();
                } catch (Throwable var11) {
                    if (preStmt != null) {
                        try {
                            preStmt.close();
                        } catch (Throwable var10) {
                            var11.addSuppressed(var10);
                        }
                    }

                    throw var11;
                }

                if (preStmt != null) {
                    preStmt.close();
                }
            } catch (SQLException var12) {
                var12.printStackTrace();
                showAlert(Alert.AlertType.ERROR, owner, "ERROR!", "Les informations ne sont pas valides");
                return;
            }

            showAlert(Alert.AlertType.INFORMATION, owner, "Réussi", "Le produita été modifié avec succès ");
            this.showsProduits();
            this.RechercheFilter();
            this.clearFields();
        } else {
            showAlert(Alert.AlertType.ERROR, owner, "ERROR!", "sélectioner le produit a modifié");
        }
    }

    @FXML
    void SupprimerButtonAction(ActionEvent event) {
        ButtonType ok = new ButtonType("oui", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancel = new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", new ButtonType[]{ok, cancel});
        alert.setHeaderText("Voulez-vous supprimer ce produit");
        Window owner = this.supprimerbutton.getScene().getWindow();
        String sql = "delete from product where prodId=?";
        ObservableList<produit> Fourn = this.tableproduits.getSelectionModel().getSelectedItems();
        if (Fourn.size() == 0) {
            showAlert(Alert.AlertType.ERROR, owner, "Erreur", "Choisissez un produit");
        } else {
            Optional<ButtonType> button = alert.showAndWait();
            if (button.get() == cancel) {
                this.showsProduits();
            }

            if (button.get() == ok) {
                this.Connect();

                try {
                    produit p = (produit) Fourn.get(0);
                    PreparedStatement preStmt = this.con.prepareStatement(sql);
                    preStmt.setInt(1, p.getCode());
                    preStmt.executeUpdate();
                    this.con.close();
                } catch (SQLException var10) {
                    var10.printStackTrace();
                    showAlert(Alert.AlertType.ERROR, owner, "Erreur", "Résseyer");
                }

                this.showsProduits();
                showAlert(Alert.AlertType.INFORMATION, owner, "réussi", "Le produit a été supprimé");
            }

            this.clearFields();
            this.RechercheFilter();
        }
    }

    private void Connect() {
        DatabaseConnection DBC = new DatabaseConnection();
        DBC.Connect();
        this.con = DBC.con;
    }

    private static void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText((String) null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }

    public void clearFields() {
        this.idtextfield.clear();
        this.nomtextfield.clear();
        this.fourniseurtextfield.clear();
        this.quantitetextfield.clear();
        this.prixtextfield.clear();
    }

    public void RechercheFilter() {
        ObservableList<produit> dataList = this.getProduitsList();
        FilteredList<produit> filtreData = new FilteredList<>(dataList, b -> true);
        this.filterField.textProperty().addListener((observable, oldValue, newValue) ->
                filtreData.setPredicate(produit -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();
                    if (produit.getNom().toLowerCase().contains(lowerCaseFilter)) {
                        return true;

                    } else if (String.valueOf(produit.getCode()).toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (String.valueOf(produit.getFourniseur()).toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (String.valueOf(produit.getPrix()).toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (String.valueOf(produit.getQuantite()).toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }else {
                        return false;
                    }
                   //

                }));
        SortedList<produit> sortedData = new SortedList<>(filtreData);
        sortedData.comparatorProperty().bind(tableproduits.comparatorProperty());
        tableproduits.setItems(sortedData);
    }
    public void ClearTextField(){ //vides les textfile
        clearFields();
        filterField.clear();


    }

}


