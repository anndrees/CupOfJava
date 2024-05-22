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

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable{

    @FXML
    private Pane allPane;

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
    private ComboBox<TipoPedido> cboxTipoPedido;

    @FXML
    private ComboBox<Categorias> cboxCatArticulos;

    @FXML
    private Pane hamburgerPane;

    @FXML
    private Label lblTicket;

    @FXML
    private Hyperlink linkCerrarSesion;

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
    private TextField txtSearch;

    @FXML
    private Button btnVentas;

    @FXML
    private Button btnArticulos;

    @FXML
    private Button btnRecibos;

    private Usuario currentUser;
    private boolean menuVisible = false;


    @FXML
    void toggleHamburgerMenu(MouseEvent event) {
        hamburgerPane.setVisible(true);
        allPane.setVisible(true);
        TranslateTransition hamburgerTransition = new TranslateTransition(Duration.millis(300), hamburgerPane);
        FadeTransition allPaneTransition = new FadeTransition(Duration.millis(300), allPane);
        if (menuVisible) {
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
        menuVisible = !menuVisible;
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
    void buscar(KeyEvent event) {
        // Añadir un listener que detecte si va cambiando el texto de txtSearch

        if(event.getCode() == KeyCode.ESCAPE) {
            txtSearch.setVisible(false);
            txtSearch.clear();
        }

        if(event.getCode() == KeyCode.ENTER) {
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
        }
    }

    @FXML
    void initBusqueda(ActionEvent event) {
        txtSearch.setVisible(true);
        txtSearch.requestFocus();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        unfocus();

        cboxTipoPedido.getItems().addAll(TipoPedido.values());
        cboxCatArticulos.getItems().addAll(Categorias.values());
        cboxTipoPedido.setValue(TipoPedido.COMER_AQUI);
        cboxCatArticulos.setValue(Categorias.TODOS);
        hamburgerPane.setTranslateX(-205);

        colFoto.setCellValueFactory(new PropertyValueFactory<>("foto"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        colFoto.setCellFactory(column -> {
            return new TableCell<Articulo, String>() {
                private final ImageView imageView = new ImageView();

                {
                    // Configura el tamaño de la imagen, no se debe deformar si las proporciones no son correctas
                    imageView.setPreserveRatio(true);
                    imageView.setFitWidth(50);
                    imageView.setFitHeight(50);
                }

                @Override
                protected void updateItem(String imagePath, boolean empty) {
                    super.updateItem(imagePath, empty);
                    if (empty || imagePath == null) {
                        // Si la celda está vacía, establece una imagen vacía
                        setGraphic(null);
                    } else {
                        // Carga la imagen desde la ruta proporcionada
                        Image image = new Image(getClass().getResourceAsStream(imagePath));
                        imageView.setImage(image);
                        setGraphic(imageView);
                    }
                }
            };
        });

        articulos = FXCollections.observableArrayList();
        tblArticulos.setItems(articulos);

        // Cargar los artículos y sus precios en la tabla
        cargarArticulos();

        // Depuración
        System.out.println("Artículos cargados: " + articulos.size());
        for (Articulo articulo : articulos) {
            System.out.println(articulo.getNombre());
        }
    }

    private void cargarArticulos() {
        // Aquí puedes cargar los artículos y sus precios de alguna fuente de datos
        // o de alguna otra parte de tu aplicación
        Articulo cafe = new Cafe("../app/assets/imgs/articulos/manzana.jpg","Cafe", 3.0);
        Articulo bombon = new Cafe("../app/assets/imgs/articulos/manzana.jpg","Cafe Bombon", 3.0);
        articulos.addAll(cafe, bombon);

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
        alert.setContentText("¿Seguro que quieres cerrar la sesión?");
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
        linkCerrarSesion.setFocusTraversable(false);
        listTicket.setFocusTraversable(false);
        tblArticulos.setFocusTraversable(false);
        txtSearch.setVisible(false);
        lblTicket.getParent().requestFocus();
    }
}
