package com.example.test1;
import com.example.test1.Database.DatabaseConnection;
import com.example.test1.Model.Vente;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Window;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

public class VenteController implements Initializable {

    @FXML
    private Button AjouterButton;

    @FXML
    private TextField Id_venteText;

    @FXML
    private Button ImprimerButton;

    @FXML
    private Button ModifierButton;
    @FXML
    private Button ClearButton;

    @FXML
    private TextField RecherchInput;

    @FXML
    private Button RechercheButton;

    @FXML
    private Button SupprimerButon;

    @FXML
    private TableView<Vente> TableVente;

    @FXML
    private ComboBox<String> TypeRecherche =new ComboBox<String>() ;


    @FXML
    private TableColumn<Vente, Date> dateCol;

    @FXML
    private DatePicker date_vente;

    @FXML
    private TableColumn<Vente,Integer> idVenteCol;

    @FXML
    private AnchorPane pane_ventes;

    @FXML
    private TableColumn<Vente,Integer> prixCol;

    @FXML
    private TableColumn<Vente,Integer> prixTotalCol;

    @FXML
    private TextField prixTotalVText;

    @FXML
    private TextField prix_unitaireText;

    @FXML
    private TextField produitText;

    @FXML
    private TableColumn<Vente,Integer> id_produitCol;

    @FXML
    private TableColumn<Vente,Integer> quantiteCol;

    @FXML
    private TextField quantiteVenteText;

    @FXML
    private TableColumn<Vente,Integer> remiseCol;

    @FXML
    private TextField remiseVenteText;


    private  Connection con=null;
// prixUnitaire get le prix de produit from table product
     public int PrixUnitaire( int idProduit){
         int prix=0;
        String sql =" select prix from product where prodId ="+idProduit+" ";


         try{
             Connect();
             Statement st=con.createStatement();
             ResultSet result =st.executeQuery(sql);
             while(result.next()){
                 prix=result.getInt("prix");
             }
             con.close();
         }catch (SQLException E){
             E.printStackTrace();
         }
         return  prix ;
     }
  //prixTotalVente calcuel prix totale de vente
     public int prixTotalVente(int prix ,int quantite ,int remise){
         int prixSanRemise= prix*quantite ;


        return  prixSanRemise - (prixSanRemise*remise/100);

     }
     //stock :return la quantité du produit qu'on a dans le stock
     int stock(int id_produit ){
         String sql1="select quantite from product where prodId="+id_produit+"";
         int stock=0;
         try{
             Connect();
             Statement st=con.createStatement();
             ResultSet result =st.executeQuery(sql1);
             while(result.next()){
                 stock=result.getInt("quantite");
             }

             con.close();
         }catch (SQLException E){

             E.printStackTrace();
         }
        return stock;
     }
     // subtractQuantity : soustraire la quantité vendu dans table produit
    void subtractQuantity(int id_produit ,int quantite){

       int stock= stock(id_produit);
       int  StockApresVente =stock-quantite;


        try {
            Connect();
            Statement st = con.createStatement();
            PreparedStatement preStmt = con.prepareStatement("UPDATE product SET quantite =? WHERE prodId="+id_produit+"");

            preStmt.setInt(1, StockApresVente);

            preStmt.executeUpdate();
            con.close();
            System.out.println("le stock a ete bien mise a jour ");


        }
        catch (Exception e){
            e.printStackTrace();
        }


    }
    // AjouterVente
    @FXML
    void AjouterVente(ActionEvent event) {

        Window owner=AjouterButton.getScene().getWindow();
        if(Id_venteText.getText().isEmpty() ||produitText.getText().isEmpty()||remiseVenteText.getText().isEmpty()||quantiteVenteText.getText().isEmpty() || date_vente.getValue()==null){
            showAlert(Alert.AlertType.ERROR,owner,"Erreur","Complétez les informations s'il vous plait !");
        }

        int Id_Vente = Integer.parseInt(Id_venteText.getText()) ;
        int id_produit=Integer.parseInt(produitText.getText());
        LocalDate Date = date_vente.getValue();
        int prixUnitaire=PrixUnitaire(id_produit);
        int quantite=Integer.parseInt(quantiteVenteText.getText());
        int remise=Integer.parseInt(remiseVenteText.getText());
        int prixTotal=prixTotalVente(prixUnitaire,quantite,remise);

             ButtonType ok = new ButtonType("oui", ButtonBar.ButtonData.OK_DONE);
             ButtonType cancel = new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE);
             Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", ok, cancel);
             alert.setHeaderText("Voulez-vous ajouter cette vente");
             Optional<ButtonType> button = alert.showAndWait();
             if (button.get() == cancel) {

             }
             if (button.get() == ok) {
                 if (quantite > stock(id_produit)) {
                     showAlert(Alert.AlertType.ERROR, owner, "ERREUR", "la quantite demandé nest pas dans le stock !");
                 } else {
                     Connect();


                     String sql = "INSERT INTO Vente (id_vente,produit, dateV,prixProd,quantite ,remise ,prixTotal) values ('" + Id_Vente + "','" + id_produit + "','" + Date + "','" + prixUnitaire + "','" + quantite + "','" + remise + "','" + prixTotal + "')";
                     try {
                         Statement st = con.createStatement();
                         st.executeUpdate(sql);
                         con.close();
                         showAlert(Alert.AlertType.CONFIRMATION, owner, "Ajout", "La vente est ajoutée avec succès ");

                     } catch (SQLException e) {
                         e.printStackTrace();
                         showAlert(Alert.AlertType.ERROR, owner, "ERREUR", "Les information ne sont pas valides !");
                     }
                     subtractQuantity(id_produit, quantite);//il faut modifier quantite initiale de produit
                 }
             }

        showsVente();
        ClearTextField();


    }
    // Remplire  les textField par les donnes de ligne selectioné
    @FXML
    public void onTableItemSelect (){
        Vente v = TableVente.getSelectionModel().getSelectedItem();
        if (v !=null) {
            Id_venteText.setText(String.valueOf(v.getId_vente()));
            produitText.setText(v.getProduit());

            prix_unitaireText.setText(String.valueOf(v.getPrix()));
            quantiteVenteText.setText((String.valueOf(v.getQuantite())) );
            remiseVenteText.setText(String.valueOf((v.getRemise())));
            prixTotalVText.setText(String.valueOf(v.getPrix_total()));
            date_vente.setValue(LocalDate.parse(String.valueOf(v.getDate())));
        }
    }
// imprimer vente il me faut tibco jaspersoft studio  free!!!!!!
    @FXML
    void ImprimerFacture(ActionEvent event) {

    }

    //ModifierVente: modification de date, remise seulement en effet modificaionde  prix totale
    @FXML
    void ModifierVente(ActionEvent event) {//// probleeeeeeeemmmmmmm pas besoin de modification
        Vente vente = TableVente.getSelectionModel().getSelectedItem();
        Window  owner=this.Id_venteText.getScene().getWindow();
        if (vente == null) {
            showAlert(Alert.AlertType.WARNING,owner,"ERREUR","Choisissez une vente !");

        } else {
            int Id_Vente = Integer.parseInt(Id_venteText.getText());
            LocalDate Date = date_vente.getValue();
            int quantite = Integer.parseInt(quantiteVenteText.getText());
            int remise = Integer.parseInt(remiseVenteText.getText());
            int prixUnitaire = Integer.parseInt(prix_unitaireText.getText());;
            int prixTotal=prixTotalVente(prixUnitaire , quantite ,remise);


            try {
                Connect();
                Statement st = con.createStatement();
                PreparedStatement preStmt = con.prepareStatement("UPDATE vente SET id_vente=? ,remise =? ,prixTotal=?  WHERE id_vente='" + vente.getId_vente() + "'");

                preStmt.setInt(1, Id_Vente);
                preStmt.setInt(5,remise);//fonction remise
                preStmt.setInt(7,prixTotal);
                preStmt.executeUpdate();
                con.close();
                showAlert(Alert.AlertType.CONFIRMATION,owner,"Modification","la vente a été modifiée avec succès ");

            }
            catch (Exception e){
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR,owner,"ERREUR","Vous ne pouvez pas modifier cette vente");
            }

        }
          showsVente();
          ClearTextField();
    }

    @FXML
    void SupprimerVente(ActionEvent event) {

        Vente vente=TableVente.getSelectionModel().getSelectedItem();
        Window owner=SupprimerButon.getScene().getWindow();
        if(vente==null){

            showAlert(Alert.AlertType.ERROR,owner,"Erreur","Choisissez une vente");
        }
        else {
            ButtonType ok = new ButtonType("oui", ButtonBar.ButtonData.OK_DONE);
            ButtonType cancel = new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", ok, cancel);
            alert.setHeaderText("Voulez-vous supprimer cette vente");
            Optional<ButtonType> button = alert.showAndWait();
            if (button.get() == cancel) {

            }
            if (button.get() == ok) {
                Connect();
                try {
                    Statement st = con.createStatement();
                    String sql = "DELETE FROM VENTE WHERE id_vente='" + vente.getId_vente() + "'";
                    st.executeUpdate(sql);
                    con.close();
                    showsVente();
                    showAlert(Alert.AlertType.CONFIRMATION,owner,"Suppression","La vente a été supprimée avec succès");
                }
                catch (SQLException e){
                    e.printStackTrace();
                    showAlert(Alert.AlertType.ERROR,owner,"ERREUR","Vous ne pouvez pas supprimers cette vente");
                }
            }
        }
        showsVente();
        ClearTextField();
    }

     //Ajouter tout les element de tableau vente au liste  listeView de type vente

    public ObservableList<Vente> getVentList(){

        ObservableList<Vente> ListView = FXCollections.observableArrayList();
        Connect();
        Statement stm;
        String sql ="select * from Vente ";
        ResultSet rs;
        try {
            stm=con.createStatement();
            rs=stm.executeQuery(sql);
            while (rs.next()){
                ListView.add(
                        new Vente(Integer.parseInt(rs.getString("id_vente")),
                                rs.getString("produit"),
                                rs.getDate("dateV") ,
                                Integer.parseInt(rs.getString("prixProd"))  ,
                                Integer.parseInt(rs.getString("quantite")),
                                Integer.parseInt(rs.getString("remise")),
                                Integer.parseInt(rs.getString("prixTotal"))));


                }
            con.close();
        }catch (Exception e){
            e.printStackTrace();

        }
        return ListView ;
    }
    public void showsVente(){
        ObservableList<Vente> list = getVentList(); //appele la liste des vente
        idVenteCol.setCellValueFactory(new PropertyValueFactory<>("id_vente"));
        id_produitCol.setCellValueFactory(new PropertyValueFactory<>("produit"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        prixCol.setCellValueFactory(new PropertyValueFactory<>("prix"));
        quantiteCol.setCellValueFactory(new PropertyValueFactory<>("quantite"));
        remiseCol.setCellValueFactory(new PropertyValueFactory<>("remise"));
        prixTotalCol.setCellValueFactory(new PropertyValueFactory<>("prix_total"));
        TableVente.setItems(list);


    }
    public void SearchVente(){
        idVenteCol.setCellValueFactory(new PropertyValueFactory<>("id_vente"));
        id_produitCol.setCellValueFactory(new PropertyValueFactory<>("produit"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        prixCol.setCellValueFactory(new PropertyValueFactory<>("prix"));
        quantiteCol.setCellValueFactory(new PropertyValueFactory<>("quantite"));
        remiseCol.setCellValueFactory(new PropertyValueFactory<>("remise"));
        prixTotalCol.setCellValueFactory(new PropertyValueFactory<>("prix_total"));

        Window owner = RechercheButton.getScene().getWindow();

        if (TypeRecherche.getValue() == null) { //si aucun chanp n'est pas choisi affiche un alert
            showAlert(Alert.AlertType.WARNING, owner, "ERROR!",
                    "Choisissez un champ");

        } else if (RecherchInput.getText().isEmpty()) { //si aucune valeur n'est pas saisi affiche un Alert
            showAlert(Alert.AlertType.WARNING, owner, "ERROR!",
                    "Saisissez un text");
        } else {
            DatabaseConnection  DBC = new DatabaseConnection ();
                           DBC.Connect();
                           Connection con = DBC.con;
                          Statement stm = DBC.stmt;

            try {
                ResultSet rs = stm.executeQuery("SELECT * FROM vente WHERE " + TypeRecherche.getValue() + " = '" + RecherchInput.getText() + "'");

                ObservableList<Vente> data = FXCollections.observableArrayList();
                while (rs.next()) {
                    data.add(
                            new Vente(Integer.parseInt(rs.getString("id_vente")),
                                    rs.getString("produit"),
                                    rs.getDate("dateV") ,
                                    Integer.parseInt(rs.getString("prixProd"))  ,
                                    Integer.parseInt(rs.getString("quantite")),
                                    Integer.parseInt(rs.getString("remise")),
                                    Integer.parseInt(rs.getString("prixTotal")))

                    );
                }
                con.close();
                TableVente.setItems(data);


            }
            catch (SQLException e){
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR,owner,"ERREUR","Saisissez les informations une autre fois");

            }

        }

    }

    private void Connect(){
        DatabaseConnection DBC = new DatabaseConnection();
        DBC.Connect();
        con = DBC.con;


    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showsVente();
        ClearTextField();
        // data liste des types de recherche
        ObservableList<String> data = FXCollections.observableArrayList("id_vente","produit","dateV","prixProd","quantite","Remise","prixTotal");
        TypeRecherche.setItems(data);
    }
    private static void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }

    public void ClearTextField(){ //vides les textfile
        Id_venteText.clear();
        produitText.clear();
        prix_unitaireText.clear();
        quantiteVenteText.clear();
        remiseVenteText.clear();
        prixTotalVText.clear();
        date_vente.setValue(null);


    }
    public void actualiser(){ //vider les textfile et recherche input
        ClearTextField();
        RecherchInput.clear();
        showsVente();//afficher table avant recherche
    }
}
