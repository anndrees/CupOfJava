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
import java.util.ResourceBundle;

public class CupOfJavaLoginController implements Initializable{

    @FXML
    private Button btnLogin;

    @FXML
    private ImageView imgLogo;

    @FXML
    private Hyperlink linkSignUp;

    @FXML
    private PasswordField pwdPassword;

    @FXML
    private TextField txtUsername;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        // Necesito que no haya ningun campo con el foco
        txtUsername.setFocusTraversable(false);
        pwdPassword.setFocusTraversable(false);
        btnLogin.setFocusTraversable(false);
        linkSignUp.setFocusTraversable(false);
    }

    @FXML
    void login(ActionEvent event) {
        // Quiero que cuando se pulse el boton, compruebe si el nombre de usuario es "admin" y la contraseña es "admin"
        // Si los campos son correctos, deberá mostrar la página principal del programa
        // Si alguno de los campos está vacío deberá salir una alerta WARNING y que el primer campo que esté vacío haga un requestFocus()

        String username = txtUsername.getText();
        String password = pwdPassword.getText();

        if (username.equals("admin") && password.equals("admin")) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Cup of Java");
            alert.setContentText("¡Bienvenido!");
            alert.showAndWait();



        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Cup of Java");
            alert.setContentText("Credenciales incorrectas");
            alert.showAndWait();

            if(username.isEmpty()) {
                txtUsername.requestFocus();
            } else {
                pwdPassword.requestFocus();
            }
        }
    }

    @FXML
    void abrirRegister(ActionEvent event) throws IOException{
        // Obtener el nodo fuente del evento (en este caso, el botón "abrirRegister")
        Node sourceNode = (Node) event.getSource();

        // Obtener la escena actual desde el nodo fuente
        Scene currentScene = sourceNode.getScene();

        // Obtener la ventana principal (Stage) desde la escena actual
        Stage mainStage = (Stage) currentScene.getWindow();

        // Cargar la vista de registro en la ventana principal
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/CupOfJavaRegister.fxml"));
        Parent root = loader.load();
        mainStage.setScene(new Scene(root));
        mainStage.setTitle("Register for Cup of Java");
        mainStage.setResizable(false);
        mainStage.show();
    }


}
