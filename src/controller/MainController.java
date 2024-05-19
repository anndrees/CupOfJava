package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class MainController {

    @FXML
    private ImageView btnBuscar;

    @FXML
    private Button btnCobrar;

    @FXML
    private Button btnTickets;

    @FXML
    private ComboBox<?> cboxCatArticulos;

    @FXML
    private Label lblTicket;

    @FXML
    private Hyperlink linkCerrarSesion;

    @FXML
    private TableView<?> tblArticulos;

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
            }catch(IOException e){}
		}
    }
}
