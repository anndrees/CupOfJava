package controller;

import com.google.gson.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Articulos.*;
import model.Categorias;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class CreateItemController implements Initializable{

    @FXML
    private AnchorPane anchorCreateItem;

    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnCreateItem;

	@FXML
	private ComboBox<Categorias> cboxCat;

    @FXML
    private ImageView imgLogo;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtPrecio;

	@FXML
	void focusCancelBtn(KeyEvent event) {
		if(event.getCode() == KeyCode.TAB) {
			btnCancelar.requestFocus();
		}
	}

	@FXML
	void focusCategory(KeyEvent event) {
		if(event.getCode() == KeyCode.TAB) {
			cboxCat.requestFocus();
		}
	}

	@FXML
	void focusCreateBtn(KeyEvent event) {
		if(event.getCode() == KeyCode.TAB) {
			btnCreateItem.requestFocus();
		}
	}

	@FXML
	void focusName(KeyEvent event) {
		if(event.getCode() == KeyCode.TAB) {
			txtNombre.requestFocus();
		}
	}

	@FXML
	void focusPrice(KeyEvent event) {
		if(event.getCode() == KeyCode.TAB) {
			txtPrecio.requestFocus();
		}
	}

    @FXML
    void cancelar(ActionEvent event) {
		if(txtNombre.getText().isEmpty() && txtPrecio.getText().isEmpty() && cboxCat.getValue() == null) {
			Stage stage = (Stage) btnCancelar.getScene().getWindow();
			stage.close();
		}else{
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setTitle("Cancelar operación");
			alert.setHeaderText("Estás a punto cancelar la operación");
			alert.setContentText(
					"Se perderán todos los cambios que no hayas guardado.\n" +
					"¿Seguro que quieres continuar?"
			);
			Optional<ButtonType> result = alert.showAndWait();
			if(result.get() == ButtonType.OK) {
				Stage stage = (Stage) btnCancelar.getScene().getWindow();
				stage.close();
			}
		}
    }

    @FXML
    void createItem(ActionEvent event) {
		String rutaProyecto = System.getProperty("user.dir");
		String rutaJsonCoffes = rutaProyecto + File.separator + "src" + File.separator + "app" + File.separator + "assets" + File.separator + "json" + File.separator + "coffes.json";
		String rutaJsonFruits = rutaProyecto + File.separator + "src" + File.separator + "app" + File.separator + "assets" + File.separator + "json" + File.separator + "fruits.json";
		String rutaJsonDrinks = rutaProyecto + File.separator + "src" + File.separator + "app" + File.separator + "assets" + File.separator + "json" + File.separator + "drinks.json";
		String rutaJsonDesserts = rutaProyecto + File.separator + "src" + File.separator + "app" + File.separator + "assets" + File.separator + "json" + File.separator + "desserts.json";
		String rutaJsonOthers = rutaProyecto + File.separator + "src" + File.separator + "app" + File.separator + "assets" + File.separator + "json" + File.separator + "others.json";

		String nombre = txtNombre.getText();
		String precio = txtPrecio.getText();
		Categorias categoria = cboxCat.getValue();
		precio = precio.replace(',', '.');
		if(!nombre.isEmpty()) {
			nombre = Character.toUpperCase(nombre.charAt(0)) + nombre.substring(1);
		}

		if(precio.contains("€") || precio.contains("$")) {
			precio = precio.replace("€", "");
			precio = precio.replace("$", "");
		}

		if(precio.contains(".") && precio.matches("^-?[0-9]+(\\.[0-9])?$")) {
			if(Integer.parseInt(precio.substring(precio.indexOf(".") + 1)) < 10) {
				precio = precio.substring(0, precio.indexOf(".") + 2) + "0";
			}
		}
		System.out.println(precio);

		if(!nombre.isEmpty() && precio.matches("^-?[0-9]+(\\.[0-9]{2})?$") && !precio.contains("€") && categoria != null) {

			switch(cboxCat.getValue().toString()) {
				case "Cafés":
					Cafe cafe = new Cafe(nombre, Double.parseDouble(precio));
					ArticulosController.getArticulos().add(cafe);

					Gson gson = new GsonBuilder().setPrettyPrinting().create();
					try {
						FileReader reader = new FileReader(rutaJsonCoffes);
						JsonElement jsonElement = gson.fromJson(reader, JsonElement.class);
						JsonArray jsonArray = jsonElement.getAsJsonArray();
						JsonObject coffeeObject = new JsonObject();
						coffeeObject.addProperty("nombre", cafe.getNombre());
						coffeeObject.addProperty("precio", cafe.getPrecio());
						jsonArray.add(coffeeObject);
						FileWriter writer = new FileWriter(rutaJsonCoffes);
						writer.write(jsonElement.toString());
						writer.close();

					} catch (IOException e) {
						System.out.println(e.getMessage());
					}

					System.out.println("Articulo creado: " + cafe.getNombre() + " " + cafe.getPrecio());
					break;
				case "Frutas":
					Fruta fruta = new Fruta(nombre, Double.parseDouble(precio));
					ArticulosController.getArticulos().add(fruta);

					Gson gsonFruits = new GsonBuilder().setPrettyPrinting().create();
					try {
						FileReader reader = new FileReader(rutaJsonFruits);
						JsonElement jsonElement = gsonFruits.fromJson(reader, JsonElement.class);
						JsonArray jsonArray = jsonElement.getAsJsonArray();
						JsonObject fruitObject = new JsonObject();
						fruitObject.addProperty("nombre", fruta.getNombre());
						fruitObject.addProperty("precio", fruta.getPrecio());
						jsonArray.add(fruitObject);
						FileWriter writer = new FileWriter(rutaJsonFruits);
						writer.write(jsonElement.toString());
						writer.close();

					} catch (IOException e) {
						System.out.println(e.getMessage());
					}
					System.out.println("Articulo creado: " + fruta.getNombre() + " " + fruta.getPrecio());
					break;
				case "Refrescos":
					Refresco refresco = new Refresco(nombre, Double.parseDouble(precio));
					ArticulosController.getArticulos().add(refresco);

					Gson gsonDrinks = new GsonBuilder().setPrettyPrinting().create();
					try {
						FileReader reader = new FileReader(rutaJsonDrinks);
						JsonElement jsonElement = gsonDrinks.fromJson(reader, JsonElement.class);
						JsonArray jsonArray = jsonElement.getAsJsonArray();
						JsonObject drinkObject = new JsonObject();
						drinkObject.addProperty("nombre", refresco.getNombre());
						drinkObject.addProperty("precio", refresco.getPrecio());
						jsonArray.add(drinkObject);
						FileWriter writer = new FileWriter(rutaJsonDrinks);
						writer.write(jsonElement.toString());
						writer.close();
					} catch (IOException e) {
						System.out.println(e.getMessage());
					}
					System.out.println("Articulo creado: " + refresco.getNombre() + " " + refresco.getPrecio());
					break;
				case "Postres":
					Postre postre = new Postre(nombre, Double.parseDouble(precio));
					ArticulosController.getArticulos().add(postre);

					Gson gsonDesserts = new GsonBuilder().setPrettyPrinting().create();
					try {
						FileReader reader = new FileReader(rutaJsonDesserts);
						JsonElement jsonElement = gsonDesserts.fromJson(reader, JsonElement.class);
						JsonArray jsonArray = jsonElement.getAsJsonArray();
						JsonObject dessertObject = new JsonObject();
						dessertObject.addProperty("nombre", postre.getNombre());
						dessertObject.addProperty("precio", postre.getPrecio());
						jsonArray.add(dessertObject);
						FileWriter writer = new FileWriter(rutaJsonDesserts);
						writer.write(jsonElement.toString());
						writer.close();
					} catch (IOException e) {
						System.out.println(e.getMessage());
					}
					System.out.println("Articulo creado: " + postre.getNombre() + " " + postre.getPrecio());
					break;
				case "Otros":
					Otro otro = new Otro(nombre, Double.parseDouble(precio));
					ArticulosController.getArticulos().add(otro);

					Gson gsonOthers = new GsonBuilder().setPrettyPrinting().create();
					try {
						FileReader reader = new FileReader(rutaJsonOthers);
						JsonElement jsonElement = gsonOthers.fromJson(reader, JsonElement.class);
						JsonArray jsonArray = jsonElement.getAsJsonArray();
						JsonObject dessertObject = new JsonObject();
						dessertObject.addProperty("nombre", otro.getNombre());
						dessertObject.addProperty("precio", otro.getPrecio());
						jsonArray.add(dessertObject);
						FileWriter writer = new FileWriter(rutaJsonDesserts);
						writer.write(jsonElement.toString());
						writer.close();
					} catch (IOException e) {
						System.out.println(e.getMessage());
					}
					System.out.println("Articulo creado: " + otro.getNombre() + " " + otro.getPrecio());
					break;
				default:
					System.out.println("No se ha podido crear el articulo");
					break;
			}

			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setTitle("Artículo creado");
			alert.setHeaderText("Artículo creado con éxito");
			alert.setContentText(
					"Se ha creado el articulo en la categoría " + categoria + ":\n" + nombre + " con un precio de " + precio + "€\n\n" +
					"¿Deseas crear otro articulo?"
			);
			ButtonType btnSi = new ButtonType("Si");
			ButtonType btnNo = new ButtonType("No");
			alert.getButtonTypes().setAll(btnNo, btnSi);
			if(alert.showAndWait().get() == btnSi) {
				txtNombre.clear();
				txtPrecio.clear();
				cboxCat.getSelectionModel().clearSelection();
				cboxCat.setPromptText("Selecciona una categoría");
				txtNombre.requestFocus();
			}else{
				anchorCreateItem.getScene().getWindow().hide();
			}
		} else {
			Alert alert = getAlert();
			alert.showAndWait();
		}
    }

	private static Alert getAlert(){
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("Datos incorrectos");
		alert.setHeaderText("Por favor, introduce todos los datos correctamente");
		alert.setContentText(
				"""
						Parece que algunos de los datos no están correctamente introducidos
						Si no sabes a qué se debe, puedes revisar estos puntos:
						· ¿El nombre está vacío?
						· ¿El precio tiene el formato correcto? (Sin símbolos)
						· ¿Has seleccionado una categoría para el producto?"""
		);
		return alert;
	}

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle){
		unfocus();
		cboxCat.getItems().addAll(Categorias.values());
		cboxCat.getItems().removeFirst();
	}

	private void unfocus(){
		txtNombre.setFocusTraversable(false);
		txtPrecio.setFocusTraversable(false);
		cboxCat.setFocusTraversable(false);
		btnCancelar.setFocusTraversable(false);
		btnCreateItem.setFocusTraversable(false);
		imgLogo.setFocusTraversable(false);
	}
}
