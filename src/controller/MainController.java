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
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import javafx.scene.image.ImageView;
import model.*;

import com.google.gson.*;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.Comparator;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.TreeSet;

public class MainController implements Initializable{

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
    private ListView<?> listTicket;

    @FXML
    private TableView<Articulo> tblArticulos;

    @FXML
    private TableColumn<Articulo, String> colFoto;

    @FXML
    private TableColumn<Articulo, String> colNombre;

    @FXML
    private TableColumn<Articulo, Double> colPrecio;

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
    void abrirArticulos(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/Articulos.fxml"));
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

        if(event.getCode() == KeyCode.ESCAPE) {
            txtSearch.setVisible(false);
        }

        if(event.getCode() == KeyCode.ENTER) {
            String searchTerm = txtSearch.getText();
            ObservableList<Articulo> filteredArticulos = FXCollections.observableArrayList();

            for (Articulo articulo : articulos) {
                if (articulo.getNombre().toLowerCase().contains(searchTerm.toLowerCase())) {
                    filteredArticulos.add(articulo);
                }
            }

            tblArticulos.setItems(filteredArticulos);
        }


        if(txtSearch.isVisible()) {
            btnBuscar.setOnMouseClicked(e -> {
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

        colFoto.setCellValueFactory(new PropertyValueFactory<>("foto"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));

        colFoto.setCellFactory(column -> new TableCell<Articulo, String>() {
            private final ImageView imageView = new ImageView();

            {
                imageView.setPreserveRatio(true);
                imageView.setFitWidth(50);
                imageView.setFitHeight(50);
            }

            @Override
            protected void updateItem(String imagePath, boolean empty) {
                super.updateItem(imagePath, empty);
                if (empty || imagePath == null) {
                    setGraphic(null);
                } else {
                    Image image = new Image(getClass().getResourceAsStream(imagePath));
                    imageView.setImage(image);
                    setGraphic(imageView);
                }
            }
        });

        articulos = FXCollections.observableArrayList();
        tblArticulos.setItems(articulos);

        // Crear los artículos en los ficheros json con FileWriter y Gson
        Articulo solo = new Cafe("../app/assets/imgs/articulos/solo.jpg", "Cafe solo", 3.0);
        Articulo bombon = new Cafe("../app/assets/imgs/articulos/bombon.jpg", "Cafe Bombon", 3);
        Articulo carajillo = new Cafe("", "Carajillo", 7.5);
        Articulo manzana = new Fruta("../app/assets/imgs/articulos/manzana.jpg", "Manzana", 2.5);
        articulos.addAll(solo, bombon, carajillo, manzana);


        File file = new File("../app/assets/json/coffes.json");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException ignored) {
            }
        }

        try (FileWriter writer = new FileWriter(file)) {
            Gson gson = new Gson();
            gson.toJson(articulos, writer);
        } catch (IOException ignored) {
        }

        cargarArticulos();

        System.out.println("Artículos cargados: " + articulos.size());
    }

    private void cargarArticulos() {
        Gson gson = new Gson();
        try {
            File file = new File("app/assets/json/coffes.json");
            if (!file.exists()) {
                System.out.println("El archivo coffes.json no existe en la ruta especificada.");
                return;
            }
            FileReader reader = new FileReader(file);
            JsonElement jsonElement = gson.fromJson(reader, JsonElement.class);
            JsonArray jsonArray = jsonElement.getAsJsonArray();
            for (JsonElement element : jsonArray) {
                JsonObject jsonObject = element.getAsJsonObject();
                String nombre = jsonObject.get("nombre").getAsString();
                String foto = jsonObject.get("fotos").getAsString();
                double precio = jsonObject.get("precio").getAsDouble();
                articulos.add(new Cafe(foto, nombre, precio));
            }
        } catch (IOException ignored) {
        }

        // Cargar el JSON con las frutas
        try {
            File file = new File("app/assets/json/fruits.json");
            if (!file.exists()) {
                System.out.println("El archivo fruits.json no existe en la ruta especificada.");
                return;
            }
            FileReader reader = new FileReader(file);
            JsonElement jsonElement = gson.fromJson(reader, JsonElement.class);
            JsonArray jsonArray = jsonElement.getAsJsonArray();
            for (JsonElement element : jsonArray) {
                JsonObject jsonObject = element.getAsJsonObject();
                String nombre = jsonObject.get("nombre").getAsString();
                String foto = jsonObject.get("fotos").getAsString();
                double precio = jsonObject.get("precio").getAsDouble();
                articulos.add(new Fruta(foto, nombre, precio));
            }
        } catch (IOException ignored) {
        }

        TreeSet<Articulo> articulosSet = new TreeSet<>(new Comparator<Articulo>() {
            @Override
            public int compare(Articulo articulo1, Articulo articulo2) {
                return articulo1.getNombre().compareTo(articulo2.getNombre());
            }
        });

        articulosSet.addAll(articulos);
        articulos.clear();
        articulos.addAll(articulosSet);

        // Depuración
        for (Articulo articulo : articulos) {
            System.out.println("Foto: " + articulo.getFoto() + ", Nombre: " + articulo.getNombre() + ", Precio: " + articulo.getPrecio());
        }
    }


    public void setCurrentUser(Usuario currentUser) {
        this.currentUser = currentUser;
    }


	@FXML
    void openFileChooser(ActionEvent event) {
        /*
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            btnBuscar.setImage(new Image(file.toURI().toString()));
        }
        */
    }


    @FXML
    void abrirLogin(ActionEvent event) throws IOException{
        Node sourceNode = (Node) event.getSource();
        Scene currentScene = sourceNode.getScene();
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
        listTicket.setFocusTraversable(false);
        tblArticulos.setFocusTraversable(false);
        txtSearch.setVisible(false);
        lblTicket.getParent().requestFocus();
    }
}
