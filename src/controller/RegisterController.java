package controller;

import com.google.gson.*;
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
import model.Usuario;

import java.io.FileReader;
import java.io.FileWriter;
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



    @FXML
    void focusLoginLink(KeyEvent event) {
        if(event.getCode() == KeyCode.TAB) {
            linkLogin.requestFocus();
        }
    }

    @FXML
    void focusPassword(KeyEvent event) {
        if((event.getCode() == KeyCode.TAB) || (event.getCode() == KeyCode.ENTER)) {
            pwdPassword.requestFocus();
        }
    }

    @FXML
    void focusSignupBtn(KeyEvent event) {
        if(event.getCode() == KeyCode.ENTER) {
            btnRegister.fire();
        }else if(event.getCode() == KeyCode.TAB) {
            btnRegister.requestFocus();
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
		mainStage.getIcons().add(new Image(getClass().getResource("../app/assets/imgs/squareFavicon.png").toString()));
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
                    dialog.getDialogPane().setStyle("-fx-background-color: #c79f92;");
                    dialog.getDialogPane().setGraphic(new ImageView(getClass().getResource("../app/assets/imgs/auth.png").toString()));
                    TextField input = dialog.getEditor();
                    input.setStyle("-fx-background-color: white;");
                    input.setStyle("-fx-border-color: black;");
                    input.setStyle("-fx-border-width: 1px;");
                    input.setStyle("-fx-border-radius: 5px;");
                    input.setStyle("-fx-padding: 5px;");
                    input.setStyle("-fx-font-size: 14px;");
                    input.setStyle("-fx-text-fill: black;");
                    result = dialog.showAndWait();
                    if(result.isPresent()){
                        //System.out.println("Texto: " + result.get());
                        if(result.get().equals("admin")){
                            try{

                                Usuario u = new Usuario(txtUsername.getText(), pwdPassword.getText());
                                users.add(u);
                                FileReader reader = new FileReader("src/app/assets/json/users.json");
                                JsonParser parser = new JsonParser();
                                JsonElement json = parser.parse(reader);
                                JsonArray usuarios = json.getAsJsonArray();

                                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                                String nuevoUsuarioJson = gson.toJson(u);

                                JsonElement nuevoUsuarioElement = new JsonParser().parse(nuevoUsuarioJson);
                                usuarios.add(nuevoUsuarioElement);

                                FileWriter writer = new FileWriter("src/app/assets/json/users.json");
                                // Lo escribe en el archivo JSON, pero formateado
                                gson.toJson(usuarios, writer);
                                writer.close();

                                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                alert.setTitle("Cup of Java");
                                alert.setHeaderText("Registro correcto");
                                alert.setContentText("Ahora puedes iniciar sesión");
                                alert.showAndWait();
                                txtUsername.clear();
                                pwdPassword.clear();
                                abrirLogin(event);
                            }catch(IOException e){
                                e.printStackTrace();
                            }
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
            }catch(Exception ignored){}
		}
    }
}
