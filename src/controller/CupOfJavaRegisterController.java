package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CupOfJavaRegisterController implements Initializable{

    @FXML
    private Button btnRegister;

    @FXML
    private ImageView imgLogo;

    @FXML
    private Hyperlink linkLogin;

    @FXML
    private PasswordField pwdPassword;

    @FXML
    private TextField txtUsername;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        // Necesito que no haya ningun campo con el foco
        txtUsername.setFocusTraversable(false);
        pwdPassword.setFocusTraversable(false);
        btnRegister.setFocusTraversable(false);
        linkLogin.setFocusTraversable(false);
    }

    @FXML
    void abrirLogin(ActionEvent event) throws IOException{
        // Obtener el nodo fuente del evento (en este caso, el botón "linkLogin")
        Node sourceNode = (Node) event.getSource();

        // Obtener la escena actual desde el nodo fuente
        Scene currentScene = sourceNode.getScene();

        // Obtener la ventana principal (Stage) desde la escena actual
        Stage mainStage = (Stage) currentScene.getWindow();

        // Cargar la vista de login en la ventana principal
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/CupOfJavaLogin.fxml"));
        Parent root = loader.load();
        mainStage.setScene(new Scene(root));
        mainStage.setTitle("Cup of Java");
        mainStage.setResizable(false);
        mainStage.show();
    }

    @FXML
    void register(ActionEvent event) {

    }

}
