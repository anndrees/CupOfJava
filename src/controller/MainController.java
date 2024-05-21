package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Articulo;
import model.Categorias;
import model.TipoPedido;
import model.Usuario;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class MainController implements Initializable{

    @FXML
    private AnchorPane anchorMain;

    @FXML
    private Button btnBuscar;

    @FXML
    private Button btnCobrar;

    @FXML
    private Button btnHamburgerMenu;

    @FXML
    private Button btnTickets;

    @FXML
    private Button btnVerticalMenu;

    @FXML
    private ComboBox<TipoPedido> cboxTipoPedido;

    // Es un combobox que contiene el nombre de todas las clases que heredan de Articulo
    @FXML
    private ComboBox<Categorias> cboxCatArticulos;

    @FXML
    private Label lblTicket;

    @FXML
    private Hyperlink linkCerrarSesion;

    @FXML
    private ListView<?> listTicket;

    @FXML
    private TableView<?> tblArticulos;

    @FXML
    private TextField txtSearch;

    private Usuario currentUser;

    @FXML
    void buscar(KeyEvent event) {
        // Añadir un listener que detecte si va cambiando el texto de txtSearch

        if(event.getCode() == KeyCode.ESCAPE) {
            txtSearch.setVisible(false);
            txtSearch.clear();
        }

        if(event.getCode() == KeyCode.ENTER) {
            /*
            TODO
            Implementar la busqueda
             */
        }
    }

    @FXML
    void initBusqueda(ActionEvent event) {
        txtSearch.setVisible(true);
        txtSearch.requestFocus();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        unfocus();

        cboxTipoPedido.getItems().addAll(TipoPedido.values());
        cboxCatArticulos.getItems().addAll(Categorias.values());
        cboxTipoPedido.setValue(TipoPedido.COMER_AQUI);
        cboxCatArticulos.setValue(Categorias.TODOS);
    }



    public void setCurrentUser(Usuario currentUser) {
        this.currentUser = currentUser;
    }


	@FXML
    void openFileChooser(ActionEvent event) {
        /*
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            btnBuscar.setImage(new Image(file.toURI().toString()));
        }
        */
    }


    @FXML
    void abrirLogin(ActionEvent event) throws IOException{
        Node sourceNode = (Node) event.getSource();
        Scene currentScene = sourceNode.getScene();
        Stage mainStage = (Stage) currentScene.getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/Login.fxml"));
        Parent root = loader.load();
        mainStage.setScene(new Scene(root));
        mainStage.setTitle("Cup of Java - Inicio de sesión");
        mainStage.setResizable(false);
		mainStage.getIcons().add(new Image(getClass().getResource("../app/assets/imgs/squareFavicon.png").toString()));
        mainStage.centerOnScreen();
        mainStage.show();
    }

    @FXML
    void cerrarSesion(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cup of Java - Cerrar sesión");
        alert.setContentText("¿Seguro que quieres cerrar la sesión?");
        alert.showAndWait();
        if (alert.getResult() == ButtonType.OK) {
            try {
                abrirLogin(event);
            }catch(IOException ignored){
            }
		}
    }


    @FXML
    void unfocus() {
        btnBuscar.setFocusTraversable(false);
        btnCobrar.setFocusTraversable(false);
        txtSearch.setFocusTraversable(false);
        btnHamburgerMenu.setFocusTraversable(false);
        btnTickets.setFocusTraversable(false);
        btnVerticalMenu.setFocusTraversable(false);
        cboxCatArticulos.setFocusTraversable(false);
        cboxTipoPedido.setFocusTraversable(false);
        lblTicket.setFocusTraversable(false);
        linkCerrarSesion.setFocusTraversable(false);
        listTicket.setFocusTraversable(false);
        tblArticulos.setFocusTraversable(false);
        txtSearch.setVisible(false);
        lblTicket.getParent().requestFocus();
    }
}
