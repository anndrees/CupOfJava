package controller;

import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Usuario;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static model.Usuario.users;

public class LoginController implements Initializable{


    @FXML
    private AnchorPane anchorLogin;

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
        Usuario admin = new Usuario("admin", "admin");
        users.add(admin);
    }

    @FXML
    void unfocus(MouseEvent event) {
        txtUsername.getParent().requestFocus();
        pwdPassword.getParent().requestFocus();
    }

    @FXML
    void login(ActionEvent event) {
        // Quiero que cuando se pulse el boton, compruebe si el nombre de usuario es "admin" y la contraseña es "admin"
        // Si los campos son correctos, deberá mostrar la página principal del programa
        // Si alguno de los campos está vacío deberá salir una alerta WARNING y que el primer campo que esté vacío haga un requestFocus()


        String username = txtUsername.getText();
        String password = pwdPassword.getText();

        /*if (username.equals("admin") && password.equals("admin")){

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Cup of Java");
            alert.setContentText("¡Bienvenido!");
            alert.showAndWait();

        } else */if(username.isEmpty() || password.isEmpty()){

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Cup of Java");
            alert.setContentText("Por favor, rellene todos los campos");
            alert.showAndWait();

        }else {// verfica que nombre de usuario exista como objeto Usuario revisando todos los usuarios sin el uso de un metodo getUsers()
            for (Usuario u : Usuario.getUsers()) {
                if(username.equals(u.getUsername()) && password.equals(u.getPassword())){
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Cup of Java");
                    alert.setContentText("¡Bienvenido!");
                    alert.showAndWait();

                    // Carga la vista principal de la aplicación
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/Main.fxml"));
					Parent root = null;
					try{
						root = loader.load();
					}catch(IOException e){
						throw new RuntimeException(e);
					}
					Stage mainStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    mainStage.setScene(new Scene(root));
                    mainStage.setTitle("Cup of Java");
                    mainStage.setResizable(false);
                    mainStage.show();
                }
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/Register.fxml"));
        Parent root = loader.load();
        mainStage.setScene(new Scene(root));
        mainStage.setTitle("Cup of Java - Register");
        mainStage.setResizable(false);
        mainStage.show();
    }


}
