package com.example.test1;

import com.example.test1.Database.DatabaseConnection;
import com.example.test1.Model.client;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Window;

import java.net.URL;
import java.sql.*;
import java.util.Optional;
import java.util.ResourceBundle;

import static com.example.test1.HelloController.showAlert;

public class ClientController  implements Initializable {


    @FXML
    private Button supprimerButton;
    @FXML
    private Button modifierButton;
    @FXML
    private Button ajouterButton;
    @FXML
    private TextField id_clientTextField;
    @FXML
    private Button clearbutton;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField phoneTextField;
    @FXML
    private TextField cityTextField;
    @FXML
    private TextField filterTextField;
    @FXML
    private TableView<client> tableClient;
    @FXML
    private TableColumn<client, Integer> cole_idclient;
    @FXML
    private TableColumn<client, String> cole_name;
    @FXML
    private TableColumn<client, String> cole_city;
    @FXML
    private TableColumn<client, Integer> cole_phone;
    private Connection con = null;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        showsClient();
        RechercheFilter();
    }

    public ObservableList<client> getClientList() {
        ObservableList<client> ListView = FXCollections.observableArrayList();
        Connect();
        Statement stm;
        String sql = "select * from client ";
        ResultSet rs;
        try {
            stm = con.createStatement();
            rs = stm.executeQuery(sql);
            while (rs.next()) {
                ListView.add(new client(rs.getString("idClient"), rs.getString("NomClient"), rs.getString("TelClient"), rs.getString("VilleClient")));
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();


        }
        return ListView;
    }

    // Remplire  les textField par les donnes de ligne selectioné
    @FXML
    public void onTableItemSelect() {
        client cl = tableClient.getSelectionModel().getSelectedItem();
        if (cl != null) {
            id_clientTextField.setText(String.valueOf(cl.getId()));
            nameTextField.setText(cl.getNom());
            phoneTextField.setText(String.valueOf(cl.getPhone()));
            cityTextField.setText(cl.getVille());

        }
    }
    @FXML
    public void showsClient() {
        ObservableList<client> list = getClientList();
        cole_idclient.setCellValueFactory(new PropertyValueFactory<>("id")); //
        cole_name.setCellValueFactory(new PropertyValueFactory<>("nom"));
        cole_phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        cole_city.setCellValueFactory(new PropertyValueFactory<>("ville"));//
        tableClient.setItems(list);
    }

    @FXML
    public void AjouterButtonAction() {
        Window owner = ajouterButton.getScene().getWindow();

        String id = id_clientTextField.getText();
        String nom = nameTextField.getText();
        String ville = cityTextField.getText();
        String phone = phoneTextField.getText();

        if (id.isEmpty() || nom.isEmpty() || ville.isEmpty() || phone.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "ERROR!",
                    "Complétez les infomation s'il vous plait");
            return;
        }
        Connect();
        try (
                PreparedStatement preStmt = con.prepareStatement("insert into client (idClient ,NomClient  ,TelClient,VilleClient) value (?, ?, ?, ?)");
        ) {
            preStmt.executeUpdate("USE stock;");
            preStmt.setString(1, id);
            preStmt.setString(2, nom);
            preStmt.setString(3, phone);
            preStmt.setString(4, ville);
            preStmt.executeUpdate();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, owner, "ERROR!",
                    "Les informations ne sont pas valides");
            return;

        }
        showAlert(Alert.AlertType.INFORMATION, owner, "Réussie",
                "Le client a été ajouté avec succès ");
        clearFields();
        showsClient();
        RechercheFilter();
    }

    @FXML
    public void ModifierButtonAction(ActionEvent event) {
        Window owner = this.filterTextField.getScene().getWindow();
        String id = id_clientTextField.getText();
        String nom = nameTextField.getText();
        String ville = cityTextField.getText();
        String phone = phoneTextField.getText();
        if (id.isEmpty() || nom.isEmpty() || ville.isEmpty() || phone.isEmpty()) {

            showAlert(Alert.AlertType.ERROR, owner, "ERROR!",
                    "selectionner un client a modifie");
            return;
        }

        Connect();
        try (
                PreparedStatement preStmt = con.prepareStatement("UPDATE client set NomClient=?,TelClient=?,VilleClient =? where idClient=? ");
        ) {
            preStmt.executeUpdate("USE stock;");

            preStmt.setString(1, nom);
            preStmt.setString(2, phone);
            preStmt.setString(3, ville);
            preStmt.setString(4, id);
            preStmt.executeUpdate();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, owner, "ERROR!",
                    "Les informations ne sont pas valides");
            return;

        }
        showAlert(Alert.AlertType.INFORMATION, owner, "Réussi",
                "Le client a été modifié avec succès ");
        showsClient();
        RechercheFilter();
        clearFields();
    }

    @FXML
    public void SupprimerButtonAction() {
        ButtonType ok = new ButtonType("oui", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancel = new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", ok, cancel);
        alert.setHeaderText("Voulez-vous supprimer ce client");
        Window owner = supprimerButton.getScene().getWindow();


        String sql = "delete from client where idClient=?";
        ObservableList<client> Client;

        Client = tableClient.getSelectionModel().getSelectedItems();
        if (Client.size() == 0) {
            showAlert(Alert.AlertType.ERROR, owner, "Erreur", "Choisissez un client");
            return;
        }

        Optional<ButtonType> button = alert.showAndWait();
        if (button.get() == cancel) {
            showsClient();
        }
        if (button.get() == ok) {
            Connect();
            try {
                client cl = Client.get(0);

                PreparedStatement preStmt = con.prepareStatement(sql);
                preStmt.setString(1, cl.getId());
                preStmt.executeUpdate();
                con.close();


            } catch (SQLException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, owner, "Erreur",
                        "Résseyer");
            }
            showsClient();
            showAlert(Alert.AlertType.INFORMATION, owner, "Réussi",
                    "Le client a été supprimer");

        }
        clearFields();
        RechercheFilter();
    }

    public void clearFields() {
        id_clientTextField.clear();
        nameTextField.clear();
        cityTextField.clear();
        phoneTextField.clear();
    }


    @FXML
    public void RechercheFilter() {
        ObservableList<client> dataList = getClientList();
        FilteredList<client> filtreData = new FilteredList<>(dataList, b -> true);
        filterTextField.textProperty().addListener((observable, oldValue, newValue) -> filtreData.setPredicate(client -> {

            if (newValue == null || newValue.isEmpty()) {
                return true;
            }
            String lowerCaseFilter = newValue.toLowerCase();
            if (client.getNom().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else if (client.getVille().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else if (client.getPhone().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else if (client.getId().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else return false;
        }));

        SortedList<client> sortedData = new SortedList<>(filtreData);
        sortedData.comparatorProperty().bind(tableClient.comparatorProperty());
        tableClient.setItems(sortedData);
    }

    private void Connect() {
        DatabaseConnection DBC = new DatabaseConnection();
        DBC.Connect();
        con = DBC.con;
    }
    public void ClearTextField(){ //vides les textfile
        clearFields();
        filterTextField.clear();


    }
}
