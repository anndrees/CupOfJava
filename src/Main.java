import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

	private Stage stage; // Declaración de loginStage como miembro de la clase

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage loginStage) throws Exception {
		stage = loginStage;
		Parent root = FXMLLoader.load(getClass().getResource("view/Login.fxml"));
		stage.setScene(new Scene(root));
		stage.setTitle("Cup of Java - Login");
		stage.setResizable(false);
		//loginStage.getIcons().add(new javafx.scene.image.Image("imgs/faviconBlack.png"));
		stage.show();
	}

	public void loadRegisterView() throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("view/Register.fxml"));
		stage.setScene(new Scene(root));
		stage.setTitle("Cup of Java - Register");
		stage.setResizable(false);
		stage.show();
	}
}
