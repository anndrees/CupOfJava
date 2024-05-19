package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Usuario;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static model.Usuario.users;

public class LoginController implements Initializable{


    @FXML
    private AnchorPane anchorLogin;

    @FXML
    private Button btnLogin;

	@FXML
    private Hyperlink linkSignUp;

    @FXML
    private PasswordField pwdPassword;

    @FXML
    private TextField txtUsername;

    @FXML
    void focusPassword(KeyEvent event) {
        if((event.getCode() == KeyCode.TAB) || (event.getCode() == KeyCode.ENTER)) {
            pwdPassword.requestFocus();
        }
    }

    @FXML
    void focusLoginBtn(KeyEvent event) {
        if(event.getCode() == KeyCode.ENTER) {
            btnLogin.fire();
        }else if(event.getCode() == KeyCode.TAB) {
            btnLogin.requestFocus();
        }
    }

    @FXML
    void focusSignupLink(KeyEvent event) {
        if(event.getCode() == KeyCode.TAB) {
            linkSignUp.requestFocus();
        }
    }


    @FXML
    void focusUsername(KeyEvent event) {
        if(event.getCode() == KeyCode.TAB) {
            txtUsername.requestFocus();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        txtUsername.setFocusTraversable(false);
        pwdPassword.setFocusTraversable(false);
        btnLogin.setFocusTraversable(false);
        linkSignUp.setFocusTraversable(false);
        Usuario admin = new Usuario("admin", "admin");
        users.add(admin);
    }

    @FXML
    void unfocus() {
        txtUsername.getParent().requestFocus();
        pwdPassword.getParent().requestFocus();
    }

    @FXML
    void login(ActionEvent event){
        String username = txtUsername.getText();
        String password = pwdPassword.getText();

        if(username.isEmpty() || password.isEmpty()){

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Cup of Java");
            alert.setContentText("Por favor, rellene todos los campos");
            alert.showAndWait();

        }else {
            for (Usuario u : Usuario.getUsers()) {
                if(username.equals(u.getUsername()) && password.equals(u.getPassword())){
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/Main.fxml"));
                    Parent root;
					try{
                        root = loader.load();
                        Stage mainStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        mainStage.setScene(new Scene(root));
                        mainStage.setTitle("Cup of Java");
                        mainStage.setResizable(false);
						//noinspection DataFlowIssue
						mainStage.getIcons().add(new Image(getClass().getResource("../app/assets/imgs/squareFavicon.png").toString()));
                        mainStage.setX((Toolkit.getDefaultToolkit().getScreenSize().getWidth() - mainStage.getWidth()) / 2);
                        mainStage.setY((Toolkit.getDefaultToolkit().getScreenSize().getHeight() - mainStage.getHeight()) / 2);
                        mainStage.show();
					}catch(Exception ignored){}
                }
            }
        }
    }

    @FXML
    void abrirRegister(ActionEvent event) throws IOException{
        Node sourceNode = (Node) event.getSource();
        Scene currentScene = sourceNode.getScene();
        Stage mainStage = (Stage) currentScene.getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/Register.fxml"));
        Parent root = loader.load();
        mainStage.setScene(new Scene(root));
        mainStage.setTitle("Cup of Java - Registro");
        mainStage.setResizable(false);
		//noinspection DataFlowIssue
		mainStage.getIcons().add(new Image(getClass().getResource("../app/assets/imgs/squareFavicon.png").toString()));
        mainStage.show();
    }
}
