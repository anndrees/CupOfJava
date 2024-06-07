package controller;

import com.google.gson.*;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import model.*;
import model.Articulos.*;

import java.io.*;
import java.net.URL;
import java.util.*;

public class ArticulosController implements Initializable{

    @FXML
    private Pane allPane;

    @FXML
    private Pane allPane1;

    @FXML
    private AnchorPane anchorMain;

    @FXML
    private Button btnBuscar;

    @FXML
    private Button btnHamburgerMenu;

    @FXML
    private Button btnRemove;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnVerticalMenu;

    @FXML
    private Button btnCloseApp;

    @FXML
    private ComboBox<Categorias> cboxCatArticulos;
    
    @FXML
    private Pane hamburgerPane;

    @FXML
    private Text txtUsuario;

    @FXML
    private TableView<Articulo> tblArticulos;

    @FXML
    private TableColumn<Articulo, String> colNombre;

    @FXML
    private TableColumn<Articulo, Double> colPrecio;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPrice;

    public static ObservableList<Articulo> articulos;

    @FXML
    private Pane verticalPane;

    @FXML
    private Pane allPaneModal;

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

    public static List<Articulo> getArticulos(){
        return articulos;
    }


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
    void abrirArticulos(MouseEvent event) {
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
    void abrirRecibos(MouseEvent event) {
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
        try{
            Node sourceNode = (Node) event.getSource();
            Scene currentScene = sourceNode.getScene();
            Stage mainStage = (Stage) currentScene.getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/Main.fxml"));
            Parent root = loader.load();
            MainController controller = loader.getController();
            controller.setCurrentUser(currentUser);
            controller.setArticulos(articulos);
            mainStage.setScene(new Scene(root));
            mainStage.setTitle("Cup of Java");
            mainStage.setResizable(false);
            mainStage.getIcons().add(new Image(getClass().getResource("../app/assets/imgs/squareFavicon.png").toString()));
            mainStage.centerOnScreen();
            mainStage.show();
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
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

        tblArticulos.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                System.out.println("Elemento seleccionado: " + newValue.getNombre());

				txtName.setText(newValue.getNombre());
                txtPrice.setText(newValue.getPrecio() + "");
                txtName.setDisable(false);
                txtPrice.setDisable(false);
                btnSave.setDisable(false);
                btnRemove.setDisable(false);
            }
            else{
                txtName.setDisable(true);
                txtPrice.setDisable(true);
                btnSave.setDisable(true);
                btnRemove.setDisable(true);
            }
        });
        articulos = FXCollections.observableArrayList();
        cargarArticulos();
        
        System.out.println("Artículos cargados: " + articulos.size());
    }

    private void cargarArticulos() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String rutaProyecto = System.getProperty("user.dir");
        String rutaJsonCoffes = rutaProyecto + File.separator + "src" + File.separator + "app" + File.separator + "assets" + File.separator + "json" + File.separator + "coffes.json";
        String rutaJsonFruits = rutaProyecto + File.separator + "src" + File.separator + "app" + File.separator + "assets" + File.separator + "json" + File.separator + "fruits.json";
        String rutaJsonDrinks = rutaProyecto + File.separator + "src" + File.separator + "app" + File.separator + "assets" + File.separator + "json" + File.separator + "drinks.json";
        String rutaJsonDesserts = rutaProyecto + File.separator + "src" + File.separator + "app" + File.separator + "assets" + File.separator + "json" + File.separator + "desserts.json";
        String rutaJsonOthers = rutaProyecto + File.separator + "src" + File.separator + "app" + File.separator + "assets" + File.separator + "json" + File.separator + "others.json";

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

        for (Articulo articulo : articulos) {
            System.out.println("nombre: " + articulo.getNombre() + ", precio: " + articulo.getPrecio());
        }
    }


    public void setCurrentUser(Usuario currentUser) {
        this.currentUser = currentUser;

        txtUsuario.setText("usuario: " + currentUser.getUsername());

    }


	@FXML
    void openFileChooser(ActionEvent event) {
        /*
        Un selector de archivos
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
        txtSearch.setFocusTraversable(false);
        btnHamburgerMenu.setFocusTraversable(false);
        btnVerticalMenu.setFocusTraversable(false);
        cboxCatArticulos.setFocusTraversable(false);
        tblArticulos.setFocusTraversable(false);
        txtSearch.setVisible(false);
    }

    @FXML
    public void guardarArticulo(MouseEvent mouseEvent){
        Articulo selectedArticulo = tblArticulos.getSelectionModel().getSelectedItem();
        String nombre = txtName.getText();
        String precio = txtPrice.getText().replace(",", ".");

        if(precio.matches("[0-9]+(\\.[0-9]+)?")) {
            System.out.println("precio " + precio);

            selectedArticulo.setNombre(nombre);
            selectedArticulo.setPrecio(Double.parseDouble(precio));

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String rutaProyecto = System.getProperty("user.dir");
            String rutaJsonCoffes = rutaProyecto + File.separator + "src" + File.separator + "app" + File.separator + "assets" + File.separator + "json" + File.separator + "coffes.json";
            String rutaJsonFruits = rutaProyecto + File.separator + "src" + File.separator + "app" + File.separator + "assets" + File.separator + "json" + File.separator + "fruits.json";
            String rutaJsonDrinks = rutaProyecto + File.separator + "src" + File.separator + "app" + File.separator + "assets" + File.separator + "json" + File.separator + "drinks.json";
            String rutaJsonDesserts = rutaProyecto + File.separator + "src" + File.separator + "app" + File.separator + "assets" + File.separator + "json" + File.separator + "desserts.json";
            String rutaJsonOthers = rutaProyecto + File.separator + "src" + File.separator + "app" + File.separator + "assets" + File.separator + "json" + File.separator + "others.json";

            switch(selectedArticulo){
                case Cafe ignored -> {
                    try{
                        File file = new File(rutaJsonCoffes);
                        if(file.exists() && file.length() > 0){
                            try{
                                FileReader reader = new FileReader(file);
                                JsonElement jsonElement = gson.fromJson(reader, JsonElement.class);
                                JsonArray jsonArray = jsonElement.getAsJsonArray();
                                for(JsonElement jsonElement1 : jsonArray){
                                    JsonObject jsonObject = jsonElement1.getAsJsonObject();
                                    if(jsonObject.get("nombre").getAsString().equals(selectedArticulo.getNombre())){
                                        jsonObject.addProperty("nombre", selectedArticulo.getNombre());
                                        jsonObject.addProperty("precio", selectedArticulo.getPrecio());
                                    }
                                }
                                FileWriter writer = new FileWriter(file);
                                writer.write(jsonElement.toString());
                                writer.close();
                                alertaCambio(selectedArticulo);

                            }catch(IOException e){
                                System.out.println(e.getMessage());
                            }
                        }
                    }catch(Exception e){
                        System.out.println(e.getMessage());
                    }
                }
                case Fruta ignored1 -> {
                    try{
                        File file = new File(rutaJsonFruits);
                        if(file.exists() && file.length() > 0){
                            try{
                                FileReader reader = new FileReader(file);
                                JsonElement jsonElement = gson.fromJson(reader, JsonElement.class);
                                JsonArray jsonArray = jsonElement.getAsJsonArray();
                                for(JsonElement jsonElement1 : jsonArray){
                                    JsonObject jsonObject = jsonElement1.getAsJsonObject();
                                    if(jsonObject.get("nombre").getAsString().equals(selectedArticulo.getNombre())){
                                        jsonObject.addProperty("nombre", selectedArticulo.getNombre());
                                        jsonObject.addProperty("precio", selectedArticulo.getPrecio());
                                    }
                                }
                                FileWriter writer = new FileWriter(file);
                                writer.write(jsonElement.toString());
                                writer.close();
                                alertaCambio(selectedArticulo);
                            }catch(IOException e){
                                System.out.println(e.getMessage());
                            }
                        }
                    }catch(Exception e){
                        System.out.println(e.getMessage());
                    }
                }
                case Refresco ignored2 -> {
                    try{
                        File file = new File(rutaJsonDrinks);
                        if(file.exists() && file.length() > 0){
                            try{
                                FileReader reader = new FileReader(file);
                                JsonElement jsonElement = gson.fromJson(reader, JsonElement.class);
                                JsonArray jsonArray = jsonElement.getAsJsonArray();
                                for(JsonElement jsonElement1 : jsonArray){
                                    JsonObject jsonObject = jsonElement1.getAsJsonObject();
                                    if(jsonObject.get("nombre").getAsString().equals(selectedArticulo.getNombre())){
                                        jsonObject.addProperty("nombre", selectedArticulo.getNombre());
                                        jsonObject.addProperty("precio", selectedArticulo.getPrecio());
                                    }
                                }
                                FileWriter writer = new FileWriter(file);
                                writer.write(jsonElement.toString());
                                writer.close();
                                alertaCambio(selectedArticulo);
                            }catch(IOException e){
                                System.out.println(e.getMessage());
                            }
                        }
                    }catch(Exception e){
                        System.out.println(e.getMessage());
                    }
                }
                case Postre ignored3 -> {
                    try{
                        File file = new File(rutaJsonDesserts);
                        if(file.exists() && file.length() > 0){
                            try{
                                FileReader reader = new FileReader(file);
                                JsonElement jsonElement = gson.fromJson(reader, JsonElement.class);
                                JsonArray jsonArray = jsonElement.getAsJsonArray();
                                for(JsonElement jsonElement1 : jsonArray){
                                    JsonObject jsonObject = jsonElement1.getAsJsonObject();
                                    if(jsonObject.get("nombre").getAsString().equals(selectedArticulo.getNombre())){
                                        jsonObject.addProperty("nombre", selectedArticulo.getNombre());
                                        jsonObject.addProperty("precio", selectedArticulo.getPrecio());
                                    }
                                }
                                FileWriter writer = new FileWriter(file);
                                writer.write(jsonElement.toString());
                                writer.close();
                                alertaCambio(selectedArticulo);
                            }catch(IOException e){
                                System.out.println(e.getMessage());
                            }
                        }
                    }catch(Exception e){
                        System.out.println(e.getMessage());
                    }
                }
                case Otro ignored4 -> {
                    try{
                        File file = new File(rutaJsonOthers);
                        if(file.exists() && file.length() > 0){
                            try{
                                FileReader reader = new FileReader(file);
                                JsonElement jsonElement = gson.fromJson(reader, JsonElement.class);
                                JsonArray jsonArray = jsonElement.getAsJsonArray();
                                for(JsonElement jsonElement1 : jsonArray){
                                    JsonObject jsonObject = jsonElement1.getAsJsonObject();
                                    if(jsonObject.get("nombre").getAsString().equals(selectedArticulo.getNombre())){
                                        jsonObject.addProperty("nombre", selectedArticulo.getNombre());
                                        jsonObject.addProperty("precio", selectedArticulo.getPrecio());
                                    }
                                }
                                FileWriter writer = new FileWriter(file);
                                writer.write(jsonElement.toString());
                                writer.close();
                                alertaCambio(selectedArticulo);
                            }catch(IOException e){
                                System.out.println(e.getMessage());
                            }
                        }
                    }catch(Exception e){
                        System.out.println(e.getMessage());
                    }
                }
                default -> {
                }
            }
        }



        tblArticulos.refresh();
    }

    private void alertaCambio(Articulo selectedArticulo) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Cup of Java");
        alert.setHeaderText("Modificación realizada con éxito");
        alert.setContentText("Se han guardado los cambios en el artículo " + selectedArticulo.getNombre());
        alert.showAndWait();
    }

    @FXML
    public void eliminarArticulo(MouseEvent mouseEvent){
        Articulo selectedArticulo = tblArticulos.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Eliminar artículo");
        alert.setHeaderText("¿Estás seguro de eliminar este artículo?");
        alert.setContentText("Se eliminará el siguiente artículo: " + selectedArticulo.getNombre());
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String rutaProyecto = System.getProperty("user.dir");
            String rutaJsonCoffes = rutaProyecto + File.separator + "src" + File.separator + "app" + File.separator + "assets" + File.separator + "json" + File.separator + "coffes.json";
            String rutaJsonFruits = rutaProyecto + File.separator + "src" + File.separator + "app" + File.separator + "assets" + File.separator + "json" + File.separator + "fruits.json";
            String rutaJsonDrinks = rutaProyecto + File.separator + "src" + File.separator + "app" + File.separator + "assets" + File.separator + "json" + File.separator + "drinks.json";
            String rutaJsonDesserts = rutaProyecto + File.separator + "src" + File.separator + "app" + File.separator + "assets" + File.separator + "json" + File.separator + "desserts.json";
            String rutaJsonOthers = rutaProyecto + File.separator + "src" + File.separator + "app" + File.separator + "assets" + File.separator + "json" + File.separator + "others.json";

			switch(selectedArticulo){
				case Cafe cafe -> {
					try{
						File file = new File(rutaJsonCoffes);
						if(file.exists() && file.length() > 0){
							try{
								FileReader reader = new FileReader(file);
								JsonElement jsonElement = gson.fromJson(reader, JsonElement.class);
								JsonArray jsonArray = getJsonElements(jsonElement, selectedArticulo);
								FileWriter writer = new FileWriter(file);
								gson.toJson(jsonArray, writer);
								writer.close();
							}catch(Exception e){
								System.out.println(e.getMessage());
							}
						}
					}catch(Exception e){
						System.out.println(e.getMessage());
					}
				}
				case Fruta fruta -> {
					try{
						File file = new File(rutaJsonFruits);
						if(file.exists() && file.length() > 0){
							try{
								FileReader reader = new FileReader(file);
								JsonElement jsonElement = gson.fromJson(reader, JsonElement.class);
								JsonArray jsonArray = getJsonElements(jsonElement, selectedArticulo);
								FileWriter writer = new FileWriter(file);
								gson.toJson(jsonArray, writer);
								writer.close();
							}catch(Exception e){
								System.out.println(e.getMessage());
							}
						}
					}catch(Exception e){
						System.out.println(e.getMessage());
					}
				}
				case Refresco refresco -> {
					try{
						File file = new File(rutaJsonDrinks);
						if(file.exists() && file.length() > 0){
							try{
								FileReader reader = new FileReader(file);
								JsonElement jsonElement = gson.fromJson(reader, JsonElement.class);
								JsonArray jsonArray = getJsonElements(jsonElement, selectedArticulo);
								FileWriter writer = new FileWriter(file);
								gson.toJson(jsonArray, writer);
								writer.close();
							}catch(Exception e){
								System.out.println(e.getMessage());
							}
						}
					}catch(Exception e){
						System.out.println(e.getMessage());
					}
				}
				case Postre postre -> {
					try{
						File file = new File(rutaJsonDesserts);
						if(file.exists() && file.length() > 0){
							try{
								FileReader reader = new FileReader(file);
								JsonElement jsonElement = gson.fromJson(reader, JsonElement.class);
								JsonArray jsonArray = getJsonElements(jsonElement, selectedArticulo);
								FileWriter writer = new FileWriter(file);
								gson.toJson(jsonArray, writer);
								writer.close();
							}catch(Exception e){
								System.out.println(e.getMessage());
							}
						}
					}catch(Exception e){
						System.out.println(e.getMessage());
					}
				}
				case Otro otro -> {
					try{
						File file = new File(rutaJsonOthers);
						if(file.exists() && file.length() > 0){
							try{
								FileReader reader = new FileReader(file);
								JsonElement jsonElement = gson.fromJson(reader, JsonElement.class);
								JsonArray jsonArray = getJsonElements(jsonElement, selectedArticulo);
								FileWriter writer = new FileWriter(file);
								gson.toJson(jsonArray, writer);
								writer.close();
							}catch(Exception e){
								System.out.println(e.getMessage());
							}
						}
					}catch(Exception e){
						System.out.println(e.getMessage());
					}
				}
				default -> {
				}
			}
            articulos.remove(selectedArticulo);
            Set<Articulo> articulosSet = new TreeSet<>(Comparator.comparing(Articulo::getNombre));
            articulosSet.addAll(articulos);
            articulos.clear();
            articulos.addAll(articulosSet);
            tblArticulos.setItems(articulos);
            txtName.setText("");
            txtPrice.setText("");
            txtName.setDisable(true);
            txtPrice.setDisable(true);
            btnRemove.setDisable(true);
            btnSave.setDisable(true);
        }
    }

    private static JsonArray getJsonElements(JsonElement jsonElement, Articulo selectedArticulo){
        JsonArray jsonArray = jsonElement.getAsJsonArray();
        for(int i = 0 ; i < jsonArray.size() ; i++){
            JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
            if(jsonObject.get("nombre").getAsString().equals(selectedArticulo.getNombre()) && jsonObject.get("precio").getAsDouble() == selectedArticulo.getPrecio()){
                jsonArray.remove(i);
            }
        }
        return jsonArray;
    }

    @FXML
    public void createItem(MouseEvent actionEvent){
		Parent root;
		try{
			root = FXMLLoader.load(getClass().getResource("../view/CreateItemForm.fxml"));
		}catch(IOException e){
			throw new RuntimeException(e);
		}

        Stage modalStage = new Stage();
        modalStage.initModality(Modality.APPLICATION_MODAL);
        modalStage.initOwner(((Node) actionEvent.getSource()).getScene().getWindow());
        Scene createItemScene = new Scene(root);
        modalStage.setScene(createItemScene);
        closeVerticalMenu(actionEvent);
        opacarAllPaneModal();

        modalStage.showAndWait();
        System.out.println("despues de abrir el modal");
        desopacarAllPaneModal();


        Set<Articulo> articulosSet = new TreeSet<>(Comparator.comparing(Articulo::getNombre));
        articulosSet.addAll(articulos);
        articulos.clear();
        articulos.addAll(articulosSet);
        tblArticulos.setItems(articulos);


        for (Articulo articulo : articulos) {
            System.out.println(articulo.getNombre() + " " + articulo.getPrecio());
        }



    }

    private void desopacarAllPaneModal(){
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(300), allPaneModal);
        fadeTransition.setFromValue(0.45);
        fadeTransition.setToValue(0.0);
        fadeTransition.play();
        fadeTransition.setOnFinished(event -> allPaneModal.setVisible(false));
    }

    private void opacarAllPaneModal(){
        allPaneModal.setOpacity(0.0);
        allPaneModal.setVisible(true);

        FadeTransition fadeTransition = new FadeTransition(Duration.millis(300), allPaneModal);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(0.45);
        fadeTransition.play();
    }

    public void setArticulos(ObservableList<Articulo> articulos){
        ArticulosController.articulos = articulos;
    }


}
