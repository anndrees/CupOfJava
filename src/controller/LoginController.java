package controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
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

import java.io.File;
import java.io.FileReader;
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
            Usuario user = null;
            for (Usuario u : Usuario.getUsers()) {
                if(username.equals(u.getUsername()) && password.equals(u.getPassword())){
                    user = u;
                }
            }
            String rutaProyecto = System.getProperty("user.dir");
            String rutaJson = rutaProyecto + File.separator + "src" + File.separator + "app" + File.separator + "assets" + File.separator + "json" + File.separator + "users.json";
            try{
                FileReader reader = new FileReader(rutaJson);
                JsonParser parser = new JsonParser();
                JsonElement json = parser.parse(reader);
                JsonArray users = json.getAsJsonArray();
                boolean encontrado = false;
                for (JsonElement userElement : users) {
                    JsonObject userObj = userElement.getAsJsonObject();
                    if (userObj.get("username").getAsString().equals(username) && userObj.get("password").getAsString().equals(password)) {
                        encontrado = true;

                        user = new Usuario(userObj.get("username").getAsString(), userObj.get("password").getAsString());
                    }
                }

                if (encontrado) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/Main.fxml"));
                    Parent root;
                    try{
                        root = loader.load();
                        MainController mainController = loader.getController();
                        mainController.setCurrentUser(user);
                        Stage mainStage = new Stage();
                        mainStage.setScene(new Scene(root));
                        mainStage.setTitle("Cup of Java");
                        mainStage.setResizable(false);
                        mainStage.getIcons().add(new Image(getClass().getResource("../app/assets/imgs/squareFavicon.png").toString()));
                        mainStage.centerOnScreen();
                        mainStage.show();

                        ((Node) (event.getSource())).getScene().getWindow().hide();
                    }catch(Exception ignored){}
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Cup of Java");
                    alert.setHeaderText("Credenciales incorrectas");
                    alert.setContentText("Nombre de usuario o contraseña incorrectos, intentalo de nuevo");
                    alert.showAndWait();
                }
            }catch(IOException e){
                System.out.println(e.getMessage());
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
		mainStage.getIcons().add(new Image(getClass().getResource("../app/assets/imgs/squareFavicon.png").toString()));
        mainStage.show();
    }
}
