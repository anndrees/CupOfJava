package controller;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import model.*;

import com.google.gson.*;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class MainController implements Initializable{

    @FXML
    public Button btnCerrarSesion;
    @FXML
    private Pane allPane;

    @FXML
    private Pane allPane1; // este es el panel que se activaal pulsar el boton del menu vertical

    @FXML
    private AnchorPane anchorMain;

    @FXML
    private Button btnBuscar;

    @FXML
    private Button btnCobrar;

    @FXML
    private Button btnHamburgerMenu;

    @FXML
    private Button btnTickets;

    @FXML
    private Button btnVerticalMenu;

    @FXML
    private Button btnClearTicket;

    @FXML
    private Button btnCloseApp;

    @FXML
    private Button btnMoveTicket;

    @FXML
    private ComboBox<TipoPedido> cboxTipoPedido;

    @FXML
    private ComboBox<Categorias> cboxCatArticulos;

    @FXML
    private Pane hamburgerPane;

    @FXML
    private Label lblTicket;

    @FXML
    private Text txtUsuario;

    @FXML
    private TableView<Articulo> tblArticulos;

    @FXML
    private TableColumn<Articulo, String> colNombre;

    @FXML
    private TableColumn<Articulo, Double> colPrecio;

    @FXML
    private TableColumn<?, ?> colArticuloInTicket;

    @FXML
    private TableColumn<?, ?> colPrecioInTicket;

    @FXML
    private TableColumn<?, ?> colQtyInTicket;

    @FXML
    private TableView<?> tblTicket;

    private ObservableList<Articulo> articulos;

    @FXML
    private Pane verticalPane;

    @FXML
    private TextField txtSearch;

    @FXML
    private Button btnVentas;

    @FXML
    private Button btnArticulos;

    @FXML
    private Button btnRecibos;

    private Usuario currentUser;
    private boolean hamburgerMenuVisible = false;


    @FXML
    void toggleHamburgerMenu(MouseEvent event) {
        hamburgerPane.setVisible(true);
        allPane.setVisible(true);
        TranslateTransition hamburgerTransition = new TranslateTransition(Duration.millis(300), hamburgerPane);
        FadeTransition allPaneTransition = new FadeTransition(Duration.millis(300), allPane);
        if (hamburgerMenuVisible) {
            hamburgerPane.setMouseTransparent(true);
            allPaneTransition.setFromValue(0.5);
            allPaneTransition.setToValue(0.0);
            allPane.setMouseTransparent(true);
            hamburgerTransition.setToX(-200);
        } else {
            hamburgerPane.setMouseTransparent(false);
            allPaneTransition.setFromValue(0.0);
            allPaneTransition.setToValue(0.5);
            allPane.setMouseTransparent(false);
            hamburgerTransition.setToX(0);
        }
        allPaneTransition.play();
        hamburgerTransition.play();
        hamburgerMenuVisible = !hamburgerMenuVisible;
    }

    @FXML
    void abrirArticulos(ActionEvent event){
		try{
            Node sourceNode = (Node) event.getSource();
            Scene currentScene = sourceNode.getScene();
            Stage mainStage = (Stage) currentScene.getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/Articulos.fxml"));
            Parent root = loader.load();
            ArticulosController controller = loader.getController();
            controller.setCurrentUser(currentUser);
            controller.setArticulos(articulos);
            mainStage.setScene(new Scene(root));
            mainStage.setTitle("Cup of Java - Artículos");
            mainStage.setResizable(false);
            mainStage.getIcons().add(new Image(getClass().getResource("../app/assets/imgs/squareFavicon.png").toString()));
            mainStage.show();
		}catch(IOException e){
            e.printStackTrace();
		}
	}

    @FXML
    void abrirRecibos(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/Recibos.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(scene);
            stage.show();
            ((Node) event.getSource()).getScene().getWindow().hide();
        } catch (IOException ignored) {}
    }

    @FXML
    void abrirVentas(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/Ventas.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(scene);
            stage.show();
            ((Node) event.getSource()).getScene().getWindow().hide();
        } catch (IOException ignored) {}
    }

    @FXML
    void openVerticalMenu(ActionEvent event) { //Abre el vertical menu con una transicion que dure 300 milisegundos y establece el valor de opacidad de 0.0 a 1.0
        verticalPane.setMouseTransparent(false);
        allPane1.setMouseTransparent(false);
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(150), verticalPane);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);
        fadeTransition.play();
    }

    @FXML
    void closeVerticalMenu(MouseEvent event) {
        verticalPane.setMouseTransparent(true);
        allPane1.setMouseTransparent(true);
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(150), verticalPane);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);
        fadeTransition.play();
    }

    @FXML
    void clearTicket(ActionEvent event) {

    }

    @FXML
    void moveTicket(ActionEvent event) {

    }

    @FXML
    void closeApp(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar");
        alert.setHeaderText("Estás a punto de cerrar la aplicación");
        alert.setContentText("¿Estás seguro?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK) {
            System.exit(0);
        }
    }

    @FXML
    void buscar(KeyEvent event) {

        if(event.getCode() == KeyCode.ESCAPE || !txtSearch.isFocused()) {
            txtSearch.setVisible(false);
        }

        if(event.getCode() == KeyCode.ENTER) {
            cboxCatArticulos.setValue(Categorias.TODOS);


            String searchTerm = txtSearch.getText();
            ObservableList<Articulo> filteredArticulos = FXCollections.observableArrayList();

            for (Articulo articulo : articulos) {
                if (articulo.getNombre().toLowerCase().contains(searchTerm.toLowerCase())) {
                    filteredArticulos.add(articulo);
                }
            }

            tblArticulos.setItems(filteredArticulos);
            if(txtSearch.isVisible()){
                txtSearch.setVisible(false);
            }
            cboxCatArticulos.setValue(Categorias.TODOS);
        }


        if(txtSearch.isVisible()) {
            btnBuscar.setOnMouseClicked(e -> {
                cboxCatArticulos.setValue(Categorias.TODOS);

                String searchTerm = txtSearch.getText();
                ObservableList<Articulo> filteredArticulos = FXCollections.observableArrayList();

                for (Articulo articulo : articulos) {
                    if (articulo.getNombre().toLowerCase().contains(searchTerm.toLowerCase())) {
                        filteredArticulos.add(articulo);
                    }
                }

                tblArticulos.setItems(filteredArticulos);


            });
        }
    }

    @FXML
    void initBusqueda(ActionEvent event) {
        txtSearch.setVisible(true);
        txtSearch.setStyle("-fx-font-size: 20px; -fx-font-weight: bold");
        txtSearch.requestFocus();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Initialize");

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        String rutaProyecto = System.getProperty("user.dir");
        String rutaJsonCoffes = rutaProyecto + File.separator + "src" + File.separator + "app" + File.separator + "assets" + File.separator + "json" + File.separator + "coffes.json";
        String rutaJsonFruits = rutaProyecto + File.separator + "src" + File.separator + "app" + File.separator + "assets" + File.separator + "json" + File.separator + "fruits.json";
        unfocus();
        cboxTipoPedido.getItems().addAll(TipoPedido.values());
        cboxCatArticulos.getItems().addAll(Categorias.values());
        cboxTipoPedido.setValue(TipoPedido.COMER_AQUI);
        cboxCatArticulos.setValue(Categorias.TODOS);
        hamburgerPane.setTranslateX(-205);
        verticalPane.setVisible(true);
        verticalPane.setOpacity(0.0);
        verticalPane.setMouseTransparent(true);
        allPane1.setVisible(true);
        allPane1.setMouseTransparent(true);


        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));


        cboxCatArticulos.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == Categorias.TODOS) {
                tblArticulos.setItems(articulos);
            } else {
                ObservableList<Articulo> filteredArticulos = FXCollections.observableArrayList();
                for (Articulo articulo : articulos) {
                    if (articulo.getCategoria().equals(newValue.toString())) {
                        filteredArticulos.add(articulo);
                    }
                }
                tblArticulos.setItems(filteredArticulos);
            }
        });
/*
        articulos = FXCollections.observableArrayList();

        tblArticulos.setItems(articulos);
        Articulo solo = new Cafe("Solo", 1.5);
        Articulo bombon = new Cafe("Bombon", 3.0);
        Articulo carajillo = new Cafe("Carajillo", 3.5);
        Articulo manzana = new Fruta("Manzana", 1.0);
        articulos.addAll(solo, bombon, carajillo, manzana);


        File fileCoffee = new File(rutaJsonCoffes);
        File fileFruits = new File(rutaJsonFruits);

        try{
            FileWriter writer;
            writer = new FileWriter(fileCoffee);

            JsonArray jsonArray = new JsonArray();
            for (Articulo articulo : articulos) {
                if(articulo.getClass().getSimpleName().equals("Cafe")){
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("nombre", articulo.getNombre());
                    jsonObject.addProperty("precio", articulo.getPrecio());
                    jsonArray.add(jsonObject);
                }
            }
            gson.toJson(jsonArray, writer);
            writer.close();
		}catch(IOException e){
			throw new RuntimeException(e);
		}

        try{
            FileWriter writer;
            writer = new FileWriter(fileFruits);

            JsonArray jsonArray = new JsonArray();
            for (Articulo articulo : articulos) {
                if(articulo.getClass().getSimpleName().equals("Fruta")){
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("nombre", articulo.getNombre());
                    jsonObject.addProperty("precio", articulo.getPrecio());
                    jsonArray.add(jsonObject);
                }
            }
            gson.toJson(jsonArray, writer);
            writer.close();
        }catch(IOException e){
            throw new RuntimeException(e);
        }
        */

		cargarArticulos();


        System.out.println("Artículos cargados: " + articulos.size());
    }

    private void cargarArticulos() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String rutaProyecto = System.getProperty("user.dir");
        String rutaJsonCoffes = rutaProyecto + File.separator + "src" + File.separator + "app" + File.separator + "assets" + File.separator + "json" + File.separator + "coffes.json";
        String rutaJsonFruits = rutaProyecto + File.separator + "src" + File.separator + "app" + File.separator + "assets" + File.separator + "json" + File.separator + "fruits.json";

        articulos = FXCollections.observableArrayList();


        try {
            File file = new File(rutaJsonCoffes);
            if (file.exists() && file.length() > 0) {
                try {
                    FileReader reader = new FileReader(file);
                    JsonElement jsonElement = gson.fromJson(reader, JsonElement.class);
                    JsonArray jsonArray = jsonElement.getAsJsonArray();

                    for (JsonElement jsonElement1 : jsonArray) {
                        JsonObject jsonObject = jsonElement1.getAsJsonObject();
                        if (jsonObject.has("nombre") && jsonObject.has("precio")) {
                            String nombre = jsonObject.get("nombre").getAsString();
                            double precio = jsonObject.get("precio").getAsDouble();
                            Articulo articulo = new Cafe(nombre, precio);
                            articulos.add(articulo);
                        } else {
                            System.out.println("El objeto JSON no tiene los atributos 'nombre' y 'precio'.");
                        }
                    }

                    tblArticulos.setItems(articulos);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("El archivo coffes.json no existe o está vacío en la ruta especificada.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            File file = new File(rutaJsonFruits);
            if (file.exists() && file.length() > 0) {
                try {
                    FileReader reader = new FileReader(file);
                    JsonElement jsonElement = gson.fromJson(reader, JsonElement.class);
                    JsonArray jsonArray = jsonElement.getAsJsonArray();

                    for (JsonElement jsonElement1 : jsonArray) {
                        JsonObject jsonObject = jsonElement1.getAsJsonObject();
                        if (jsonObject.has("nombre") && jsonObject.has("precio")) {
                            String nombre = jsonObject.get("nombre").getAsString();
                            double precio = jsonObject.get("precio").getAsDouble();
                            Articulo articulo = new Fruta(nombre, precio);
                            articulos.add(articulo);
                        } else {
                            System.out.println("El objeto JSON no tiene los atributos 'nombre' y 'precio'.");
                        }
                    }

                    tblArticulos.setItems(articulos);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("El archivo fruits.json no existe o está vacío en la ruta especificada.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Set<Articulo> articulosSet = new TreeSet<>(Comparator.comparing(Articulo::getNombre));


        articulosSet.addAll(articulos);
        articulos.clear();
        articulos.addAll(articulosSet);

        // Depuración
        for (Articulo articulo : articulos) {
            //System.out.println("nombre: " + articulo.getNombre() + ", precio: " + articulo.getPrecio());
        }
    }


    public void setCurrentUser(Usuario currentUser) {
        this.currentUser = currentUser;

        txtUsuario.setText("usuario: " + currentUser.getUsername());

    }


	/*
    @FXML
    void openFileChooser(ActionEvent event) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            btnBuscar.setImage(new Image(file.toURI().toString()));
        }

    }
	 */


    @FXML
    void abrirLogin(ActionEvent event) throws IOException{
        Stage mainStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/Login.fxml"));
        Parent root = loader.load();
        mainStage.setScene(new Scene(root));
        mainStage.setTitle("Cup of Java - Inicio de sesión");
        mainStage.setResizable(false);
		mainStage.getIcons().add(new Image(getClass().getResource("../app/assets/imgs/squareFavicon.png").toString()));
        mainStage.centerOnScreen();
        mainStage.show();

        ((Node) (event.getSource())).getScene().getWindow().hide();

    }

    @FXML
    void cerrarSesion(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cup of Java - Cerrar sesión");
        alert.setHeaderText("Estás a punto de cerrar tu sesión");
        alert.setContentText("¿Seguro que quieres continuar?");
        alert.showAndWait();
        if (alert.getResult() == ButtonType.OK) {
            try {
                abrirLogin(event);
            }catch(IOException ignored){
            }
		}
    }


    @FXML
    void unfocus() {
        btnBuscar.setFocusTraversable(false);
        btnCobrar.setFocusTraversable(false);
        txtSearch.setFocusTraversable(false);
        btnHamburgerMenu.setFocusTraversable(false);
        btnTickets.setFocusTraversable(false);
        btnVerticalMenu.setFocusTraversable(false);
        cboxCatArticulos.setFocusTraversable(false);
        cboxTipoPedido.setFocusTraversable(false);
        lblTicket.setFocusTraversable(false);
        tblTicket.setFocusTraversable(false);
        tblArticulos.setFocusTraversable(false);
        txtSearch.setVisible(false);
        lblTicket.getParent().requestFocus();
        tblArticulos.getSelectionModel().clearSelection();
    }

    public void setArticulos(ObservableList<Articulo> articulos){
        this.articulos = articulos;
    }
}
