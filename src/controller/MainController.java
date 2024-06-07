package controller;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
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
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import model.*;

import com.google.gson.*;
import model.Articulos.*;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class MainController implements Initializable{

    @FXML
    public Button btnCerrarSesion;
    @FXML
    private Pane allPane;

    @FXML
    private Pane allPane1;

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
    private TableColumn<Ticket, String> colArticuloInTicket;

    @FXML
    private TableColumn<Ticket, Double> colPrecioInTicket;

    @FXML
    private TableColumn<Ticket, Integer> colQtyInTicket;

    @FXML
    private TableView<Ticket> tblTicket;

    private ObservableList<Articulo> articulos;
    private final ObservableList<Ticket> tickets = FXCollections.observableArrayList();


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
            System.out.println(e.getMessage());
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
    void openVerticalMenu(ActionEvent event) {
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
        unfocus();
        cboxCatArticulos.getItems().addAll(Categorias.values());
        cboxCatArticulos.setValue(Categorias.TODOS);
        hamburgerPane.setTranslateX(-205);
        verticalPane.setVisible(true);
        verticalPane.setOpacity(0.0);
        verticalPane.setMouseTransparent(true);
        allPane1.setVisible(true);
        allPane1.setMouseTransparent(true);


        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        colArticuloInTicket.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colQtyInTicket.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colPrecioInTicket.setCellValueFactory(new PropertyValueFactory<>("total"));


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

        tblTicket.itemsProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue != null && !newValue.isEmpty()) {
                btnTickets.setText("GUARDAR TICKET");
            } else {
                btnTickets.setText("TICKETS ABIERTOS");
            }
        });

		cargarArticulos();
    }

    private void cargarArticulos() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String rutaProyecto = System.getProperty("user.dir");
        String rutaJsonCoffes = rutaProyecto + File.separator + "src" + File.separator + "app" + File.separator + "assets" + File.separator + "json" + File.separator + "coffes.json";
        String rutaJsonFruits = rutaProyecto + File.separator + "src" + File.separator + "app" + File.separator + "assets" + File.separator + "json" + File.separator + "fruits.json";
        String rutaJsonDrinks = rutaProyecto + File.separator + "src" + File.separator + "app" + File.separator + "assets" + File.separator + "json" + File.separator + "drinks.json";
        String rutaJsonDesserts = rutaProyecto + File.separator + "src" + File.separator + "app" + File.separator + "assets" + File.separator + "json" + File.separator + "desserts.json";
        String rutaJsonOthers = rutaProyecto + File.separator + "src" + File.separator + "app" + File.separator + "assets" + File.separator + "json" + File.separator + "others.json";

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
                    System.out.println(e.getMessage());
                }
            } else {
                System.out.println("El archivo coffes.json no existe o está vacío en la ruta especificada.");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
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
                    System.out.println(e.getMessage());
                }
            } else {
                System.out.println("El archivo fruits.json no existe o está vacío en la ruta especificada.");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try{
            File file = new File(rutaJsonDrinks);
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
                            Articulo articulo = new Refresco(nombre,precio);
                            articulos.add(articulo);
                        } else {
                            System.out.println("El objeto JSON no tiene los atributos 'nombre' y 'precio'.");
                        }
                    }

                    tblArticulos.setItems(articulos);
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            } else {
                System.out.println("El archivo drinks.json no existe o está vacío en la ruta especificada.");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try{
            File file = new File(rutaJsonDesserts);
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
                            Articulo articulo = new Postre(nombre,precio);
                            articulos.add(articulo);
                        } else {
                            System.out.println("El objeto JSON no tiene los atributos 'nombre' y 'precio'.");
                        }
                    }

                    tblArticulos.setItems(articulos);
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            } else {
                System.out.println("El archivo desserts.json no existe o está vacío en la ruta especificada.");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try{
            File file = new File(rutaJsonOthers);
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
                            Articulo articulo = new Otro(nombre,precio);
                            articulos.add(articulo);
                        } else {
                            System.out.println("El objeto JSON no tiene los atributos 'nombre' y 'precio'.");
                        }
                    }

                    tblArticulos.setItems(articulos);
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            } else {
                System.out.println("El archivo others.json no existe o está vacío en la ruta especificada.");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


        Set<Articulo> articulosSet = new TreeSet<>(Comparator.comparing(Articulo::getNombre));


        articulosSet.addAll(articulos);
        articulos.clear();
        articulos.addAll(articulosSet);

    }


    public void setCurrentUser(Usuario currentUser) {
        this.currentUser = currentUser;

        txtUsuario.setText("usuario: " + currentUser.getUsername());

    }


	/*
	Método para abrir el selector de archivos
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
    private void handleTableClick(MouseEvent event) {
        Articulo selectedArticulo = tblArticulos.getSelectionModel().getSelectedItem();

        if(selectedArticulo != null){
            if (event.getClickCount() == 2 && event.getButton() == MouseButton.PRIMARY) {

                // Un ticket es un objeto Ticket que tiene una cantidad y un artículo
                // recorre los tickets y verifica si hay alguno con el mismo nombre
                Ticket ticketEncontrado = null;

                for (Ticket ticket : tickets) {
                    if (ticket.getArticulo().getNombre().equals(selectedArticulo.getNombre())) {
                        ticketEncontrado = ticket;
                        ticketEncontrado.increaseQuantity();
                        ticketEncontrado.updatePrecio();
                        tickets.add(ticketEncontrado);
                        tickets.remove(ticket);
                        break;
                    }else {
                        ticketEncontrado = null;
                    }
                }
                if(ticketEncontrado == null){
                    tickets.add(new Ticket(1, selectedArticulo));
                }

                Set<Ticket> ticketsSet = new TreeSet<>(Comparator.comparing(Ticket::getNombre));
                ticketsSet.addAll(tickets);
                tickets.clear();
                tickets.addAll(ticketsSet);
                tblTicket.setItems(tickets);
                tblTicket.refresh();

                //recorre los tickets y sumando las cantidades

                int total = 0;
                for (Ticket ticket : tickets) {
                    total += ticket.getQty();
                }
                lblTicket.setText("TICKET  " + total);

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
        lblTicket.setFocusTraversable(false);
        tblTicket.setFocusTraversable(false);
        tblArticulos.setFocusTraversable(false);
        txtSearch.setVisible(false);
        lblTicket.getParent().requestFocus();
    }

    public void setArticulos(ObservableList<Articulo> articulos){
        this.articulos = articulos;
    }

    @FXML
    void clickOnbtnTickets(MouseEvent event) {
        if(btnTickets.getText().equals("TICKETS ABIERTOS")){
            Parent root;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/ListaTicketsAbiertos.fxml"));
            try{
                root = loader.load();
            }catch(IOException e){
                throw new RuntimeException(e);
            }
            OpenListController controller = loader.getController();
            controller.setStage((Stage) btnBuscar.getScene().getWindow());
            Stage modalStage = new Stage();
            modalStage.initModality(Modality.APPLICATION_MODAL);
            modalStage.initOwner(((Node) event.getSource()).getScene().getWindow());
            Scene createItemScene = new Scene(root);
            modalStage.setScene(createItemScene);
            modalStage.setTitle("Cup of Java - Lista de Tickets");
            modalStage.setResizable(false);
            modalStage.getIcons().add(new Image(getClass().getResource("../app/assets/imgs/squareFavicon.png").toString()));
            modalStage.centerOnScreen();
            modalStage.showAndWait();
        }else if(btnTickets.getText().equals("GUARDAR TICKET")){

            Parent root;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/SaveTicketForm.fxml"));
            try{
                root = loader.load();
            }catch(IOException e){
                throw new RuntimeException(e);
            }

            SaveTicketController controller = loader.getController();
            controller.setTickets(tickets);
            Stage modalStage = new Stage();
            modalStage.initModality(Modality.APPLICATION_MODAL);
            modalStage.initOwner(((Node) event.getSource()).getScene().getWindow());
            Scene createItemScene = new Scene(root);
            modalStage.setScene(createItemScene);
            modalStage.setTitle("Cup of Java - Guardar Ticket");
            modalStage.setResizable(false);
            modalStage.getIcons().add(new Image(getClass().getResource("../app/assets/imgs/squareFavicon.png").toString()));
            modalStage.centerOnScreen();
            modalStage.showAndWait();

            tickets.clear();
            tblTicket.setItems(tickets);
            tblTicket.refresh();
            lblTicket.setText("TICKET");
            btnTickets.setText("TICKETS ABIERTOS");

            tickets.addListener((ListChangeListener<Ticket>) c -> {
                if (!tickets.isEmpty()) {
                    btnTickets.setText("GUARDAR TICKET");
                } else {
                    btnTickets.setText("TICKETS ABIERTOS");
                }
            });

        }
    }
}
