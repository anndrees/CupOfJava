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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Usuario;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static model.Usuario.users;

public class RegisterController implements Initializable{

    @FXML
    private AnchorPane anchorRegister;

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
    void unfocus(MouseEvent event) {
        txtUsername.getParent().requestFocus();
        pwdPassword.getParent().requestFocus();
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
        } else{
			Optional<String> result;
            try{
                do{
                    TextInputDialog dialog = new TextInputDialog();
                    dialog.setTitle("Cup of Java - Autorización");
                    dialog.setHeaderText("Autorización requerida");
                    dialog.setContentText("Debes introducir la contraseña del administrador para poder registrar el usuario:");
                    dialog.setResizable(false);
                    result = dialog.showAndWait();
                    if(result.isPresent()){
                        System.out.println("Texto: " + result.get());
                        if(result.get().equals("admin")){
                            Usuario u = new Usuario(txtUsername.getText(), pwdPassword.getText());
                            users.add(u);
                            System.out.println("Registro correcto");
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Cup of Java");
                            alert.setHeaderText("Registro correcto");
                            alert.setContentText("Ahora puedes iniciar sesión");
                            alert.showAndWait();
                        }else{
                            Alert alert = new Alert(Alert.AlertType.WARNING);
                            alert.setTitle("Cup of Java");
                            alert.setHeaderText("Contraseña de administrador incorrecta");
                            alert.setContentText("Vuelva a intentarlo");
                            alert.setResizable(false);
                            alert.showAndWait();
                        }
                    }
                }while(!result.get().equals("admin"));
            }catch(Exception e){}
		}
    }
}
