package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class RegisterController implements Initializable{

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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/Login.fxml"));
        Parent root = loader.load();
        mainStage.setScene(new Scene(root));
        mainStage.setTitle("Cup of Java - Login");
        mainStage.setResizable(false);
        mainStage.show();
    }

    @FXML
    void register(ActionEvent event) {
        if(txtUsername.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Cup of Java");
            alert.setContentText("Por favor, introduce un nombre de usuario");
            alert.showAndWait();
        } else if(pwdPassword.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Cup of Java");
            alert.setContentText("Por favor, introduce una contraseña");
            alert.showAndWait();
        } else {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Cup of Java - Autorización");
            dialog.setHeaderText("Autorización requerida");
            dialog.setContentText("Debes introducir la contraseña del administrador para poder registrar el usuario:");
            dialog.setResizable(false);
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()){
                System.out.println("Texto ingresado: " + result.get());
                if(result.get().equals("admin")){
                    System.out.println("Registro correcto");
                }
            }
        }
    }

}
